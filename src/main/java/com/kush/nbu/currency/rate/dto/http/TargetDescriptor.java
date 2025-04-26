package com.kush.nbu.currency.rate.dto.http;

import java.time.LocalDate;

public class TargetDescriptor {
    
    private static final String USD = "USD";
    
    private final String fCurrency;
    private final String fYear;
    private final String fMonth;
    private final boolean fIsCurrentMonth;

    public TargetDescriptor(RawTargetDescriptor aRawTargetDescriptor) {
        fCurrency = isEmpty(aRawTargetDescriptor.getCurrency()) ? USD : aRawTargetDescriptor.getCurrency();
        
        LocalDate lNow = LocalDate.now();
        
        String lDefaultYear = String.valueOf(lNow.getYear());
        fYear = (aRawTargetDescriptor.getYear() == null) ? lDefaultYear : String.valueOf(aRawTargetDescriptor.getYear());

        int lDefaultMonth = lNow.getMonthValue();
        fMonth = (aRawTargetDescriptor.getMonth() == null) ? convertMonth(lDefaultMonth) : convertMonth(aRawTargetDescriptor.getMonth());

        fIsCurrentMonth = (lNow.getYear() == Integer.valueOf(fYear)) && (lNow.getMonthValue() == Integer.valueOf(fMonth));
    }

    public String convertMonth(int aValue) {
        return aValue >= 10 ? String.valueOf(aValue) : ("0" + aValue);
    }

    private boolean isEmpty(String aValue) {
        return aValue == null || aValue.trim().length() == 0;
    }

    public String getCurrency() {
        return fCurrency;
    }

    public String getYear() {
        return fYear;
    }

    public String getMonth() {
        return fMonth;
    }

    public boolean isCurrentMonth() {
        return fIsCurrentMonth;
    }

    @Override
    public String toString() {
        return "currency='" + fCurrency + '\'' +
                ", year=" + fYear +
                ", month=" + fMonth;
    }
}
