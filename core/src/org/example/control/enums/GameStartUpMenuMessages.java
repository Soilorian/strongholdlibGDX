package org.example.control.enums;

import org.example.model.DataBase;
import org.example.model.ingame.castle.Colors;

public enum GameStartUpMenuMessages {
    NOT_VALID_ID("id is not in valid format\nit should contain only numbers"),
    SUCCESS("action succeeded!"),
    NO_MAP("no map with this id!"),
    NO_PLAYER("no player with given username exists!"),
    PLAYER_ALREADY_ADDED("this player is already in the game"),
    ALREADY_CHOSEN("someone else has already chosen this option"),
    NO_CASTLE("no castles with this number exists!"),
    NO_COLOR("no such color exists in game!\nplease choose between colors:\n" + Colors.list()),
    EMPTY_FIELD("you have an empty field"),
    NO_CASTLE_CHOSEN("you still haven't chosen a castle for your empire"),
    NO_COLOR_CHOSEN("you don't have a color"),
    NO_MAP_CHOSEN("you need to choose a map before the game starts"),
    NEXT_PLAYER(", it's your turn to choose!"),
    DONE("game start up completed");

    private final String message;

    GameStartUpMenuMessages(String messages) {
        message = messages;
    }

    @Override
    public String toString() {
        if (this.equals(NEXT_PLAYER))
            return DataBase.getCurrentEmpire().getOwner().getNickname() + message;
        return message;
    }
}
