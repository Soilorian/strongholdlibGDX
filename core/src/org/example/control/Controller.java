package org.example.control;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Timer;
import com.google.gson.Gson;
import org.example.control.menucontrollers.GameMenuController;
import org.example.control.menucontrollers.inGameControllers.MapViewMenuController;
import org.example.model.*;
import org.example.model.ingame.castle.Buildings;
import org.example.model.ingame.map.Map;
import org.example.model.ingame.map.Tile;
import org.example.model.ingame.map.enums.TileTypes;
import org.example.model.ingame.map.enums.TreeTypes;
import org.example.model.utils.Request;
import org.example.view.enums.Menus;
//import sun.management.jdp.JdpBroadcaster;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    public static final AssetManager manager = new AssetManager();
    public final static LinkedBlockingQueue<Player> players = new LinkedBlockingQueue<>();
    public final static LinkedBlockingQueue<Game> games = new LinkedBlockingQueue<>();
    public static final Gson gson = new Gson();
    public static final Logger log = Logger.getLogger(Thread.currentThread().getName() + ".logger");
    private static final String jsonSkinAddress = "button/skin/sgx-ui.json";
    private static final String junkSkin = "junk-skin/skin/golden-ui-skin.json";
    private static final String shieldAddress = "pictures/shield.png";
    private static java.util.Timer timer;
    private static Map currentMap;
    private static Socket socket;
    private static DataInputStream in;
    private static DataOutputStream out;
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
    private Player player;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public Controller() {
        log.setLevel(Level.ALL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINER);
        handler.setFormatter(new SimpleFormatter());
        log.addHandler(handler);
        timer = new java.util.Timer();
        setupConnection();
    }

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

    public static Texture addHighlight(Texture drawable) {
        if (!drawable.getTextureData().isPrepared()) drawable.getTextureData().prepare();
        Pixmap pixmap = drawable.getTextureData().consumePixmap();
        pixmap.setColor(Color.YELLOW);
        for (int i = 0; i < pixmap.getWidth(); i++) {
            for (int j = 0; j < pixmap.getHeight(); j++) {
                if (j > MapViewMenuController.getZoomPrime() && j < pixmap.getHeight() - MapViewMenuController.getZoomPrime()) {
                    if (i <= MapViewMenuController.getZoomPrime() || i >= pixmap.getWidth() - MapViewMenuController.getZoomPrime())
                        pixmap.drawPixel(i, j);
                } else
                    pixmap.drawPixel(i, j);
            }
        }
        return new Texture(pixmap);
    }

    public static void setSocket(Socket socket) {
        Controller.socket = socket;
    }

    public static void setCurrentMenu(Menus menus) {

    }

    public static void copyToClipboard(String address) {
        ClipboardImage.write(Toolkit.getDefaultToolkit().createImage(address));
    }

    public static void getClient() {

    }

    private void playerGoneOffline() {
        if (!players.remove(DataBase.getCurrentPlayer())) {
            System.out.println("ummm, line 190, controller");
        }
    }

    private void updatePlayers(Player player) {
        log.fine("players updated");
        if (players.contains(player)) {
            if (players.remove(player)) {
            }
        } else {
            players.add(player);
        }
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

    public void handleServer() {
        log.fine("started handling");
        if (sendData()) {
            log.fine("connection lost!");
            return;
        }
        while (true) {
            try {
                handleIncomingJson();
            } catch (IOException e) {
                safeDisconnect();
                break;
            }
        }
    }

    private boolean sendData() {
        try {
            ois = new ObjectInputStream(in);
            oos = new ObjectOutputStream(out);
            String j = gson.toJson(DataBase.getPlayers().toArray());
            out.writeUTF(j);
        } catch (IOException e) {
            safeDisconnect();
            return true;
        }
        return false;
    }


    private void sendGames() {
        try {
            Game[] toSendGame = (Game[]) games.stream().toArray();
            oos.writeUTF(gson.toJson(toSendGame, Game[].class));
        } catch (Exception e) {
            safeDisconnect();
        }

    }


    private void handleIncomingJson() throws IOException {
        Object json = null;
        Player playerTest = new Player("ar","ar","ar","ar","ar");
        Game gameTest = new Game();
        Tile tileTest = new Tile(10,10,TileTypes.BEACH);
        Map mapTest = new Map(2,2,"mamad");
        Request requestTest = new Request("oskol");
        System.out.println("truck father");
        try {
            json = ois.readObject();
            log.fine("packet received!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (json.getClass().equals(playerTest.getClass()))
            updatePlayers(((Player) json));
        else if (json.getClass().equals(requestTest.getClass())) {
            Request request = ((Request) json);
            if (request.getString().equalsIgnoreCase("chats")){
                log.fine("request handled");
                sendChats();
            }
        } else if (json.getClass().equals(mapTest.getClass())) {
            log.fine("map received");
            Map map = ((Map) json);
        } else if (json.getClass().equals(gameTest.getClass())) {
            log.fine("game collected");
            handleGame(((Game) json));
        } else if (json.getClass().equals(tileTest.getClass())) {
            log.fine("tile arrived");
            handleTile(((Tile) json));
        }
//        System.out.println(json);
//        try {
//            player = ((Player) json);
//            if (player == null) {
//                throw new RuntimeException();
//            }
//            updatePlayers(player);
//            return;
//        } catch (RuntimeException ignored) {
//        }
//        try {
//            Request request = ((Request) json);
//            if (request == null) {
//                throw new RuntimeException();
//            }
//            if (request.getString().equalsIgnoreCase("chats")){
//                System.out.println("yay");
//                sendChats();
//            }
//        }catch (RuntimeException ignored){}
//        try {
//            Map map = gson.fromJson(((String) json), Map.class);
//            if (map == null) {
//                throw new RuntimeException();
//            }
//            return;
//        } catch (RuntimeException ignored){}
//        try {
//            Game game = ((Game) json);
//            if (game == null) {
//                throw new RuntimeException();
//            }
//            handleGame(game);
//
//            return;
//        } catch (RuntimeException ignored) {
//        }
//        try {
//            Tile tile = gson.fromJson(in.readUTF(), Tile.class);
//            handleTile(tile);
//
//        } catch (RuntimeException ignored) {
//
//        }
    }

    private void handleTile(Tile tile) {
        GameMenuController.getCurrentGame().getCurrentMap().setTile(tile);
    }

    private void sendChats() throws IOException {
        oos.writeObject(player.getChats());
        System.out.println("hapoijf");
    }

    private void handleGame(Game game) {
        Game searchedGame;
        if ((searchedGame = getGameById(game.getGameId())) == null)
            games.add(game);
        else {

            games.remove(searchedGame);
            if (game.getPlayersLength() != 0)
                games.add(game);
        }
    }

    private void safeDisconnect() {
        if (players.remove(player)) {
            log.finest("player " + player.getUsername() + " disconnected");
        }
    }

    private void setupConnection() {
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ignored) {
            System.out.println("reconnecting in 5 seconds...");
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    setupConnection();
                }
            }, 5000);
        }
    }

    public void setScreen(org.example.view.menus.Menu menu) {

    }

    public void changeMenu(org.example.view.menus.Menu menu, org.example.view.menus.Menu menu1) {

    }

    public void createMenus() {

    }

    public void exitGame() {

    }

    public Game getGameById(String id) {
        for (Game game : games)
            if (game.getGameId().equals(id))
                return game;
        return null;
    }
}
