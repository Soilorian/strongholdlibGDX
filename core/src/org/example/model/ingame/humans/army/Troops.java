package org.example.model.ingame.humans.army;

import com.badlogic.gdx.graphics.Texture;
import org.example.control.Controller;
import org.example.model.ingame.humans.army.details.*;
import org.example.model.ingame.map.resourses.Resource;
import org.example.model.ingame.map.resourses.Resources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum Troops  implements Serializable {
    ARABIAN_SWORDSMAN("Arabian Swordsman", Speed.VERY_FAST, Price.EXPENSIVE, HP.HIGH, Range.VERY_LOW,
            Damage.HIGH, "troops/arabSwordman.png", new ArrayList<>()),
    ARCHER("Archer", Speed.FAST, Price.FREE, HP.LOW, Range.HIGH,
            Damage.LOW, "troops/archer.png", new ArrayList<>(List.of(new Resource[]{new Resource(Resources.BOW, 1)}))),
    ARCHER_BOW("Archer Bow", Speed.FAST, Price.CHEAP, HP.LOW, Range.VERY_HIGH,
            Damage.LOW, "troops/archer.png", new ArrayList<>()),
    ASSASSIN("Assassin", Speed.MEDIUM, Price.VERY_EXPENSIVE, HP.MEDIUM, Range.VERY_LOW,
            Damage.MEDIUM, "troops/assassin.png", new ArrayList<>()),
    BLACK_MONK("Black Monk", Speed.SLOW, Price.FREE, HP.MEDIUM, Range.VERY_LOW,
            Damage.MEDIUM, "troops/monk.png", new ArrayList<>()),
    CATAPULT("siege weapon", Speed.VERY_SLOW, Price.MEDIUM, HP.HIGH, Range.HIGH, Damage.HIGH,
            "troops/catapult.png", new ArrayList<>(List.of(new Resource[]{new Resource(Resources.WOOD, 10)}))),
    CATAPULT_WITH_WEIGHT("catapult with weight", Speed.VERY_SLOW, Price.MEDIUM, HP.HIGH, Range.VERY_HIGH,
            Damage.VERY_HIGH, "troops/catapult with weight.png", new ArrayList<>(List.of(new Resource[]{new Resource(Resources.WOOD, 10)}))),
    CROSSBOWMAN("Crossbowmen", Speed.SLOW, Price.FREE, HP.MEDIUM, Range.HIGH,
            Damage.LOW, "troops/crosbowman.png", new ArrayList<>(List.of(new Resource[]{new Resource(Resources.CROSSBOW, 1), new Resource(Resources.LEATHER_VEST, 1)}))),
    DOG("wilde dog", Speed.FAST, Price.FREE, HP.LOW, Range.VERY_LOW,
            Damage.MEDIUM, "troops/dog.png", new ArrayList<>()),
    FIRE_CATAPULT("fire catapult", Speed.VERY_SLOW, Price.MEDIUM, HP.HIGH, null, Damage.HIGH,
            "troops/fire catapult.png", new ArrayList<>(List.of(new Resource[]{new Resource(Resources.WOOD, 10)}))),
    FIRE_THROWER("Fire Thrower", Speed.VERY_FAST, Price.CHEAP, HP.LOW, Range.LOW,
            Damage.HIGH, "troops/firethrower.png", new ArrayList<>()),
    HORSE_ARCHER("Horse Archer", Speed.VERY_FAST, Price.MEDIUM, HP.MEDIUM, Range.MEDIUM,
            Damage.LOW, "troops/horseArcher.png", new ArrayList<>()),
    KNIGHT("Knight", Speed.VERY_FAST, Price.FREE, HP.HIGH, Range.VERY_LOW,
            Damage.VERY_HIGH, "troops/knightt.png", new ArrayList<>(List.of(new Resource[]{new Resource(Resources.SWORD, 1), new Resource(Resources.CHESTPLATE, 1)}))),
    LADDER_MAN("LadderMan", Speed.VERY_FAST, Price.VERY_CHEAP, HP.MEDIUM, Range.VERY_LOW,
            Damage.LOW, "troops/ladderman.png", new ArrayList<>()),
    LORD("lord", Speed.SLOW, Price.FREE, HP.VERY_HIGH, Range.VERY_LOW,
            Damage.VERY_HIGH, "troops/lord.png", new ArrayList<>()),
    MACEMAN("Maceman", Speed.MEDIUM, Price.FREE, HP.MEDIUM, Range.VERY_LOW,
            Damage.HIGH, "troops/macemann.png", new ArrayList<>(List.of(new Resource[]{new Resource(Resources.MACE, 1), new Resource(Resources.MACE, 1)}))),
    PEASANT("peasant", Speed.FAST, Price.FREE, HP.LOW, Range.VERY_LOW,
            Damage.VERY_LOW, "troops/human.png", new ArrayList<>()),

    PIKEMAN("Pikeman", Speed.SLOW, Price.FREE, HP.HIGH, Range.VERY_LOW,
            Damage.MEDIUM, "troops/pikemann.png", new ArrayList<>(List.of(new Resource[]{new Resource(Resources.PIKE, 1), new Resource(Resources.CHESTPLATE, 1)}))),
    RAM("battling ram", Speed.VERY_SLOW, Price.MEDIUM, HP.HIGH, Range.VERY_LOW, Damage.VERY_HIGH,
            "troops/ram.png", new ArrayList<>(List.of(new Resource[]{new Resource(Resources.WOOD, 10)}))),
    SIEGE_TOWER("siege tower", Speed.VERY_SLOW, Price.MEDIUM, HP.HIGH, Range.VERY_LOW, Damage.VERY_LOW,
            "troops/siegetower.png", new ArrayList<>(List.of(new Resource[]{new Resource(Resources.WOOD, 10)}))),
    SLAVE("Slave", Speed.FAST, Price.VERY_CHEAP, HP.VERY_LOW, Range.VERY_LOW,
            Damage.VERY_LOW, "troops/slave.png", new ArrayList<>()),
    SLINGER("Slinger", Speed.FAST, Price.MEDIUM, HP.VERY_LOW, Range.LOW,
            Damage.LOW, "troops/slinger.png", new ArrayList<>()),
    SPEARMAN("Spearman", Speed.MEDIUM, Price.FREE, HP.VERY_LOW, Range.VERY_LOW,
            Damage.MEDIUM, "troops/spearmann.png", new ArrayList<>(List.of(new Resource[]{new Resource(Resources.SPEAR, 1)}))),
    SWORDSMAN("Swordsman", Speed.VERY_SLOW, Price.FREE, HP.VERY_LOW, Range.VERY_LOW,
            Damage.VERY_HIGH, "troops/Swordman.png", new ArrayList<>(List.of(new Resource[]{new Resource(Resources.SWORD, 1), new Resource(Resources.CHESTPLATE, 1)})));


    private final String name;
    private final Price price;
    private final Speed speed;
    private final HP hp;
    private final Range range;
    private final Damage damage;
    private final ArrayList<Resource> equipments;
    private final String address;

    Troops(String name, Speed speed, Price price, HP hp, Range range, Damage damage, String address, ArrayList<Resource> equipments) {
        this.name = name;
        this.speed = speed;
        this.price = price;
        this.hp = hp;
        this.range = range;
        this.damage = damage;
        this.address = address;
        this.equipments = equipments;
    }


    public static Troops getTroopByName(String type) {
        for (Troops value : Troops.values()) {
            if (value.getName().equalsIgnoreCase(type))
                return value;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public Resource getPrice(int count) {
        return new Resource(Resources.GOLD, count * price.getGold());
    }

    public int getSpeed() {
        return speed.getSpeed();
    }

    public int getHp() {
        return hp.getHp();
    }

    public int getRange() {
        return range.getRange();
    }

    public int getDamage() {
        return damage.getDamage();
    }

    public ArrayList<Resource> getEquipments(int count) {
        ArrayList<Resource> resources = new ArrayList<>(equipments);
        for (Resource resource : resources) {
            resource.setAmount(count);
        }
        return resources;
    }

    public Texture getTexture() {
        return null;
    }

    public String getTextureAddress() {
        return address;
    }
}
