import assert from 'node:assert/strict';
import {
    validateAssetsManifest,
    getReelsLayoutConfig,
    getSymbolFrameDefsByIndex
} from '../src/config/assetsConfig';

function createValidDesktopManifest() {
    return {
        logo: 'assets/logo.png',
        backgrounds: { base: 'assets/bg.jpg' },
        layout: {
            desktop: {
                baseReel: {
                    symbolsVisible: 3,
                    clicksToStop: 5,
                    spinStep: 43,
                    pitch: 272,
                    symbolWidth: 278,
                    symbolHeight: 264,
                    trimTopY: 0,
                    trimBottomY: 0
                },
                reels: {
                    count: 5,
                    spacing: 162,
                    x: 105,
                    y: 65,
                    width: 162,
                    height: 440,
                    strip: [1, 5, 6, 4, 5]
                },
                layers: {
                    reelsBgX: 0,
                    reelsBgY: 0,
                    titleX: 0,
                    titleY: 0
                }
            }
        }
    };
}

function createValidMobileManifest() {
    const base = createValidDesktopManifest();
    return {
        ...base,
        backgrounds: {
            landscape: { base: 'assets/bg-land.jpg' },
            portrait: { base: 'assets/bg-port.jpg' }
        },
        layout: {
            mobileLandscape: base.layout.desktop,
            mobilePortrait: base.layout.desktop
        }
    };
}

function run() {
    {
        const manifest = createValidDesktopManifest();
        const errors = validateAssetsManifest(manifest, 'desktop');
        assert.equal(errors.length, 0, `Expected no desktop validation errors, got: ${errors.join(', ')}`);
    }

    {
        const manifest = createValidDesktopManifest();
        delete manifest.layout.desktop.baseReel.spinStep;
        const errors = validateAssetsManifest(manifest, 'desktop');
        assert.ok(errors.some((entry) => entry.includes('baseReel.spinStep')), 'Missing baseReel.spinStep should fail validation');
    }

    {
        const manifest = createValidMobileManifest();
        delete manifest.layout.mobilePortrait;
        const errors = validateAssetsManifest(manifest, 'mobile');
        assert.ok(errors.some((entry) => entry.includes('layout.mobilePortrait')), 'Missing mobilePortrait layout should fail validation');
    }

    {
        const manifest = createValidDesktopManifest();
        const layout = getReelsLayoutConfig(manifest, 'desktop', true);
        assert.equal(layout.reels.count, 5);
        assert.equal(layout.reels.spacing, 162);
        assert.equal(layout.reels.x, 105);
        assert.equal(layout.reels.y, 65);
    }

    {
        const manifest = createValidMobileManifest();
        manifest.layout.mobilePortrait.reels.x = 77;
        const mobilePortraitLayout = getReelsLayoutConfig(manifest, 'mobile', false);
        assert.equal(mobilePortraitLayout.reels.x, 77);
    }

    {
        const manifest = createValidDesktopManifest();
        manifest.symbols = {
            frames: [{ prefix: 'A_blur_', atlas: 'symbols-0.json', offsetX: 5, offsetY: -7 }]
        };
        const defs = getSymbolFrameDefsByIndex(manifest);
        const aDef = defs.find((d) => d.prefix === 'A_blur_');
        assert.ok(aDef, 'A_blur_ symbol def should exist');
        assert.equal(aDef.offsetX, 5);
        assert.equal(aDef.offsetY, -7);
    }

    {
        const manifest = createValidDesktopManifest();
        manifest.symbols = {
            frames: [{ prefix: 'A_blur_', atlas: 'symbols-0.json', offsetX: 'bad' }]
        };
        const errors = validateAssetsManifest(manifest, 'desktop');
        assert.ok(errors.some((entry) => entry.includes('symbols.frames[0].offsetX')), 'Invalid symbols.frames offsetX should fail validation');
    }

    console.log('assetsConfig tests: OK');
}

run();
