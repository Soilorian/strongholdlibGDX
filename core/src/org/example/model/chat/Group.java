package org.example.model.chat;

import org.example.model.Player;

import java.io.Serializable;

public class Group extends Chat implements Serializable {
    public Group(Player admin, String id, String name) {
        super(admin, id, name);
        super.addMember(admin);
    }
}
