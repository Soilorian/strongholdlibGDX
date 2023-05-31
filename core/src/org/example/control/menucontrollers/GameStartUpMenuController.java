package org.example.control.menucontrollers;


import org.example.Main;
import org.example.control.Controller;
import org.example.control.enums.GameStartUpMenuMessages;
import org.example.model.DataBase;
import org.example.model.Game;
import org.example.model.Player;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.model.ingame.castle.Castle;
import org.example.model.ingame.castle.Colors;
import org.example.model.ingame.castle.Empire;
import org.example.model.ingame.castle.details.Storage;
import org.example.model.ingame.map.Map;
import org.example.view.enums.Menus;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.HashMap;

public class GameStartUpMenuController {

    public static void startMakingGame() {
        GameMenuController.setCurrentGame(new Game());
        Empire empire = new Empire(DataBase.getCurrentPlayer());
        DataBase.setCurrentEmpire(empire);
        GameMenuController.getCurrentGame().getEmpires().add(empire);
        GameMenuController.getCurrentGame().getAllEmpires().add(empire);
        GameMenuController.getCurrentGame().getPlayers().add(DataBase.getCurrentPlayer());
    }


    public static String showAllMap() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map map : DataBase.getMaps()) {
            if (map.getGroundHeight() == Controller.getCurrentMap().getGroundHeight()
                    && map.getGroundWidth() == Controller.getCurrentMap().getGroundWidth())
                stringBuilder.append(map).append('\n');
        }
        if (stringBuilder.toString().isEmpty())
            stringBuilder.append("no saved maps for chosen size");
        return stringBuilder.toString();
    }

    public static GameStartUpMenuMessages selectMap(String id) throws IOException, UnsupportedAudioFileException, LineUnavailableException, CoordinatesOutOfMap, NotInStoragesException {
        if (id.equals("blank")) {
            Main.getController().setScreen(Menus.MAP_EDIT_MENU.getMenu());
            GameMenuController.getCurrentGame().setCurrentMap(Controller.getCurrentMap());
        } else if (id.equals("random")) {
            Main.getController().setScreen(Menus.MAP_BUILDER_MENU.getMenu());
            GameMenuController.getCurrentGame().setCurrentMap(Controller.getCurrentMap());
        } else {
            Map map = DataBase.getMapById(id);
            if (map == null)
                return GameStartUpMenuMessages.NO_MAP;
            Controller.setCurrentMap(map);
            GameMenuController.getCurrentGame().setCurrentMap(map);
        }
        return GameStartUpMenuMessages.SUCCESS;
    }

    public static GameStartUpMenuMessages addPlayer(String username) {
        Player playerByUsername = DataBase.getPlayerByUsername(username);
        if (playerByUsername == null)
            return GameStartUpMenuMessages.NO_PLAYER;
        else if (GameMenuController.getCurrentGame().getPlayers().contains(playerByUsername))
            return GameStartUpMenuMessages.PLAYER_ALREADY_ADDED;
        GameMenuController.getCurrentGame().addPlayer(playerByUsername);
        return GameStartUpMenuMessages.SUCCESS;
    }

    public static GameStartUpMenuMessages selectColor(String color) {
        Colors colors = Colors.getColor(color);
        if (colors == null)
            return GameStartUpMenuMessages.NO_COLOR;
        if (colorChosen(colors))
            return GameStartUpMenuMessages.ALREADY_CHOSEN;
        DataBase.getCurrentEmpire().setColor(colors);
        return GameStartUpMenuMessages.SUCCESS;
    }

    public static void enterGameMenu() {
        Controller.setCurrentMenu(Menus.GAME_MENU);
    }

    private static boolean colorChosen(Colors colors) {
        for (Empire empire : GameMenuController.getCurrentGame().getEmpires())
            if (empire.getColor() != null && empire.getColor().equals(colors)) return true;
        return false;
    }

    public static GameStartUpMenuMessages selectCastle(int castle) {
        HashMap<Integer, Castle> castles = GameMenuController.getCurrentGame().getCurrentMap().getCastles();
        Castle oldCastle = DataBase.getCurrentEmpire().getCastle();
        if (oldCastle != null) oldCastle.clearEmpire();
        if (!castles.containsKey(castle))
            return GameStartUpMenuMessages.NO_CASTLE;
        if (castles.get(castle).getEmpire() != null)
            return GameStartUpMenuMessages.ALREADY_CHOSEN;
        castles.get(castle).setEmpire(DataBase.getCurrentEmpire());
        return GameStartUpMenuMessages.SUCCESS;
    }

    public static void selectSize(int width, int height) {
        Controller.setCurrentMap(new Map(width, height, "blank map" + Map.getLastNumUsed()));
    }

    public static GameStartUpMenuMessages nextTurn() {
        if (DataBase.getCurrentEmpire().getCastle() == null)
            return GameStartUpMenuMessages.NO_CASTLE_CHOSEN;
        if (DataBase.getCurrentEmpire().getColor() == null)
            return GameStartUpMenuMessages.NO_COLOR_CHOSEN;
        for (Empire empire : GameMenuController.getCurrentGame().getEmpires())
            if (empire.getCastle() == null) {
                DataBase.setCurrentEmpire(empire);
                return GameStartUpMenuMessages.NEXT_PLAYER;
            }
        GameMenuController.setCurrentGame(GameMenuController.getCurrentGame());
        for (Empire empire : GameMenuController.getCurrentGame().getEmpires()) {
            Castle castle = empire.getCastle();
            castle.setLord();
            castle.setEmpire(empire);
            castle.setOwner(empire);
            castle.setTile();
            for (Storage value : Storage.values()) {
                if (!value.equals(Storage.MAIN_CASTLE))
                    castle.setStorages(value.getStoringPlace());
            }
        }
        return GameStartUpMenuMessages.DONE;
    }

    public static void cancel() {
        Controller.setCurrentMenu(Menus.MAIN_MENU);
    }
}
