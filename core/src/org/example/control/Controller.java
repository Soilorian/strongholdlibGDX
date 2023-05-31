package org.example.control;


import com.badlogic.gdx.Game;
import org.example.model.DataBase;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.model.ingame.humans.Peasant;
import org.example.model.ingame.humans.army.Troop;
import org.example.model.ingame.map.Map;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;
import org.example.view.menus.Menu;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller extends Game {
    private static Scanner mainScanner;
    private static Map currentMap;
    private static Menus currentMenu;

    public static String removeQuotes(String string) {
        if (string.isEmpty()) return string;
        if (string.charAt(0) == '\"' && string.charAt(string.length() - 1) == '\"')
            return string.substring(1, string.length() - 1);
        return string;
    }

    public static Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches() ? matcher : null;
    }

    public static boolean isFieldEmpty(String... str) {
        for (String s : str) {
            if (s.isEmpty())
                return true;
        }
        return false;
    }

    public static Map getCurrentMap() {
        return currentMap;
    }

    public static void setCurrentMap(Map currentMap) {
        Controller.currentMap = currentMap;
    }

    public void changeMenu(){
        
    }

    @Override
    public void create() {
        DataBase.generateInfoFromJson();
        if (DataBase.isStayLogged())
            currentMenu = Menus.MAIN_MENU;
        else
            currentMenu = Menus.ENTRANCE_MENU;
        do {
            try {
                SoundPlayer.play(Sounds.BENAZAM);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println("can't play sound");
                throw new RuntimeException(e);
            }
            setScreen(currentMenu.getMenu());
        } while (currentMenu != null);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        try {
            DataBase.updatePlayersXS();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        try {
            DataBase.updateMaps();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
}
