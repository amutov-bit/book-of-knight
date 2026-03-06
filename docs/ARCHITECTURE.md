# Architecture

## Goals

- Preserve current slot behavior while improving maintainability.
- Keep architecture readable for mixed senior/junior teams.
- Enforce clear ownership per layer.

## High-Level Structure

```text
src/
  app/            # bootstrap + composition
  core/           # shared runtime building blocks
  architecture/   # lifecycle and gameplay orchestration
  game/           # slot domain logic
  ui/             # rendering and menu components
  net/            # server/ws transport and mapping
  config/         # manifest/config resolvers and constants
  types/          # shared TS contracts
```

## Runtime Lifecycle

`BOOT -> PRELOAD -> INTRO -> IDLE -> SPIN -> RESOLVE -> RETURN -> IDLE`

Managed by:

- `src/architecture/state/LifecycleStateMachine.ts`

Gameplay spin states are managed by:

- `src/architecture/gameplay/GameplayStateMachine.ts`

## App Composition

- `src/app/App.ts` orchestrates app startup and frame loop.
- `src/app/wireGameModules.ts` wires game modules in a single place.
- `src/app/createTicker.ts` is the single ticker owner.

## Eventing

- Canonical event bus: `src/core/events/EventBus.ts`
- Compatibility re-export: `src/architecture/events/EventBus.ts`

## Config and Assets

- Manifests: `assets/desktop/assets-manifest.*.json`
- Resolver/validation: `src/config/assetsConfig.ts`
- Sensitive strips: `src/config/stripsConfig.ts`

## Gameplay Ownership

- `Controller.ts`: high-level coordinator
- `Reels.ts` + `BaseReel.ts`: reel movement and stop behavior
- `Menu.ts`: HUD states, start/stop/autoplay controls
- `GsLink.ts`: server session and outcome protocol

## Quality and Safety

Mandatory before merge:

```bash
npm run typecheck
npm run test:config
npm run test:smoke
```

## Current Technical Debt (Known)

- Several legacy modules still use `// @ts-nocheck`.
- There are compatibility adapter paths kept for migration safety.
- Next cleanup step: strict typing of UI/gameplay hot paths (`Menu`, `Reels`, `GsLink`).
