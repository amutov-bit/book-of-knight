import { Assets, type Texture } from 'pixi.js';
import AtlasRegistry from './AtlasRegistry';
import { warn } from '../utils/logger';

interface AssetResource {
  texture?: Texture;
  textures?: Record<string, Texture>;
  source?: unknown;
  data?: unknown;
}

/**
 * Centralized Pixi Assets loader and texture registration.
 */
export default class AssetManager {
  private textureCache: Record<string, Texture>;
  private registry: AtlasRegistry;

  constructor(options: { textureCache?: Record<string, Texture> } = {}) {
    this.textureCache = options.textureCache || {};
    this.registry = new AtlasRegistry();
  }

  async loadAll(assetPaths: string[], onProgress?: (progress: number, assetPath: string) => void): Promise<Record<string, AssetResource>> {
    const list = Array.isArray(assetPaths) ? assetPaths : [];
    const resources: Record<string, AssetResource> = {};
    if (list.length === 0) return resources;

    let loaded = 0;
    for (let i = 0; i < list.length; i++) {
      const assetPath = list[i];
      try {
        const asset = await Assets.load(assetPath);
        loaded += 1;
        if (typeof onProgress === 'function') {
          onProgress((loaded / list.length) * 100, assetPath);
        }
        resources[assetPath] = this.normalize(asset as AssetResource);
        this.register(assetPath, resources[assetPath]);
      } catch (_error) {
        warn(`AssetManager::skipMissingAsset ${assetPath}`);
      }
    }

    return resources;
  }

  private normalize(asset: AssetResource): AssetResource {
    if (asset && asset.texture) return asset;
    if (asset && asset.source) return { texture: asset as unknown as Texture };
    if (asset && asset.textures) return asset;
    return { data: asset };
  }

  register(assetPath: string, resource: AssetResource): void {
    if (!resource) return;

    if (resource.texture) {
      this.textureCache[assetPath] = resource.texture;
    }

    if (resource.textures && typeof resource.textures === 'object') {
      this.registry.registerAtlas(assetPath, resource.textures);
      Object.assign(this.textureCache, resource.textures);
    }
  }

  getTexture(frameName: string): Texture | null {
    return this.registry.get(frameName) || this.textureCache[frameName] || null;
  }

  getAtlasRegistry(): AtlasRegistry {
    return this.registry;
  }
}
