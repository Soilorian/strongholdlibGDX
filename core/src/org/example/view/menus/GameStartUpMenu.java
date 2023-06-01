package org.example.view.menus;


import org.example.control.Controller;
import org.example.control.SoundPlayer;
import org.example.control.enums.GameStartUpMenuMessages;
import org.example.control.menucontrollers.GameMenuController;
import org.example.control.menucontrollers.GameStartUpMenuController;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;
import org.example.view.enums.commands.GameStartUpMenuCommands;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.regex.Matcher;


public class GameStartUpMenu extends Menu {
    public static boolean isNotDigit(String str) {
        return !str.matches("\\d+");
    }

    public static boolean next() {
        GameStartUpMenuMessages message = GameStartUpMenuController.nextTurn();
        System.out.println(message);
        if (message.equals(GameStartUpMenuMessages.DONE)) {
            System.out.println("let the battle begin!");
            return true;
        }
        return false;
    }



    @Override
    public void run(String input) throws IOException, UnsupportedAudioFileException, LineUnavailableException,
            CoordinatesOutOfMap, NotInStoragesException {
        GameStartUpMenuController.startMakingGame();
    }

    private boolean gameStartUp(String input) throws UnsupportedAudioFileException, CoordinatesOutOfMap,
            LineUnavailableException, NotInStoragesException, IOException {
        do {
            Matcher matcher;
            if (input.equalsIgnoreCase("show menu"))
                System.out.println(Menus.getNameByObj(this));
            else if (GameStartUpMenuCommands.getMatcher(input, GameStartUpMenuCommands.CANCEL) != null) {
                cancel();
                return false;
            } else if ((input.equalsIgnoreCase("open music player"))) {
                controller.setScreen(Menus.MUSIC_CONTROL_MENU.getMenu());
                controller.changeMenu(this);
            }else if (GameStartUpMenuCommands.getMatcher(input, GameStartUpMenuCommands.NEXT) != null) {
                if (next())
                    return true;
            } else if ((matcher = GameStartUpMenuCommands.getMatcher(input, GameStartUpMenuCommands.ADD_PLAYER)) != null)
                addPlayer(matcher);
            else if ((matcher = GameStartUpMenuCommands.getMatcher(input, GameStartUpMenuCommands.SELECT_CASTLE)) != null)
                selectCastle(matcher);
            else if ((matcher = GameStartUpMenuCommands.getMatcher(input, GameStartUpMenuCommands.SELECT_COLOR)) != null)
                selectColor(matcher);
            else System.out.println("invalid command");
        } while (true);
    }


    private boolean selectMapMenu(String input) throws UnsupportedAudioFileException, CoordinatesOutOfMap,
            LineUnavailableException, NotInStoragesException, IOException {
        System.out.println("select the map you want");
        do {
            Matcher matcher;
            if (input.equalsIgnoreCase("show menu"))
                System.out.println(Menus.getNameByObj(this));
            else if ((matcher = GameStartUpMenuCommands.getMatcher(input, GameStartUpMenuCommands.SELECT_MAP)) != null) {
                if (selectMap(matcher)) {
                    return true;
                }
            } else if (GameStartUpMenuCommands.getMatcher(input, GameStartUpMenuCommands.SHOW_MAPS) != null)
                showMaps();
            else if (GameStartUpMenuCommands.getMatcher(input, GameStartUpMenuCommands.CANCEL) != null) {
                return false;
            } else {
                SoundPlayer.play(Sounds.AKHEY);
                System.out.println("invalid command");
            }
        } while (true);
    }



    private void showMaps() {
        System.out.println(GameStartUpMenuController.showAllMap());
    }

    private boolean selectMap(Matcher matcher) throws IOException, UnsupportedAudioFileException, LineUnavailableException, CoordinatesOutOfMap, NotInStoragesException {
        String id = Controller.removeQuotes(matcher.group("Id"));
        if (Controller.isFieldEmpty(id))
            System.out.println(GameStartUpMenuMessages.EMPTY_FIELD);
        else {
            GameStartUpMenuMessages x = GameStartUpMenuController.selectMap(id);
            System.out.println(x);
            if (x.equals(GameStartUpMenuMessages.SUCCESS)) {
                GameMenuController.getCurrentGame().setCurrentMap(Controller.getCurrentMap());
                return true;
            }
        }
        return false;
    }

    private void addPlayer(Matcher matcher) {
        String username = Controller.removeQuotes(matcher.group("Username"));
        if (Controller.isFieldEmpty(username))
            System.out.println(GameStartUpMenuMessages.EMPTY_FIELD);
        else
            System.out.println(GameStartUpMenuController.addPlayer(username));
    }

    private void selectColor(Matcher matcher) {
        String color = Controller.removeQuotes(matcher.group("Color"));
        if (Controller.isFieldEmpty(color))
            System.out.println(GameStartUpMenuMessages.EMPTY_FIELD);
        else
            System.out.println(GameStartUpMenuController.selectColor(color));

    }

    private void selectCastle(Matcher matcher) {
        String castle = Controller.removeQuotes(matcher.group("Id"));
        if (Controller.isFieldEmpty(castle))
            System.out.println(GameStartUpMenuMessages.EMPTY_FIELD);
        else if (isNotDigit(castle))
            System.out.println("castle number should be a digit!");
        else
            System.out.println(GameStartUpMenuController.selectCastle(Integer.parseInt(castle)));
    }


    private void cancel() {
        GameStartUpMenuController.cancel();
    }
}
