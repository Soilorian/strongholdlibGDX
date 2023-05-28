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


public class GameStartUpMenu implements Menu {
    private static boolean isNotDigit(String str) {
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

    static boolean selectSize(Matcher matcher) {
        String widthInString = Controller.removeQuotes(matcher.group("Width"));
        String heightInString = Controller.removeQuotes(matcher.group("Height"));
        if (Controller.isFieldEmpty(widthInString) || Controller.isFieldEmpty(heightInString))
            System.out.println(GameStartUpMenuMessages.EMPTY_FIELD);
        else if (isNotDigit(widthInString))
            System.out.println("not valid width\nwidth should only contain digits");
        else if (isNotDigit(heightInString))
            System.out.println("not valid height!\nheight should only contain digits");
        else {
            int width = Integer.parseInt(widthInString);
            int height = Integer.parseInt(heightInString);
            if (width < 200 || width > 400)
                System.out.println("width not in valid range!");
            else if (height < 200 || height > 400)
                System.out.println("height not in valid range!");
            else {
                GameStartUpMenuController.selectSize(width, height);
                System.out.println("map size " + width + " x " + height + " selected!");
                return true;
            }
        }
        return false;
    }

    @Override
    public void run() throws IOException, UnsupportedAudioFileException, LineUnavailableException, CoordinatesOutOfMap, NotInStoragesException {
        GameStartUpMenuController.startMakingGame();
        do {
            if (selectMapSize()) {
                if (selectMapMenu()) {
                    if ((gameStartUp())) {
                        GameStartUpMenuController.enterGameMenu();
                        break;
                    }
                }
            } else return;
        } while (true);
    }

    private boolean gameStartUp() throws UnsupportedAudioFileException, CoordinatesOutOfMap, LineUnavailableException, NotInStoragesException, IOException {
        do {
            Matcher matcher;
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("show menu"))
                System.out.println(Menus.getNameByObj(this));
            else if (GameStartUpMenuCommands.getMatcher(input, GameStartUpMenuCommands.CANCEL) != null) {
                cancel();
                return false;
            } else if ((input.equalsIgnoreCase("open music player")))
                Menus.MUSIC_CONTROL_MENU.getMenu().run();
            else if (GameStartUpMenuCommands.getMatcher(input, GameStartUpMenuCommands.NEXT) != null) {
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

    private boolean selectMapSize() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        System.out.println("please select the size of the map you want to play on");
        do {
            Matcher matcher;
            String input = scanner.nextLine();
            if ((matcher = GameStartUpMenuCommands.getMatcher(input, GameStartUpMenuCommands.SELECT_SIZE)) != null) {
                if (selectSize(matcher))
                    return true;
            } else if (input.equalsIgnoreCase("show menu"))
                System.out.println(Menus.getNameByObj(this));
            else if (GameStartUpMenuCommands.getMatcher(input, GameStartUpMenuCommands.CANCEL) != null) {
                cancel();
                return false;
            } else {
                SoundPlayer.play(Sounds.AKHEY);
                System.out.println("invalid command");
            }
        } while (true);
    }

    private boolean selectMapMenu() throws UnsupportedAudioFileException, CoordinatesOutOfMap, LineUnavailableException, NotInStoragesException, IOException {
        System.out.println("select the map you want");
        do {
            Matcher matcher;
            String input = scanner.nextLine();
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


    private void cancel() {
        GameStartUpMenuController.cancel();
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
}
