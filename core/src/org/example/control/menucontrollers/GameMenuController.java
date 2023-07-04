package org.example.control.menucontrollers;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import org.example.control.Controller;
import org.example.control.enums.GameMenuMessages;
import org.example.model.DataBase;
import org.example.model.Game;
import org.example.model.Player;
import org.example.model.enums.Menus;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.model.ingame.castle.Building;
import org.example.model.ingame.castle.Buildings;
import org.example.model.ingame.castle.Castle;
import org.example.model.ingame.castle.Empire;
import org.example.model.ingame.humans.Peasant;
import org.example.model.ingame.humans.army.Troop;
import org.example.model.ingame.humans.army.Troops;
import org.example.model.ingame.humans.army.details.Status;
import org.example.model.ingame.humans.army.details.TrainingPlace;
import org.example.model.ingame.humans.specialworkers.Engineer;
import org.example.model.ingame.humans.specialworkers.Tunneler;
import org.example.model.ingame.map.Map;
import org.example.model.ingame.map.Tile;
import org.example.model.ingame.map.enums.Direction;
import org.example.model.ingame.map.resourses.Resource;
import org.example.model.ingame.map.resourses.Resources;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import static org.example.control.menucontrollers.EntranceMenuController.isFieldEmpty;


public class GameMenuController{
    private static final ArrayList<Troop> selectedTroops = new ArrayList<>();
    private static Game currentGame;
    private static Building selectedBuilding;
    private static boolean hasAttacked = false;
    private static final ArrayList<Tile> selectedTiles = new ArrayList<>();
    private static final ArrayList<Buildings> copiedBuildings = new ArrayList<>();

    public static boolean isThereBuilding(int x, int y) {
        return false;
    }

    public static boolean canLadder(Troop troop) {
        switch (troop.getType()) {
            case "Spearman":
            case "Maceman":
            case "LadderMan":
                return true;
            default:
                return false;
        }
    }

    public static boolean isWall(Building building) {
        switch (building.getBuildingName()) {
            case "Lookout Tower":
            case "Perimeter Tower":
            case "TURRET":
            case "Wall":
            case "Stair":
            case "Square Tower":
            case "Circle Tower":
                return true;
            default:
                return false;
        }
    }

    public static boolean isFearValid(int fear) {
        return fear >= -5 && fear <= 5;
    }

    public static boolean isNotCoordinatesValid(int x, int y) {
        return !(x >= 0 && y >= 0
                && x < Controller.getCurrentMap().getGroundHeight()
                && y < Controller.getCurrentMap().getGroundWidth());
    }

    public static boolean isCoordinatesNotDigit(String x, String y) {
        return !EntranceMenuController.isDigit(x) || !EntranceMenuController.isDigit(y);
    }

    public static String setFearRate(String fearRate) {
        if (isFieldEmpty(fearRate))
            return GameMenuMessages.EMPTY_FEAR_RATE.toString();
        if (!EntranceMenuController.isDigit(fearRate))
            return GameMenuMessages.FEAR_DIGIT.toString();
        int FearRate = Integer.parseInt(fearRate);
        if (!isFearValid(FearRate))
            return GameMenuMessages.INVALID_FEAR.toString();
        DataBase.getCurrentEmpire().setFear(FearRate);
        return GameMenuMessages.SUCCEED.toString();
    }

    public static GameMenuMessages dropBuildingGameMenu(int x, int y, Buildings buildings, Direction dir) {
        Building building = new Building(buildings);
        building.setOwner(DataBase.getCurrentEmpire());
        if (!lookAround(x, y, DataBase.getCurrentEmpire(), 5).isEmpty()) return GameMenuMessages.ENEMY_AROUND;
        if (isGate(buildings))
            currentGame.getCurrentMap().addGate(x, y, dir);
        if (isWall(building)) Controller.getCurrentMap().dropWall(x, y, building);
        return Building.build(buildings, GameMenuController.getCurrentGame().getCurrentMap().getTile(y, x));
    }

    public static boolean isGate(Buildings buildings) {
        return buildings.equals(Buildings.BIG_STONE_GATEHOUSE) || buildings.equals(Buildings.SMALL_STONE_GATEHOUSE)
                || buildings.equals(Buildings.DRAWBRIDGE);
    }


    public static GameMenuMessages fight(int x, int y) {
        removeDeads(selectedTroops);
        int totalDamage = getTotalDamage(selectedTroops);
        if (totalDamage == 0) return GameMenuMessages.NOTHING_SELECTED;
        if (!currentGame.getCurrentMap().isInRange(x, y)) return GameMenuMessages.NOT_IN_RANGE;
        Tile tile = currentGame.getCurrentMap().getTile(y, x);
        if (!tile.hasEnemies(DataBase.getCurrentEmpire())) return GameMenuMessages.NO_ENEMIES;
        if (hasAttacked) return GameMenuMessages.ALREADY_ATTACKED;
        ArrayList<Troop> enemies = tile.getEnemies(DataBase.getCurrentEmpire());
        for (Troop enemy : enemies) {
            if (enemy.getHitPoint() < totalDamage) {
                totalDamage -= enemy.getHitPoint();
                enemy.kill();
            } else {
                enemy.takeDamage(totalDamage);
                break;
            }
        }
        hasAttacked = true;
        return GameMenuMessages.SUCCEED;
    }

    private static void removeDeads(ArrayList<Troop> troops) {
        for (Iterator<Troop> iterator = troops.iterator(); iterator.hasNext(); ) {
            if ((iterator.next()).isDead())
                iterator.remove();
        }
    }


    public static GameMenuMessages attackBuilding(int x, int y) {
        removeDeads(selectedTroops);
        int totalDamage = getTotalDamage(selectedTroops);
        if (totalDamage == 0) return GameMenuMessages.NOTHING_SELECTED;
        if (!currentGame.getCurrentMap().isInRange(x, y)) return GameMenuMessages.NOT_IN_RANGE;
        Tile tile = currentGame.getCurrentMap().getTile(y, x);
        if (tile.getBuilding() == null) return GameMenuMessages.NO_BUILDING_HERE;
        if (hasAttacked) return GameMenuMessages.ALREADY_ATTACKED;
        tile.getBuilding().takeDamage(totalDamage);
        hasAttacked = true;
        return GameMenuMessages.SUCCEED;
    }

    public void undo() {
        Building building = DataBase.getCurrentEmpire().getBuildings().get(
                DataBase.getCurrentEmpire().getBuildings().size() - 1);
        if (building != null)
            building.destroy();
    }

    public void deleteBuilding() {
        if (selectedBuilding !=null)
            selectedBuilding.destroy();
        selectedBuilding = null;
    }

    public static GameMenuMessages setUnitStrategy(String type) {
        for (Status value : Status.values())
            if (value.getStatus().equals(type)) {
                for (Troop selectedTroop : selectedTroops) {
                    selectedTroop.setHumanStatus(value);
                }
                return GameMenuMessages.SUCCESS;
            }
        return GameMenuMessages.NOT_A_STRATEGY;
    }

    public static Troop findEnemies(Tile tile, Empire empire) {
        for (Troop troop : tile.getTroops())
            if (!troop.getKing().equals(empire))
                return troop;
        for (Peasant peasant : tile.getPeasants()) {
            if (peasant.getKing().equals(empire))
                return peasant;
        }
        return null;
    }

    public static ArrayList<Troop> lookAround(int x, int y, Empire empire, int range) {
        ArrayList<Troop> enemy = new ArrayList<>();
        for (int i = 0; i < range; i++) {
            for (int j = 0; j < range; j++) {
                if (Controller.getCurrentMap().isInRange(x - 2 + j, y - 2 + i))
                    for (Troop troop : Controller.getCurrentMap().getTile(y - 2 + i, x - 2 + j).getTroops())
                        if (!troop.getKing().equals(empire))
                            enemy.add(troop);
            }
        }
        return enemy;
    }

    public static int getTotalDamage(ArrayList<Troop> troops) {
        int totalDamage = 0;
        for (Troop troop : troops)
            totalDamage += troop.getDamage();
        return totalDamage;
    }

    public static String selectBuilding(int x, int y) throws UnsupportedAudioFileException, CoordinatesOutOfMap, LineUnavailableException, NotInStoragesException, IOException {
        Map currentMap = currentGame.getCurrentMap();
        if (!currentMap.isInRange(x, y))
            return GameMenuMessages.NOT_IN_RANGE.toString();
        Tile tile = currentMap.getTile(y, x);
        if (tile.getBuilding() == null)
            return GameMenuMessages.NO_BUILDING_HERE.toString();
        selectedBuilding = tile.getBuilding();
        return selectedBuilding.onClick();
    }


    public static String repair() {
        if (selectedBuilding == null)
            return GameMenuMessages.SELECT_BUILDING_NULL.toString();
        if (selectedBuilding.repair())
            return GameMenuMessages.SUCCESS.toString();
        else
            return GameMenuMessages.UNREPAIRABLE.toString();
    }

    public static GameMenuMessages selectUnit(int x, int y) {
        Map currentMap = currentGame.getCurrentMap();
        if (!currentMap.isInRange(x, y))
            return GameMenuMessages.NOT_IN_RANGE;
        Tile tile = currentMap.getTile(y, x);
        if (tile.getTroops().isEmpty())
            return GameMenuMessages.NO_TROOPS_HERE;
        if (tile.getTroopsByEmpire().get(DataBase.getCurrentEmpire().getColor()) == null)
            return GameMenuMessages.NOT_YOUR_TROOPS;
        selectedTroops.clear();
        for (Troop troop : tile.getTroops())
            if (troop.getKing().equals(DataBase.getCurrentEmpire())) {
                if (selectedTroops.isEmpty())
                    selectedTroops.add(troop);
                else if (troop.getType().equals(selectedTroops.get(0).getType()))
                    selectedTroops.add(troop);
            }
        if (selectedTroops.isEmpty()) return GameMenuMessages.EMPTY;
        return GameMenuMessages.SUCCESS;
    }

    public static GameMenuMessages moveUnit(int x, int y) {
        if (!currentGame.getCurrentMap().isInRange(x, y)) return GameMenuMessages.NOT_IN_RANGE;
        if (!currentGame.getCurrentMap().getTile(y, x).isPassable()) return GameMenuMessages.CANT_GO_THERE;
        for (Troop selectedTroop : selectedTroops)
            selectedTroop.setMovement(currentGame.getCurrentMap().getTile(y, x), false);
        return GameMenuMessages.SUCCESS;
    }


    public static GameMenuMessages patrolUnit(int x, int y) {
        if (!currentGame.getCurrentMap().isInRange(x, y)) return GameMenuMessages.NOT_IN_RANGE;
        for (Troop selectedTroop : selectedTroops)
            selectedTroop.setMovement(currentGame.getCurrentMap().getTile(y, x), true);
        return GameMenuMessages.SUCCESS;
    }


    public static GameMenuMessages pourOil(String direction) {
        for (Direction value : Direction.values()) {
            if (value.getDirection().equalsIgnoreCase(direction)) {
                for (Troop selectedTroop : selectedTroops) {
                    if (selectedTroop instanceof Engineer) {
                        Tile tile = selectedTroop.getCurrentTile();
                        int x = tile.getX();
                        int y = tile.getY();
                        switch (value) {
                            case NORTH: {
                                if (currentGame.getCurrentMap().isInRange(x, y - 1))
                                    ((Engineer) selectedTroop).pourOil((currentGame.getCurrentMap().getTile(y - 1, x)));
                                else return GameMenuMessages.CANT_GO_THERE;
                                break;
                            }
                            case EAST: {
                                if (currentGame.getCurrentMap().isInRange(x + 1, y))
                                    ((Engineer) selectedTroop).pourOil((currentGame.getCurrentMap().getTile(y, x + 1)));
                                else return GameMenuMessages.CANT_GO_THERE;
                                break;
                            }
                            case SOUTH: {
                                if (currentGame.getCurrentMap().isInRange(x, y + 1))
                                    ((Engineer) selectedTroop).pourOil((currentGame.getCurrentMap().getTile(y + 1, x)));
                                else return GameMenuMessages.CANT_GO_THERE;
                                break;
                            }
                            case WEST: {
                                if (currentGame.getCurrentMap().isInRange(x - 1, y))
                                    ((Engineer) selectedTroop).pourOil((currentGame.getCurrentMap().getTile(y, x - 1)));
                                else return GameMenuMessages.CANT_GO_THERE;
                                break;
                            }
                        }
                    }
                }
                return GameMenuMessages.SUCCESS;
            }
        }
        return GameMenuMessages.NOT_A_DIRECTION;
    }

    public static GameMenuMessages digTunnel(int x, int y) {
        if (Controller.getCurrentMap().isInRange(x, y))
            return GameMenuMessages.NOT_IN_RANGE;
        if (isTunnelerSelected()) return GameMenuMessages.NO_TUNNELER;
        for (Troop selectedTroop : selectedTroops) {
            if (selectedTroop instanceof Tunneler)
                if (!isInTunnelRange(currentGame.getCurrentMap().getTile(y, x)))
                    return GameMenuMessages.CANT_TUNNEL;
        }
        for (Troop selectedTroop : selectedTroops) {
            if (selectedTroop instanceof Tunneler)
                ((Tunneler) selectedTroop).digTunnel(currentGame.getCurrentMap().getTile(y, x));
        }
        return GameMenuMessages.SUCCESS;
    }

    private static boolean isInTunnelRange(Tile tile) {
        Tile start = null;
        int moves = 0;
        for (Troop selectedTroop : selectedTroops)
            if (selectedTroop instanceof Tunneler) {
                moves += ((Tunneler) selectedTroop).getMovesLeft();
                start = ((Tunneler) selectedTroop).getTunnelStart();
            }
        assert start != null;
        return moves >= Math.abs(tile.getX() - start.getX()) + Math.abs(tile.getY() - start.getY());
    }

    private static boolean isTunnelerSelected() {
        for (Troop selectedTroop : selectedTroops) {
            if (selectedTroop instanceof Tunneler)
                return true;
        }
        return false;
    }

    public static void disbandUnit() {
        for (int i = 0; i < selectedTroops.size(); i++)
            selectedTroops.add(i, new Peasant(selectedTroops.get(i).getKing(), selectedTroops.get(i).getCurrentTile()));
    }

    public static Building getSelectedBuilding() {
        return selectedBuilding;
    }

    public static GameMenuMessages BuildSieges(String type) {
        if (type.equals("Shield")) {
            healUp(selectedBuilding.getTileUnder().getX(), selectedBuilding.getTileUnder().getY());
        } else {
            return GameMenuMessages.NOT_A_TROOP;
        }
        return GameMenuMessages.SUCCEED;
    }


    public static void healUp(int x, int y) {
        for (Troop troop : Controller.getCurrentMap().getTile(y, x).getTroops())
            if (troop.getKing().equals(DataBase.getCurrentEmpire()))
                troop.changeHp(Math.min(200, Objects.requireNonNull(Troops.getTroopByName(troop.getType())).getHp() - troop.getHitPoint()));
    }

    public static String createUnit(String type, int count) {
        Empire currentEmpire = DataBase.getCurrentEmpire();
        final Tile placedOn = selectedBuilding.getTileUnder();
        switch (getSelectedBuilding().getBuilding()) {
            case ENGINEER_GUILD: {
                if (!type.equalsIgnoreCase(Engineer.getName()))
                    return GameMenuMessages.CANT_PRODUCE_UNIT.toString();
                else {
                    Resource price = new Resource(Resources.GOLD, Engineer.getPrice() * count);
                    if (currentEmpire.isThereNotResource(price))
                        return GameMenuMessages.NOT_ENOUGH_GOLD.toString();
                    else {
                        currentEmpire.takeResource(price);
                        for (int i = 0; i < count; i++)
                            placedOn.addPeasant(new Engineer(currentEmpire, placedOn));
                        return GameMenuMessages.SUCCESS.toString();
                    }
                }
            }
            case TUNNELERS_GUILD: {
                if (!type.equalsIgnoreCase(Tunneler.getName()))
                    return GameMenuMessages.CANT_PRODUCE_UNIT.toString();
                else {
                    Resource price = new Resource(Resources.GOLD, Tunneler.getPrice() * count);
                    if (currentEmpire.isThereNotResource(price))
                        return GameMenuMessages.NOT_ENOUGH_GOLD.toString();
                    else {
                        currentEmpire.takeResource(price);
                        for (int i = 0; i < count; i++)
                            placedOn.addPeasant(new Tunneler(currentEmpire, placedOn));
                        return GameMenuMessages.SUCCESS.toString();
                    }
                }
            }
            case BARRACK: {
                if (TrainingPlace.BARRACK.doesntHaveUnit(type))
                    return GameMenuMessages.CANT_PRODUCE_UNIT.toString();
                else {
                    Troops troop = Troops.getTroopByName(type);
                    assert troop != null;
                    if (currentEmpire.isThereNotResource(troop.getEquipments(count)))
                        return GameMenuMessages.NOT_ENOUGH_WEAPONS.toString();
                    if (currentEmpire.isThereNotResource(troop.getPrice(count)))
                        return GameMenuMessages.NOT_ENOUGH_GOLD.toString();
                    currentEmpire.takeResource(troop.getEquipments(count));
                    currentEmpire.takeResource(troop.getPrice(count));
                    for (int i = 0; i < count; i++)
                        placedOn.getTroops().add(new Troop(troop, currentEmpire, placedOn));
                    return GameMenuMessages.SUCCESS.toString();
                }
            }
            case MERCENARY_POST: {
                if (TrainingPlace.MERCENARY_POST.doesntHaveUnit(type))
                    return GameMenuMessages.CANT_PRODUCE_UNIT.toString();
                else {
                    Troops troop = Troops.getTroopByName(type);
                    assert troop != null;
                    if (currentEmpire.isThereNotResource(troop.getPrice(count)))
                        return GameMenuMessages.NOT_ENOUGH_GOLD.toString();
                    currentEmpire.takeResource(troop.getEquipments(count));
                    currentEmpire.takeResource(troop.getPrice(count));
                    for (int i = 0; i < count; i++)
                        placedOn.getTroops().add(new Troop(troop, currentEmpire, placedOn));
                    return GameMenuMessages.SUCCESS.toString();
                }
            }
            default: {
                return GameMenuMessages.CANT_PRODUCE_UNIT.toString();
            }
        }
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game game) {
        currentGame = game;
    }

    public static boolean isSiegeUnit(Troop troop) {
        return troop.getTroop().getName().equalsIgnoreCase("");
    }

    public static GameMenuMessages getGold(int i) {
        DataBase.getCurrentEmpire().addResource(new Resource(Resources.GOLD, i));
        return GameMenuMessages.ENJOY;
    }

    public static GameMenuMessages getItem(String item, int i) {
        Resources resources = Resources.getResourcesByName(item);
        if (resources == null) return GameMenuMessages.NOT_A_RESOURCE;
        DataBase.getCurrentEmpire().addResource(new Resource(resources, i));
        return GameMenuMessages.ENJOY;
    }

    public static GameMenuMessages spawnUnit(String troop, int a, int x, int y) {
        Troops troopByName = Troops.getTroopByName(troop);
        if (troopByName == null) return GameMenuMessages.NOT_A_TROOP;
        if (!currentGame.getCurrentMap().isInRange(x, y)) return GameMenuMessages.NOT_IN_RANGE;
        Tile tile = currentGame.getCurrentMap().getTile(y, x);
        for (int i = 0; i < a; i++) {
            Troop troop1 = new Troop(troopByName, DataBase.getCurrentEmpire(), tile);
            tile.addTroop(troop1);
            DataBase.getCurrentEmpire().addTroop(troop1);
        }
        return GameMenuMessages.ENJOY;
    }

    public static GameMenuMessages changePopularity(int i) {
        DataBase.getCurrentEmpire().setPopularity(i);
        return GameMenuMessages.ENJOY;
    }

    public static GameMenuMessages choosePlayer(String username) {
        Player player = DataBase.getPlayerByUsername(username);
        if (player == null) return GameMenuMessages.NOT_IN_GAME;
        for (Empire empire : currentGame.getEmpires())
            if (empire.getOwner().equals(player)) {
                DataBase.setCurrentEmpire(empire);
                return GameMenuMessages.ENJOY;
            }
        return GameMenuMessages.NOT_IN_GAME;
    }

    public static GameMenuMessages infiniteHealth() {
        for (Troop selectedTroop : selectedTroops) selectedTroop.setHitPoint(Integer.MAX_VALUE);
        return GameMenuMessages.ENJOY;
    }

    public static GameMenuMessages attackTroop(int x, int y) {
        return fight(x, y);
    }

    public static boolean nextTurn() throws CoordinatesOutOfMap, NotInStoragesException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        for (Empire empire : currentGame.getEmpires()) {
            empire.removeDeads();
            for (Troop troop : empire.getTroops()) {
                switch (troop.getHumanStatus()) {
                    case DEFENSIVE: {
                        troop.searchForEnemyWithHammingDistance(0);
                        break;
                    }
                    case OFFENSIVE: {
                        troop.searchForEnemyWithHammingDistance(2);
                        break;
                    }
                    case NATURAL: {
                        troop.searchForEnemyWithHammingDistance(1);
                        break;
                    }
                }
                troop.moveAndPatrol();
                troop.attack();
                empire.removeDeads();
            }
            for (Building building : empire.getBuildings()) building.update();
            for (Peasant peasant : empire.getPeasants()) peasant.update();
            empire.giveFood();
            empire.updatePopulation();
            empire.taxCollector();
        }
        Iterator<Empire> iterator = currentGame.getEmpires().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isLordDead())
                iterator.remove();
        }
        if (currentGame.getEmpires().size() < 2) {
            endGame();
            Controller.setCurrentMenu(Menus.MAIN_MENU);
            return true;
        }
        Empire currentEmpire = getCurrentGame().getEmpires().get(getCurrentGame().getPlayerTurn());
        while (currentEmpire == null)
            currentEmpire = getCurrentGame().getEmpires().get(getCurrentGame().getPlayerTurn());
        DataBase.setCurrentEmpire(currentEmpire);
        return false;
    }

    public static void openTradeMenu() {
        Controller.setCurrentMenu(Menus.TRADE_MENU);
    }

    private static void endGame() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        System.out.println(currentGame.getEmpires().get(0).getOwner().getSlogan());
        for (Empire empire : currentGame.getAllEmpires()) {
            int score = 0;
            score += empire.getCastle().getLord().getHitPoint() * 50;
            score += empire.getCastle().getHp() * 5;
            for (Resource resource : empire.getResources())
                score += resource.getAmount() * resource.getResourceName().getSellingPrice();
            for (Building building : empire.getBuildings()) score += building.getHp();
            empire.getOwner().updateMaxScore(score);
        }
        DataBase.updatePlayersXS();
    }


    public static String showCastleCoordinates() {
        Castle castle = DataBase.getCurrentEmpire().getCastle();
        return "castle coordinates are:\nx: " + castle.getX() + " y: " + castle.getY();
    }

    public static void addPeasant() {
        DataBase.getCurrentEmpire().addPopulation();
    }

    public static boolean isChurch(Buildings building) {
        return building.equals(Buildings.CATHEDRAL) || building.equals(Buildings.CHURCH);
    }

    public static void removeSubsetFromTroop(ArrayList<Troop> main, ArrayList<Troop> subset) {
        for (Troop troop : subset)
            main.remove(troop);
    }

    public static void removeSubsetFromPeasant(ArrayList<Peasant> main, ArrayList<Peasant> subset) {
        for (Peasant peasant : subset)
            main.remove(peasant);
    }

    public static Texture getMapPrev(Map selectedMap, int scale) {
        Pixmap pixmap = new Pixmap(selectedMap.getGroundWidth() * scale, selectedMap.getGroundHeight() * scale,
                Pixmap.Format.RGBA8888);
        selectedMap.setUpPixmap(pixmap, scale);
        Texture texture = new Texture(pixmap);
//        pixmap.dispose();
        return texture;
    }

    public static Pixmap getMapPrevPixmap(Map selectedMap, int scale) {
        Pixmap pixmap = new Pixmap(selectedMap.getGroundWidth() * scale, selectedMap.getGroundHeight() * scale,
                Pixmap.Format.RGBA8888);
        selectedMap.setUpPixmap(pixmap, scale);
        return pixmap;
    }

    public static ArrayList<Tile> getSelectedTiles() {
        return selectedTiles;
    }

    public static boolean addSelectedTile(Tile tile){
        if (selectedTiles.contains(tile)){
            selectedTiles.remove(tile);
            return true;
        }
        selectedTiles.add(tile);
        return false;
    }

    public static void transferSelectedIntoCopy() {

    }

    public static void placeCopiedBuildings() {

    }
}
