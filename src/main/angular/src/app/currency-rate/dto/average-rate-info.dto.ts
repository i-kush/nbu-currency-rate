export class AverageRateInfo {

    private readonly fCurrencyCode: string;
    private readonly fAverageNbuRate: string;
    private readonly fYear: number;
    private readonly fMonth: number;

    constructor(aCurrencyCode: string, aAverageNbuRate: string, aYear: number, aMonth: number) {
        this.fCurrencyCode = aCurrencyCode;
        this.fAverageNbuRate = aAverageNbuRate;
        this.fYear = aYear;
        this.fMonth = aMonth;
    }

    get currencyCode(): string {
        return this.fCurrencyCode;
    }

    get averageNbuRate(): string {
        return this.fAverageNbuRate;
    }

    get year(): number {
        return this.fYear;
    }

    get month(): number {
        return this.fMonth;
    }
}
