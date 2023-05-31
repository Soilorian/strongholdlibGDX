package org.example.view.menus;

import org.example.control.SoundPlayer;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;
import org.example.view.enums.commands.InGameMenuCommands;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.regex.Matcher;

public class SettingsMenu implements Menu {
    @Override
    public void run(String commands) throws UnsupportedAudioFileException, LineUnavailableException, IOException,
            CoordinatesOutOfMap, NotInStoragesException {
        Matcher matcher;
        while (true) {
            if ((InGameMenuCommands.getMatcher(commands, InGameMenuCommands.BACK)) != null) {
                return;
            } else if (commands.equalsIgnoreCase("show menu"))
                System.out.println(Menus.getNameByObj(this));
            else if ((commands.equalsIgnoreCase("open music player"))) {
                controller.setScreen(Menus.MUSIC_CONTROL_MENU.getMenu());
                controller.changeMenu(this);
            }else {
                SoundPlayer.play(Sounds.AKHEY);
                System.out.println("invalid command!");
            }
        }
    }
}
