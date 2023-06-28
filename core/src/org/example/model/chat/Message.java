package org.example.model.chat;

import org.example.model.Player;

public class Message {
    private final Player owner;
    private final String content;

    public Message(Player owner, String content) {
        this.owner = owner;
        this.content = content;
    }

    public Player getOwner() {
        return owner;
    }

    public String getContent() {
        return content;
    }

    public String toString(){
        return owner.getNickname()+"("+owner.getUsername()+"): \""+content+"\"";
    }
}
