# Feature Workflow

A repeatable workflow for adding/changing slot features without regressions.

## 1) Define the change

- What player behavior must change?
- Which state(s) are affected (`IDLE`, `START_SPIN`, `REELS_SPINNING`, ...)?
- Is server payload/schema affected?

## 2) Change the right layer

- Visual-only: manifest + UI layer
- Reel mechanics: `BaseReel` / `Reels`
- Spin flow/timing: `GameplayStateMachine`
- Server data mapping: `GsLink` + `GameOutcome`

## 3) Keep config-driven behavior

- Add new tunables to manifest/config (`src/config/assetsConfig.ts` fallback + validator)
- Avoid new hardcoded pixel/timing values in game logic

## 4) Validate quickly

```bash
npm run typecheck
npm run test:config
npm run test:smoke
```

Then run desktop and mobile manually for visual checks.

## 5) Regression checklist

- Start/stop buttons and autoplay states still correct
- Reel stop order and force stop behavior unchanged
- Meters and balance labels update correctly
- Server disconnect/errors show modal and block gameplay
- Localization keys resolve with fallback text

## 6) PR documentation template

Include in every PR:

- Functional summary
- Files touched by layer (UI/gameplay/network/config)
- Risk assessment
- Manual test steps
- Screenshots/videos for UI changes
