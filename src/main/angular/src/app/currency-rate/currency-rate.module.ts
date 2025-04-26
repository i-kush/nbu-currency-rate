import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";
import { ReactiveFormsModule } from "@angular/forms";
import { NgModule } from "@angular/core";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { MonthlyRateComponent } from "./monthly-rate/monthly-rate.component";

@NgModule({
    declarations: [
        MonthlyRateComponent
    ],
    exports: [
        RouterModule
    ],
    imports: [
        CommonModule,
        RouterModule,
        ReactiveFormsModule,
        BrowserAnimationsModule
    ]
})
export class CurrencyRateModule {
}
