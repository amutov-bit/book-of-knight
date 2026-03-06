# TypeScript Guide

## Scope

- Runtime source code under `src/` is TypeScript.
- Some legacy modules remain `// @ts-nocheck` during migration.
- New or refactored modules must be typed.

## Key Type Files

- `src/types/domain.ts`: domain-level contracts
- `src/types/events.ts`: typed app event payloads
- `src/app/types.ts`: runtime composition (`GameRuntime`, `AppRuntimeServices`)

## Event Typing

- Canonical bus: `src/core/events/EventBus.ts`
- Add new event payload types in `src/types/events.ts` first.

## Runtime Composition Typing

- `src/app/wireGameModules.ts` wires runtime modules.
- `src/app/App.ts` owns lifecycle and frame tick.
- Keep constructor order stable in `wireGameModules` to avoid behavior changes.

## tsconfig Notes

- `noEmit: true` (Vite handles transpilation)
- `allowJs: false`
- `strict` is currently relaxed for migration safety

## Migration Rule

When touching a `@ts-nocheck` file:

1. Add explicit types for changed API surface.
2. Avoid introducing new `any` in new code.
3. Prefer extracting typed helper modules instead of expanding untyped monoliths.

## Required Checks

```bash
npm run typecheck
npm run test:config
npm run test:smoke
```
