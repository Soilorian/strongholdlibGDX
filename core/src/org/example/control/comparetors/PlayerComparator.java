package org.example.control.comparetors;

import org.example.model.Player;

import java.util.Comparator;

public class PlayerComparator implements Comparator<Player> {
    public int compare(Player player1, Player player2) {
        if (player1.getMaxStore() != player2.getMaxStore())
            return player2.getMaxStore() - player1.getMaxStore();
        return player1.getUsername().compareTo(player2.getUsername());
    }
}