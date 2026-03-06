// @ts-nocheck
/**
 * Immutable reel strip model.
 */
export default class ReelStrip {
    /**
     * @param {number[]} symbols
     */
    constructor(symbols) {
        this.symbols = Array.isArray(symbols) ? symbols.slice() : [];
    }

    at(index) {
        if (this.symbols.length === 0) return 0;
        const safe = ((index % this.symbols.length) + this.symbols.length) % this.symbols.length;
        return this.symbols[safe];
    }

    size() {
        return this.symbols.length;
    }
}
