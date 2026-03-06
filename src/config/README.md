# config/

Centralized runtime configuration readers and static defaults.

## What Lives Here

- environment and variant resolution
- assets-manifest loading and validation
- display/layout/gameplay defaults
- static domain constants such as strips and game rules

## Expected Flow

1. Resolve `desktop` or `mobile` via `runtimeConfig.ts`.
2. Load `assets/assets-manifest.<variant>.json`.
3. Read variant/layout-specific branches through config helpers instead of hardcoding values in UI or gameplay modules.

Prefer adding new knobs to manifests or config helpers before introducing inline constants elsewhere.
