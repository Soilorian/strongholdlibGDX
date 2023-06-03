package org.example.model;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.apache.commons.codec.digest.DigestUtils;
import org.example.control.SoundPlayer;
import org.example.model.ingame.castle.Empire;
import org.example.model.ingame.map.Map;
import org.example.view.enums.Sounds;
import org.example.view.enums.commands.Slogans;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DataBase {
    private static final ArrayList<Player> players = new ArrayList<>();
    private static final ArrayList<Map> maps = new ArrayList<>();
    private static final ArrayList<String> slogans = new ArrayList<>();
    private static final XStream xstream = new XStream(new StaxDriver());
    private static final ArrayList<String> securityQuestions = new ArrayList<>();
    private static Player currentPlayer;
    private static boolean stayLoggedIn = false;
    private static Empire currentEmpire;

    static {
        securityQuestions.add("What is the first game you played?");
        securityQuestions.add("When did you meet mossayeb?");
        securityQuestions.add("What is your favorite patoq in university?");
        xstream.autodetectAnnotations(true);
        xstream.addPermission(AnyTypePermission.ANY);
    }

    public static Empire getCurrentEmpire() {
        return currentEmpire;
    }

    public static void setCurrentEmpire(Empire currentEmpire) {
        DataBase.currentEmpire = currentEmpire;
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setCurrentPlayer(Player currentPlayer) {
        DataBase.currentPlayer = currentPlayer;
    }

    public static Player getPlayerByUsername(String username) {
        for (Player player : players)
            if (player.getUsername().equals(username))
                return player;
        return null;
    }

    public static Player getPlayerByEmail(String email) {
        for (Player player : players)
            if (player.getEmail().equals(email))
                return player;
        return null;
    }

    public static Player getPlayerByPassword(String password) {
        for (Player player : players)
            if (player.checkPassword(password))
                return player;
        return null;
    }

    public static Map getMapById(String id) {
        for (Map map : maps)
            if (map.getId().equals(id))
                return map;
        return null;

    }

    public static void addPlayer(Player player) {
        players.add(player);
    }

    public static Player getLastPlayer() {
        return players.get(players.size() - 1);
    }

    public static void addSlogan(String slogan) {
        slogans.add(slogan);
    }

    public static void addMap(Map map) {
        maps.add(map);
    }

    public static String hashWithApacheCommons(final String originalString) {
        return DigestUtils.sha256Hex(originalString);
    }

    public static String getRandomSlogan() {
        return Slogans.getRandomSlogan();
    }

    public static void updatePlayersXS() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
//        SoundPlayer.play(Sounds.WAIT_A_MOMENT);
        ObjectOutputStream objectOutputStream = xstream.createObjectOutputStream(
                Files.newOutputStream(Paths.get("assets/players.txt")));
        for (Player player : players)
            objectOutputStream.writeObject(player);
        objectOutputStream.close();
//        SoundPlayer.pause();
    }

    public static void updateMaps() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
//        SoundPlayer.play(Sounds.WAIT_A_MOMENT);
        ObjectOutputStream objectOutputStream = xstream.createObjectOutputStream(
                Files.newOutputStream(Paths.get("assets/maps.txt")));
        for (Map map : maps)
            objectOutputStream.writeObject(map);
        objectOutputStream.close();
//        SoundPlayer.pause();
//        SoundPlayer.play(Sounds.DONE);
    }

    public static void generateInfoFromJson() {
        Player player = new Player("t", "t", "t", "t", "t");
        Map map = new Map(0, 0, "0");
        importFromJson("assets/players.txt", player, true);
        importFromJson("assets/maps.txt", map, false);
    }

    public static void importFromJson(String path, Object object, boolean bol) {

        try {
            ObjectOutputStream objectOutputStream = xstream.createObjectOutputStream(
                    new FileOutputStream(path, true));
            objectOutputStream.writeObject(object);
            ObjectInputStream objectInputStream = xstream.createObjectInputStream(
                    Files.newInputStream(Paths.get(path)));
            while (true) {
                if (bol) {
                    object = objectInputStream.readObject();
                    players.add((Player) object);
                } else {
                    object = objectInputStream.readObject();
                    maps.add((Map) object);
                }
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean isStayLogged() {
        try {
            ObjectOutputStream objectOutputStream = xstream.createObjectOutputStream(
                    new FileOutputStream("assets/stayLogged.txt", true));
            Player player = new Player("ar", "ar", "ar", "ar", "ar");
            objectOutputStream.writeObject(player);
            ObjectInputStream objectInputStream = xstream.createObjectInputStream(
                    Files.newInputStream(Paths.get("assets/stayLogged.txt")));
            currentPlayer = (Player) objectInputStream.readObject();
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return currentPlayer != null;
    }

    public static void clearStayLogged() throws IOException {
        currentPlayer = null;
        ObjectOutputStream objectOutputStream = xstream.createObjectOutputStream(
                Files.newOutputStream(Paths.get("assets/stayLogged.txt")));
        objectOutputStream.writeObject(currentPlayer);
        objectOutputStream.close();
    }

    public static void addStayLoggedPlayed(Player player) throws IOException {
        ObjectOutputStream objectOutputStream = xstream.createObjectOutputStream(
                Files.newOutputStream(Paths.get("assets/stayLogged.txt")));
        objectOutputStream.writeObject(player);
        objectOutputStream.close();
    }

    public static String getSecurityQuestionByNumber(int i) {
        return securityQuestions.get(i);
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public static ArrayList<Map> getMaps() {
        return maps;
    }

    public static boolean isStayLoggedIn() {
        return stayLoggedIn;
    }

    public static void setStayLoggedIn(boolean b) {
        stayLoggedIn = b;
    }

    public static Empire getNatural() {
        return new Empire(getPlayerByUsername("ar"));
    }
}