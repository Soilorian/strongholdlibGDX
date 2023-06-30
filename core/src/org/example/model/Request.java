package org.example.model;

import java.io.Serializable;

public class Request implements Serializable {
    private String string;
    public Request(String s) {
        string = s;
    }

    public String getString() {
        return string;
    }
}
