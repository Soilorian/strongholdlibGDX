package org.example.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Tunnel {

    public DataOutputStream out;
    public DataInputStream in;

    public Tunnel(Socket socket) throws IOException {
        this.out = new DataOutputStream(socket.getOutputStream());
        this.in = new DataInputStream(socket.getInputStream());
    }
}
