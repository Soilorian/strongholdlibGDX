package org.example.control.enums;

public enum GameMenuMessages {
    SUCCEED("operation succeed"),
    DIGIT_COORDINATES("you must enter digit for coordinates"),
    INVALID_COORDINATES("invalid number for COORDINATES"),
    ALREADY_BUILDING("there is a Building in this place"),
    INVALID_BUILDING("there is no building with this name"),
    POPULARITY_FACTORS("Food\nTax\nReligion\nFear"),
    POPULARITY("Your Popularity : "),
    EMPTY_FEAR_RATE("fear rate's field is empty"),
    FEAR_DIGIT("Fear Rate Must Be Digit"),
    NOT_IN_GAME("this user is not in game"),
    INVALID_FEAR("Fear must be between -5 and 5"),
    FEAR_RATE("Your Fear Rate : "),
    CANT_PRODUCE_UNIT("This building can not create this unit"),
    NOT_ENOUGH_GOLD("you don't have enough gold!"),
    NOT_ENOUGH_WEAPONS("you don't have the required weapons to train this unit"),
    SUCCESS("completed action successfully"),
    NOT_IN_RANGE("the coordinates does not exist"),
    NOT_ENOUGH_RESOURCE("we don't have enough resources for production sultan"),
    STORAGE_FULL("storage is full my lord"),
    WRONG_DIRECTION("directions show only contain up, right, down or left"),
    SELECT_BUILDING_NULL("your selected building is null"),
    UNREPAIRABLE("you can't repair this building"),


    NO_BUILDING_HERE("there is no building on this tile"),
    NO_TROOPS_HERE("there are no troops here to be selected!"),
    ENJOY("enjoy your "),
    NOT_A_RESOURCE("there is no such resource"),
    NOT_A_TROOP("there is no such troop"),
    NOT_A_STRATEGY("this is not a valid strategy"),
    NOT_A_DIRECTION("idk what this is but this is not a direction"),
    CANT_GO_THERE("can't go there my lord"),
    READY_TO_ATTACK("ready to attack"),
    HAVE_OIL("I now have oil"),
    ON_THE_WAY("on the way"),
    READY_TO_DIG("ready to dig the best tunnels"),
    TUNNEL_REACHED("tunnel reached destination"),
    EMPTY("no troops selected"),
    NO_ENEMIES("no enemies to attack"),
    ALREADY_ATTACKED("you have already attacked this turn"),
    NOTHING_SELECTED("nothing is currently selected"),
    NO_TUNNELER("no tunnelers to dig this tunnel"),
    NOT_DIGGING("not digging tunnel"),
    CANT_DIG("too tired to dig"),
    CANT_TUNNEL("too far away"),
    NOT_YOUR_TROOPS("you can't control others' troops"),
    NOT_PLACEABLE_TILE("can not place this building on this tile"),
    ENEMY_AROUND("there are enemies around");

    private final String message;

    GameMenuMessages(String messages) {
        message = messages;
    }

    @Override
    public String toString() {
        return message;
    }
}

