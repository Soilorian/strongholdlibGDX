package org.example.view.enums;

import org.example.view.menus.*;
import org.example.view.menus.ingamemenus.*;

public enum Menus {
    ENTRANCE_MENU("entrance menu", new EntranceMenu()),
    MAIN_MENU("main menu", new MainMenu()),
    MAP_EDIT_MENU("map edit menu", new MapEditMenu()),
    MAP_BUILDER_MENU("map random ", new MapBuilderMenu()),
    PROFILE_MENU("profile menu", new ProfileMenu()),
    GAME_MENU("game menu", new GameMenu()),
    GAME_START_UP_MENU("game start up menu", new GameStartUpMenu()),
    SETTINGS_MENU("settings menu", new SettingsMenu()),
    TRADE_MENU("trade menu", new TradeMenu()),
    TAX_MENU("tax menu", new TaxMenu()),
    SHOP_MENU("shop menu", new ShopMenu()),
    MAP_VIEW_MENU("map view menu", new MapViewMenu()),
    GRANARY_MENU("granary menu", new GranaryMenu()),
    UNIT_CREATING_MENU("unit creating menu", new UnitCreatingMenu()),
    MUSIC_CONTROL_MENU("music control menu", new MusicMenu()),

    ;

    private final String menuName;
    private final Menu menu;

    Menus(String menuName, Menu menu) {
        this.menuName = menuName;
        this.menu = menu;
    }

    public static String getNameByObj(Menu menu) {
        for (Menus value : values()) {
            if (value.getMenu().equals(menu))
                return value.getMenuName();
        }
        return null;
    }

    public String getMenuName() {
        return menuName;
    }

    public Menu getMenu() {
        return menu;
    }
}
