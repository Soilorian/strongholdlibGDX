package org.example.model.enums.commands;

public enum Musics {
    BLEED_IT_OUT("src\\main\\resources\\Music\\04 - Bleed it Out.wav"),
    DAY_ONE("src\\main\\resources\\Music\\Hans Zimmer - Day One.wav"),
    //    CATCH("src\\main\\resources\\Music\\@SOULNICE - The Catch - Koan.wav"),
//    LOST("src\\main\\resources\\Music\\01 Gesaffelstein & The Weeknd - Lost.wav"),
    CASTLE_JAM("src\\main\\resources\\Music\\02 - Castle Jam.wav"),
    DARK_TIMES("src\\main\\resources\\Music\\Dark Time.wav"),
    HAPPY_TIMES("src\\main\\resources\\Music\\Happy Times.wav"),
    HONOR_MEDLEY("src\\main\\resources\\Music\\Honer Medley.wav"),


    ;
    private final String Path;

    Musics(String path) {
        Path = path;
    }

    public String getPath() {
        return Path;
    }
}
