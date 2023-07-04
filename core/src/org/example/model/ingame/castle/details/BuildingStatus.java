package org.example.model.ingame.castle.details;

public enum BuildingStatus {
    WAITING_FOR_RESOURCE("waiting for resource"),
    STARTING_PRODUCTION("starting production"),
    IN_THE_MIDDLE_OF_PRODUCTION("in the middle of production"),
    FINISHING_PRODUCTION("finishing production"),
    DELIVERING_GOODS("delivering goods"),
    EMPLOYMENT("employing"),
    USELESS("doing nothing"),
    GATE("Gateway"),
    OPEN("gate is open"),
    CLOSE("gate is closed"),
    NOT_ACTIVE("not activated");
    private final String status;

    BuildingStatus(String status) {
        this.status = status;
    }

    public static BuildingStatus goNext(BuildingStatus productionStep) {
        BuildingStatus[] values = BuildingStatus.values();
//        List<BuildingStatus> productionSteps = Arrays.stream(values).toList();
//        for (int i = 0; i < values.length; i++)
//            if (productionSteps.get(i).equals(productionStep))
//                productionStep = productionSteps.get((i + 1) % 5);
        for (int i = 0 ; i< values.length ; i++)
            if (values[i].equals(productionStep))
                productionStep = values[(i+1)%5];
        return productionStep;
    }

    public String getStatus() {
        return status;
    }
}
