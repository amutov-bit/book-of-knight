// @ts-nocheck
import Pool from '../../core/utils/Pool';
import SymbolView from './SymbolView';

/**
 * Symbol view pool. Optional for future transient symbol effects.
 */
export default class SymbolPool {
    /**
     * @param {import('../../core/BaseGame').default} game
     * @param {import('../../core/BaseReel').default} reel
     */
    constructor(game, reel) {
        this.game = game;
        this.reel = reel;
        this.pool = new Pool({
            create: () => new SymbolView(this.game, this.reel, 0),
            reset: (symbol) => {
                symbol.alpha = 1;
                symbol.visible = true;
            },
            maxSize: 64
        });
    }

    acquire(index) {
        const symbol = this.pool.acquire();
        symbol.setIndex(index);
        return symbol;
    }

    release(symbol) {
        this.pool.release(symbol);
    }
}
