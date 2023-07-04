package org.example.model.chat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.example.model.Player;

import java.util.ArrayList;

public class Chat {
    private final ArrayList<Player> members = new ArrayList<>();
    private final ArrayList<Message> messages = new ArrayList<>();
    private final Player owner;
    private final String id;
    private final String name;
    public Chat(Player owner, String id, String name) {
        this.owner = owner;
        this.id = id;
        this.name = name;
    }

    public ArrayList<Player> getMembers() {
        return members;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public Player getOwner() {
        return owner;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addMember(Player player){
        this.members.add(player);
    }

    public void addMessage(Message message){
        this.messages.add(message);
    }

    public String toString() {
        String result = name + ", id: " + id + ", ";
        if (this instanceof Group) {
            result+= "group";
        }
        return result;
    }


    public void draw(SpriteBatch batch, float x, float y, float h) {
        for (int i = 0; i < h / 160 && messages.size() - 1 > i; i++) {
            Message message = messages.get(messages.size() - 1 - i);
            message.draw(batch, x, y + i * 160);
        }
    }
}
