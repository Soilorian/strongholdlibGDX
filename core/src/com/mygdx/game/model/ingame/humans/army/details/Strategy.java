package org.example.model.ingame.humans.army.details;

public enum Strategy {
    DEFENDING("defend"),
    ATTACKING("attacking"),
    NEUTRAL("neutral");
    private final String strategyName;

    Strategy(String strategyName) {
        this.strategyName = strategyName;
    }

    @Override
    public String toString() {
        return "Strategy{" +
                "strategyName='" + strategyName + '\'' +
                '}';
    }

    public String getStrategyName() {
        return strategyName;
    }
}
