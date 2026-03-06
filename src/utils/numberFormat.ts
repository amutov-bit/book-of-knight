// @ts-nocheck
const DEFAULT_NUMBER_PATTERN = '#,###.00';

function isDigitToken(char) {
    return char === '0' || char === '#';
}

function collectSeparators(pattern) {
    const separators = [];
    for (let i = 0; i < pattern.length; i++) {
        const char = pattern[i];
        if (!isDigitToken(char)) {
            separators.push({ char, index: i });
        }
    }
    return separators;
}

function collectSeparatorChars(pattern) {
    return [...pattern].filter((char) => !isDigitToken(char));
}

function parsePattern(pattern) {
    const raw = typeof pattern === 'string' ? pattern : '';
    const normalized = raw.length > 0 ? raw : DEFAULT_NUMBER_PATTERN;

    const separators = collectSeparators(normalized);

    let decimalSeparator = '.';
    let groupSeparator = ',';
    let fractionDigits = 2;
    let useGrouping = false;

    if (separators.length === 0) {
        fractionDigits = 0;
        useGrouping = false;
    } else {
        const lastSeparator = separators[separators.length - 1];
        const fractionPart = normalized.slice(lastSeparator.index + 1);
        const hasOnlyDigitTokens = fractionPart.length > 0 && [...fractionPart].every(isDigitToken);

        const onlyOneSeparator = separators.length === 1;
        const looksLikeGroupingOnly = onlyOneSeparator
            && fractionPart.length === 3
            && fractionPart.includes('#');

        if (hasOnlyDigitTokens && !looksLikeGroupingOnly) {
            const integerPart = normalized.slice(0, lastSeparator.index);
            const integerSeparators = collectSeparatorChars(integerPart);

            decimalSeparator = lastSeparator.char;
            fractionDigits = fractionPart.length;
            useGrouping = integerSeparators.length > 0;
            groupSeparator = useGrouping ? integerSeparators[0] : ',';
        } else {
            fractionDigits = 0;
            useGrouping = true;
            groupSeparator = separators[0].char;
        }
    }

    return {
        pattern: normalized,
        decimalSeparator,
        groupSeparator,
        fractionDigits,
        useGrouping
    };
}

export function formatCentsByPattern(valueInCents, pattern) {
    const amount = Number.isFinite(Number(valueInCents)) ? Number(valueInCents) / 100 : 0;
    const parsed = parsePattern(pattern);

    const formattedUs = amount.toLocaleString('en-US', {
        minimumFractionDigits: parsed.fractionDigits,
        maximumFractionDigits: parsed.fractionDigits,
        useGrouping: parsed.useGrouping
    });

    if (parsed.fractionDigits <= 0) {
        return formattedUs.replace(/,/g, parsed.groupSeparator || '');
    }

    const dotIndex = formattedUs.lastIndexOf('.');
    const integerPart = dotIndex >= 0 ? formattedUs.slice(0, dotIndex) : formattedUs;
    const fractionPart = dotIndex >= 0 ? formattedUs.slice(dotIndex + 1) : '';

    const groupedInteger = parsed.useGrouping
        ? integerPart.replace(/,/g, parsed.groupSeparator)
        : integerPart.replace(/,/g, '');

    return `${groupedInteger}${parsed.decimalSeparator}${fractionPart}`;
}

export function getDefaultNumberPattern() {
    return DEFAULT_NUMBER_PATTERN;
}
