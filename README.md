# Book of Knight (PixiJS + TypeScript + Vite)

Browser slot runtime built on PixiJS. The `src/app` layer boots renderer, localization, and gameplay modules; `src/config` resolves runtime variant and asset-manifest driven configuration.

## Requirements

- Node.js 20+
- npm 10+

## Install

```bash
npm install
```

## Development

Desktop:

```bash
npm run dev:desktop
```

Mobile:

```bash
npm run dev:mobile
```

Notes:

- `scripts/prepare-assets.mjs` prepares `public/assets` before dev/build.
- Variant is passed via `VARIANT` and `VITE_VARIANT`.

## Runtime Params

- `VITE_VARIANT=desktop|mobile`: selects which assets manifest and layout branch should be loaded at build/dev time.
- `__VARIANT__`: legacy runtime fallback used when `VITE_VARIANT` is not present.
- `?lang=ENG|BG|ESP|...`: overrides the locale resolved during app bootstrap. Two-letter locale values also work.
- `?debugOverlay=1`: shows the in-game debug overlay after boot.

## Boot Flow

1. `src/app/bootstrap.ts` creates `App`, runs `init()`, then starts the RAF ticker.
2. `src/app/App.ts` creates the renderer, resolves locale, wires gameplay/runtime modules, and moves lifecycle state from `preload` to `idle`.
3. `src/config/assetsConfig.ts` loads and validates the variant-specific assets manifest consumed by UI/layout/gameplay config readers.
4. `src/app/wireGameModules.ts` attaches game modules in constructor order and connects `GameplayEngine` to controller, gs-link, and lifecycle flow.

## Build

```bash
npm run build:desktop
npm run build:mobile
```

Outputs:

- `dist/prod/desktop`
- `dist/prod/mobile`

## Quality Gate

```bash
npm run typecheck
npm run test:config
npm run test:smoke
```

## Utility Scripts

```bash
npm run mock:server
npm run localization:build
```

## Quick Navigation

- Start onboarding: `docs/START_HERE.md`
- Module map: `docs/CODEMAP.md`
- Feature workflow: `docs/FEATURE_WORKFLOW.md`
- Architecture: `docs/ARCHITECTURE.md`
- Team engineering guide: `docs/ENGINEERING_GUIDE.md`
- Rendering/perf notes: `docs/RENDERING_PERFORMANCE.md`
- Assets pipeline: `docs/ASSETS_PIPELINE.md`
- TS conventions: `docs/TYPESCRIPT.md`

## Fast Where To Edit

- Reel spin behavior: `src/core/BaseReel.ts`, `src/game/Reels.ts`
- Spin state flow: `src/architecture/gameplay/GameplayStateMachine.ts`
- HUD buttons/menus: `src/ui/Menu.ts`, `src/ui/BetMenu.ts`, `src/ui/AutoPlayMenu.ts`
- Server protocol/outcome mapping: `src/net/GsLink.ts`, `src/game/GameOutcome.ts`
- Layout/colors/positions: `assets/desktop/assets-manifest.desktop.json`
