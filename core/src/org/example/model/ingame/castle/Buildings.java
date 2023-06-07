package org.example.model.ingame.castle;


import com.badlogic.gdx.graphics.Texture;
import org.example.model.ingame.castle.details.Durability;
import org.example.model.ingame.map.enums.TileTypes;
import org.example.model.ingame.map.resourses.Resource;
import org.example.model.ingame.map.resourses.Resources;
import org.example.view.enums.Menus;

import java.util.ArrayList;
import java.util.List;

public enum Buildings {
    SMALL_STONE_GATEHOUSE("Small Stone Gatehouse", Durability.MEDIUM, Menus.TAX_MENU, null, null,
            0, 0, new Resource[]{}, new TileTypes[]
            {TileTypes.GRASS, TileTypes.BEACH, TileTypes.DENSE_GRASS_LAND,
                    TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND, TileTypes.GROUND,
                    TileTypes.IRON_GROUND, TileTypes.PLAIN}, 0),
    BIG_STONE_GATEHOUSE("Big Stone Gatehouse", Durability.HIGH, Menus.TAX_MENU, null, null,
            0, 0, new Resource[]{new Resource(Resources.STONE, 20)},
            new TileTypes[]{TileTypes.GRASS, TileTypes.BEACH, TileTypes.DENSE_GRASS_LAND,
                    TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND, TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 0),
    KEEP("Keep", Durability.VERY_HIGH, null, null, null,
            0, 0, new Resource[]{}, new TileTypes[]{TileTypes.GRASS,
            TileTypes.BEACH, TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND,
            TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 0),
    DRAWBRIDGE("Drawbridge", Durability.MEDIUM, null, null, null,
            0, 0, new Resource[]{new Resource(Resources.WOOD, 10)},
            new TileTypes[]{TileTypes.RIVER, TileTypes.SHALLOW_WATER, TileTypes.SWAMP}, 0),
    LOOKOUT_TOWER("Lookout Tower", Durability.MEDIUM, null, null, null,
            0, 0, new Resource[]{new Resource(Resources.STONE,
            10)}, new TileTypes[]{TileTypes.GRASS, TileTypes.BEACH, TileTypes.DENSE_GRASS_LAND,
            TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND, TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 0),
    PERIMETER_TOWER("Perimeter Tower", Durability.MEDIUM, null, null, null,
            0, 0, new Resource[]{new Resource(Resources.STONE,
            10)}, new TileTypes[]{TileTypes.GRASS, TileTypes.BEACH,
            TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND,
            TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 0),
    TURRET("Turret", Durability.MEDIUM, null, null, null,
            0, 0, new Resource[]{new Resource(Resources.STONE,
            15)}, new TileTypes[]{TileTypes.GRASS, TileTypes.BEACH,
            TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND,
            TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 0),
    WALL("Wall", Durability.MEDIUM, null, null, null,
            0, 0, new Resource[]{new Resource(Resources.STONE,
            10)}, new TileTypes[]{TileTypes.GRASS, TileTypes.BEACH,
            TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND,
            TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 0),
    STAIR("Stair", Durability.MEDIUM, null, null, null,
            0, 0, new Resource[]{new Resource(Resources.STONE,
            5)}, new TileTypes[]{TileTypes.GRASS, TileTypes.BEACH,
            TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND,
            TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 0),
    SQUARE_TOWER("Square Tower", Durability.MEDIUM, null, null, null,
            0, 0, new Resource[]{new Resource(Resources.STONE,
            35)}, new TileTypes[]{TileTypes.GRASS, TileTypes.BEACH,
            TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND,
            TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 0),
    CIRCLE_TOWER("Circle Tower", Durability.MEDIUM, null, null, null,
            0, 0, new Resource[]{new Resource(Resources.STONE,
            40)}, new TileTypes[]{TileTypes.GRASS, TileTypes.BEACH,
            TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND,
            TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 0),
    ARMOURY("Armoury", Durability.MEDIUM, null, null, null,
            200, 0, new Resource[]{new Resource(Resources.WOOD,
            5)}, new TileTypes[]{TileTypes.GRASS, TileTypes.BEACH, TileTypes.DENSE_GRASS_LAND,
            TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND, TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 0),
    BARRACK("Barrack", Durability.MEDIUM, Menus.UNIT_CREATING_MENU, null, null,
            0, 0, new Resource[]{new Resource(Resources.STONE, 15)}, new TileTypes[]
            {TileTypes.GRASS, TileTypes.BEACH, TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND,
                    TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 0),
    ENGINEER_GUILD("Engineer Guild", Durability.MEDIUM, Menus.UNIT_CREATING_MENU, null, null,
            0, 0, new Resource[]{new Resource(Resources.WOOD, 10),
            new Resource(Resources.GOLD, 100)}, new TileTypes[]{TileTypes.GRASS, TileTypes.BEACH,
            TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND, TileTypes.GROUND,
            TileTypes.IRON_GROUND, TileTypes.PLAIN}, 0),
    KILLING_PIT("Killing Pit", Durability.INFINITY, null, null, null,
            10, 0, new Resource[]{new Resource(Resources.WOOD, 6)}, new TileTypes[]
            {TileTypes.GRASS, TileTypes.BEACH, TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND,
                    TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 0),
    CAGED_WAR_DOGS("Caged War Dogs", Durability.INFINITY, null, null, null,
            0, 0, new Resource[]{new Resource(Resources.STONE, 6)}, new TileTypes[]{TileTypes.GRASS
            , TileTypes.BEACH, TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND, TileTypes.GROUND
            , TileTypes.IRON_GROUND, TileTypes.PLAIN}, 0),
    DITCH("Ditch", Durability.INFINITY, null, null, null,
            0, 0, new Resource[]{new Resource(Resources.WOOD, 2)},
            new TileTypes[]{TileTypes.SWAMP}, 4),
    INN("Inn", Durability.MEDIUM, null, null, null,
            0, 1, new Resource[]{new Resource(Resources.WOOD, 20),
            new Resource(Resources.GOLD, 100)}, new TileTypes[]{TileTypes.GRASS, TileTypes.BEACH,
            TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND, TileTypes.GROUND,
            TileTypes.IRON_GROUND, TileTypes.PLAIN}, 0),
    MILL("Mill", Durability.MEDIUM, null, Resources.WHEAT, Resources.FLOUR,
            0, 3, new Resource[]{new Resource(Resources.WOOD, 20)},
            new TileTypes[]{TileTypes.GRASS, TileTypes.BEACH, TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND,
                    TileTypes.GRAVEL_GROUND, TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 2),
    IRON_MINE("Iron Mine", Durability.MEDIUM, null, null, null,
            0, 2, new Resource[]{new Resource(Resources.WOOD, 20)},
            new TileTypes[]{TileTypes.IRON_GROUND}, 4),
    MARKET("Market", Durability.MEDIUM, Menus.SHOP_MENU, null, null,
            0, 1, new Resource[]{new Resource(Resources.WOOD, 5)},
            new TileTypes[]{TileTypes.GRASS, TileTypes.BEACH, TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND,
                    TileTypes.GRAVEL_GROUND, TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 0),
    OX_TETHER("Ox Tether", Durability.MEDIUM, null, null, null,
            0, 1, new Resource[]{new Resource(Resources.WOOD, 5)},
            new TileTypes[]{TileTypes.GRASS, TileTypes.BEACH, TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND,
                    TileTypes.GRAVEL_GROUND, TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 0),
    QUARRY("Quarry", Durability.MEDIUM, null, null, null,
            0, 3, new Resource[]{new Resource(Resources.WOOD, 20)},
            new TileTypes[]{TileTypes.STONE, TileTypes.GRAVEL_GROUND}, 8),
    STOCKPILE("Stockpile", Durability.MEDIUM, null, null, null,
            60, 0, new Resource[]{}, new TileTypes[]{TileTypes.GRASS, TileTypes.BEACH,
            TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND, TileTypes.GROUND,
            TileTypes.IRON_GROUND, TileTypes.PLAIN}, 0),
    WOODCUTTER("Woodcutter", Durability.MEDIUM, Menus.TAX_MENU, null, null,
            0, 1, new Resource[]{new Resource(Resources.WOOD, 3)},
            new TileTypes[]{TileTypes.GRASS, TileTypes.BEACH, TileTypes.DENSE_GRASS_LAND,
                    TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND, TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 10),
    HOVEL("hovel", Durability.LOW, null, null, null,
            0, 0, new Resource[]{new Resource(Resources.WOOD, 6)},
            new TileTypes[]{TileTypes.GRASS, TileTypes.BEACH, TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND,
                    TileTypes.GRAVEL_GROUND, TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 0),
    CHURCH("church", Durability.MEDIUM, Menus.UNIT_CREATING_MENU, null, null,
            0, 2, new Resource[]{new Resource(Resources.GOLD, 250)},
            new TileTypes[]{TileTypes.GRASS, TileTypes.BEACH, TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND,
                    TileTypes.GRAVEL_GROUND, TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 0),
    CATHEDRAL("cathedral", Durability.HIGH, Menus.UNIT_CREATING_MENU, null, null,
            0, 4, new Resource[]{new Resource(Resources.GOLD, 1000)}, new TileTypes[]
            {TileTypes.GRASS, TileTypes.BEACH, TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND,
                    TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 0),
    ARMOURER("armourer", Durability.LOW, null, Resources.IRON, Resources.CHESTPLATE,
            0, 1, new Resource[]{new Resource(Resources.GOLD, 100),
            new Resource(Resources.WOOD, 20)}, new TileTypes[]{TileTypes.GRASS, TileTypes.BEACH, TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND, TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 1),
    BLACKSMITH("blacksmith", Durability.LOW, null, Resources.IRON, Resources.SWORD,
            0, 1, new Resource[]{new Resource(Resources.GOLD, 100), new Resource(Resources.WOOD, 20)}, new TileTypes[]{TileTypes.GRASS, TileTypes.BEACH, TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND, TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 1),
    FLETCHER("fletcher", Durability.LOW, null, Resources.WOOD, Resources.BOW,
            0, 1, new Resource[]{new Resource(Resources.GOLD, 100), new Resource(Resources.WOOD, 20)}, new TileTypes[]{TileTypes.GRASS, TileTypes.BEACH, TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND, TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 1),
    POLETURNER("poleturner", Durability.LOW, null, Resources.WOOD, Resources.SPEAR,
            0, 1, new Resource[]{new Resource(Resources.GOLD, 100), new Resource(Resources.WOOD, 10)}, new TileTypes[]{TileTypes.GRASS, TileTypes.BEACH, TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND, TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 1),
    OIL_SMELTER("oil smelter", Durability.LOW, null, Resources.PITCH, Resources.OIL,
            0, 1, new Resource[]{new Resource(Resources.IRON, 10), new Resource(Resources.GOLD, 100)}, new TileTypes[]{TileTypes.GRASS, TileTypes.BEACH, TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND, TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 1),
    SIEGE_TENT("siege tent", Durability.VERY_LOW, null, null, null,
            0, 0, new Resource[]{new Resource(Resources.GOLD, 500)}, new TileTypes[]{TileTypes.GRASS, TileTypes.BEACH, TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND, TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 1),
    STABLE("stable", Durability.LOW, null, null, Resources.HORSE,
            4, 0, new Resource[]{new Resource(Resources.GOLD, 400),
            new Resource(Resources.WOOD, 20)}, new TileTypes[]{TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.PLAIN}, 1),
    TUNNELERS_GUILD("tunnelers' guild", Durability.MEDIUM, Menus.UNIT_CREATING_MENU, null, null,
            0, 0, new Resource[]{new Resource(Resources.GOLD, 100), new Resource(Resources.WOOD, 10)}, new TileTypes[]{TileTypes.GRASS, TileTypes.BEACH, TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND, TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 1),
    APPLE_FARM("apple farm", Durability.LOW, null, null, Resources.APPLE,
            0, 1, new Resource[]{new Resource(Resources.WOOD, 5)},
            new TileTypes[]{TileTypes.GRASS, TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.PLAIN}, 5),
    DIARY_FARM("diary farm", Durability.LOW, null, null, Resources.CHEESE,
            0, 1, new Resource[]{new Resource(Resources.WOOD, 10)},
            new TileTypes[]{TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.PLAIN}, 4),
    GRAIN_FARM("grain farm", Durability.LOW, null, null, Resources.HOPS,
            0, 1, new Resource[]{new Resource(Resources.WOOD, 15)},
            new TileTypes[]{TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.PLAIN}, 4),
    HUNTING_POST("hunting post", Durability.LOW, null, null, Resources.MEAT,
            0, 1, new Resource[]{new Resource(Resources.WOOD, 5)}, new TileTypes[]
            {TileTypes.GRASS, TileTypes.BEACH, TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND,
                    TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 4),
    WHEAT_FARM("wheat farm", Durability.LOW, null, null, Resources.WHEAT,
            0, 1, new Resource[]{new Resource(Resources.WOOD, 15)},
            new TileTypes[]{TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.PLAIN}, 6),
    BAKERY("bakery", Durability.LOW, null, Resources.FLOUR, Resources.BREAD,
            0, 1, new Resource[]{new Resource(Resources.WOOD, 10)},
            new TileTypes[]{TileTypes.GRASS, TileTypes.BEACH, TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND,
                    TileTypes.GRAVEL_GROUND, TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 4),
    BREWERY("brewery", Durability.LOW, null, Resources.HOPS, Resources.ALE,
            0, 1, new Resource[]{new Resource(Resources.WOOD, 10)},
            new TileTypes[]{TileTypes.GRASS, TileTypes.BEACH, TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND,
                    TileTypes.GRAVEL_GROUND, TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 1),
    GRANARY("granary", Durability.LOW, Menus.GRANARY_MENU, null, null,
            60, 0, new Resource[]{new Resource(Resources.WOOD, 5)}, new TileTypes[]
            {TileTypes.GRASS, TileTypes.BEACH, TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND,
                    TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 0),
    MERCENARY_POST("mercenary post", Durability.LOW, Menus.UNIT_CREATING_MENU, null, null,
            0, 0, new Resource[]{new Resource(Resources.WOOD, 10)}, new TileTypes[]
            {TileTypes.GRASS, TileTypes.BEACH, TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND,
                    TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 0),

    HELL_DOORWAY("mercenary post", Durability.HIGH, Menus.UNIT_CREATING_MENU, null, null,
            0, 0, new Resource[]{new Resource(Resources.GOLD, 1000), new Resource(Resources.HORSE, 10)}
            , new TileTypes[]{TileTypes.GRASS, TileTypes.BEACH, TileTypes.DENSE_GRASS_LAND, TileTypes.GRASS_LAND, TileTypes.GRAVEL_GROUND,
                    TileTypes.GROUND, TileTypes.IRON_GROUND, TileTypes.PLAIN}, 0);

    private final String buildingName;
    private final Durability durability;
    private final Menus menus;
    private final Resources consumingResource;
    private final Resources producingResource;
    private final int capacity;
    private final int workersRequired;
    private final int producingAmount;
    private final ArrayList<Resource> requirements = new ArrayList<>();
    private final ArrayList<TileTypes> placableOn = new ArrayList<>();

    Buildings(String buildingName, Durability durability, Menus menus, Resources consumingResource, Resources
            producingResource, int capacity, int workersRequired, Resource[] resources, TileTypes[] placableOn, int producingAmount) {
        this.buildingName = buildingName;
        this.durability = durability;
        this.menus = menus;
        this.consumingResource = consumingResource;
        this.producingResource = producingResource;
        this.capacity = capacity;
        this.workersRequired = workersRequired;
        this.producingAmount = producingAmount;
        this.placableOn.addAll(List.of(placableOn));
        this.requirements.addAll(List.of(resources));
    }

    public static Buildings getBuildingsEnumByName(String name) {
        for (Buildings buildings : Buildings.values())
            if (buildings.getBuildingName().equals(name))
                return buildings;
        return null;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public Durability getDurability() {
        return durability;
    }

    public Menus getMenus() {
        return menus;
    }

    public Resources getConsumingResource() {
        return consumingResource;
    }

    public Resources getProducingResource() {
        return producingResource;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getWorkersRequired() {
        return workersRequired;
    }

    public ArrayList<Resource> getRequirements() {
        return requirements;
    }

    public ArrayList<TileTypes> getPlacableOn() {
        return placableOn;
    }

    public int getProducingAmount() {
        return producingAmount;
    }

    public Texture getTexture() {
        return null;
    }
}