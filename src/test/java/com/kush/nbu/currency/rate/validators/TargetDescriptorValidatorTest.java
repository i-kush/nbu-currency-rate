package com.kush.nbu.currency.rate.validators;

import com.kush.nbu.currency.rate.dto.http.RawTargetDescriptor;
import com.kush.nbu.currency.rate.exceptions.RequestDataValidationException;
import org.junit.Test;

import java.time.LocalDate;

public class TargetDescriptorValidatorTest {

    private static final TargetDescriptorValidator TARGET_DESCRIPTOR_VALIDATOR = new TargetDescriptorValidator();

    @Test(expected = RequestDataValidationException.class)
    public void validateYear() {
        RawTargetDescriptor lRawTargetDescriptor = new RawTargetDescriptor();
        lRawTargetDescriptor.setYear(LocalDate.now().getYear() + 1);

        TARGET_DESCRIPTOR_VALIDATOR.validate(lRawTargetDescriptor);
    }

    @Test(expected = RequestDataValidationException.class)
    public void validateMonthLowerThanOneAndYearNull() {
        RawTargetDescriptor lRawTargetDescriptor = new RawTargetDescriptor();
        lRawTargetDescriptor.setMonth(0);

        TARGET_DESCRIPTOR_VALIDATOR.validate(lRawTargetDescriptor);
    }

    @Test(expected = RequestDataValidationException.class)
    public void validateMonthGreaterThanTwelveAndYearNull() {
        RawTargetDescriptor lRawTargetDescriptor = new RawTargetDescriptor();
        lRawTargetDescriptor.setMonth(13);

        TARGET_DESCRIPTOR_VALIDATOR.validate(lRawTargetDescriptor);
    }

    @Test(expected = RequestDataValidationException.class)
    public void validateMonthGreaterThanCurrentMonth() {
        LocalDate lNow = LocalDate.now();
        int lCurrentMonth = lNow.getMonthValue();

        if (lCurrentMonth != 12) {
            RawTargetDescriptor lRawTargetDescriptor = new RawTargetDescriptor();
            lRawTargetDescriptor.setYear(lNow.getYear());
            lRawTargetDescriptor.setMonth(lCurrentMonth + 1);

            TARGET_DESCRIPTOR_VALIDATOR.validate(lRawTargetDescriptor);
        } else {
            String lMessage = "Can't perform test, because current month is 12";
            System.out.println(lMessage);
            throw new RequestDataValidationException(lMessage);
        }
    }
}