# Engineering Guide

## Objective

Maintain a production-grade PixiJS slot codebase that is easy to extend safely.

## Current Stack

- Runtime: PixiJS v8
- Language: TypeScript
- Build: Vite
- Variants: desktop / mobile

## Project Principles

- Keep gameplay behavior deterministic and state-machine driven.
- Keep layout/visual tuning manifest-driven.
- Keep server mapping isolated in network layer.
- Prefer small, explicit modules over implicit cross-module coupling.

## Canonical Runtime Flow

1. `src/app.ts` -> `src/app/bootstrap.ts`
2. `App.init()` bootstraps renderer + localization
3. `wireGameModules()` composes runtime gameplay modules
4. `LoadingScreen` loads manifests/assets
5. `GameplayEngine` and `GameplayStateMachine` drive spin lifecycle

## Layer Responsibilities

- `src/app`: composition/bootstrap only
- `src/core`: shared runtime primitives (renderer shell, base classes, event bus, timers)
- `src/architecture`: orchestration and state lifecycles
- `src/game`: gameplay domain logic
- `src/ui`: view components and menus
- `src/net`: server protocol and transport
- `src/config`: all config resolution/validation

## Configuration Rules

- **Manifest first** for layout, button positions, text style, UI composition.
- Add fallback and validation logic in `src/config/assetsConfig.ts` when introducing new manifest fields.
- Keep sensitive strips out of assets (`src/config/stripsConfig.ts`).

## Gameplay Rules

- Use `GameplayStateMachine` for flow changes (no ad-hoc state toggles from UI/network code).
- Keep `Controller` orchestration thin where possible.
- Ensure force-stop and stop-by-reel logic stays consistent after edits.

## UI Rules

- Menus (`BetMenu`, `AutoPlayMenu`, `ServerErrorModal`) should be self-contained.
- HUD state coordination stays in `Menu.ts`.
- Any visual tweak must be reproducible through manifest values.

## Quality Gate (required)

```bash
npm run typecheck
npm run test:config
npm run test:smoke
```

## Debugging

- Backquote (`) toggles `DebugOverlay`.
- Use `npm run mock:server` for deterministic spin testing.

## Junior Developer Path

1. Read `docs/START_HERE.md`
2. Read `docs/CODEMAP.md`
3. Follow `docs/FEATURE_WORKFLOW.md`
4. Change one layer at a time and run quality gate
