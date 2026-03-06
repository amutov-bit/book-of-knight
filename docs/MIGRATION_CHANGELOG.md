# Migration Changelog (Gulp/Browserify -> Vite/TypeScript)

## Replaced Toolchain

Removed legacy build stack usage:

- Gulp
- Browserify/Babelify/Envify
- Vinyl/Gulp stream processing
- HTML script tag injection for PIXI

Introduced:

- Vite (`vite.config.ts`)
- TypeScript (`tsconfig.json`)
- `tsx` for running tests against TS sources

## Entry and Runtime

- Entry changed to Vite HTML entry:
  - `index.html` -> `<script type="module" src="/src/app.ts">`
- Source entry converted:
  - `src/app.js` -> `src/app.ts`

## Variant Handling

- Desktop/mobile scripts preserved as UX:
  - `npm run dev:desktop`
  - `npm run dev:mobile`
  - `npm run build:desktop`
  - `npm run build:mobile`
- Variant now flows through Vite env:
  - `VITE_VARIANT`
  - `__VARIANT__` define

## Assets Pipeline

- Added `scripts/prepare-assets.mjs`.
- Before dev/build, assets are overlaid to `public/assets`:
  1. `assets/common`
  2. `assets/<variant>` (override)
- Keeps existing manifest URLs (`assets/assets-manifest.*.json`) valid.

## TypeScript Migration

- `src/**/*.js` migrated to `src/**/*.ts`.
- Added typed domain/event layer:
  - `src/types/domain.ts`
  - `src/types/events.ts`
- Added typed event bus:
  - `src/core/events/EventBus.ts`
- Added runtime env typing:
  - `src/vite-env.d.ts`

## Preserved Behavior

- Gameplay flow and UI logic preserved.
- Mock server, localization build, config/smoke tests preserved.

## Validation Commands

- `npm run typecheck`
- `npm run test:config`
- `npm run test:smoke`
- `npm run build:desktop`
- `npm run build:mobile`
