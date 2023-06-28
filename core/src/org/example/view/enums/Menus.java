package org.example.view.enums;

import org.example.view.menus.Menu;

public enum Menus {
    ENTRANCE_MENU("entrance menu"),
    MAIN_MENU("main menu"),
    MAP_EDIT_MENU("map edit menu"),
    RANDOM_MAP_MENU("random map menu"),
    PROFILE_MENU("profile menu"),
    GAME_MENU("game menu"),
    GAME_START_UP_MENU("game start up menu"),
    SETTINGS_MENU("settings menu"),
    TRADE_MENU("trade menu"),
    TAX_MENU("tax menu"),
    SHOP_MENU("shop menu"),
    MAP_VIEW_MENU("map view menu"),
    GRANARY_MENU("granary menu"),
    UNIT_CREATING_MENU("unit creating menu"),

    MUSIC_CONTROL_MENU("music control menu"),
    SELECT_SIZE_MENU("select size menu"),
    SELECT_MAP_MENU("select map menu"),
    FORGOT_PASSWORD("forgot password"),
    New("select map menu"),

    LOADING_MENU("loading menu"),
    RECONNECTING_MENU("reconnecting menu");

    private final String menuName;

    private Menu menu;

    Menus(String menuName) {
        this.menuName = menuName;
    }

    public static String getNameByObj(Menu menu) {
        for (Menus value : values()) {
            if (value.getMenu().equals(menu))
                return value.name();
        }
        return null;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public String getMenuName() {
        return menuName;
    }

    public Menu getMenu() {
        return menu;
    }
}
