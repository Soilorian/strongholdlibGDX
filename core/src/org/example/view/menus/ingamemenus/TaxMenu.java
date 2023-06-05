package org.example.view.menus.ingamemenus;


import org.example.control.SoundPlayer;
import org.example.control.menucontrollers.inGameControllers.TaxMenuController;
import org.example.model.DataBase;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;
import org.example.view.enums.commands.InGameMenuCommands;
import org.example.view.menus.Menu;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.regex.Matcher;

public class TaxMenu extends Menu {


    public void run(String commands) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Matcher matcher;
        do {
            if ((matcher = InGameMenuCommands.getMatcher(commands, InGameMenuCommands.CHANGE_TAX_RATE)) != null)
                setTax(matcher);
            else if (InGameMenuCommands.getMatcher(commands, InGameMenuCommands.SHOW_TAX_RATE) != null)
                showTax();
            else if (commands.equalsIgnoreCase("show menu"))
                System.out.println(Menus.getNameByObj(this));
            else {
                SoundPlayer.play(Sounds.AKHEY);
                System.out.println("invalid command!");
            }
        } while (InGameMenuCommands.getMatcher(commands, InGameMenuCommands.BACK) == null);
    }

    @Override
    public void create() {

    }

    public void setTax(Matcher matcher) {
        String rate = matcher.group("Rate");
        System.out.println(TaxMenuController.setTax(rate));

    }



    public void showTax() {
        System.out.println(DataBase.getCurrentEmpire().getTax());
    }
}
