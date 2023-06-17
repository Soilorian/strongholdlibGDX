package org.example.control.menucontrollers;


import com.badlogic.gdx.Screen;
import org.example.Main;
import org.example.control.Controller;
import org.example.control.enums.EntranceMenuMessages;
import org.example.model.Captcha;
import org.example.model.DataBase;
import org.example.model.Player;
import org.example.view.enums.Menus;
import org.example.view.menus.EntranceMenu;
import org.example.view.menus.Menu;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import static org.example.control.Controller.isFieldEmpty;
import static org.example.control.Controller.setCurrentMenu;

public class EntranceMenuController {

    private static final Random random = new Random();
    private static int passwordMiss = 0;

    public static boolean isUsernameInvalid(String username) {
        return !username.matches("[\\w\\s]+");
    }

    public static boolean isUsernameExist(String username) {
        return DataBase.getPlayerByUsername(username) != null;
    }

    public static boolean isEmailInvalid(String email) {
        return !email.matches("[\\w.]+@[\\w.]+\\.[\\w.]+");
    }

    public static boolean isEmailExist(String email) {
        return DataBase.getPlayerByEmail(email) != null;
    }

    public static boolean isPasswordWeak(String password) {
        return !password.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).{6,}");
    }

    public static boolean isDigit(String... str) {
        for (String s : str)
            if (!s.matches("-?\\d+"))
                return false;
        return true;
    }

    public static boolean isConfirmationNotEqual(String str, String confirmation) {
        return !str.equals(confirmation);
    }

    public static boolean isQuestionNumCorrect(int questionNum) {
        return questionNum <= 3 && questionNum >= 1;
    }

    public static boolean isPasswordIncorrect(Player player, String entryPassword) {
        return !player.checkPassword(entryPassword);
    }

    private static String checkEmpty(String username, String password, String passwordConfirmation,
                                     String nickname, String email, String slogan) {
        if (isFieldEmpty(username)) {
            return EntranceMenuMessages.EMPTY_USERNAME.toString();
        } else if (isFieldEmpty(nickname)) {
            return EntranceMenuMessages.EMPTY_NICKNAME.toString();
        } else if (isFieldEmpty(slogan)) {
            return EntranceMenuMessages.EMPTY_SLOGAN.toString();
        } else if (isFieldEmpty(password)) {
            return EntranceMenuMessages.EMPTY_PASSWORD.toString();
        } else if (isFieldEmpty(passwordConfirmation)) {
            return EntranceMenuMessages.EMPTY_PASSWORD_CONFIRMATION.toString();
        } else if (isFieldEmpty(email)) {
            return EntranceMenuMessages.EMPTY_EMAIL.toString();
        }
        return null;
    }


    public static String findPasswordProblem(String password) {
        if (Controller.getMatcher(password, "^.*[a-z]+.*$") == null)
            return EntranceMenuMessages.LOWERCASE_PASSWORD.toString();
        if (Controller.getMatcher(password, "^.*[A-Z]+.*$") == null)
            return EntranceMenuMessages.UPPERCASE_PASSWORD.toString();
        if (Controller.getMatcher(password, "^.*[0-9]+.*$") == null)
            return EntranceMenuMessages.DIGIT_PASSWORD.toString();
        if (Controller.getMatcher(password, "^.*[^a-zA-Z0-9]+.*$") == null)
            return EntranceMenuMessages.SYMBOl_PASSWORD.toString();
        return EntranceMenuMessages.LENGTH_PASSWORD.toString();
    }


    static public String createNewUser(String username, String password, String passwordConfirmation,
                                       String nickname, String email, String slogan,
                                       String questionNum, String answer, String answerConfirmation) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        String result;
        if ((result = checkEmpty(username, password, passwordConfirmation, nickname, email, slogan)) != null) {
            return result;
        }
        if (isUsernameInvalid(username))
            return EntranceMenuMessages.USERNAME_INVALID.toString();
        if (isUsernameExist(username))
            return EntranceMenuMessages.USERNAME_ALREADY_EXISTS.toString();
        if (isPasswordWeak(password))
            return findPasswordProblem(password);
        if (isConfirmationNotEqual(password, passwordConfirmation))
            return EntranceMenuMessages.INVALID_CONFIRMATION.toString();
        if (isEmailInvalid(email))
            return EntranceMenuMessages.INVALID_EMAIL.toString();
        if (isEmailExist(email))
            return EntranceMenuMessages.EMAIL_ALREADY_EXISTS.toString();
        Player player = new Player(username, password, nickname, email, slogan);
        DataBase.addPlayer(player);
        return pickSecurityQuestion(questionNum, answer ,answerConfirmation);
    }


    static public String pickSecurityQuestion(String questionNum, String answer, String answerConfirmation) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (isFieldEmpty(questionNum)) {
            return EntranceMenuMessages.EMPTY_QUESTION_NUM.toString();
        } else if (isFieldEmpty(answer)) {
            return EntranceMenuMessages.EMPTY_ANSWER.toString();
        } else if (isFieldEmpty(answerConfirmation)) {
            return EntranceMenuMessages.EMPTY_ANSWER_CONFIRMATION.toString();
        }
        if (!isDigit(questionNum))
            return EntranceMenuMessages.IS_DIGIT.toString();
        int questionNumber = Integer.parseInt(questionNum);
        if (!isQuestionNumCorrect(questionNumber))
            return EntranceMenuMessages.INVALID_QUESTION_NUMBER.toString();
        if (isConfirmationNotEqual(answer, answerConfirmation))
            return EntranceMenuMessages.INVALID_CONFIRMATION.toString();
        Player player = DataBase.getLastPlayer();
        switch (questionNumber) {
            case 1 : player.setSecurityQuestion("What is the first game you played?");
            case 2 : player.setSecurityQuestion("When did you meet Mossayeb?");
            case 3 : player.setSecurityQuestion("What is your favorite patoq in university?");
        }
        player.setSecurityQuestionAnswer(answer);
        DataBase.updatePlayersXS();
        return EntranceMenuMessages.SUCCEED.toString();
    }

    public static Captcha createCaptcha() {
        return new Captcha();
    }


    static public String login(String username, String password, boolean stayLogged) throws IOException {
        if (isFieldEmpty(username)) {
            return EntranceMenuMessages.EMPTY_USERNAME.toString();
        } else if (isFieldEmpty(password)) {
            return EntranceMenuMessages.EMPTY_PASSWORD.toString();
        }
        Player player;
        if ((player = DataBase.getPlayerByUsername(username)) == null)
            return EntranceMenuMessages.USERNAME_NOT_EXIST.toString();
        if (isPasswordIncorrect(player, password)) {
            passwordMiss++;
            passwordDelay();
            return EntranceMenuMessages.INCORRECT_PASSWORD.toString();
        }
        passwordMiss = 0;
        DataBase.setCurrentPlayer(player);
        if (stayLogged)
            DataBase.addStayLoggedPlayed(player);
        return EntranceMenuMessages.SUCCEED.toString();

    }

    public static void stayLogged() {
        DataBase.setStayLoggedIn(true);
    }

    public static void passwordDelay() {
        if (passwordMiss % 5 == 0){
            Screen screen = Main.getController().getScreen();
            if (screen instanceof EntranceMenu) {
                ((EntranceMenu) screen).delay();
            }
        }
    }

    static public String changePassword(String username, String newPassword) {
        Player player = DataBase.getPlayerByUsername(username);
        assert player != null;
        if (isPasswordWeak(newPassword))
            return findPasswordProblem(newPassword);
        player.setPassword(newPassword);
        return EntranceMenuMessages.SUCCEED.toString();
    }

    public static String checkForUsername(String username) {
        if (!isUsernameExist(username))
            return EntranceMenuMessages.USERNAME_NOT_EXIST.toString();
        return EntranceMenuMessages.SUCCEED.toString();
    }

    static public String randomPassword() {
        String password = getRandomChar(65, 90);
        password += getRandomChar(48, 57);
        password += getRandomChar(97, 122);
        password += getRandomChar(33, 47);
        return password;
    }

    private static String getRandomChar(int randomNumberOrigin, int randomNumberBound) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            result.append((char) (random.nextInt(randomNumberOrigin, randomNumberBound)));
        }
        return result.toString();
    }

    public static String getSecurityQuestion(String username) {
        return Objects.requireNonNull(DataBase.getPlayerByUsername(username)).getSecurityQuestion();
    }

    public static boolean checkSecurityAnswer(String username, String answer) {
        Player player = DataBase.getPlayerByUsername(username);
        if (player == null) return false;
        return player.checkSecurityQuestion(answer);
    }
}