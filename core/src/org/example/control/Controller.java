package org.example.control;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import org.example.model.DataBase;
import org.example.model.ingame.map.Map;
import org.example.view.enums.Menus;
import org.example.view.menus.*;
import org.example.view.menus.ingamemenus.*;
import org.example.view.menus.minimenus.SelectMapMenu;
import org.example.view.menus.minimenus.SelectSizeMenu;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller extends Game {
    private static Map currentMap;
    private static Menus currentMenu;
    private final AssetManager manager = new AssetManager();
    private final String jsonSkinAddress = "button/skin/sgx-ui.json";
    private final String userAvatarPath = "EntranceAssets/users.png";
    private final String lockPath = "EntranceAssets/lock.png";


    public static String removeQuotes(String string) {
        if (string.isEmpty()) return string;
        if (string.charAt(0) == '\"' && string.charAt(string.length() - 1) == '\"')
            return string.substring(1, string.length() - 1);
        return string;
    }

    public static Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches() ? matcher : null;
    }

    public static boolean isFieldEmpty(String... str) {
        for (String s : str) {
            if (s.isEmpty())
                return true;
        }
        return false;
    }

    public static Map getCurrentMap() {
        return currentMap;
    }

    public static void setCurrentMap(Map currentMap) {
        Controller.currentMap = currentMap;
    }

    public static void setCurrentMenu(Menus menus) {
    }

    public void changeMenu(Menu menu, Menu from){
        setScreen(menu);
    }

    @Override
    public void create() {
//        DataBase.generateInfoFromJson();
//        if (DataBase.isStayLogged())
//            currentMenu = Menus.MAIN_MENU;
//        else
//            currentMenu = Menus.ENTRANCE_MENU;
//        do {
//            try {
//                SoundPlayer.play(Sounds.BENAZAM);
//            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
//                System.out.println("can't play sound");
//                throw new RuntimeException(e);
//            }
//            setScreen(currentMenu.getMenu());
//        } while (currentMenu != null);
        manageAssets();
        createMenus();
        super.setScreen(Menus.PROFILE_MENU.getMenu());
    }

    private void manageAssets() {
        manager.load(jsonSkinAddress, Skin.class);
        manager.load(userAvatarPath, Texture.class);
        manager.load(lockPath, Texture.class);
        manager.finishLoading();
    }

    private void createMenus() {
        Menus.MUSIC_CONTROL_MENU.setMenu(new MusicMenu());
        Menus.MAP_EDIT_MENU.setMenu(new MapEditMenu());
        Menus.TRADE_MENU.setMenu(new TradeMenu());
        Menus.MAP_VIEW_MENU.setMenu(new MapViewMenu());
        Menus.GAME_START_UP_MENU.setMenu(new GameStartUpMenu());
        Menus.ENTRANCE_MENU.setMenu(new EntranceMenu());
        Menus.GAME_MENU.setMenu(new GameMenu());
        Menus.GRANARY_MENU.setMenu(new GranaryMenu());
        Menus.MAIN_MENU.setMenu(new MainMenu());
        Menus.RANDOM_MAP_MENU.setMenu(new RandomMapMenu());
        Menus.PROFILE_MENU.setMenu(new ProfileMenu());
        Menus.SETTINGS_MENU.setMenu(new SettingsMenu());
        Menus.SHOP_MENU.setMenu(new ShopMenu());
        Menus.TAX_MENU.setMenu(new TaxMenu());
        Menus.UNIT_CREATING_MENU.setMenu(new UnitCreatingMenu());
        Menus.SELECT_MAP_MENU.setMenu(new SelectMapMenu());
        Menus.SELECT_SIZE_MENU.setMenu(new SelectSizeMenu());
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        try {
            DataBase.updatePlayersXS();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        try {
            DataBase.updateMaps();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public Skin getSkin() {
        return manager.get(jsonSkinAddress);
    }

    public Texture getUserAvatar() {
        return manager.get(userAvatarPath);
    }

    public Texture getLock() {
        return manager.get(lockPath);
    }


    public Texture resizer(float width, float height, Texture texture){
        if (!texture.getTextureData().isPrepared())
            texture.getTextureData().prepare();
        Pixmap pixmap200 =texture.getTextureData().consumePixmap();
        Pixmap pixmap100 = new Pixmap((int) width, (int) height, pixmap200.getFormat());
        pixmap100.drawPixmap(pixmap200,
                0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
        );
        Texture result = new Texture(pixmap100);
        pixmap200.dispose();
        pixmap100.dispose();
        return result;
    }
}
