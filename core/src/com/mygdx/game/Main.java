package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import org.example.control.Controller;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Scanner;

public class Main extends ApplicationAdapter {
    public static void main(String[] args) throws ClassNotFoundException, IOException, UnsupportedAudioFileException, LineUnavailableException, CoordinatesOutOfMap, NotInStoragesException {
        Scanner scanner = new Scanner(System.in);
        Controller.run(scanner);
    }
}