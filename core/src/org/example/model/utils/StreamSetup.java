package org.example.model.utils;


import org.example.model.Player;

import java.io.Serializable;

public class StreamSetup implements Serializable {
    Player streamer;
    String streamerIp;
    String watcherIp;
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

    public String getStreamerIp() {
        return streamerIp;
    }

    public void setStreamerIp(String streamerIp) {
        this.streamerIp = streamerIp;
    }

    public String getWatcherIp() {
        return watcherIp;
    }

    public void setWatcherIp(String watcherIp) {
        this.watcherIp = watcherIp;
    }
}
