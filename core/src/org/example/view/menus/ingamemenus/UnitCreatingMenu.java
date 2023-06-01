package org.example.view.menus.ingamemenus;


import org.example.control.Controller;
import org.example.control.SoundPlayer;
import org.example.control.enums.GameStartUpMenuMessages;
import org.example.control.menucontrollers.EntranceMenuController;
import org.example.control.menucontrollers.GameMenuController;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;
import org.example.view.enums.commands.InGameMenuCommands;
import org.example.view.menus.Menu;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.regex.Matcher;

public class UnitCreatingMenu extends Menu {

    @Override
    public void run(String input) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        do {
            Matcher matcher;
            if ((matcher = InGameMenuCommands.getMatcher(input, InGameMenuCommands.CREATE_UNIT)) != null)
                createUnit(matcher);
            else if (InGameMenuCommands.getMatcher(input, InGameMenuCommands.BACK) != null)
                return;
            else if (input.equalsIgnoreCase("show menu"))
                System.out.println(Menus.getNameByObj(this));
            else {
                SoundPlayer.play(Sounds.AKHEY);
                System.out.println("invalid command!");
            }
        } while (true);
    }

    public void createUnit(Matcher matcher) {
        String type = Controller.removeQuotes(matcher.group("Type"));
        String count = Controller.removeQuotes(matcher.group("Count"));
        if (Controller.isFieldEmpty(type, count))
            System.out.println(GameStartUpMenuMessages.EMPTY_FIELD);
        else if (!EntranceMenuController.isDigit(count))
            System.out.println("not valid count!\ncount filed should only contain digits");
        else System.out.println(GameMenuController.createUnit(type, Integer.parseInt(count)));
    }
}
