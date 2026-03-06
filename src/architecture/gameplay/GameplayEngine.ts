import { GameplayEvent, GameplayState } from './GameplayStateMachine';
import { LifecycleState } from '../state/LifecycleStateMachine';
import type BaseGame from '../../core/BaseGame';
import type EventBus from '../events/EventBus';
import type Controller from '../../game/Controller';
import type GsLink from '../../net/GsLink';
import type LifecycleStateMachine from '../state/LifecycleStateMachine';

const STATE_TO_LIFECYCLE = new Map<string, string>([
  [GameplayState.IDLE.title, LifecycleState.IDLE],
  [GameplayState.START_SPIN.title, LifecycleState.SPIN],
  [GameplayState.REELS_SPINNING.title, LifecycleState.SPIN],
  [GameplayState.REELS_STOPPING.title, LifecycleState.RESOLVE],
  [GameplayState.REELS_STOPPED.title, LifecycleState.RESOLVE],
  [GameplayState.SHOW_WINS.title, LifecycleState.WIN_PRESENTATION],
  [GameplayState.SHOW_ALL_WINNING_LINES.title, LifecycleState.WIN_PRESENTATION],
  [GameplayState.SHOW_LAST_WINS.title, LifecycleState.WIN_PRESENTATION],
  [GameplayState.SPIN_END.title, LifecycleState.RETURN],
  [GameplayState.TAKE_WINS.title, LifecycleState.RETURN],
  [GameplayState.WIN_TO_CREDIT.title, LifecycleState.RETURN]
]);

export default class GameplayEngine {
  private game: BaseGame;
  private bus: EventBus;
  private controller: Controller | null = null;
  private gsLink: GsLink | null = null;
  private flow: LifecycleStateMachine | null = null;
  private spinInFlight = false;
  private isWired = false;

  constructor(game: BaseGame, bus: EventBus) {
    this.game = game;
    this.bus = bus;
  }

  attachController(controller: Controller): void {
    this.controller = controller;
  }

  attachGsLink(gsLink: GsLink): void {
    this.gsLink = gsLink;
  }

  attachFlow(flow: LifecycleStateMachine): void {
    this.flow = flow;
  }

  wire(): void {
    if (this.isWired || !this.controller || !this.gsLink) return;
    this.isWired = true;

    this.wrapControllerStateChange();
    this.wrapStartSpin();
    this.wrapSpinResult();
  }

  update(delta: number): void {
    if (!this.controller) return;
    this.controller.update(delta);
  }

  requestSpin(): boolean {
    if (!this.controller) return false;
    this.controller.event = GameplayEvent.START;
    return true;
  }

  getStateTitle(): string {
    if (!this.controller || !this.controller.state) return 'UNKNOWN';
    return this.controller.state.title;
  }

  private wrapControllerStateChange(): void {
    if (!this.controller) return;
    const original = this.controller.setNextState.bind(this.controller);
    this.controller.setNextState = (nextState: any): void => {
      const before = this.controller && this.controller.state && this.controller.state.title ? this.controller.state.title : 'UNKNOWN';
      original(nextState);
      const after = nextState && nextState.title ? nextState.title : 'UNKNOWN';
      (this.bus as any).emit('controller:stateChanged', { from: before, to: after });
      this.syncLifecycle(after);
    };
  }

  private wrapStartSpin(): void {
    if (!this.controller) return;
    const originalStartSpin = this.controller.startSpin.bind(this.controller);
    this.controller.startSpin = (): boolean => {
      (this.bus as any).emit('spin:requested', { turbo: false, quick: false });
      const ok = originalStartSpin();
      if (ok) {
        this.spinInFlight = true;
        this.syncLifecycle(GameplayState.REELS_SPINNING.title);
      }
      return ok;
    };
  }

  private wrapSpinResult(): void {
    if (!this.gsLink) return;
    const originalOnSpin = this.gsLink.onSpin.bind(this.gsLink);
    this.gsLink.onSpin = (outcome: any): void => {
      originalOnSpin(outcome);
      (this.bus as any).emit('spin:resultReceived', {
        result: {
          reelStops: Array.isArray(outcome && outcome.matrix) ? outcome.matrix : [],
          totalWin: Number.isFinite(Number(outcome && outcome.win)) ? Number(outcome && outcome.win) : 0,
          hasBonus: false
        }
      });
      this.syncLifecycle(GameplayState.REELS_STOPPING.title);
    };
  }

  private syncLifecycle(controllerStateTitle: string): void {
    const mapped = STATE_TO_LIFECYCLE.get(controllerStateTitle);
    if (!mapped) return;
    this.transitionLifecycle(mapped);
    if (mapped === LifecycleState.IDLE && this.spinInFlight) {
      this.spinInFlight = false;
      (this.bus as any).emit('spin:resolved', { result: null });
    }
  }

  private transitionLifecycle(target: string): void {
    if (!this.flow) return;
    if ((this.flow as any).state === target) return;
    if ((this.flow as any).canTransition(target)) {
      const payload = (this.flow as any).transition(target);
      (this.bus as any).emit('lifecycle:changed', payload);
      return;
    }

    if (target === LifecycleState.IDLE && (this.flow as any).state !== LifecycleState.IDLE) {
      if ((this.flow as any).canTransition(LifecycleState.RETURN)) {
        const ret = (this.flow as any).transition(LifecycleState.RETURN);
        (this.bus as any).emit('lifecycle:changed', ret);
      }
      if ((this.flow as any).canTransition(LifecycleState.IDLE)) {
        const idle = (this.flow as any).transition(LifecycleState.IDLE);
        (this.bus as any).emit('lifecycle:changed', idle);
      }
    }
  }
}
