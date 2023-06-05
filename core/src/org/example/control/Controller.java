package org.example.control;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import org.example.control.menucontrollers.GameMenuController;
import org.example.control.menucontrollers.inGameControllers.MapViewMenuController;
import org.example.model.DataBase;
import org.example.model.ingame.map.Map;
import org.example.model.ingame.map.enums.TileTypes;
import org.example.model.ingame.map.enums.TreeTypes;
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
    public static final AssetManager manager = new AssetManager();
    private final String jsonSkinAddress = "button/skin/sgx-ui.json";
    private final String junkSkin = "junk-skin/skin/golden-ui-skin.json";
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
    private final String gameStartUpBG = "pictures/game-start-up-background.png";
    private final String refresh = "EntranceAssets/Refresh.png";
    private final String showPassPath = "EntranceAssets/showPass.png";
    private final String entranceBack = "EntranceAssets/Dragon.jpg";
    private final String granaryBack = "GraneryAssets/background.jpg";
    private final String shopBack = "ShopAssets/background.jpg";
    private String blackTileAddress = "pictures/black-tile.png.";
    private final String entranceBG = "EntranceAssets/entrance-bg.jpg";
    private Menu nextMenu;
    private String boarderAddress = "pictures/frame.png";


    public static String removeQuotes(String string) {
        if (string.isEmpty()) return string;
        if (string.charAt(0) == '\"' && string.charAt(string.length() - 1) == '\"')
            return string.substring(1, string.length() - 1);
        return string;
    }

    public static String addQuotes(String string) {
        return "\""+string+"\"";
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

    public void changeMenu(Menu menu, Menu from) {
        if (!menu.equals(Menus.MAIN_MENU.getMenu()) && nextMenu != null) {
            setScreen(nextMenu);
            nextMenu = null;
        } else
            setScreen(menu);
        if (from.equals(Menus.MAP_EDIT_MENU.getMenu()))
            nextMenu = from;
    }


    @Override
    public void setScreen(Screen screen) {
        ((Menu) screen).create();
        super.setScreen(screen);
    }

    @Override
    public void create() {
        DataBase.generateInfoFromJson();
        manageAssets();
        createMenus();
        GameMenuController.setCurrentGame(new org.example.model.Game());
        GameMenuController.getCurrentGame().setCurrentMap(new Map(200, 200, "adsf"));
        GameMenuController.getCurrentGame().getCurrentMap().getTile(100, 100).setTile(TileTypes.SEA);
        GameMenuController.getCurrentGame().getCurrentMap().getTile(101, 100).setTile(TileTypes.SEA);
        GameMenuController.getCurrentGame().getCurrentMap().getTile(102, 100).setTile(TileTypes.SEA);
        GameMenuController.getCurrentGame().getCurrentMap().getTile(103, 102).setTile(TileTypes.SEA);
        GameMenuController.getCurrentGame().getCurrentMap().getTile(103, 102).setTree(TreeTypes.OLIVE_TREE);
        GameMenuController.getCurrentGame().getCurrentMap().getTile(100, 102).setTile(TileTypes.IRON_GROUND);
        MapViewMenuController.setViewingY(10);
        MapViewMenuController.setViewingX(10);
        setScreen(Menus.PROFILE_MENU.getMenu());
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
        manager.load(blackTileAddress, Texture.class);
        manager.load(boarderAddress, Texture.class);
        for (TileTypes value : TileTypes.values()) manager.load(value.getTextureAddress(), Texture.class);
        for (TreeTypes value : TreeTypes.values()) manager.load(value.getTextureAddress(), Texture.class);


        for (TileTypes value : TileTypes.values()) {
            manager.load(value.getTextureAddress(), Texture.class);
        }
        manager.load(rainSoundAddress, Music.class);
        manager.load(refresh, Texture.class);
        manager.load(showPassPath, Texture.class);
        manager.load(entranceBack,Texture.class);
        manager.load(granaryBack,Texture.class);
        manager.load(shopBack,Texture.class);
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

    public Texture getCaptchaPath() {
        return manager.get(captchaPath);
    }

    public static Texture resizer(float width, float height, Texture texture){
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

    public Texture getMainMenuBackground(){
        return manager.get(backgroundMainMenu);
    }

    public Music getRainSound(){
        return manager.get(rainSoundAddress);
    }

    public Texture getGameStartBG(){
        return manager.get(gameStartUpBG);
    }

    public Texture getEntranceBack() {
        return manager.get(entranceBack);
    }

    public void exitGame() {
        for (Menus value : Menus.values()) {
            value.getMenu().dispose();
        }
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
        return new Texture("pictures/background.png");
    }

    public Texture addBoarder(Texture texture) {
        if (!texture.getTextureData().isPrepared())
            texture.getTextureData().prepare();
        Pixmap pixmap = texture.getTextureData().consumePixmap();
        Texture boarder = resizer(pixmap.getWidth(), pixmap.getHeight(), getBoarder());
        if (!boarder.getTextureData().isPrepared())
            boarder.getTextureData().prepare();
        pixmap.drawPixmap(boarder.getTextureData().consumePixmap(), 0, 0);
        Texture texture1 = new Texture(pixmap);
        pixmap.dispose();
        return texture1;
    }

    private Texture getBoarder() {
        return manager.get(boarderAddress);
    }
}
