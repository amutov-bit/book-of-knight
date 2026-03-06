# Reels Troubleshooting

## Goal

Quick diagnostics for reel rendering and stop-flow issues without touching unrelated systems.

## 1) Reel Stops Crooked (One Reel Lower/Higher)

Check in this order:

1. `src/core/BaseReel.js` -> `alignToGrid()`
2. `src/core/BaseReel.js` -> `spinFixedStep()` wrap logic (`nextY >= WRAP_Y`)
3. `assets/desktop/assets-manifest.desktop.json` -> `layout.*.baseReel.pitch`
4. `assets/desktop/assets-manifest.desktop.json` -> `layout.*.reels.y`

Expected:

- All reels use same `pitch`.
- Final stop always calls `alignToGrid()`.
- No custom per-reel Y offset unless intentional.

## 2) Trim Works Outside Spin (Should Affect Spin Only)

Relevant file:

- `src/core/BaseReel.js`

Expected behavior:

- On spin start: use spin mask (`this.reelMaskSpin`).
- On stop/bounce/idle: return to normal idle trim (`trim(null)` in current implementation).

If idle visuals move after spin trim changes:

- Verify state transitions in `REEL_STOP`, `REEL_SKILL_STOP`, `REEL_BOUNCE` restore idle trim.

## 3) Symbol Offsets Not Applied

Relevant files:

- `assets/desktop/assets-manifest.desktop.json` -> `symbols.frames[].offsetX/offsetY`
- `src/config/assetsConfig.js`
- `src/ui/screens/LoadingScreen.js`
- `src/game/ReelSymbol.js`

Checklist:

1. Manifest entry has exact `prefix` that matches symbol mapping.
2. `offsetX/offsetY` are numbers.
3. Loader populates `game.textures.symbolOffsets`.
4. `ReelSymbol` applies visual position from logical position + offset.

## 4) Spin Not Visible, Only Final Matrix Appears

Relevant files:

- `src/game/Reels.js`
- `src/architecture/gameplay/GameplayStateMachine.js`
- `src/core/BaseReel.js`

Checklist:

1. Reels actually start (`startSpin()` called for all reels).
2. Stop timing is not immediate (`stopReel` sequence should be staggered by state machine or timer).
3. `STEP` and `pitch` values are sane for current symbol sizes.

## 5) Wrong Symbols Displayed vs Expected Strip

Relevant files:

- `src/config/stripsConfig.js` (source of sensitive strips)
- `src/config/assetsConfig.js` (`getReelStripsConfig`)
- `src/game/Reels.js` (`setStripMode`, `updateStopSymbols`)
- `src/net/GsLink.js` / `src/game/GameOutcome.js` (stop matrix source)

Checklist:

1. Active strip mode is correct (`normal` / `free` / `holdAndWin`).
2. Strip arrays align with symbol index mapping.
3. Forced symbol index (if enabled) is not overriding strips.

## 6) Blur Frames Look Wrong During Spin

Relevant files:

- `src/core/BaseReel.js` (`applySpinBlurRamp`, `applyBounceBlurRamp`, `debounce`)
- `src/game/ReelSymbol.js` (`setBlurFrame`)
- Manifest `symbols.frames` order and atlas content

Checklist:

1. Each symbol has blur frames available in atlas.
2. Frame count supports used blur indices.
3. Bounce returns to blur frame `0` at idle.

## Safe Debug Procedure

1. Change one variable only.
2. Rebuild and test 10+ spins.
3. If issue persists, log one reel's `travel`, `spriteOffset`, and first 3 visible symbol logical Y values on stop.
4. Revert noisy debug logs after fix.

## High-Risk Files

- `src/core/BaseReel.js` (movement physics + stop snap)
- `src/game/Reels.js` (orchestration and stop command ordering)
- `src/architecture/gameplay/GameplayStateMachine.js` (stop timing)

Treat edits here as behavior-critical and verify with repeated spin tests.
