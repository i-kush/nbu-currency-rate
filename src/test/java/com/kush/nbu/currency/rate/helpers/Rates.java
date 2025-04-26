package com.kush.nbu.currency.rate.helpers;

public enum Rates {

    JAN(1, 28.43432861290322),
    FEB(2, 27.17104939285714),
    MAR(3, 26.341204741935485),
    APR(4, 26.151748566666665),
    MAY(5, 26.18122458064516),
    JUN(6, 26.20217046666666),
    JUL(7, 26.400686419354855),
    AUG(8, 27.482209032258066),
    SEP(9, 28.19126743333334);

    private final int fMonth;
    private final double fRate;

    Rates(int aMonth, double aRate) {
        fMonth = aMonth;
        fRate = aRate;
    }

    public int getMonth() {
        return fMonth;
    }

    public double getRate() {
        return fRate;
    }
}
