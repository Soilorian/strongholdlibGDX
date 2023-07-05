package org.example.model.chat;

import org.example.model.DataBase;
import org.example.model.Player;

import java.io.Serializable;
import java.util.Objects;

public class PrivateChat extends Chat implements Serializable {
    public PrivateChat(Player admin, String id, String name) {
        super(admin, id, name);
        addMember(admin);
        if(!Objects.equals(DataBase.getPlayerByUsername(id), admin))
            addMember(DataBase.getPlayerByUsername(id));
    }
    public String getName(){
        if(super.getOwner().equals(DataBase.getCurrentPlayer())){
            return super.getName();
        } else return super.getOwner().getUsername();
    }
    public String getId(){
        if(super.getOwner().equals(DataBase.getCurrentPlayer())){
            return super.getId();
        } else return super.getOwner().getUsername();
    }

    public String toString(){
        return getName() + ", id: " + getId() + ", "+"private chat";
    }
}
