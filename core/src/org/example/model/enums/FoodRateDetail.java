package org.example.model.enums;

public enum FoodRateDetail {
    _TWO(-2, 0, -8),
    _ONE(-1, 0.5, -4),
    ZERO(0, 1.0, 0),
    ONE(1, 1.5, 4),
    TWO(2, 2.0, 8),
    ;

    private final int foodRate;
    private final double foodPerPeasant;
    private final int popularity;

    FoodRateDetail(int foodRate, double foodPerPeasant, int popularity) {
        this.foodRate = foodRate;
        this.foodPerPeasant = foodPerPeasant;
        this.popularity = popularity;
    }

    public static FoodRateDetail getFoodByRate(int rate) {
        for (FoodRateDetail foodRateDetail : FoodRateDetail.values())
            if (foodRateDetail.getFoodRate() == rate)
                return foodRateDetail;
        return null;
    }

    public int getFoodRate() {
        return foodRate;
    }

    public double getFoodPerPeasant() {
        return foodPerPeasant;
    }

    public int getPopularity() {
        return popularity;
    }
}
