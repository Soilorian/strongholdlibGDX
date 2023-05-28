package org.example.control.enums;

public enum InGameMessages {
    IS_FOOD_RATE_DIGIT("Your Food Rate Must Be Digit"),

    IS_FOOD_RATE_VALID("Your Food Rate Must Be Between -2 and 2"),
    INVALID_AMOUNT("your amount must be a digit bigger than 0"),
    INVALID_TYPE("there is no item with this name"),
    NOT_ENOUGH_GOLD("you don't hava enough gold to buy this item "),
    NOT_ENOUGH_RESOURCE("you don't have enough resource to sell"),
    INVALID_TAX("Your Tax Rate Must Between -3 and 8 "),
    TAX_DIGIT("Your Tax Rate Must Be Digit"),
    INVALID_RESOURCE("invalid resource type"),
    NOT_DIGIT("price and amount must be digit"),
    INVALID_NUMBER("invalid amount or price"),
    INVALID_ID("there's not trade with this id"),
    ID_DIGIT("The id must be digit"),
    NOT_ENOUGH_RESOURCE_FOR_TRADE("you don't have enough resource to accept this trade"),
    NOT_ENOUGH_GOLD_FOR_TRADE("you don't have enough gold to send this trade"),

    TRADE_LIST_EMPTY("trade list is empty"),
    TRADE_HISTORY_EMPTY("trade history is empty");

    private final String message;

    InGameMessages(String messages) {
        this.message = messages;
    }

    @Override
    public String toString() {
        return message;
    }
}
