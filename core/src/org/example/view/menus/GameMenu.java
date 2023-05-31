package org.example.view.menus;

import org.example.control.Controller;
import org.example.control.SoundPlayer;
import org.example.control.enums.EntranceMenuMessages;
import org.example.control.enums.GameMenuMessages;
import org.example.control.enums.GameStartUpMenuMessages;
import org.example.control.menucontrollers.EntranceMenuController;
import org.example.control.menucontrollers.GameMenuController;
import org.example.model.DataBase;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;
import org.example.view.enums.commands.GameMenuCommands;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.regex.Matcher;

public class GameMenu implements Menu {

    public void run(String command) throws IOException, UnsupportedAudioFileException, LineUnavailableException, CoordinatesOutOfMap, NotInStoragesException {
        Matcher matcher;
        while (true) {
            if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.MOVE_UNIT)) != null)
                moveUnit(matcher);
            else if (command.equalsIgnoreCase("show menu"))
                System.out.println(Menus.getNameByObj(this));
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.DROP_BUILDING)) != null)
                dropBuilding(matcher);
            else if (GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_POPULARITY_FACTORS) != null)
                showPopularityFactors();
            else if (GameMenuCommands.getMatcher(command, GameMenuCommands.REPAIR) != null)
                repair();
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.CHANGE_FEAR_RATE)) != null)
                setFearRate(matcher);
            else if ((GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_FEAR_RATE) != null))
                showFearRate();
            else if ((GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_POPULARITY) != null))
                showPopularity();
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SELECT_BUILDING)) != null)
                selectBuilding(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SELECT_UNIT)) != null)
                selectUnit(matcher);
            else if (GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_MAP) != null)
                showMap();
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.PATROL_UNIT)) != null)
                patrolUnit(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SET_STRATEGY)) != null)
                setUnitStrategy(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.ATTACK)) != null)
                attack(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.POUR_OIL)) != null)
                pourOil(matcher);
            else if (GameMenuCommands.getMatcher(command, GameMenuCommands.DISBAND) != null)
                disbandUnit();
            else if (GameMenuCommands.getMatcher(command, GameMenuCommands.TRADE) != null){
                controller.setScreen(Menus.TRADE_MENU.getMenu());
                controller.changeMenu(this);
            }
            else if (GameMenuCommands.getMatcher(command, GameMenuCommands.NEXT_TURN) != null) {
                if (nextTurn())
                    break;
            } else if (GameMenuCommands.getMatcher(command, GameMenuCommands.OPEN_TRADE_MENU) != null)
                GameMenuController.openTradeMenu();
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.DIG_TUNNEL)) != null)
                digTunnel(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.GET_GOLD)) != null)
                getGold(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.GET_ITEM)) != null)
                getItem(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SPAWN_TROOP)) != null)
                spawnTroop(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.CHANGE_POPULARITY)) != null)
                changePopularity(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.CHOOSE_PLAYER)) != null)
                choosePlayer(matcher);
            else if (GameMenuCommands.getMatcher(command, GameMenuCommands.INFINITE_HEALTH) != null)
                infiniteHealth();
            else if ((GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_PLAYER)) != null)
                showCurrentPlayer();
            else if ((command.equalsIgnoreCase("open music player"))) {
                controller.setScreen(Menus.MUSIC_CONTROL_MENU.getMenu());
                controller.changeMenu(this);
            }else if (command.equalsIgnoreCase("show castle coordinates"))
                System.out.println(GameMenuController.showCastleCoordinates());
            else {
                SoundPlayer.play(Sounds.AKHEY);
                System.out.println("invalid command");
            }
        }


    }

    private boolean nextTurn() throws CoordinatesOutOfMap, NotInStoragesException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        return GameMenuController.nextTurn();
    }

    private void attack(Matcher matcher) {
        String x = Controller.removeQuotes(matcher.group("X"));
        String y = Controller.removeQuotes(matcher.group("Y"));
        boolean attackingABuilding = false;
        try {
            if (matcher.group("option") != null)
                attackingABuilding = true;
        } catch (Exception ignored) {
        }
        if (checkCoordinates(x, y))
            if (attackingABuilding)
                System.out.println(GameMenuController.attackBuilding(Integer.parseInt(x), Integer.parseInt(y)));
            else {
                System.out.println(GameMenuController.attackTroop(Integer.parseInt(x), Integer.parseInt(y)));
            }
    }

    private void showMap() throws IOException, UnsupportedAudioFileException, LineUnavailableException, CoordinatesOutOfMap, NotInStoragesException {
        controller.setScreen(Menus.MAP_VIEW_MENU.getMenu());
        controller.changeMenu(this);
    }

    private void infiniteHealth() {
        System.out.println(GameMenuController.infiniteHealth() + "immune troops");
    }

    private void choosePlayer(Matcher matcher) {
        String username = Controller.removeQuotes(matcher.group("Player"));
        GameMenuMessages result = GameMenuController.choosePlayer(username);
        if (result.equals(GameMenuMessages.ENJOY)) System.out.println(result + "choice");
        else System.out.println(result);
    }

    private void changePopularity(Matcher matcher) {
        String amount = Controller.removeQuotes(matcher.group("Popularity"));
        if (Controller.isFieldEmpty(amount))
            System.out.println(EntranceMenuMessages.EMPTY_FIELD);
        else if (!EntranceMenuController.isDigit(amount)) {
            System.out.println("invalid amount\ndummy you can't write in english here");
            return;
        }
        GameMenuMessages result = GameMenuController.changePopularity(Integer.parseInt(amount));
        if (result.equals(GameMenuMessages.ENJOY))
            System.out.println(result + amount + " popularity");
        else System.out.println(result);
    }

    private void spawnTroop(Matcher matcher) {
        String x = Controller.removeQuotes(matcher.group("X"));
        String y = Controller.removeQuotes(matcher.group("Y"));
        String amount = Controller.removeQuotes(matcher.group("Amount"));
        String troop = Controller.removeQuotes(matcher.group("Troop"));
        if (Controller.isFieldEmpty(amount, x, y, troop))
            System.out.println(EntranceMenuMessages.EMPTY_FIELD);
        else if (!EntranceMenuController.isDigit(amount, x, y))
            System.out.println("invalid amount\ndummy you can't write in english here");
        else if (checkCoordinates(x, y)) {
            GameMenuMessages result = GameMenuController.spawnUnit(troop, Integer.parseInt(amount), Integer.parseInt(x), Integer.parseInt(y));
            if (result.equals(GameMenuMessages.ENJOY)) System.out.println(result + "new " + amount + " " + troop);
            System.out.println(result);
        }
    }

    private void getItem(Matcher matcher) {
        String amount = Controller.removeQuotes(matcher.group("Amount"));
        String item = Controller.removeQuotes(matcher.group("Item"));
        if (Controller.isFieldEmpty(amount, item))
            System.out.println(EntranceMenuMessages.EMPTY_FIELD);
        else if (!EntranceMenuController.isDigit(amount)) {
            System.out.println("invalid amount\ndummy you can't write in english here");
            return;
        }
        GameMenuMessages result = GameMenuController.getItem(item, Integer.parseInt(amount));
        if (result.equals(GameMenuMessages.ENJOY))
            System.out.println(result + amount + " of " + item);
        else System.out.println(result);
    }

    private void getGold(Matcher matcher) {
        String amount = Controller.removeQuotes(matcher.group("Amount"));
        if (Controller.isFieldEmpty(amount))
            System.out.println(EntranceMenuMessages.EMPTY_FIELD);
        else if (!EntranceMenuController.isDigit(amount)) {
            System.out.println("invalid amount\ndummy you can't write in english here");
            return;
        }
        GameMenuMessages result = GameMenuController.getGold(Integer.parseInt(amount));
        if (result.equals(GameMenuMessages.ENJOY))
            System.out.println(result + amount + " Golds");
        else System.out.println(result);
    }

    private void showPopularityFactors() {
        System.out.println(GameMenuMessages.POPULARITY_FACTORS);
    }

    private void showCurrentPlayer() {
        System.out.println(DataBase.getCurrentEmpire().getOwner().getNickname() + ", " + DataBase.getCurrentEmpire().getColor());
    }

    private void showPopularity() {
        System.out.println(GameMenuMessages.POPULARITY.toString()
                + (DataBase.getCurrentEmpire().getPopularity()));
    }

    private void showFearRate() {
        System.out.println(GameMenuMessages.FEAR_RATE.toString() + DataBase.getCurrentEmpire().getFear());
    }

    private void setFearRate(Matcher matcher) {
        String fear = matcher.group("Rate");
        System.out.println(GameMenuController.setFearRate(fear));
    }

    public void dropBuilding(Matcher matcher) {
        String x = matcher.group("X");
        String y = matcher.group("Y");
        String type = Controller.removeQuotes(matcher.group("Type"));
        String direction = Controller.removeQuotes(matcher.group("Direction"));
        System.out.println(GameMenuController.dropBuildingGameMenu(x, y, type, direction));
    }

    public void selectBuilding(Matcher matcher) throws IOException, UnsupportedAudioFileException, LineUnavailableException, CoordinatesOutOfMap, NotInStoragesException {
        String x = Controller.removeQuotes(matcher.group("X"));
        String y = Controller.removeQuotes(matcher.group("Y"));
        if (checkCoordinates(x, y))
            System.out.println(GameMenuController.selectBuilding(Integer.parseInt(x), Integer.parseInt(y)));
    }

    private boolean checkCoordinates(String x, String y) {
        if (Controller.isFieldEmpty(x, y))
            System.out.println(GameStartUpMenuMessages.EMPTY_FIELD);
        else if (!EntranceMenuController.isDigit(x, y))
            System.out.println("not valid coordinates!\ncoordinates should only contain digits");
        else return true;
        return false;
    }


    public void repair() {
        System.out.println(GameMenuController.repair());
    }

    public void selectUnit(Matcher matcher) {
        String x = Controller.removeQuotes(matcher.group("X"));
        String y = Controller.removeQuotes(matcher.group("Y"));
        if (checkCoordinates(x, y))
            System.out.println(GameMenuController.selectUnit(Integer.parseInt(x), Integer.parseInt(y)));
    }

    public void moveUnit(Matcher matcher) {
        String x = Controller.removeQuotes(matcher.group("X"));
        String y = Controller.removeQuotes(matcher.group("Y"));
        if (checkCoordinates(x, y))
            System.out.println(GameMenuController.moveUnit(Integer.parseInt(x), Integer.parseInt(y)));
    }

    public void patrolUnit(Matcher matcher) {
        String x = Controller.removeQuotes(matcher.group("X"));
        String y = Controller.removeQuotes(matcher.group("Y"));
        if (checkCoordinates(x, y))
            System.out.println(GameMenuController.patrolUnit(Integer.parseInt(x), Integer.parseInt(y)));
    }

    public void setUnitStrategy(Matcher matcher) {
        String strategy = Controller.removeQuotes(matcher.group("Strategy"));
        System.out.println(GameMenuController.setUnitStrategy(strategy));
    }

    public void pourOil(Matcher matcher) {
        String direction = Controller.removeQuotes(matcher.group("Direction"));
        System.out.println(GameMenuController.pourOil(direction));
    }

    public void digTunnel(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        System.out.println(GameMenuController.digTunnel(x, y));
    }

    public void disbandUnit() {
        GameMenuController.disbandUnit();
    }


}
