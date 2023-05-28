package org.example.control;


import org.example.model.DataBase;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.model.ingame.humans.Peasant;
import org.example.model.ingame.humans.army.Troop;
import org.example.model.ingame.map.Map;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {


    private static Scanner mainScanner;
    private static Map currentMap;
    private static Menus currentMenu;

    static public void run(Scanner scanner) throws IOException, UnsupportedAudioFileException, LineUnavailableException, ClassNotFoundException, CoordinatesOutOfMap, NotInStoragesException {
        mainScanner = scanner;
        DataBase.generateInfoFromJson();
        if (DataBase.isStayLogged())
            currentMenu = Menus.MAIN_MENU;
        else
            currentMenu = Menus.ENTRANCE_MENU;
        do {
            SoundPlayer.play(Sounds.BENAZAM);
            currentMenu.getMenu().run();
        } while (currentMenu != null);
        DataBase.updatePlayersXS();
        DataBase.updateMaps();
    }


    public static void removeSubsetFromTroop(ArrayList<Troop> main, ArrayList<Troop> subset) {
        for (Troop troop : subset)
            main.remove(troop);
    }

    public static void removeSubsetFromPeasant(ArrayList<Peasant> main, ArrayList<Peasant> subset) {
        for (Peasant peasant : subset)
            main.remove(peasant);
    }

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

    public static Scanner getMainScanner() {
        return mainScanner;
    }

    public static Map getCurrentMap() {
        return currentMap;
    }

    public static void setCurrentMap(Map currentMap) {
        Controller.currentMap = currentMap;
    }

    public static Menus getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(Menus currentMenu) {
        Controller.currentMenu = currentMenu;
    }
}
