import { Component } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { CurrencyInfo } from "../dto/currency-info.dto";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { AverageRateInfo } from "../dto/average-rate-info.dto";
import { Utils } from "../../components/utils/utils";

@Component({
    selector: "current-rate",
    templateUrl: "./monthly-rate.component.html",
    styleUrls: ["./monthly-rate.component.css"]
})

export class MonthlyRateComponent {

    private readonly fHttpClient: HttpClient;
    private readonly fAvailableYears: number[];
    private readonly fAvailableMonths: number[];
    private readonly fCurrentDate: Date;
    private readonly fWaitingMessage: string;

    private fMonthlyFormGroup: FormGroup;
    private fCurrentAverageInfo: AverageRateInfo;
    private fIsMessageShown: boolean;
    private fErrorMessage: string;
    private fCurrenciesInfo: CurrencyInfo[];
    private fSelectedCurrencyInfo: CurrencyInfo;

    constructor(aHttpClient: HttpClient) {
        this.fHttpClient = aHttpClient;
        this.fAvailableYears = Utils.getYearsForDropdownFromAndTill(2000, null);
        this.fAvailableMonths = Utils.getMonths();
        this.fCurrentDate = new Date();
        this.fWaitingMessage = "Отримання інформації може зайняти деякий час";

        this.fMonthlyFormGroup = null;
        this.fIsMessageShown = null;
        this.fErrorMessage = null;

        this.fHttpClient.post("api/monthly-rate/currencies-info", {}).subscribe(
            (aValues: CurrencyInfo[]) => {
                this.fCurrenciesInfo = aValues;
                this.fSelectedCurrencyInfo = aValues[0];
            }
        );
    }

    public get currenciesInfo(): CurrencyInfo[] {
        return this.fCurrenciesInfo;
    }

    public get selectedCurrencyInfoText(): string {
        return this.fSelectedCurrencyInfo == undefined ? null : this.fSelectedCurrencyInfo.text;
    }

    public onCurrencyInfoSelect(aCurrencyCode: string): void {
        for (let lElement of this.fCurrenciesInfo) {
            if (lElement.code === aCurrencyCode) {
                this.fSelectedCurrencyInfo = lElement;
            }
        }
    }

    public get monthlyRateFormGroup(): FormGroup {
        if (this.fMonthlyFormGroup == null) {
            this.fMonthlyFormGroup = new FormGroup({});

            let lFormControl: FormControl = new FormControl("USD", [Validators.required]);
            this.fMonthlyFormGroup.addControl("currencyCode", lFormControl);

            lFormControl = new FormControl(this.currentYear, [Validators.required]);
            this.fMonthlyFormGroup.addControl("currencyYear", lFormControl);

            lFormControl = new FormControl(this.currentMonth, [Validators.required]);
            this.fMonthlyFormGroup.addControl("currencyMonth", lFormControl);
        }

        return this.fMonthlyFormGroup;
    }

    public calculateMonthlyRate(): void {
        const lFormValues = this.monthlyRateFormGroup.getRawValue();

        if (this.isMonthValid(lFormValues.currencyYear, lFormValues.currencyMonth)) {
            const lParams = {
                currency: lFormValues.currencyCode,
                month: lFormValues.currencyMonth,
                year: lFormValues.currencyYear
            };

            this.beforeDataGetting();
            this.fHttpClient.post("api/monthly-rate/calculate-rate", lParams)
                .subscribe(
                    (aResponse: AverageRateInfo) => {
                        this.fCurrentAverageInfo = aResponse;
                        this.afterDataGetting();
                    },
                    (aError: any) => {
                        this.fErrorMessage = aError.error.message;
                    }
                );
        }
    }

    public onDownloadClick(): void {
        const lControlsAsBlob: Blob = new Blob([this.fCurrentAverageInfo.averageNbuRate], { type: "text/plain;charset=utf-8" });
        const lFileName: string = `${this.fCurrentAverageInfo.year}-${this.fCurrentAverageInfo.month}-${this.fCurrentAverageInfo.currencyCode}.txt`;
        Utils.downloadFileWithCustomName(URL.createObjectURL(lControlsAsBlob), lFileName);
    }

    private isMonthValid(aYear: number, aMonth: number): boolean {
        let lResult: boolean = true;
        const lCurrentMonth: number = (this.fCurrentDate.getMonth() + 1);

        if (this.fCurrentDate.getFullYear() == aYear && aMonth > lCurrentMonth) {
            this.fIsMessageShown = true;
            this.fErrorMessage = `Необхідний місяць не може бути більшим за поточний місяць поточного року. Поточний = ${lCurrentMonth}, Необхідний = ${aMonth}`;
            lResult = false;
        }

        return lResult;
    }

    private beforeDataGetting(): void {
        this.fErrorMessage = null;
        this.fIsMessageShown = true;
    }

    private afterDataGetting(): void {
        this.fIsMessageShown = false;
        this.fErrorMessage = null;
    }

    public get currentAverageRateInfo(): AverageRateInfo {
        return this.fCurrentAverageInfo;
    }

    public get isMessageShown(): boolean {
        return this.fIsMessageShown;
    }

    public get message(): string {
        return (this.fErrorMessage == null) ? this.fWaitingMessage : this.fErrorMessage;
    }

    public get availableYears(): number[] {
        return this.fAvailableYears;
    }

    public get availableMonths(): number[] {
        return this.fAvailableMonths;
    }

    public get currentMonth(): number {
        return this.fCurrentDate.getMonth() + 1;
    }

    public get currentYear(): number {
        return this.fCurrentDate.getFullYear();
    }

    public get selectedCurrencyInfo(): CurrencyInfo {
        return this.fSelectedCurrencyInfo;
    }
}
