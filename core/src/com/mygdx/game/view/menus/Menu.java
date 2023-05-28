package org.example.view.menus;

import org.example.control.Controller;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Scanner;

public interface Menu {
    Scanner scanner = Controller.getMainScanner();

    void run() throws IOException, UnsupportedAudioFileException, LineUnavailableException, CoordinatesOutOfMap, NotInStoragesException;

    default void showAvailableCommands() {

    }
}
