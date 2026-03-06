# Start Here (Team Onboarding)

## 1) Run the project

```bash
npm install
npm run dev:desktop
```

For mobile variant:

```bash
npm run dev:mobile
```

## 2) Validate before every push

```bash
npm run typecheck
npm run test:config
npm run test:smoke
```

## 3) Read in this order

1. `docs/ARCHITECTURE.md`
2. `docs/CODEMAP.md`
3. `docs/FEATURE_WORKFLOW.md`
4. `docs/ASSETS_PIPELINE.md`

## 4) Golden rules

- Do not hardcode positions/sizes in gameplay classes if they belong to layout.
- Keep visual tuning in manifests (`assets/desktop/assets-manifest.*.json`).
- Keep server protocol mapping inside `src/net/GsLink.ts` and outcome mapping inside `src/game/GameOutcome.ts`.
- Do not bypass `GameplayStateMachine` for spin lifecycle changes.

## 5) Where to edit common tasks

- Reel speed/geometry: `assets/desktop/assets-manifest.desktop.json` -> `layout.shared.baseReel`
- Reel strips: `src/config/stripsConfig.ts`
- Bet/Autoplay menu UI: `src/ui/BetMenu.ts`, `src/ui/AutoPlayMenu.ts`, plus `ui.hud` config in manifest
- Main HUD buttons/meters: `src/ui/Menu.ts` + `ui.hud.buttons/texts`
- Server connection/protocol: `src/net/GsLink.ts`
- Localization text source: `assets/common/localization/translations.json`

## 6) Debug helpers

- Press backquote (`) to toggle debug overlay.
- Use `npm run mock:server` for local deterministic spin testing.
