/// <reference types="vite/client" />

declare const __VARIANT__: 'desktop' | 'mobile';

interface ImportMetaEnv {
  readonly VITE_VARIANT?: 'desktop' | 'mobile';
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
