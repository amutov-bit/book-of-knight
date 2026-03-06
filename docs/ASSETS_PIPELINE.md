# Assets Pipeline (TexturePacker)

## Atlas naming

- Keep atlas json/png pairs grouped by domain:
  - `symbols-*.json` for symbol strips
  - `interface-*.json`, `menu_buttons-*.json` for UI

## Frame naming conventions

- Button states:
  - `_001` -> idle
  - `_002` -> pressed
  - `_deact` -> disabled
  - `_hover` -> hover

- Symbol frames:
  - `prefix + 2-digit/3-digit frame + .png`
  - Example: `torch_blur_01.png`, `torch_blur_001.png`

## HUD Button Transition Config (Manifest)

Each HUD button can override transition timing directly in manifest (`ui.hud.buttons.*`):

- `enableFadeFrames`: transition duration in 60fps frames.
- `enableFadeFromAlpha`: starting alpha when enabling (0..1).

Example:

```json
"start": {
  "base": "button_start",
  "x": 1616,
  "y": 368,
  "width": 300,
  "height": 300,
  "enableFadeFrames": 12,
  "enableFadeFromAlpha": 0.55
}
```
## TexturePacker recommendations

- Padding: 2-4 px
- Extrude: 1-2 px
- Trim: enabled for UI, validated for symbols
- Multipack: enable for large symbol sets
- Power-of-two sheets where possible for GPU stability

## Manifest ownership

- Per-variant manifests:
  - `assets/desktop/assets-manifest.desktop.json`
  - `assets/desktop/assets-manifest.mobile.json`
- Shared non-visual data should stay in config modules when sensitive.

## Runtime loading

- `LoadingScreen` resolves manifest list and delegates to `AssetManager`.
- `AssetManager` registers atlas frame maps in `AtlasRegistry`.
- `Textures.findFrames` uses registry to reduce lookup cost.
