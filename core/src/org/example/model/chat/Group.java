package org.example.model.chat;

import org.example.model.Player;

public class Group extends Chat{
    public Group(Player admin, String id, String name) {
        super(admin, id, name);
        super.addMember(admin);
    }
}
