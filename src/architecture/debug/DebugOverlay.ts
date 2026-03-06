// @ts-nocheck
import { getTextureCache } from '../../core/RuntimeContext';

export default class DebugOverlay extends PIXI.Container {
    constructor() {
        super();
        this.visible = false;
        this.frameCount = 0;
        this.elapsed = 0;
        this.lastState = 'boot';

        this.label = new PIXI.Text({
            text: 'FPS: 0',
            style: {
                fontFamily: 'monospace',
                fontSize: 14,
                fill: 0x00ff88
            }
        });
        this.label.position.set(12, 12);
        this.addChild(this.label);
    }

    toggle() {
        this.visible = !this.visible;
    }

    /**
     * @param {number} dt
     * @param {string} lifecycleState
     * @param {import('pixi.js').Renderer} renderer
     */
    update(dt, lifecycleState, renderer) {
        if (!this.visible) return;
        this.frameCount += 1;
        this.elapsed += dt || 0;
        this.lastState = lifecycleState || this.lastState;

        if (this.elapsed < 500) return;

        const fps = (this.frameCount * 1000) / this.elapsed;
        const heap = performance && performance.memory && performance.memory.usedJSHeapSize
            ? (performance.memory.usedJSHeapSize / (1024 * 1024)).toFixed(1)
            : 'n/a';

        const textureCount = Object.keys(getTextureCache() || {}).length;
        const drawCalls = this.getDrawCalls(renderer);

        this.label.text = `FPS:${fps.toFixed(1)} DRAW:${drawCalls} TEX:${textureCount} HEAP(MB):${heap} STATE:${this.lastState}`;
        this.frameCount = 0;
        this.elapsed = 0;
    }

    getDrawCalls(renderer) {
        if (!renderer) return 'n/a';
        if (renderer.stats && Number.isFinite(renderer.stats.drawCalls)) {
            return String(renderer.stats.drawCalls);
        }
        if (renderer.renderPipes && renderer.renderPipes.batch && Number.isFinite(renderer.renderPipes.batch.drawCount)) {
            return String(renderer.renderPipes.batch.drawCount);
        }
        if (renderer.runners && renderer.runners.render && Number.isFinite(renderer.runners.render.items)) {
            return String(renderer.runners.render.items);
        }
        return 'n/a';
    }
}
