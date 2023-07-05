package org.example.model.utils;


import org.example.model.Player;

import java.io.Serializable;

public class StreamSetup implements Serializable {
    Player streamer;
    Player watcher;

    public StreamSetup(Player streamer, Player watcher) {
        this.streamer = streamer;
        this.watcher = watcher;
    }

    public Player getStreamer() {
        return streamer;
    }

    public Player getWatcher() {
        return watcher;
    }
}
