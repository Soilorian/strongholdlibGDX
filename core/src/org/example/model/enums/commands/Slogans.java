package org.example.model.enums.commands;

import org.example.control.menucontrollers.inGameControllers.MusicMenuController;

public enum Slogans {
    EZIO("Nothing is true, everything is permitted"),
    LEO("Where's Everyone going? Bingo?"),
    SCORPION("get over here"),
    GROOT("I am groot"),
    EZIO2("Requiescat In Pace"),
    VAAS("Did I ever tell you the definition of insanity?"),
    JOSHUA("I survived because the fire inside me burned brighter than the fire around me."),
    SORA("I don't need a weapon; My Friends are my power!"),
    ZELDA("It's dangerous to go  alone! Take this."),
    ;

    private final String slogan;

    Slogans(String slogan) {
        this.slogan = slogan;
    }

    public static String getRandomSlogan() {
        Slogans slogans = MusicMenuController.randomEnum(Slogans.class);
        return slogans.getSlogan();
    }

    public String getSlogan() {
        return slogan;
    }
}
