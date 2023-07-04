package org.example.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.example.model.chat.Chat;
import org.example.model.chat.Group;
import org.example.model.chat.PrivateChat;
import org.example.model.utils.FriendShipRequest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

@XStreamAlias("player")

public class Player implements Serializable {

    static ArrayList<Chat> chats = new ArrayList<>();
    private ArrayList<FriendShipRequest> friendShipRequests = new ArrayList<>();
    private ArrayList<Player> friends = new ArrayList<>();
    String username;

    String password;

    String nickname;

    String email;

    String securityQuestion;

    String securityQuestionAnswer;

    String slogan;

    int maxStore = 0;
    int profImage = 0;
    private String lastVisit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return maxStore == player.maxStore && profImage == player.profImage &&
                Objects.equals(friendShipRequests, player.friendShipRequests) &&
                Objects.equals(friends, player.friends) && Objects.equals(username, player.username) &&
                Objects.equals(password, player.password) && Objects.equals(nickname, player.nickname) &&
                Objects.equals(email, player.email) && Objects.equals(securityQuestion, player.securityQuestion) &&
                Objects.equals(securityQuestionAnswer, player.securityQuestionAnswer) &&
                Objects.equals(slogan, player.slogan) && Objects.equals(lastVisit, player.lastVisit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(friendShipRequests, friends, username, password, nickname, email, securityQuestion, securityQuestionAnswer, slogan, maxStore, profImage, lastVisit);
    }

    public Player(String username, String password, String nickname, String email, String slogan) {
        this.username = username;
        this.password = DataBase.hashWithApacheCommons(password);
        this.nickname = nickname;
        this.email = email;
        if (!slogan.equals("null"))
            this.slogan = slogan;

    }


    public boolean checkPassword(String password) {
        return this.password.equals(DataBase.hashWithApacheCommons(password));
    }

    public boolean checkSecurityQuestion(String answer) {
        return this.securityQuestionAnswer.equals(DataBase.hashWithApacheCommons(answer));
    }

    public void updateMaxScore(int score) {
        maxStore = Math.max(score, maxStore);
        sendPlayersChangesToServer(this);
    }

    private void sendPlayersChangesToServer(Player player) {
//        try {
//            Controller.getClient().sendPlayer(this);
//        }
//        catch (IOException e) {
//            Main.getController().changeMenu(Menus.RECONNECTING_MENU, ((Menu) Main.getController().getScreen()));
//        }
    }

    public int getMaxStore() {
        return maxStore;
    }

    public String toStringScore() {
        return Integer.valueOf(getMaxStore()).toString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        sendPlayersChangesToServer(this);

    }

    public void setPassword(String password) {
        this.password = DataBase.hashWithApacheCommons(password);
        sendPlayersChangesToServer(this);

    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        sendPlayersChangesToServer(this);

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        sendPlayersChangesToServer(this);

    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
        sendPlayersChangesToServer(this);

    }

    public int getProfImage() {
        return profImage;
    }

    public void setProfImage(int profImage) {
        this.profImage = profImage;
        sendPlayersChangesToServer(this);

    }

    public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
        this.securityQuestionAnswer = DataBase.hashWithApacheCommons(securityQuestionAnswer);
        sendPlayersChangesToServer(this);

    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
        sendPlayersChangesToServer(this);
    }

    public ArrayList<Chat> getChats() {
        return chats;
    }

    public void addChat(Chat chat) {
        chats.add(chat);
        chat.addMember(this);
        sendPlayersChangesToServer(this);
    }

    public void removeChat(Chat chat) {
        chats.remove(chat);
        sendPlayersChangesToServer(this);
    }

    public PrivateChat getPrivateChatById(String id) {
        for (Chat chat : chats) {
            if (chat instanceof PrivateChat && chat.getId().equalsIgnoreCase(id))
                return (PrivateChat) chat;
        }
        return null;
    }

    public static Group getGroupById(String id) {
        for (Chat chat : chats) {
            if (chat instanceof Group && chat.getId().equalsIgnoreCase(id))
                return (Group) chat;
        }
        return null;
    }

    public void update(Player player) {
        chats = player.chats;
        username = player.username;
        password = player.password;
        nickname = player.nickname;
        email = player.email;
        securityQuestion = player.securityQuestion;
        slogan = player.slogan;
        maxStore = player.maxStore;
        profImage = player.profImage;
        lastVisit = player.lastVisit;
    }


    public ArrayList<FriendShipRequest> getFriendShipRequests() {
        return friendShipRequests;
    }

    public void addFriendShipRequest(FriendShipRequest friendShipRequest) {
        friendShipRequests.add(friendShipRequest);
        sendPlayersChangesToServer(this);
    }

    public void removeFriendShipRequest(FriendShipRequest friendShipRequest) {
        friendShipRequests.remove(friendShipRequest);
        sendPlayersChangesToServer(this);
    }

    public void addFriends(Player friend) {
        friends.add(friend);
        sendPlayersChangesToServer(this);
    }

    public void removeFriends(Player friend) {
        friends.add(friend);
        sendPlayersChangesToServer(this);
    }

    public Player getFriendByUsername(String username) {
        for (Player friend : friends) {
            if (friend.getUsername().equals(username))
                return friend;
        }
        return null;
    }

    public ArrayList<Player> getFriends() {
        return friends;
    }

    public String getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(String lastVisit) {
        this.lastVisit = lastVisit;
    }
}
