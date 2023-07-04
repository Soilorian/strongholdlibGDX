package org.example.model.enums;

public enum TaxDetail {
    _THREE(-3, 7, -1.0),
    _TWO(-2, 5, -0.8),
    _ONE(-1, 3, -0.6),
    ZERO(0, 1, 0.0),
    ONE(1, -2, 0.6),
    TWO(2, -4, 0.8),
    THREE(3, -6, 1.0),
    FOUR(4, -8, 1.2),
    FIVE(5, -12, 1.4),
    SIX(6, -16, 1.6),
    SEVEN(7, -20, 1.8),
    EIGHT(8, -24, 2.0);
    private final int taxRate;
    private final int popularity;
    private final double taxPerPeasant;

    TaxDetail(int taxRate, int popularity, double taxPerPeasant) {
        this.taxRate = taxRate;
        this.popularity = popularity;
        this.taxPerPeasant = taxPerPeasant;
    }

    public static TaxDetail getTaxByRate(int taxRate) {
        for (TaxDetail taxDetail : TaxDetail.values())
            if (taxDetail.getTaxRate() == taxRate)
                return taxDetail;
        return null;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public int getPopularity() {
        return popularity;
    }

    public double getTaxPerPeasant() {
        return taxPerPeasant;
    }
}
