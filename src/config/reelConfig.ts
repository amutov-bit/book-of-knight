import { GAME_RULES } from './gameRules';

function getManifestBranch(manifest, variant, isLandscape) {
    if (!manifest || !manifest.layout || typeof manifest.layout !== 'object') return null;
    const shared = manifest.layout.shared && typeof manifest.layout.shared === 'object'
        ? manifest.layout.shared
        : {};

    let specific = manifest.layout.desktop;
    if (variant === 'mobile') {
        specific = isLandscape ? manifest.layout.mobileLandscape : manifest.layout.mobilePortrait;
    }

    const merged = {
        ...shared,
        ...(specific && typeof specific === 'object' ? specific : {})
    };
    if (shared.baseReel || (specific && specific.baseReel)) {
        merged.baseReel = {
            ...(shared.baseReel && typeof shared.baseReel === 'object' ? shared.baseReel : {}),
            ...(specific && specific.baseReel && typeof specific.baseReel === 'object' ? specific.baseReel : {})
        };
    }
    return merged;
}

function toNumber(value) {
    return Number.isFinite(value) ? value : null;
}

export function getBaseReelConfig(manifest, variant, isLandscape) {
    const branch = getManifestBranch(manifest, variant, isLandscape);
    const manifestBaseReel = branch && branch.baseReel && typeof branch.baseReel === 'object'
        ? branch.baseReel
        : {};
    const orientationKey = variant === 'mobile'
        ? (isLandscape ? 'mobileLandscape' : 'mobilePortrait')
        : 'desktop';

    const config = {
        symbolsVisible: GAME_RULES.SYMBOLS,
        clicksToStop: GAME_RULES.CLICKS_TO_STOP,
        spinStep: toNumber(manifestBaseReel.spinStep),
        pitch: toNumber(manifestBaseReel.pitch),
        symbolWidth: toNumber(manifestBaseReel.symbolWidth),
        symbolHeight: toNumber(manifestBaseReel.symbolHeight),
        trimTopY: toNumber(manifestBaseReel.trimTopY),
        trimBottomY: toNumber(manifestBaseReel.trimBottomY)
    };

    const required = Object.keys(config);
    for (let i = 0; i < required.length; i++) {
        const key = required[i];
        if (config[key] == null) {
            throw new Error(`Missing manifest.layout.${orientationKey}.baseReel.${key}`);
        }
    }

    return config;
}


