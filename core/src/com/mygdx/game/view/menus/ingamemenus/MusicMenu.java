package org.example.view.menus.ingamemenus;

import org.example.control.SoundPlayer;
import org.example.control.menucontrollers.inGameControllers.MusicMenuController;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;
import org.example.view.enums.commands.InGameMenuCommands;
import org.example.view.menus.Menu;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class MusicMenu implements Menu {


    @Override
    public void run() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        String input;
        do {
            input = scanner.nextLine();
            if (InGameMenuCommands.getMatcher(input, InGameMenuCommands.RANDOM_SONG) != null)
                playRandomSong();
            else if (InGameMenuCommands.getMatcher(input, InGameMenuCommands.RESUME) != null)
                resume();
            else if (InGameMenuCommands.getMatcher(input, InGameMenuCommands.PAUSE) != null)
                pause();
            else if (InGameMenuCommands.getMatcher(input, InGameMenuCommands.SHOW_SONGS) != null)
                showALlSongs();
            else if (input.equalsIgnoreCase("show menu"))
                System.out.println(Menus.getNameByObj(this));
            else if (InGameMenuCommands.getMatcher(input, InGameMenuCommands.BACK) != null) {
                break;
            } else {
                SoundPlayer.play(Sounds.AKHEY);
                System.out.println("invalid command!");
            }
        } while (true);
    }


    public void playRandomSong() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        pause();
        MusicMenuController.playRandomSong();
    }

    public void pause() {
        MusicMenuController.pause();
    }

    public void resume() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        MusicMenuController.resume();
    }

    public void showALlSongs() {
        System.out.println(MusicMenuController.showALlSongs());
    }
}
