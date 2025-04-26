export class Utils {

    public static getYearsForDropdownFromAndTill(aFromYearInclusive: number, aTillYearInclusive: number | null): number[] {
        const lTill = (aTillYearInclusive == null) ? new Date().getFullYear() : aTillYearInclusive;

        if (lTill < aFromYearInclusive) {
            throw Error("'Till' year can not be less then 'from' year");
        }

        const lResult: number[] = [];
        for (let lCurrentValue = lTill; lCurrentValue >= aFromYearInclusive; lCurrentValue--) {
            lResult.push(lCurrentValue);
        }

        return lResult;
    }

    public static getMonths(): number[] {
        const lResult: number[] = [];

        for (let lMonthIndex = 1; lMonthIndex <= 12; lMonthIndex++) {
            lResult.push(lMonthIndex);
        }

        return lResult;
    }

    public static downloadFile(aFileUrl: string): void {
        Utils.downloadFileWithCustomName(aFileUrl, "");
    }

    public static downloadFileWithCustomName(aFileUrl: string, aFileName): void {
        const lLinkElement: HTMLElement = document.createElement("a");
        lLinkElement.style.display = "none";
        lLinkElement.setAttribute("href", aFileUrl);
        lLinkElement.setAttribute("download", aFileName);
        document.body.appendChild(lLinkElement);
        lLinkElement.click();
        document.body.removeChild(lLinkElement);
    }

}
