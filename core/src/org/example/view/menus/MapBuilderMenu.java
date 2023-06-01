package org.example.view.menus;

import org.example.control.Controller;
import org.example.control.SoundPlayer;
import org.example.control.enums.MapEditorMenuMessages;
import org.example.control.menucontrollers.MapEditMenuController;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.model.ingame.map.Map;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;
import org.example.view.enums.commands.MapBuilderMenuCommands;
import org.example.view.enums.commands.MapEditorMenuCommands;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.regex.Matcher;

import static org.example.control.menucontrollers.MapBuilderMenuController.*;

public class MapBuilderMenu extends Menu {
    @Override
    public void run(String command) throws IOException, UnsupportedAudioFileException, LineUnavailableException,
            CoordinatesOutOfMap, NotInStoragesException {
        System.out.println("Choose a map");
        while (true) {
            if (MapBuilderMenuCommands.getMatcher(command, MapBuilderMenuCommands.ROCK_LAND) != null) {
                createRockLand();
                Map map = Controller.getCurrentMap();
                System.out.println(map);
                exit();
                break;
            } else if ((command.equalsIgnoreCase("open music player"))) {
                controller.setScreen(Menus.MUSIC_CONTROL_MENU.getMenu());
                controller.changeMenu(this);
            }else if (command.matches("show menu"))
                System.out.println(Menus.getNameByObj(this));
            else if (MapBuilderMenuCommands.getMatcher(command, MapBuilderMenuCommands.ISLAND) != null) {
                createIsland();
                Map map = Controller.getCurrentMap();
                System.out.println(map);
                exit();
                break;
            } else if (MapBuilderMenuCommands.getMatcher(command, MapBuilderMenuCommands.BASE_LAND) != null) {
                createBaseLand();
                Map map = Controller.getCurrentMap();
                System.out.println(map);
                exit();
                break;
            } else if (command.equals("back")) {
                back();
                break;
            } else {
                SoundPlayer.play(Sounds.AKHEY);
                System.out.println("invalid command");
            }
        }
    }

    private boolean exit() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
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

    private void back() {
        Controller.setCurrentMenu(Menus.GAME_START_UP_MENU);
    }
}
