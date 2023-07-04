package org.example.model.enums;

public enum Sounds {
    WAIT_A_MOMENT("Project-Voices\\Jeopardy Theme - DJ Lunatique.wav"),
    BENAZAM("Project-Voices\\Benazam.wav"),
    ESHQ("src\\main\\resources\\Project-Voices\\eshq.wav"),
    AKHEY("src\\main\\resources\\Project-Voices\\Akey.wav"),
    ARAB_FIRE_THROWER("src\\main\\resources\\Project-Voices\\Arab-Fire-Thrower.wav"),
    ARABIAN_SWORDSMEN("src\\main\\resources\\Project-Voices\\Arabian-Swordsmen.wav"),
    CROSSBOWMEN("src\\main\\resources\\Project-Voices\\Crossbowmen.wav"),
    HORSE_ARCHERS("src\\main\\resources\\Project-Voices\\Horse-Archers.wav"),
    KNIGHT("src\\main\\resources\\Project-Voices\\Knight.wav"),
    MACEMEN("src\\main\\resources\\Project-Voices\\Macemen.wav"),
    PEOPLE_LEAVE_THE_CASTLE("src\\main\\resources\\Project-Voices\\People-Leave-The-Castle.wav"),
    PIKEMEN("src\\main\\resources\\Project-Voices\\Pikemen.wav"),
    SLAVE("src\\main\\resources\\Project-Voices\\Slave.wav"),
    SLINGERS("src\\main\\resources\\Project-Voices\\Slingers.wav"),
    SPEARMEN("src\\main\\resources\\Project-Voices\\Spearmen.wav"),
    DONE("Project-Voices\\Ding.wav");


    private final String filePath;

    Sounds(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
