import App from './App';

/**
 * Browser entry point used by `src/app.ts`.
 *
 * The returned instance is already initialized and ticking.
 */
export async function bootstrap(): Promise<App> {
  const app = new App();
  await app.init();
  app.start();
  return app;
}

export default bootstrap;
