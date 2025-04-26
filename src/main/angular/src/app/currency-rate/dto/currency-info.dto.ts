export class CurrencyInfo {

    private readonly fCode;
    private readonly fText;

    constructor(aCode, aText) {
        this.fCode = aCode;
        this.fText = aText;
    }

    get code() {
        return this.fCode;
    }

    get text() {
        return this.fText;
    }
}
