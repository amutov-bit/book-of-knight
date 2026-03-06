# Rendering & Performance

## Implemented Optimizations

## 1) Centralized ticker + frame timers

- Single RAF ticker in `src/app/createTicker.js`.
- Frame-based timers in `src/core/time/Timers.js`.
- `App` updates timers each frame (`timers.update(dtMs)`).
- `GsLink` uses ticker timers for delayed login and mock spin callbacks when available.

## 2) Asset loading and atlas registry

- `AssetManager` introduced in `src/core/assets/AssetManager.js`.
- `AtlasRegistry` introduced in `src/core/assets/AtlasRegistry.js`.
- `LoadingScreen` now loads via `AssetManager` and registers atlas frames centrally.
- `Textures.findFrames` uses atlas registry first, fallback to cache scan.

## 3) Object pooling

- Generic `Pool` in `src/core/utils/Pool.js`.
- `GsLink` uses pooled win objects during server outcome mapping to reduce per-spin allocations.

## 4) Stop button behavior and reel force stop

- Stop action now supports force-stop path for immediate reel stop.
- Avoids prolonged reel-by-reel waiting when stop is requested.

## 5) Button transition smoothness

- UI buttons now support configurable enable fade transition:
  - `enableFadeFrames`
  - `enableFadeFromAlpha`
- Uses eased interpolation (smoothstep) for production-like polish.

## 6) Debug overlay improvements

- Overlay shows:
  - FPS
  - draw call estimate (when renderer stats are available)
  - texture count
  - heap usage (when browser supports `performance.memory`)

## Policies

- `cacheAsBitmap`: avoid for spinning reel content.
- Filters: use only when explicitly needed and avoid per-frame filter churn.
- Batching: use atlas-based textures and avoid frequent texture swaps in reel symbols.

## Button fade timing

- Button enable transitions now consume frame delta (deltaSeconds * 60) so enableFadeFrames maps to real frame counts.
- Prevents overlong alpha transitions on variable dt and keeps UX consistent across devices.
