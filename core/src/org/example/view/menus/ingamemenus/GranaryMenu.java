package org.example.view.menus.ingamemenus;


import org.example.control.SoundPlayer;
import org.example.control.menucontrollers.inGameControllers.GranaryMenuController;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;
import org.example.view.enums.commands.InGameMenuCommands;
import org.example.view.menus.Menu;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.regex.Matcher;

public class GranaryMenu extends Menu {
    @Override
    public void run(String commands) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Matcher matcher;
        while (true) {
            if (InGameMenuCommands.getMatcher(commands, InGameMenuCommands.SHOW_FOODLIST) != null)
                GranaryMenuController.showFoodList();
            else if (InGameMenuCommands.getMatcher(commands, InGameMenuCommands.SHOW_FOOD_RATE) != null)
                GranaryMenuController.showFoodRate();
            else if ((matcher = InGameMenuCommands.getMatcher(commands, InGameMenuCommands.CHANGE_FOOD_RATE)) != null)
                changeFoodRate(matcher);
            else if ((InGameMenuCommands.getMatcher(commands, InGameMenuCommands.BACK)) != null) {
                System.out.println("exited granary menu");
                return;

            } else if (commands.equalsIgnoreCase("show menu"))
                System.out.println(Menus.getNameByObj(this));
            else {
                SoundPlayer.play(Sounds.AKHEY);
                System.out.println("invalid command!");
            }
        }
    }

    private void changeFoodRate(Matcher matcher) {
        String foodRate = matcher.group("Rate");
        System.out.println(GranaryMenuController.changeFoodRate(foodRate));
    }
}
