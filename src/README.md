# src/ Developer Notes

This folder contains the runtime code.

## Layer order

1. `app` composes modules and starts runtime.
2. `core` provides shared primitives.
3. `architecture` orchestrates lifecycle/gameplay states.
4. `game` contains slot domain logic.
5. `ui` renders screens and menus.
6. `net` maps server protocol.
7. `config` resolves manifests/constants.

## Rule

If a change can be represented as configuration, prefer config/manifest over hardcoded values.
