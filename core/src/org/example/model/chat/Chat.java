package org.example.model.chat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import org.example.model.Player;

import java.awt.*;
import java.util.ArrayList;

public class Chat {
    private final ArrayList<Player> members;
    private final ArrayList<Message> messages;
    private final Player owner;
    private final String id;
    private final String name;

    public Chat(Player owner, String id, String name) {
        this.owner = owner;
        this.id = id;
        this.name = name;
        members= new ArrayList<>();
        messages= new ArrayList<>();
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

    public void draw(SpriteBatch batch, int x, int y){

    }


}
