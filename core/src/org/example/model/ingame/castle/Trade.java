package org.example.model.ingame.castle;

import org.example.model.ingame.map.resourses.Resource;

public class Trade {

    private static int lastId = 0;
    private final Empire sender;
    private final int price;
    private Empire acceptor;
    private Resource resource;
    private String message;
    private int id;

    public Trade(Empire sender, Resource resource, int price, String message) {
        this.sender = sender;
        this.resource = resource;
        this.price = price;
        this.message = "Sender message:\n" + message;
        id = ++lastId;
    }

    public Empire getSender() {
        return sender;
    }

    public Empire getAcceptor() {
        return acceptor;
    }

    public void setAcceptor(Empire acceptor) {
        this.acceptor = acceptor;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public int getPrice() {
        return price;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
