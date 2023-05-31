package org.example.view.menus;

import org.example.control.Controller;
import org.example.control.SoundPlayer;
import org.example.control.enums.MapEditorMenuMessages;
import org.example.control.menucontrollers.MapEditMenuController;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;
import org.example.view.enums.commands.MapEditorMenuCommands;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.regex.Matcher;

public class MapEditMenu implements Menu {

    @Override
    public void run(String input) throws IOException, UnsupportedAudioFileException, LineUnavailableException, CoordinatesOutOfMap, NotInStoragesException {
        Matcher matcher;
        while (true) {
            if ((matcher = MapEditorMenuCommands.getMatcher(input, MapEditorMenuCommands.SET_TEXTURE)) != null) {
                setTexture(matcher);
            } else if (input.equalsIgnoreCase("show menu")) {
                System.out.println(Menus.getNameByObj(this));
            } else if (input.equalsIgnoreCase("view map")) {
                controller.setScreen(Menus.MAP_VIEW_MENU.getMenu());
                controller.changeMenu(this);
            } else if ((matcher = MapEditorMenuCommands.getMatcher(input, MapEditorMenuCommands.SET_TEXTURE_RECTANGLE)) != null) {
                setTextureRectangle(matcher);
            } else if ((matcher = MapEditorMenuCommands.getMatcher(input, MapEditorMenuCommands.CLEAR)) != null) {
                clear(matcher);
            } else if ((matcher = MapEditorMenuCommands.getMatcher(input, MapEditorMenuCommands.DROP_ROCK)) != null) {
                dropRock(matcher);
            } else if ((matcher = MapEditorMenuCommands.getMatcher(input, MapEditorMenuCommands.DROP_TREE)) != null) {
                dropTree(matcher);
            } else if ((matcher = MapEditorMenuCommands.getMatcher(input, MapEditorMenuCommands.DROP_BUILDING)) != null) {
                dropBuilding(matcher);
            } else if ((matcher = MapEditorMenuCommands.getMatcher(input, MapEditorMenuCommands.DROP_UNIT)) != null) {
                dropUnit(matcher);
            } else if (input.equals("back")) {
                if (exit())
                    break;
            } else if ((input.equalsIgnoreCase("open music player"))) {
                controller.setScreen(Menus.MUSIC_CONTROL_MENU.getMenu());
                controller.changeMenu(this);
            }else {
                SoundPlayer.play(Sounds.AKHEY);
                System.out.println("invalid command");
            }

        }

    }

    private boolean exit() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if (Controller.getCurrentMap().getCastles().size() < 2) {
            System.out.println("please place at least 2 castles");
            return false;
        }
        System.out.println("do you want to save the map?");
        do {
            String input = ""; // TODO: 6/1/2023 save window
            Matcher matcher;
            if ((matcher = MapEditorMenuCommands.getMatcher(input, MapEditorMenuCommands.SAVE)) != null) {
                if (save(matcher))
                    break;
            } else if (MapEditorMenuCommands.getMatcher(input, MapEditorMenuCommands.BACK) != null)
                return false;
            else if (MapEditorMenuCommands.getMatcher(input, MapEditorMenuCommands.NO) != null)
                break;
            else System.out.println("invalid input!");
        } while (true);
        return true;
    }

    private boolean save(Matcher matcher) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        String id = Controller.removeQuotes(matcher.group("Id"));
        MapEditorMenuMessages result = MapEditMenuController.save(id);
        System.out.println(result);
        return result.equals(MapEditorMenuMessages.SUCCEED);
    }

    private void setTexture(Matcher matcher) {
        String x = matcher.group("X");
        String y = matcher.group("Y");
        String type = Controller.removeQuotes(matcher.group("Type"));
        System.out.println(MapEditMenuController.setTexture(x, y, type));
    }

    private void setTextureRectangle(Matcher matcher) {
        String x1 = matcher.group("FromX");
        String y1 = matcher.group("FromY");
        String x2 = matcher.group("ToX");
        String y2 = matcher.group("ToY");
        String type = Controller.removeQuotes(matcher.group("Type"));
        System.out.println(MapEditMenuController.setTextureRectangle(x1, y1, x2, y2, type));

    }

    private void clear(Matcher matcher) {
        String x = matcher.group("X");
        String y = matcher.group("Y");
        System.out.println(MapEditMenuController.clear(x, y));
    }

    private void dropRock(Matcher matcher) {
        String x = matcher.group("X");
        String y = matcher.group("Y");
        String direction = Controller.removeQuotes(matcher.group("Direction"));
        String type = Controller.removeQuotes(matcher.group("Type"));
        System.out.println(MapEditMenuController.dropRock(x, y, direction, type));
    }

    private void dropTree(Matcher matcher) {
        String x = matcher.group("X");
        String y = matcher.group("Y");
        String type = Controller.removeQuotes(matcher.group("Type"));
        System.out.println(MapEditMenuController.dropTree(x, y, type));
    }

    private void dropBuilding(Matcher matcher) {
        String x = matcher.group("X");
        String y = matcher.group("Y");
        String type = Controller.removeQuotes(matcher.group("Type"));
        if (type.equals("castle"))
            System.out.println(MapEditMenuController.dropCastle(Integer.parseInt(x), Integer.parseInt(y)));
        else System.out.println(MapEditMenuController.dropBuilding(x, y, type, "MapEditMenu"));
    }

    private void dropUnit(Matcher matcher) {
        String x = matcher.group("X");
        String y = matcher.group("Y");
        String type = Controller.removeQuotes(matcher.group("Type"));
        String count = matcher.group("Count");
        System.out.println(MapEditMenuController.dropUnit(x, y, type, count));
    }
}
