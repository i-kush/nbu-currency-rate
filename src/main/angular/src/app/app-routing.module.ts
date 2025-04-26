import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { MonthlyRateComponent } from "./currency-rate/monthly-rate/monthly-rate.component";

const ROUTES: Routes = [
    {
        path: "",
        pathMatch: "full",
        redirectTo: "monthly-rate"
    },
    {
        path: "monthly-rate",
        component: MonthlyRateComponent
    }
];

@NgModule({
    exports: [
        RouterModule
    ],
    imports: [
        RouterModule.forRoot(ROUTES)
    ]
})
export class AppRoutingModule {
}
