package org.example.view.menus.ingamemenus;


import org.example.control.Controller;
import org.example.control.SoundPlayer;
import org.example.control.enums.GameMenuMessages;
import org.example.control.enums.GameStartUpMenuMessages;
import org.example.control.menucontrollers.EntranceMenuController;
import org.example.control.menucontrollers.inGameControllers.MapViewMenuController;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;
import org.example.view.enums.commands.InGameMenuCommands;
import org.example.view.menus.Menu;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.regex.Matcher;

public class MapViewMenu implements Menu {
    @Override
    public void run(String input) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        System.out.println("tell me where do you want to view");
        do {
            Matcher matcher;
            if (input.equalsIgnoreCase("show menu"))
                System.out.println(Menus.getNameByObj(this) + " first do while");
            else if (InGameMenuCommands.getMatcher(input, InGameMenuCommands.BACK) != null)
                return;
            else if ((matcher = InGameMenuCommands.getMatcher(input, InGameMenuCommands.showMeXY)) != null) {
                if (showXY(matcher)) break;
            } else {
                SoundPlayer.play(Sounds.AKHEY);
                System.out.println("invalid command");
            }
        } while (true);
        do {// TODO: 6/1/2023 split to 2 menus
            Matcher matcher;
            if ((matcher = InGameMenuCommands.getMatcher(input, InGameMenuCommands.MOVE_MAP)) != null)
                moveMap(matcher);
            else if (input.equalsIgnoreCase("show menu"))
                System.out.println(Menus.getNameByObj(this) + " second do while");
            else if (InGameMenuCommands.getMatcher(input, InGameMenuCommands.BACK) != null)
                break;
            else if ((matcher = InGameMenuCommands.getMatcher(input, InGameMenuCommands.SHOW_DETAILS)) != null)
                showDetails(matcher);
            else if (input.equalsIgnoreCase("show current coords"))
                System.out.println(MapViewMenuController.getViewingY() + " , " + MapViewMenuController.getViewingX());
            else {
                SoundPlayer.play(Sounds.AKHEY);
                System.out.println("invalid command");
            }
        } while (true);
    }

    private boolean showXY(Matcher matcher) {
        String xToString = Controller.removeQuotes(matcher.group("X"));
        String yToString = Controller.removeQuotes(matcher.group("Y"));
        if (!checkCoordinates(xToString, yToString))
            return false;
        MapViewMenuController.setViewingX(Integer.parseInt(xToString));
        MapViewMenuController.setViewingY(Integer.parseInt(yToString));
        System.out.println(MapViewMenuController.printMap());
        return true;
    }

    private void showDetails(Matcher matcher) {
        String xToString = Controller.removeQuotes(matcher.group("X"));
        String yToString = Controller.removeQuotes(matcher.group("Y"));
        if (checkCoordinates(xToString, yToString))
            System.out.println(MapViewMenuController.showDetails(Integer.parseInt(xToString), Integer.parseInt(yToString)));
    }

    private boolean checkCoordinates(String x, String y) {
        if (Controller.isFieldEmpty(x, y))
            System.out.println(GameStartUpMenuMessages.EMPTY_FIELD);
        else if (!EntranceMenuController.isDigit(x, y))
            System.out.println("not valid coordinates!\ncoordinates should only contain digits");
        else return true;
        return false;
    }

    private void moveMap(Matcher matcher) {
        String[] moves = Controller.removeQuotes(matcher.group("Direction")).split(" ");
        for (String move : moves) {
            if (!move.matches("(up)|(down)|(right)|(left)")) {
                System.out.println(GameMenuMessages.WRONG_DIRECTION);
                return;
            }
        }
        int lastX = MapViewMenuController.getViewingX();
        int lastY = MapViewMenuController.getViewingY();
        for (String move : moves) {
            switch (move) {
                case "up" :{
                    lastY--;
                    break;
                }
                case "down" :{
                    lastY++;
                    break;
                }
                case "left" :{
                    lastX--;
                    break;
                }
                case "right" : {
                    lastX++;
                    break;
                }
            }
        }
        if (!Controller.getCurrentMap().isInRange(lastX, lastY))
            System.out.println(GameMenuMessages.NOT_IN_RANGE);
        else {
            MapViewMenuController.setViewingX(lastX);
            MapViewMenuController.setViewingY(lastY);
            System.out.println(MapViewMenuController.printMap());
        }
    }
}


