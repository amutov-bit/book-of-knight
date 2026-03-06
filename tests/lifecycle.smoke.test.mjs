import assert from 'node:assert/strict';
import LifecycleStateMachine, { LifecycleState } from '../src/architecture/state/LifecycleStateMachine';

const flow = new LifecycleStateMachine();
assert.equal(flow.state, LifecycleState.BOOT);

flow.transition(LifecycleState.PRELOAD);
flow.transition(LifecycleState.INTRO);
flow.transition(LifecycleState.IDLE);
flow.transition(LifecycleState.SPIN);
flow.transition(LifecycleState.RESOLVE);
flow.transition(LifecycleState.WIN_PRESENTATION);
flow.transition(LifecycleState.RETURN);
flow.transition(LifecycleState.IDLE);

assert.equal(flow.state, LifecycleState.IDLE);
console.log('lifecycle smoke: OK');
