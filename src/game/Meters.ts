// @ts-nocheck
/**
 * Created by Dimitar on 2/21/2017.
 */

export default class Meters {
    constructor () {
        this.MAX_BET_PER_LINE = 1000;
        this.MAX_LINES = 10;
        this.MAX_DENOM = 10;

        this.MIN_BET_PER_LINE = 1;
        this.MIN_LINES = 10;
        this.MIN_DENOM = 10;

        this.betArray = [1,2,3,4,5,10,15,20,25,30,35,40,45,50,55,60,65,70,75,80,85,90,95,100,150,200,250,300,350,400,450,500,550,600,650,700,750,800,850,900,950,1000,1100,1200,1300,1400,1500,1600,1700,1800,1900,2000,3000,4000,8000];
        this.betIndex = 0;

        this.credit = 0;
        this.denomination = 10;
        this.bet = 1;
        this.lines = this.MAX_LINES;
        this.win = 0;
        this.fgwin = 0;
        this.totalBet = 0;

        this.currency = 'FUN';

        this.totalGamesPlayed = 0;
        this.totalCoinsIn = 0;
        this.totalCoinsOut = 0;
        this.totalIn = 0;
        this.totalOut = 0;
        this.minAllowedBetIndex = 0;
        this.maxAllowedBetIndex = 0;

        this.syncBetIndex();
        this.update();
    }

    updateTotals() {
        this.totalGamesPlayed++;
        this.totalCoinsIn += this.totalBet;
        this.totalCoinsOut += this.win;
    }

    selectMaxbet() {
        this.bet = this.MAX_BET_PER_LINE;
        this.lines = this.MAX_LINES;
        this.denomination = this.MAX_DENOM;
        this.syncBetIndex();
        this.update();
    }

    incrementBetPerLine() {
        if (this.betIndex < this.maxAllowedBetIndex) {
            this.betIndex++;
        } else {
            this.betIndex = this.minAllowedBetIndex;
        }

        this.bet = this.betArray[this.betIndex] || this.MIN_BET_PER_LINE;
        this.update();
    }

    decrementBetPerLine() {
        if (this.betIndex > this.minAllowedBetIndex) {
            this.betIndex--;
        } else {
            this.betIndex = this.maxAllowedBetIndex;
        }

        this.bet = this.betArray[this.betIndex] || this.MIN_BET_PER_LINE;
        this.update();
    }

    setBetPerLine(value) {
        const next = Number.isFinite(value) ? parseInt(value, 10) : this.MIN_BET_PER_LINE;
        this.bet = Math.max(this.MIN_BET_PER_LINE, Math.min(this.MAX_BET_PER_LINE, next));
        this.syncBetIndex();
        this.update();
    }

    incrementLines() {
        if(this.lines < this.MAX_LINES) this.lines++;
        this.update();
    }

    decrementLines() {
        if(this.lines > this.MIN_LINES) this.lines--;
        this.update();
    }

    incrementDenomination() {
        if(this.denomination < this.MAX_DENOM) this.denomination++;
        this.win = 0;
        this.update();
    }

    decrementDenomination() {
        if(this.denomination > this.MIN_DENOM) this.denomination--;
        this.win = 0;
        this.update();
    }

    setMinDenom(value) {
        if (!Number.isFinite(value)) return;
        this.MIN_DENOM = Math.max(1, parseInt(value, 10));
        if (this.denomination < this.MIN_DENOM) {
            this.denomination = this.MIN_DENOM;
        }
        this.update();
    }

    setMinBetPerLine(value) {
        if (!Number.isFinite(value)) return;
        this.MIN_BET_PER_LINE = Math.max(1, parseInt(value, 10));
        if (this.bet < this.MIN_BET_PER_LINE) {
            this.bet = this.MIN_BET_PER_LINE;
        }
        this.syncBetIndex();
        this.update();
    }

    setMinLines(value) {
        if (!Number.isFinite(value)) return;
        this.MIN_LINES = Math.max(1, parseInt(value, 10));
        if (this.lines < this.MIN_LINES) {
            this.lines = this.MIN_LINES;
        }
        this.update();
    }

    setDenom(value) {
        if (!Number.isFinite(value)) return;
        this.denomination = parseInt(value, 10);
        if (this.denomination < this.MIN_DENOM) this.denomination = this.MIN_DENOM;
        if (this.denomination > this.MAX_DENOM) this.denomination = this.MAX_DENOM;
        this.update();
    }

    setLines(value) {
        if (!Number.isFinite(value)) return;
        this.lines = parseInt(value, 10);
        if (this.lines < this.MIN_LINES) this.lines = this.MIN_LINES;
        if (this.lines > this.MAX_LINES) this.lines = this.MAX_LINES;
        this.update();
    }

    setMaxDenom(value) {
        if (!Number.isFinite(value)) return;
        this.MAX_DENOM = Math.max(this.MIN_DENOM, parseInt(value, 10));
        if (this.denomination > this.MAX_DENOM) {
            this.denomination = this.MAX_DENOM;
        }
        this.update();
    }

    setMaxLines(value) {
        if (!Number.isFinite(value)) return;
        this.MAX_LINES = Math.max(this.MIN_LINES, parseInt(value, 10));
        if (this.lines > this.MAX_LINES) {
            this.lines = this.MAX_LINES;
        }
        this.update();
    }
    setMaxBetPerLine(value) {
        if (!Number.isFinite(value)) return;
        this.MAX_BET_PER_LINE = Math.max(this.MIN_BET_PER_LINE, parseInt(value, 10));
        if (this.bet > this.MAX_BET_PER_LINE) {
            this.bet = this.MAX_BET_PER_LINE;
        }
        this.syncBetIndex();
        this.update();
    }

    setCurrency(currency) {
        this.currency = (typeof currency === 'string' && currency.trim().length > 0) ? currency.trim() : 'FUN';
    }

    getCurrency() {
        return this.currency;
    }

    getDenomination() {
        return this.denomination;
    }

    getLines() {
        return this.lines;
    }

    getBetPerLine() {
        return this.bet;
    }

    getTotalBet() {
        return this.totalBet;
    }

    getTotalBetInCredits() {
        return parseInt(this.totalBet / this.denomination, 10);
    }

    getTotalGamesPlayed() {
        return this.totalGamesPlayed;
    }

    getTotalCoinsIn() {
        return this.totalCoinsIn;
    }

    getTotalCoinsOut() {
        return this.totalCoinsOut;
    }

    getTotalIn() {
        return this.totalIn;
    }

    getTotalOut() {
        return this.totalOut;
    }

    getCredit(inCredits) {
        return inCredits === true ? parseInt(this.credit / this.denomination, 10) : this.credit;
    }

    update() {
        this.totalBet = this.bet * this.lines * this.denomination;
    }

    syncBetIndex() {
        this.refreshAllowedBetIndexes();

        const exact = this.betArray.indexOf(this.bet);
        if (exact >= this.minAllowedBetIndex && exact <= this.maxAllowedBetIndex) {
            this.betIndex = exact;
            return;
        }

        let closestIndex = this.minAllowedBetIndex;
        for (let i = this.minAllowedBetIndex; i <= this.maxAllowedBetIndex; i++) {
            const candidate = this.betArray[i];
            if (candidate <= this.bet) {
                closestIndex = i;
            }
        }

        this.betIndex = closestIndex;
        this.bet = this.betArray[this.betIndex] || this.MIN_BET_PER_LINE;
    }

    findMinAllowedBetIndex() {
        return this.minAllowedBetIndex;
    }

    findMaxAllowedBetIndex() {
        return this.maxAllowedBetIndex;
    }

    refreshAllowedBetIndexes() {
        let minIndex = 0;
        for (let i = 0; i < this.betArray.length; i++) {
            if (this.betArray[i] >= this.MIN_BET_PER_LINE) {
                minIndex = i;
                break;
            }
        }

        let maxIndex = minIndex;
        for (let i = 0; i < this.betArray.length; i++) {
            if (this.betArray[i] > this.MAX_BET_PER_LINE) break;
            if (i >= minIndex) {
                maxIndex = i;
            }
        }

        this.minAllowedBetIndex = minIndex;
        this.maxAllowedBetIndex = maxIndex;
    }
}

