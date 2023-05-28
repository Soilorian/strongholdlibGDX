package org.example.model.ingame.castle;

import org.example.control.menucontrollers.GameMenuController;
import org.example.model.DataBase;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.model.ingame.castle.details.BuildingStatus;
import org.example.model.ingame.castle.details.Storage;
import org.example.model.ingame.castle.traps.DogCage;
import org.example.model.ingame.castle.traps.KillingPit;
import org.example.model.ingame.humans.Peasant;
import org.example.model.ingame.humans.army.Troop;
import org.example.model.ingame.map.Tile;
import org.example.model.ingame.map.enums.Direction;
import org.example.model.ingame.map.resourses.Resource;
import org.example.model.ingame.map.resourses.Resources;
import org.example.view.enums.Menus;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Building {
    protected final Buildings building;
    protected final String buildingName;
    protected final Resources consumingResource;
    protected final ArrayList<Peasant> workers = new ArrayList<>();
    protected final int workersRequired;
    protected final Menus menu;
    protected final int capacity;
    protected final int producingAmount;
    protected BuildingStatus buildingStatus = BuildingStatus.DELIVERING_GOODS;
    protected Empire owner;
    protected Tile tileUnder;
    protected int hp;
    protected Resources producingResource;
    protected Resource holder = null;
    protected boolean onFire = false;
    protected Direction direction;


    public Building(Buildings buildings) {
        this.buildingName = buildings.getBuildingName();
        this.hp = buildings.getDurability().getHp();
        this.consumingResource = buildings.getConsumingResource();
        this.producingResource = buildings.getProducingResource();
        this.capacity = buildings.getCapacity();
        this.workersRequired = buildings.getWorkersRequired();
        this.producingAmount = buildings.getProducingAmount();
        if (workersRequired == 0) buildingStatus = BuildingStatus.USELESS;
        this.building = buildings;
        this.menu = buildings.getMenus();
    }

    public Building(Buildings buildings, Tile tile) {
        this.buildingName = buildings.getBuildingName();
        this.hp = buildings.getDurability().getHp();
        this.consumingResource = buildings.getConsumingResource();
        this.producingResource = buildings.getProducingResource();
        this.capacity = buildings.getCapacity();
        this.workersRequired = buildings.getWorkersRequired();
        this.building = buildings;
        this.tileUnder = tile;
        this.menu = buildings.getMenus();
        producingAmount = buildings.getProducingAmount();
    }

    public static boolean build(Buildings building, Tile tile) {
        if (tile.getBuilding() != null) {
            System.out.println("you are trying to replace a building");
            return false;
        }
        ArrayList<Resource> requirements = building.getRequirements();
        Empire currentEmpire = DataBase.getCurrentEmpire();
        for (Resource resource : requirements)
            if (currentEmpire.isThereNotResource(resource))
                return false;
        for (Resource requirement : requirements) currentEmpire.takeResource(requirement);
        if (!building.getPlacableOn().contains(tile.getTile())) return false;
        if (building.equals(Buildings.CAGED_WAR_DOGS))
            tile.setTrap(new DogCage(DataBase.getCurrentEmpire()));
        else if (building.equals(Buildings.KILLING_PIT))
            tile.setTrap(new KillingPit(DataBase.getCurrentEmpire()));
        else {
            Building building1 = new Building(building, tile);
            currentEmpire.addBuilding(building1);
            tile.setBuilding(building1);
            if (building.equals(Buildings.CATHEDRAL) || building.equals(Buildings.CHURCH))
                DataBase.getCurrentEmpire().addChurch();
            if (building.equals(Buildings.HOVEL)) GameMenuController.addPeasant();
        }

        return true;
    }

    public String onClick() throws IOException, UnsupportedAudioFileException, LineUnavailableException, CoordinatesOutOfMap, NotInStoragesException {
        if (this instanceof Castle)
            owner = ((Castle) this).getEmpire();
        if (owner != DataBase.getCurrentEmpire())
            if (owner == null) return buildingName;
            else return buildingName + "\nowner: " + owner.getColor() + "   hp: " + hp;
        if (menu == null) {
            StringBuilder stringBuilder = new StringBuilder("### " + buildingName + " ###");
            stringBuilder.append("\nhp = ").append(hp);
            if (workersRequired != 0) {
                stringBuilder.append("\nworkers: ").append(workers.size()).append(" / ").append(workersRequired);
                if (isWorking())
                    stringBuilder.append("the building is active!");
                if (producingResource.equals(Resources.SPEAR)) {
                    producingResource = Resources.PIKE;
                } else if (producingResource == Resources.PIKE) {
                    producingResource = Resources.SPEAR;
                } else if (producingResource == Resources.BOW) {
                    producingResource = Resources.CROSSBOW;
                } else if (producingResource == Resources.CROSSBOW) {
                    producingResource = Resources.BOW;
                } else if (producingResource == Resources.MACE) {
                    producingResource = Resources.SWORD;
                } else if (producingResource == Resources.SWORD) {
                    producingResource = Resources.MACE;
                }
                stringBuilder.append("\nproducing: ").append(producingResource.getType());
            }
            for (Storage value : Storage.values()) {
                if (building.equals(value.getStoringPlace()))
                    for (Resources storingResource : value.getStoringResources()) {
                        stringBuilder.append("\n").append(storingResource.getType()).append(" : ")
                                .append(owner.getResources().get(owner.getResources()
                                        .indexOf(new Resource(storingResource, 1))).getAmount());
                    }
            }
            return stringBuilder.toString();
        } else
            menu.getMenu().run();
        return "hp: " + hp;
    }

    private boolean isWorking() {
        return workers.size() == workersRequired;
    }

    public void update() throws CoordinatesOutOfMap, NotInStoragesException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (onFire) takeDamage(100);
        final ArrayList<Troop> troops = GameMenuController.lookAround(tileUnder.getX(), tileUnder.getY(), owner, 5);
        switch (buildingStatus) {
            case WAITING_FOR_RESOURCE -> {
                if (holder.getResourceName().equals(consumingResource)) BuildingStatus.goNext(buildingStatus);
                else employ();
            }
            case STARTING_PRODUCTION, IN_THE_MIDDLE_OF_PRODUCTION, FINISHING_PRODUCTION -> {
                if (isWorking()) BuildingStatus.goNext(buildingStatus);
                else employ();
            }
            case DELIVERING_GOODS -> {
                if (isWorking()) {
                    if (!workers.isEmpty())
                        workers.get(0).sendToGet(new Resource(consumingResource, 1), new Resource(producingResource, producingAmount));
                } else employ();
            }
            case OPEN -> {
                if (!troops.isEmpty())
                    GameMenuController.getCurrentGame().getCurrentMap().closeGate(tileUnder.getX(), tileUnder.getY(), direction);
            }
            case CLOSE -> {
                if (troops.isEmpty())
                    GameMenuController.getCurrentGame().getCurrentMap().openGate(tileUnder.getX(), tileUnder.getY(), direction);
            }
        }
    }

    private void employ() {
        do {
            for (Peasant peasant : owner.getPeasants()) {
                if (peasant.getWorkplace() == null) {
                    workers.add(peasant);
                    peasant.setWorkplace(this);
                }
            }
        } while (isWorking());
    }

    public boolean repair() {
        if (!building.getPlacableOn().contains(tileUnder.getTile())) return false;
        Buildings sample = Buildings.getBuildingsEnumByName(buildingName);
        assert sample != null;
        int maxHp = sample.getDurability().getHp();
        ArrayList<Resource> requirementsToRepair = new ArrayList<>();
        if (hp < maxHp) {
            double percentage = 1 - ((double) hp / maxHp);
            for (Resource resource : sample.getRequirements()) {
                requirementsToRepair.add(new Resource(Resources.getResourcesByName(resource.getResourceName().getType()
                ), (int) (resource.getAmount() * percentage)));
            }
            return DataBase.getCurrentEmpire().takeResource(requirementsToRepair);
        }
        return false;
    }

    public void takeDamage(int damage) {
        hp -= damage;
        if (isDead())
            destroy();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Building building = (Building) o;
        return buildingName.equals(building.buildingName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildingName);
    }

    public String getBuildingName() {
        return buildingName;
    }

    public int getHp() {
        return hp;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public Menus getMenu() {
        return menu;
    }

    public int getCapacity() {
        return capacity;
    }

    public ArrayList<Peasant> getWorkers() {
        return workers;
    }

    public Buildings getBuilding() {
        return building;
    }

    public Tile getTileUnder() {
        return tileUnder;
    }

    protected void setTileUnder(Tile tile) {
        this.tileUnder = tile;
    }

    public Empire getOwner() {
        return owner;
    }

    public void setOwner(Empire owner) {
        this.owner = owner;
    }

    public boolean atBuilding(int x, int y) {
        return GameMenuController.getCurrentGame().getCurrentMap().getTile(y, x).equals(tileUnder);
    }

    public void setHolder(Resource holder) {
        this.holder = holder;
    }

    public void destroy() {
        tileUnder.setBuilding(null);
        if (GameMenuController.isWall(this))
            GameMenuController.getCurrentGame().getCurrentMap().destroyWall(tileUnder.getX(), tileUnder.getY());
        else if (GameMenuController.isGate(building))
            GameMenuController.getCurrentGame().getCurrentMap().destroyGate(tileUnder.getX(), tileUnder.getY(), direction);
        else if (GameMenuController.isChurch(building))
            owner.churchDestroyed();
        else if (building.equals(Buildings.HOVEL))
            owner.hovelDestroyed();
        else if (Storage.IsStorage(this))
            owner.storageDestroyed(this);
        tileUnder.setBuilding(null);
    }


    public void setFire() {
        onFire = true;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
