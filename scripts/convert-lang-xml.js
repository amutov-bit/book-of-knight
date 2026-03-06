const fs = require('fs');
const path = require('path');

const DEFAULT_INPUT = path.resolve(__dirname, '../old-java-project/texts/lang.xml');
const DEFAULT_OUTPUT = path.resolve(__dirname, '../assets/common/localization/translations.json');

function decodeXmlEntities(text) {
    return text
        .replace(/&lt;/g, '<')
        .replace(/&gt;/g, '>')
        .replace(/&quot;/g, '"')
        .replace(/&apos;/g, "'")
        .replace(/&amp;/g, '&');
}

function dedentMultiline(text) {
    const lines = text.split('\n');
    while (lines.length > 0 && lines[0].trim() === '') lines.shift();
    while (lines.length > 0 && lines[lines.length - 1].trim() === '') lines.pop();

    let minIndent = Infinity;
    for (const line of lines) {
        if (!line.trim()) continue;
        const leading = line.match(/^\s*/)[0].length;
        if (leading < minIndent) minIndent = leading;
    }

    if (!Number.isFinite(minIndent) || minIndent === 0) return lines.join('\n');
    return lines.map((line) => (line.trim() ? line.slice(minIndent) : '')).join('\n');
}

function normalizeText(raw) {
    if (raw == null) return '';
    const normalizedNewlines = String(raw).replace(/\r\n/g, '\n').replace(/\r/g, '\n');
    const decoded = decodeXmlEntities(normalizedNewlines);
    const dedented = dedentMultiline(decoded);
    return dedented.replace(/\\n/g, '\n').trim();
}

function normalizeLocaleCode(code) {
    const value = String(code || '').trim().toLowerCase();
    const map = {
        eng: 'en',
        rus: 'ru',
        cze: 'cs',
        tur: 'tr',
        fra: 'fr',
        por: 'pt',
        esp: 'es',
        spa: 'es',
        bul: 'bg',
        gr: 'el',
        fr: 'fr',
        fre: 'fr',
        pol: 'pl',
        cro: 'hr'
    };
    return map[value] || value;
}

function extractLanguages(xml) {
    const result = [];
    const re = /<language\s+name="([^"]+)"\s*>([\s\S]*?)<\/language>/g;
    let m;
    while ((m = re.exec(xml)) !== null) {
        result.push({ name: m[1], body: m[2] });
    }
    return result;
}

function extractTags(languageBody) {
    const tags = {};
    const re = /<([A-Za-z0-9_:-]+)>([\s\S]*?)<\/([A-Za-z0-9_:-]+)>/g;
    let m;
    while ((m = re.exec(languageBody)) !== null) {
        const openTag = m[1];
        const rawValue = m[2];
        const value = normalizeText(rawValue);
        if (value.length === 0) continue;
        tags[openTag] = value;
    }
    return tags;
}

function mergeTextParts(targetMap, mergedKey, keys) {
    const parts = [];
    for (const key of keys) {
        const value = targetMap[key];
        if (typeof value === 'string' && value.trim()) {
            parts.push(value.trim());
        }
    }
    if (parts.length > 0) {
        targetMap[mergedKey] = parts.join('\n\n');
    }
}

function buildTranslations(xml, sourceFile) {
    const languages = extractLanguages(xml);
    const locales = {};

    for (const language of languages) {
        const tags = extractTags(language.body);
        const localePath = tags.path || language.name;
        const locale = normalizeLocaleCode(localePath);

        delete tags.path;

        mergeTextParts(tags, 'splashBody', ['splashTxt', 'splashTxt2', 'splashTxt3']);
        mergeTextParts(tags, 'splashSecondaryBody', ['splashSecTxt', 'splashSecTxt2', 'splashSecTxt3']);
        mergeTextParts(tags, 'splashPortBody', ['splashTxtPort', 'splashTxt2Port']);

        if (!locales[locale]) {
            locales[locale] = tags;
        } else {
            const existing = locales[locale];
            for (const [key, value] of Object.entries(tags)) {
                if (!Object.prototype.hasOwnProperty.call(existing, key)) {
                    existing[key] = value;
                }
            }
        }
    }

    return {
        meta: {
            format: 'pixi-localization-v1',
            source: sourceFile,
            generatedAt: new Date().toISOString(),
            fallbackLocale: 'en'
        },
        locales
    };
}

function main() {
    const inputFile = process.argv[2] ? path.resolve(process.argv[2]) : DEFAULT_INPUT;
    const outputFile = process.argv[3] ? path.resolve(process.argv[3]) : DEFAULT_OUTPUT;

    const xml = fs.readFileSync(inputFile, 'utf8');
    const translations = buildTranslations(xml, path.relative(process.cwd(), inputFile));

    fs.mkdirSync(path.dirname(outputFile), { recursive: true });
    fs.writeFileSync(outputFile, `${JSON.stringify(translations, null, 2)}\n`, 'utf8');

    const localeCount = Object.keys(translations.locales).length;
    console.log(`Generated ${outputFile} (${localeCount} locales)`);
}

main();
