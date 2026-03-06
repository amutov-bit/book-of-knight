# Refactor Changelog

## Scope

Migration toward studio-grade TypeScript architecture with Vite tooling while preserving gameplay behavior.

## Key Additions

- App composition root (`src/app/*`).
- Core infrastructure (`src/core/assets`, `src/core/events`, `src/core/time`, `src/core/utils`).
- Engine/game scaffolding layers (`src/engine/*`, `src/game/*`).
- Typed domain/event definitions (`src/types/*`).

## Toolchain Changes

- Build/dev moved to Vite.
- Source converted to TypeScript under `src/`.
- Node-side tests run via `tsx`.

## Compatibility

- Legacy gameplay/UI modules remain behavior-compatible.
- Adapter paths in `src/architecture/*` preserved.
- Mock server and localization scripts unchanged in behavior.

## 2026-03 Maintainability Pass

- Extracted runtime wiring from `src/app/App.ts` into `src/app/wireGameModules.ts`.
- Added app runtime composition types in `src/app/types.ts`.
- Simplified `App.ts` responsibilities to boot/lifecycle/tick orchestration.
- Added onboarding and maintenance docs:
  - `docs/START_HERE.md`
  - `docs/CODEMAP.md`
  - `docs/FEATURE_WORKFLOW.md`
  - `src/README.md`
- Replaced outdated documentation references to old JS/Gulp flow with current TS/Vite architecture.

For detailed migration notes, see `docs/MIGRATION_CHANGELOG.md`.
