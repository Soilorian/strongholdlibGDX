package org.example.view.menus;


import org.example.control.Controller;
import org.example.control.SoundPlayer;
import org.example.control.menucontrollers.MainMenuController;
import org.example.model.DataBase;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;
import org.example.view.enums.commands.EntranceMenuCommands;
import org.example.view.enums.commands.GameStartUpMenuCommands;
import org.example.view.enums.commands.MainMenuCommands;
import org.example.view.enums.commands.MapEditorMenuCommands;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.regex.Matcher;

public class MainMenu implements Menu {

    @Override
    public void run() throws UnsupportedAudioFileException, LineUnavailableException, IOException, CoordinatesOutOfMap, NotInStoragesException {
        System.out.println("entered " + Menus.getNameByObj(this));
        do {
            String input = scanner.nextLine();
            if (MainMenuCommands.getMatcher(input, MainMenuCommands.START_GAME) != null) {
                startNewGame();
                break;
            } else if ((input.equalsIgnoreCase("open music player")))
                Menus.MUSIC_CONTROL_MENU.getMenu().run();
            else if (input.equalsIgnoreCase("show menu")) {
                System.out.println(Menus.getNameByObj(this));
            } else if (MainMenuCommands.getMatcher(input, MainMenuCommands.PROFILE) != null) {
                profile();
                break;
            } else if (MainMenuCommands.getMatcher(input, MainMenuCommands.SETTINGS) != null) {
                settings();
                break;
            } else if (MainMenuCommands.getMatcher(input, MainMenuCommands.MAP_EDITOR) != null) {
                mapEditor();
                break;
            } else if (MainMenuCommands.getMatcher(input, MainMenuCommands.LOGOUT) != null) {
                DataBase.clearStayLogged();
                logout();
                break;
            } else if (EntranceMenuCommands.getMatcher(input, EntranceMenuCommands.EXIT) != null) {
                Controller.setCurrentMenu(null);
                break;
            } else {
                SoundPlayer.play(Sounds.AKHEY);
                System.out.println("invalid command");
            }
        } while (true);
    }

    private void mapEditor() throws UnsupportedAudioFileException, CoordinatesOutOfMap, LineUnavailableException, NotInStoragesException, IOException {
        System.out.println("please select the size of your map");
        do {
            String input = scanner.nextLine();
            Matcher matcher;
            if ((matcher = GameStartUpMenuCommands.getMatcher(input, GameStartUpMenuCommands.SELECT_SIZE)) != null) {
                if (GameStartUpMenu.selectSize(matcher))
                    break;
            } else if (MapEditorMenuCommands.getMatcher(input, MapEditorMenuCommands.BACK) != null)
                return;
            else if (MapEditorMenuCommands.getMatcher(input, MapEditorMenuCommands.NO) != null)
                break;
            else System.out.println("invalid input!");
        } while (true);
        Menus.MAP_EDIT_MENU.getMenu().run();
    }

    public void startNewGame() {
        MainMenuController.startNewGame();
    }

    public void profile() {
        MainMenuController.profile();
    }

    public void settings() {
        // TODO: 5/11/2023 for Graphics
        System.out.println("this menu will be completed for the 2nd faz");
    }

    public void logout() {
        MainMenuController.logout();
    }


}
