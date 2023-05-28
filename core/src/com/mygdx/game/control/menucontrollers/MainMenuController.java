package org.example.control.menucontrollers;

import org.example.control.Controller;
import org.example.model.DataBase;
import org.example.view.enums.Menus;

public class MainMenuController {
    static public void startNewGame() {
        Controller.setCurrentMenu(Menus.GAME_START_UP_MENU);
    }

    static public void profile() {
        Controller.setCurrentMenu(Menus.PROFILE_MENU);
    }

    static public void settings() {
        Controller.setCurrentMenu(Menus.SETTINGS_MENU);
    }

    static public void logout() {
        DataBase.setStayLoggedIn(false);
        Controller.setCurrentMenu(Menus.ENTRANCE_MENU);
    }

}
