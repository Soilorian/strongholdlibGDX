package org.example.control;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import org.example.model.DataBase;
import org.example.model.ingame.castle.Buildings;
import org.example.model.ingame.map.Map;
import org.example.model.ingame.map.enums.TileTypes;
import org.example.model.ingame.map.enums.TreeTypes;
import org.example.view.LoadingMenu;
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
    public static final AssetManager manager = new AssetManager();
    private static final String jsonSkinAddress = "button/skin/sgx-ui.json";
    private static final String junkSkin = "junk-skin/skin/golden-ui-skin.json";
    private static final String shieldAddress = "pictures/shield.png";
    private static Map currentMap;
    private static Menus currentMenu;
    private final String userAvatar = "EntranceAssets/users.png";
    private final String lock = "EntranceAssets/lock.png";
    private final String defaultMapAddress = "pictures/default-map.jpeg";
    private final String allMapIconAddress = "pictures/all-map-icon.png";
    private final String randomMapIcon = "pictures/random-map.png";
    private final String blankMapIconAddress = "pictures/blank-map.png";
    private final String userAvatarPath = "EntranceAssets/users.png";
    private final String lockPath = "EntranceAssets/lock.png";
    private final String captchaPath = "EntranceAssets/captcha.png";
    private final String backgroundMainMenu = "pictures/background-main-menu.jpg";
    private final String rainSoundAddress = "sounds/rain.mp3";
    private final String gameStartUpBG = "pictures/game-start-up-background.jpg";
    private final String refresh = "EntranceAssets/Refresh.png";
    private final String showPassPath = "EntranceAssets/showPass.png";
    private final String entranceBack = "EntranceAssets/Dragon.jpg";
    private final String granaryBack = "GraneryAssets/background.jpg";
    private final String shopBack = "ShopAssets/background.jpg";
    private final String taxBack = "TaxAssets/background.jpg";
    private final String unitBack = "UnitCreatingAssets/background.jpg";
    private final String boarderAddress = "pictures/frame.png";
    private final String blackTileAddress = "pictures/black-tile.png";
    private Menu nextMenu;

    public static String removeQuotes(String string) {
        if (string.isEmpty()) return string;
        if (string.charAt(0) == '\"' && string.charAt(string.length() - 1) == '\"')
            return string.substring(1, string.length() - 1);
        return string;
    }

    public static String addQuotes(String string) {
        return "\"" + string + "\"";
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
        //currentMenu = menus;
    }

    public static Texture getTexture(String textureAddress) {
        return manager.get(textureAddress);
    }

    public static Texture getPeseantTexture() {
        return null;
    }

    public static Skin getSkin() {
        return manager.get(jsonSkinAddress);
    }

    public static Texture resizer(float width, float height, Texture texture) {
        if (!texture.getTextureData().isPrepared())
            texture.getTextureData().prepare();
        Pixmap pixmap200 = texture.getTextureData().consumePixmap();
        Pixmap pixmap100 = new Pixmap((int) width, (int) height, pixmap200.getFormat());
        pixmap100.drawPixmap(pixmap200,
                0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
        );
        return new Texture(pixmap100);
    }

    public static Texture getPictureOf(int i) {
        return manager.get("numbers/" + i + "image.png");
    }

    public static Texture getShield() {
        return manager.get(shieldAddress);
    }

    public static Pixmap getCursorPixmap() {
        Texture texture = new Texture("pictures/cursor.png");
        if (!texture.getTextureData().isPrepared())
            texture.getTextureData().prepare();
        return texture.getTextureData().consumePixmap();
    }

    public void changeMenu(Menu menu, Menu from) {
        getRainSound().pause();
        if (!menu.equals(Menus.MAIN_MENU.getMenu()) && nextMenu != null) {
            setScreen(nextMenu);
            nextMenu = null;
        } else
            setScreen(menu);
        if (menu.equals(Menus.LOADING_MENU.getMenu()) || from.equals(Menus.MAP_EDIT_MENU.getMenu()))
            nextMenu = from;
    }


    public void setScreen(Screen screen) {
        if (screen instanceof Menu)
            ((Menu) screen).create();
        super.setScreen(screen);
    }

    @Override
    public void create() {
        DataBase.generateInfoFromJson();
        manageAssets();
        setScreen(new LoadingMenu(this));
    }

    private void manageAssets() {
        manager.load(jsonSkinAddress, Skin.class);
        manager.load(junkSkin, Skin.class);
        manager.load(userAvatar, Texture.class);
        manager.load(lock, Texture.class);
        manager.load(defaultMapAddress, Texture.class);
        manager.load(allMapIconAddress, Texture.class);
        manager.load(randomMapIcon, Texture.class);
        manager.load(blankMapIconAddress, Texture.class);
        manager.load(userAvatarPath, Texture.class);
        manager.load(lockPath, Texture.class);
        manager.load(captchaPath, Texture.class);
        manager.load(backgroundMainMenu, Texture.class);
        manager.load(gameStartUpBG, Texture.class);
        manager.load(gameStartUpBG, Texture.class);
        manager.load(unitBack, Texture.class);
        manager.load(boarderAddress, Texture.class);
        manager.load(blackTileAddress, Texture.class);
        manager.load(shieldAddress, Texture.class);
        for (TileTypes value : TileTypes.values()) manager.load(value.getTextureAddress(), Texture.class);
        for (TreeTypes value : TreeTypes.values()) manager.load(value.getTextureAddress(), Texture.class);
        for (Buildings value : Buildings.values()) manager.load(value.getTextureAddress(), Texture.class);
        for (int i = 1; i <= 10; i++) manager.load("numbers/" + i + "image.png", Texture.class);
        manager.load(rainSoundAddress, Music.class);
        manager.load(refresh, Texture.class);
        manager.load(showPassPath, Texture.class);
        manager.load(entranceBack, Texture.class);
        manager.load(granaryBack, Texture.class);
        manager.load(shopBack, Texture.class);
        manager.load(taxBack, Texture.class);
        manager.load(unitBack, Texture.class);
    }

    public void createMenus() {
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

    public Texture getUserAvatar() {
        return manager.get(userAvatarPath);
    }

    public Texture getLock() {
        return manager.get(lockPath);
    }

    public Texture getCaptchaPath() {
        return manager.get(captchaPath);
    }

    public Texture getDefaultMap() {
        return manager.get(defaultMapAddress);
    }

    public Texture getRandomMapIcon() {
        return manager.get(randomMapIcon);
    }

    public Texture getShowAllMapsIcon() {
        return manager.get(allMapIconAddress);
    }

    public Texture getBlankMapIcon() {
        return manager.get(blankMapIconAddress);
    }

    public Texture getRefreshPath() {
        return manager.get(refresh);
    }

    public Texture getShowPassPath() {
        return manager.get(showPassPath);
    }

    public Texture getMainMenuBackground() {
        return manager.get(backgroundMainMenu);
    }

    public Music getRainSound() {
        Music music = manager.get(rainSoundAddress);
        music.setVolume(0.1f);
        music.setLooping(true);
        return music;
    }

    public Texture getGameStartBG() {
        return manager.get(gameStartUpBG);
    }

    public Texture getEntranceBack() {
        return manager.get(entranceBack);
    }

    public void exitGame() {
//        for (Menus value : Menus.values()) {
//            value.getMenu().dispose();
//        }
        dispose();
        Gdx.app.exit();
    }

    public Texture getBlackMap() {
        return manager.get(blackTileAddress);
    }

    public Texture getGranaryBack() {
        return manager.get(granaryBack);
    }

    public Skin getJunkSkin() {
        return manager.get(junkSkin);
    }

    public Texture getShopBack() {
        return manager.get(shopBack);

    }

    public Texture getLoadingBG() {
        return new Texture("pictures/background.jpg");
    }

    public Texture addBoarder(Texture texture) {
        Texture boarder = resizer(texture.getWidth(), texture.getHeight(), getBoarder());
        if (!boarder.getTextureData().isPrepared()) boarder.getTextureData().prepare();
        Pixmap pixmap = boarder.getTextureData().consumePixmap();
        Texture fitTexture = resizer(texture.getWidth() / 10f * 9, texture.getHeight() / 10f * 9, texture);
        if (!fitTexture.getTextureData().isPrepared()) {
            fitTexture.getTextureData().prepare();
        }
        pixmap.drawPixmap(fitTexture.getTextureData().consumePixmap(), texture.getWidth() / 20,
                texture.getHeight() / 20);
        return new Texture(pixmap);
    }

    private Texture getBoarder() {
        return manager.get(boarderAddress);
    }

    public Texture getTaxBack() {
        return manager.get(taxBack);
    }

    public Texture getUnitBack() {
        return manager.get(unitBack);
    }
}
