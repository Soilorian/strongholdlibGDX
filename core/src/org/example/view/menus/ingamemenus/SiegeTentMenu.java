package org.example.view.menus.ingamemenus;

import org.example.control.Controller;
import org.example.control.SoundPlayer;
import org.example.control.enums.EntranceMenuMessages;
import org.example.control.menucontrollers.GameMenuController;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;
import org.example.view.enums.commands.GameMenuCommands;
import org.example.view.enums.commands.InGameMenuCommands;
import org.example.view.menus.Menu;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.regex.Matcher;

public class SiegeTentMenu extends Menu {

    @Override
    public void run(String input) throws IOException, UnsupportedAudioFileException, LineUnavailableException,
            CoordinatesOutOfMap, NotInStoragesException {
        do {
            Matcher matcher;
            if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.BUILD_EQUIPMENT)) != null)
                build(matcher);
            else if (input.equalsIgnoreCase("show menu"))
                System.out.println(Menus.getNameByObj(this));
            else {
                SoundPlayer.play(Sounds.AKHEY);
                System.out.println("invalid command!");
            }
        } while (InGameMenuCommands.getMatcher(input, InGameMenuCommands.BACK) == null);
    }

    @Override
    public void create() {

    }

    private void build(Matcher matcher) {
        String equipment = Controller.removeQuotes(matcher.group("Equipment"));
        if (Controller.isFieldEmpty(equipment))
            System.out.println(EntranceMenuMessages.EMPTY_FIELD);
        System.out.println(GameMenuController.BuildSieges(equipment));
    }


}
