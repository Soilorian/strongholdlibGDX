package org.example.model.utils;

import org.example.model.Player;

public class FriendShipRequest {
    private Player sender;
    private Player acceptor;

    public FriendShipRequest(Player sender, Player acceptor) {
        this.sender = sender;
        this.acceptor = acceptor;
    }


    public Player getSender() {
        return sender;
    }

    public Player getAcceptor() {
        return acceptor;
    }
}
