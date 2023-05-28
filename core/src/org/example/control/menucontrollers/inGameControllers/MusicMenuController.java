package org.example.control.menucontrollers.inGameControllers;

import org.example.control.SoundPlayer;
import org.example.view.enums.commands.Musics;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Random;

public class MusicMenuController {
    private static final Random random = new Random();

    public static void playRandomSong() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Musics musics = randomEnum(Musics.class);
        SoundPlayer.playMusic(musics);
    }

    public static void pause() {
        SoundPlayer.pause();
    }

    public static void resume() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        SoundPlayer.resumeAudio();
    }

    public static String showALlSongs() {
        String output = "";
        for (Musics musics : Musics.values())
            output += musics.getPath().substring(6) + "\n";
        return output.substring(0, output.length() - 1);
    }

    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
}