package org.example.control.menucontrollers;

import org.example.control.Controller;
import org.example.control.comparetors.PlayerComparator;
import org.example.control.enums.ProfileMenuMessages;
import org.example.model.DataBase;
import org.example.model.Player;


import static org.example.control.menucontrollers.EntranceMenuController.*;

public class ProfileMenuController {

    public static String changeUsername(String username) {
        if (Controller.isFieldEmpty(username)) {
            return ProfileMenuMessages.EMPTY_USERNAME.toString();
        }
        if (isUsernameInvalid(username)) {
            return ProfileMenuMessages.USERNAME_INVALID.toString();
        }
        if (isUsernameExist(username)) {
            return ProfileMenuMessages.USERNAME_ALREADY_EXISTS.toString();
        }
        Player player = DataBase.getCurrentPlayer();
        player.setUsername(username);
        return ProfileMenuMessages.SUCCEED.toString();
    }

    public static String changeNickname(String nickname) {
        if (Controller.isFieldEmpty(nickname)) {
            return ProfileMenuMessages.EMPTY_NICKNAME.toString();
        }
        Player player = DataBase.getCurrentPlayer();
        if (player.getNickname().equals(nickname)) {
            return ProfileMenuMessages.NICKNAME_EXISTS.toString();
        }
        player.setNickname(nickname);
        return ProfileMenuMessages.SUCCEED.toString();
    }

    public static String changeEmail(String email) {
        if (Controller.isFieldEmpty(email)) {
            return ProfileMenuMessages.EMPTY_EMAIL.toString();
        }
        if (isEmailInvalid(email)) {
            return ProfileMenuMessages.INVALID_EMAIL.toString();
        }
        if (isEmailExist(email)) {
            return ProfileMenuMessages.EMAIL_ALREADY_EXISTS.toString();
        }
        Player player = DataBase.getCurrentPlayer();
        player.setEmail(email);
        return ProfileMenuMessages.SUCCEED.toString();
    }

    public static String changePassword(String oldPassword, String newPassword) {
        if (Controller.isFieldEmpty(oldPassword)) {
            return ProfileMenuMessages.EMPTY_OLD_PASSWORD.toString();
        } else if (Controller.isFieldEmpty(newPassword)) {
            return ProfileMenuMessages.EMPTY_NEW_PASSWORD.toString();
        }
        Player player = DataBase.getCurrentPlayer();
        if (!player.checkPassword(oldPassword)) {
            return ProfileMenuMessages.INCORRECT_PASSWORD.toString();
        }
        if (oldPassword.equals(newPassword)) {
            return ProfileMenuMessages.PASSWORD_ALREADY_EXISTS.toString();
        }
        if (isPasswordWeak(newPassword)) {
            return findPasswordProblem(newPassword);
        }
        player.setPassword(newPassword);
        return ProfileMenuMessages.SUCCEED.toString();
    }

    public static String changeSlogan(String slogan) {
        if (Controller.isFieldEmpty(slogan)) {
            return ProfileMenuMessages.EMPTY_SLOGAN.toString();
        }
        Player player = DataBase.getCurrentPlayer();
        if (player.getSlogan().equals(slogan)) {
            return ProfileMenuMessages.SLOGAN_EXISTS.toString();
        }
        player.setSlogan(slogan);
        return ProfileMenuMessages.SUCCEED.toString();
    }

    public static void removeSlogan() {
        Player player = DataBase.getCurrentPlayer();
        player.setSlogan(null);
    }

    public static String showMaxScore() {
        Player player = DataBase.getCurrentPlayer();
        return "MaxScore: " + player.getMaxStore();
    }

    public static int showRank() {
        DataBase.getPlayers().sort(new PlayerComparator());
        return DataBase.getPlayers().indexOf(DataBase.getCurrentPlayer()) + 1;
    }

    public static String showSlogan() {
        String slogan;
        Player player = DataBase.getCurrentPlayer();
        slogan = player.getSlogan();
        if (slogan.isEmpty()) return "slogan is empty";
        return "Slogan: " + slogan;
    }

}
