package org.example.model.ingame.castle;


import org.example.control.menucontrollers.GameMenuController;
import org.example.model.Player;
import org.example.model.enums.FoodRateDetail;
import org.example.model.enums.TaxDetail;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.model.ingame.castle.details.Storage;
import org.example.model.ingame.humans.Peasant;
import org.example.model.ingame.humans.army.Troop;
import org.example.model.ingame.humans.army.details.Status;
import org.example.model.ingame.map.resourses.Resource;
import org.example.model.ingame.map.resourses.Resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Empire {
    private final Player owner;
    private final ArrayList<Resource> resources = new ArrayList<>();
    private final ArrayList<Building> stockpiles = new ArrayList<>();
    private final ArrayList<Building> granaries = new ArrayList<>();
    private final ArrayList<Building> armouries = new ArrayList<>();
    private final ArrayList<Building> buildings = new ArrayList<>();
    private final ArrayList<Peasant> peasants = new ArrayList<>();
    private int popularity = 1;
    private int fear = 0;
    private Castle castle;
    private Colors color;
    private int churchCount = 0;
    private ArrayList<Troop> troops = new ArrayList<>();
    private int resourceCount = 0;
    private int foodCount = 0;
    private int weaponCount = 0;
    private FoodRateDetail foodRate = FoodRateDetail.ZERO;

    private TaxDetail tax = TaxDetail.ZERO;
    private int maxPeasant = 8;

    public Empire(Player king) {
        owner = king;
        generateResources();
    }

    private void generateResources() {
        for (Resources value : Resources.values())
            resources.add(new Resource(value, 100));
    }

    public void updatePopulation() {
        if (popularity >= 0) {
            for (int i = 0; i < popularity && peasants.size() < maxPeasant; i++)
                peasants.add(new Peasant(this, castle.getTileUnder()));
        } else {
            ArrayList<Peasant> subset = new ArrayList<>();
            int pop = popularity;
            for (Peasant peasant : peasants)
                if (peasant.getHumanStatus().equals(Status.NOTHINGNESS)) {
                    if (pop == 0) break;
                    subset.add(peasant);
                    pop++;
                }
            if (pop < 0) for (Peasant peasant : peasants) {
                if (pop == 0) break;
                subset.add(peasant);
                pop++;
            }
            GameMenuController.removeSubsetFromPeasant(peasants, subset);
        }
    }

    public void giveFood() {
        ArrayList<Resources> foods = Storage.GRANARY.getStoringResources();
        HashMap<Resources, Integer> individualFoods = new HashMap<>();
        int foodNeeded = (int) ((double) peasants.size() / 4 * foodRate.getFoodPerPeasant());
        if (foodNeeded == 0) return;
        int totalFood = totalResources(foods);
        if (totalFood < foodNeeded) {
            foodRate = FoodRateDetail._TWO;
            return;
        }
        popularity += 4;
        for (Resources food : foods) {
            if (isThereNotResource(new Resource(food, 1))) popularity--;
            individualFoods.put(food, totalResources(new ArrayList<>(Collections.singleton(food))) / totalFood);
        }
        for (Resources food : foods) takeResource(new Resource(food, individualFoods.get(food) * foodNeeded));
    }

    public Player getOwner() {
        return owner;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public void changePopularity(int popularity) {
        int pop = this.popularity;
        this.popularity += popularity;
//        ((GameMenu) Menus.GAME_MENU.getMenu()).changeAnimationBasedOnPopularity(pop);
    }

    public int getFear() {
        return fear;
    }


    public void setFear(int fear) {
        changePopularity(-this.fear);
        changePopularity(fear);
        this.fear = fear;
    }

    public TaxDetail getTax() {
        return tax;
    }

    public void setTaxRate(int taxRate) {
        changePopularity(-tax.getPopularity());
        tax = TaxDetail.getTaxByRate(taxRate);
        assert tax != null;
        changePopularity(tax.getPopularity());
    }

    public void taxCollector() {
        int population = peasants.size();
        int taxes = (int) Math.floor(population * tax.getTaxPerPeasant());
        if (!takeResource(new Resource(Resources.GOLD, taxes))) setTaxRate(0);
    }

    public Colors getColor() {
        return color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    public FoodRateDetail getFoodRate() {
        return foodRate;
    }

    public void setFoodRate(int foodRate) {
        changePopularity(-this.foodRate.getPopularity());
        this.foodRate = FoodRateDetail.getFoodByRate(foodRate);
        assert this.foodRate != null;
        changePopularity(this.foodRate.getPopularity());
    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }

    public void setTroops(ArrayList<Troop> troops) {
        this.troops = troops;
    }

    public ArrayList<Peasant> getPeasants() {
        return peasants;
    }

    public boolean isThereNotResource(Resource consumingResource) {
        return resources.get(resources.indexOf(consumingResource)).getAmount() < consumingResource.getAmount();
    }

    public boolean isThereNotResource(ArrayList<Resource> resources) {
        for (Resource resource : resources)
            if (isThereNotResource(resource)) return true;
        return false;
    }

    public boolean takeResource(Resource resource) {
        if (isThereNotResource(resource)) return false;
        Resource fromStorage = resources.get(resources.indexOf(resource));
        fromStorage.changeAmount(resource.getAmount() * -1);
        return true;
    }

    public boolean takeResource(ArrayList<Resource> resources) {
        if (isThereNotResource(resources)) return false;
        for (Resource resource : resources) {
            takeResource(resource);
        }
        return true;
    }

    public boolean addResource(Resource resource) {
        if (Storage.STOCKPILE.getStoringResources().contains(resource.getResourceName())) {
            if (resourceCount + resource.getAmount() > stockpiles.size() * Buildings.STOCKPILE.getCapacity())
                return false;
            resourceCount += resource.getAmount();
        } else if (Storage.GRANARY.getStoringResources().contains(resource.getResourceName())) {
            if (foodCount + resource.getAmount() > granaries.size() * Buildings.GRANARY.getCapacity()) return false;
            foodCount += resource.getAmount();
        } else if (Storage.ARMOURY.getStoringResources().contains(resource.getResourceName())) {
            if (weaponCount + resource.getAmount() > armouries.size() * Buildings.ARMOURY.getCapacity()) return false;
            weaponCount += resource.getAmount();
        }
        resources.get(resources.indexOf(resource)).changeAmount(resource.getAmount());
        return true;
    }

    public Castle getCastle() {
        return castle;
    }

    public void setCastle(Castle castle) {
        this.castle = castle;
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void addBuilding(Building building) {
        if (building.getCapacity() != 0) switch (building.getBuildingName()) {
            case "stockpile" :{
                stockpiles.add(building);
                break;
            }
            case "granary" :{
                granaries.add(building);
                break;
            }
            case "armoury" :{
                armouries.add(building);
                break;
            }
        }
        buildings.add(building);
        building.setOwner(this);
    }

    public Building whereToGet(Resource resource, int x, int y) throws NotInStoragesException, CoordinatesOutOfMap {
        Resources resourceType = resource.getResourceName();
        if (Storage.STOCKPILE.getStoringResources().contains(resourceType)) return findClosest(x, y, stockpiles);
        else if (Storage.GRANARY.getStoringResources().contains(resourceType)) return findClosest(x, y, granaries);
        else if (Storage.ARMOURY.getStoringResources().contains(resourceType)) return findClosest(x, y, armouries);
        else if (Storage.MAIN_CASTLE.getStoringResources().contains(resourceType)) return castle;
        else throw new NotInStoragesException();
    }

    private Building findClosest(int x, int y, ArrayList<Building> findIn) throws UnknownError, CoordinatesOutOfMap {
        if (!GameMenuController.getCurrentGame().getCurrentMap().isInRange(x, y)) throw new CoordinatesOutOfMap();
        if (findIn.isEmpty()) return null;
        long distance = 1000000;
        Building building = null;
        for (Building armoury : findIn) {
            int s = pythagorean(armoury.getTileUnder().getX() - x, armoury.getTileUnder().getY() - y);
            if (s < distance) {
                distance = s;
                building = armoury;
            }
        }
        if (building == null) throw new UnknownError("no building found for the shortest distance");
        return building;
    }

    private int pythagorean(int x, int y) {
        return (int) (Math.pow(x, 2) + Math.pow(y, 2));
    }

    public int totalResources(ArrayList<Resources> storage) {
        int finalCount = 0;
        for (Resources storingResource : storage)
            for (Resource resource : resources)
                if (resource.getResourceName().equals(storingResource)) finalCount += resource.getAmount();
        return finalCount;
    }

    public void removeDeads() {
        ArrayList<Troop> deads = new ArrayList<>();
        for (Troop troop : troops)
            if (troop.isDead()) {
                troop.getCurrentTile().getTroops().remove(troop);
                deads.add(troop);
            }
        for (Peasant peasant : peasants) {
            if (peasant.isDead()) {
                peasant.getCurrentTile().getPeasants().remove(peasant);
                peasant.getWorkplace().getWorkers().remove(peasant);
                deads.add(peasant);
            }
        }
        GameMenuController.removeSubsetFromTroop(troops, deads);
    }

    public void addTroop(Troop troop1) {
        troops.add(troop1);
    }

    public boolean isLordDead() {
        return castle.getLord().isDead();
    }

    public void addPopulation() {
        maxPeasant += 8;
    }

    public void churchDestroyed() {
        churchCount--;
        if (churchCount < 2)
            popularity -= 2;
    }

    public void addChurch() {
        churchCount++;
        if (churchCount <= 2)
            popularity += 2;
    }

    public void hovelDestroyed() {
        maxPeasant -= 8;
    }

    public void storageDestroyed(Building building) {
        for (Storage value : Storage.values()) {
            if (value.getStoringPlace().equals(building.getBuilding())) {
                for (Resources storingResource : value.getStoringResources()) {
                    for (Resource resource : resources)
                        if (resource.getResourceName().equals(storingResource))
                            resource.setAmount(0);
                }
                return;
            }
        }
    }

    public int getReligion() {
        return churchCount;
    }
}
