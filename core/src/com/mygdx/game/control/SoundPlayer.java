package org.example.control;

import org.example.view.enums.Sounds;
import org.example.view.enums.commands.Musics;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {
    private static Clip clip;
    private static AudioInputStream audioInputStream;
    private static String filePath;

    public static void play(Sounds sounds) throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        audioInputStream =
                AudioSystem.getAudioInputStream(new File(sounds.getFilePath()).getAbsoluteFile());
        filePath = sounds.getFilePath();

        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        try {
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void playMusic(Musics musics) throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        audioInputStream =
                AudioSystem.getAudioInputStream(new File(musics.getPath()).getAbsoluteFile());
        filePath = musics.getPath();
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        try {
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void playWithLoop(Sounds sounds) throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        audioInputStream =
                AudioSystem.getAudioInputStream(new File(sounds.getFilePath()).getAbsoluteFile());
        filePath = sounds.getFilePath();
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        try {
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void pause() {
        clip.stop();
    }

    public static void resumeAudio() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        clip.close();
        resetAudioStream();
        clip.start();
    }

    public static void resetAudioStream() throws UnsupportedAudioFileException, IOException,
            LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(
                new File(filePath).getAbsoluteFile());
        clip.open(audioInputStream);
    }
}
