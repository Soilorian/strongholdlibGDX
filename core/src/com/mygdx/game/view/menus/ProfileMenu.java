package org.example.view.menus;


import org.example.control.Controller;
import org.example.control.SoundPlayer;
import org.example.control.enums.EntranceMenuMessages;
import org.example.control.menucontrollers.ProfileMenuController;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;
import org.example.view.enums.commands.EntranceMenuCommands;
import org.example.view.enums.commands.ProfileMenuCommands;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.regex.Matcher;

import static org.example.view.enums.commands.ProfileMenuCommands.*;

public class ProfileMenu implements Menu {

    @Override
    public void run() throws IOException, UnsupportedAudioFileException, LineUnavailableException, CoordinatesOutOfMap, NotInStoragesException {
        System.out.println("entered " + Menus.getNameByObj(this));
        String command;
        Matcher matcher;
        while (true) {
            command = scanner.nextLine();
            if ((matcher = ProfileMenuCommands.getMatcher(command, CHANGE_USERNAME)) != null) {
                changeUsername(matcher);
            } else if (command.equalsIgnoreCase("show menu")) {
                System.out.println(Menus.getNameByObj(this));
            } else if ((matcher = ProfileMenuCommands.getMatcher(command, CHANGE_NICKNAME)) != null) {
                changeNickname(matcher);
            } else if ((matcher = ProfileMenuCommands.getMatcher(command, CHANGE_EMAIL)) != null) {
                changeEmail(matcher);
            } else if ((matcher = ProfileMenuCommands.getMatcher(command, CHANGE_PASSWORD)) != null) {
                changePassword(matcher);
            } else if ((matcher = ProfileMenuCommands.getMatcher(command, CHANGE_SLOGAN)) != null) {
                changeSlogan(matcher);
            } else if ((ProfileMenuCommands.getMatcher(command, REMOVE_SLOGAN)) != null) {
                System.out.println(ProfileMenuController.removeSlogan());
            } else if ((matcher = ProfileMenuCommands.getMatcher(command, DISPLAY_PROFILE)) != null) {
                showProfile(matcher);
            } else if (command.equals("back")) {
                Controller.setCurrentMenu(Menus.MAIN_MENU);
                break;
            } else if (EntranceMenuCommands.getMatcher(command, EntranceMenuCommands.EXIT) != null) {
                Controller.setCurrentMenu(null);
                break;
            } else if ((command.equalsIgnoreCase("open music player")))
                Menus.MUSIC_CONTROL_MENU.getMenu().run();
            else {
                SoundPlayer.play(Sounds.AKHEY);
                System.out.println("invalid command");
            }
        }
    }

    private void changeUsername(Matcher matcher) {
        String username = Controller.removeQuotes(matcher.group("Username"));
        System.out.println(ProfileMenuController.changeUsername(username));
    }

    private void changeNickname(Matcher matcher) {
        String nickname = Controller.removeQuotes(matcher.group("Nickname"));
        System.out.println(ProfileMenuController.changeNickname(nickname));
    }

    private void changeEmail(Matcher matcher) {
        String email = Controller.removeQuotes(matcher.group("Email"));
        System.out.println(ProfileMenuController.changeEmail(email));
    }

    private void changePassword(Matcher matcher) {
        String oldPassword = Controller.removeQuotes(matcher.group("OldPassword"));
        String newPassword = Controller.removeQuotes(matcher.group("NewPassword"));
        System.out.println(ProfileMenuController.changePassword(oldPassword, newPassword));
    }

    private void changeSlogan(Matcher matcher) {
        String slogan = Controller.removeQuotes(matcher.group("Slogan"));
        System.out.println(ProfileMenuController.changeSlogan(slogan));
    }

    private void showProfile(Matcher matcher) {
        String field = null;
        String option = null;
        try {
            option = matcher.group("Option1");
        } catch (Exception ignored) {
        }
        try {
            field = matcher.group("Field");
        } catch (Exception ignored) {
        }
        if (option != null) {
            if (field == null || Controller.isFieldEmpty(field))
                System.out.println(EntranceMenuMessages.EMPTY_FIELD);
            else
                System.out.println(ProfileMenuController.showProfile(field));
        } else
            System.out.println(ProfileMenuController.showProfile("all"));
    }
}
