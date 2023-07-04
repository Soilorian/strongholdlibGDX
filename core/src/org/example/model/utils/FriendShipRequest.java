package org.example.model.utils;

import org.example.model.Player;

import java.io.Serializable;

public class FriendShipRequest  implements Serializable {
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
