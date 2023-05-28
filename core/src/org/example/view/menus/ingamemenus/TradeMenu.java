package org.example.view.menus.ingamemenus;


import org.example.control.Controller;
import org.example.control.SoundPlayer;
import org.example.control.menucontrollers.inGameControllers.TradeMenuController;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;
import org.example.view.enums.commands.InGameMenuCommands;
import org.example.view.menus.Menu;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.regex.Matcher;

public class TradeMenu implements Menu {

    @Override
    public void run() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        String commands;
        Matcher matcher;
        while (true) {
            commands = scanner.nextLine();
            if ((matcher = InGameMenuCommands.getMatcher(commands, InGameMenuCommands.SEND_NEW_TRADE)) != null)
                sendNewTrade(matcher);
            else if ((matcher = InGameMenuCommands.getMatcher(commands, InGameMenuCommands.TRADE_ACCEPT)) != null)
                tradeAccept(matcher);
            else if (InGameMenuCommands.getMatcher(commands, InGameMenuCommands.SHOW_TRADE_LIST) != null)
                showTradeList();
            else if (InGameMenuCommands.getMatcher(commands, InGameMenuCommands.TRADE_HISTORY) != null)
                showTradeHistory();
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

    public void sendNewTrade(Matcher matcher) {
        String type = Controller.removeQuotes(matcher.group("Type"));
        String amount = Controller.removeQuotes(matcher.group("Amount"));
        String price = Controller.removeQuotes(matcher.group("Price"));
        String message = Controller.removeQuotes(matcher.group("Message"));
        System.out.println(TradeMenuController.sendNewTrade(type, price, amount, message));
    }

    public void tradeAccept(Matcher matcher) {
        String id = Controller.removeQuotes(matcher.group("Id"));
        String message = Controller.removeQuotes(matcher.group("Message"));
        System.out.println(TradeMenuController.tradeAccept(id, message));
    }

    public void showTradeList() {
        System.out.println(TradeMenuController.showTradeList());
    }

    public void showTradeHistory() {
        System.out.println(TradeMenuController.showTradeHistory());
    }

}
