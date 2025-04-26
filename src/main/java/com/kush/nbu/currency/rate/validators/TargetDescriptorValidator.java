package com.kush.nbu.currency.rate.validators;

import com.kush.nbu.currency.rate.dto.http.RawTargetDescriptor;
import com.kush.nbu.currency.rate.exceptions.RequestDataValidationException;

import java.time.LocalDate;

public class TargetDescriptorValidator implements ICurrentRateValidator<RawTargetDescriptor> {

    @Override
    public void validate(RawTargetDescriptor aRawTargetDescriptor) {
        LocalDate lNow = LocalDate.now();
        int lCurrentYear = lNow.getYear();

        if (aRawTargetDescriptor.getYear() != null) {
            int lNeededYear = aRawTargetDescriptor.getYear();

            if (lNeededYear > lCurrentYear) {
                String lMessage = String.format("Year of needed info can't be greater than current. Current=%d, Needed=%d", lCurrentYear, lNeededYear);
                throw new RequestDataValidationException(lMessage);
            }
        }

        if (aRawTargetDescriptor.getMonth() != null) {
            int lNeededMonth = aRawTargetDescriptor.getMonth();
            int lCurrentMonth = lNow.getMonthValue();

            if (lNeededMonth > 12 || lNeededMonth < 1) {
                String lMessage = String.format("Month can't be greater than 12 and less than 1: %d", lNeededMonth);
                throw new RequestDataValidationException(lMessage);
            }

            Integer lNeededYear = aRawTargetDescriptor.getYear();
            if ((lNeededYear == null || lNeededYear == lCurrentYear) && lNeededMonth > lCurrentMonth) {
                String lMessage = String.format("Needed month can't be greater than current month of the current year. Current=%d, Needed=%d", lCurrentMonth, lNeededMonth);
                throw new RequestDataValidationException(lMessage);
            }
        }
    }
}
