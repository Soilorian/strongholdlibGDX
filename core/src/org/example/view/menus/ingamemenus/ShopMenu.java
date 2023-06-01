package org.example.view.menus.ingamemenus;


import org.example.control.Controller;
import org.example.control.SoundPlayer;
import org.example.control.menucontrollers.inGameControllers.ShopMenuController;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;
import org.example.view.enums.commands.InGameMenuCommands;
import org.example.view.menus.Menu;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.regex.Matcher;

public class ShopMenu extends Menu {

    @Override
    public void run(String commands) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Matcher matcher;
        while (true) {
            if ((matcher = InGameMenuCommands.getMatcher(commands, InGameMenuCommands.SHOW_SHOP_PRICES)) != null)
                showPriceList();
            else if ((matcher = InGameMenuCommands.getMatcher(commands, InGameMenuCommands.BUY_ITEM)) != null)
                buyItem(matcher);
            else if ((matcher = InGameMenuCommands.getMatcher(commands, InGameMenuCommands.SELL_ITEM)) != null)
                sellCard(matcher);
            else if (InGameMenuCommands.getMatcher(commands, InGameMenuCommands.BACK) != null)
                break;
            else if (commands.equalsIgnoreCase("show menu"))
                System.out.println(Menus.getNameByObj(this));
            else {
                SoundPlayer.play(Sounds.AKHEY);
                System.out.println("invalid command!");
            }
        }
    }

    public void showPriceList() {
        System.out.println(ShopMenuController.showPriceList());
    }

    public void buyItem(Matcher matcher) {
        String item = Controller.removeQuotes(matcher.group("Item"));
        String amount = Controller.removeQuotes(matcher.group("Amount"));
        System.out.println(ShopMenuController.buyItem(item, amount));
    }

    public void sellCard(Matcher matcher) {
        String item = Controller.removeQuotes(matcher.group("Item"));
        String amount = Controller.removeQuotes(matcher.group("Amount"));
        System.out.println(ShopMenuController.sellResource(item, amount));
    }
}
