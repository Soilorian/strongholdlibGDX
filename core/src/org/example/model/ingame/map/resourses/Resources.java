package org.example.model.ingame.map.resourses;

import java.io.Serializable;

public enum Resources implements Serializable {

    WHEAT("Wheat", 15),
    FLOUR("Flour", 25),
    HOPS("Hops", 30),
    ALE("Ale", 50),
    STONE("Stone", 10),
    IRON("Iron", 15),
    WOOD("Wood", 5),
    PITCH("Pitch", 15),
    GOLD("Gold", 1),
    MEAT("Meat", 20),
    APPLE("Apple", 15),
    CHEESE("Cheese", 20),
    BREAD("Bread", 30),
    CHESTPLATE("Chestplate", 60),
    SWORD("sword", 65),
    MACE("mace", 60),
    SPEAR("spear", 35),
    LEATHER_VEST("leather vest", 45),
    PIKE("pike", 50),
    BOW("bow", 40),
    CROSSBOW("crossbow", 50),
    OIL("oil", 40),
    HORSE("horse", 100);
    private final String type;
    private final int sellingPrice;
    private final int buyingPrice;

    Resources(String type, int buyingPrice) {
        this.type = type;
        this.sellingPrice = Math.floorDiv(buyingPrice * 8, 10);
        this.buyingPrice = buyingPrice;
    }

    public static Resources getResourcesByName(String type) {
        for (Resources resources : Resources.values())
            if (resources.getType().equals(type))
                return resources;
        return null;
    }


    public String getType() {
        return type;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public int getBuyingPrice() {
        return buyingPrice;
    }

}
