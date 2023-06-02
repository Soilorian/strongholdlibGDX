package org.example.view.menus;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.utils.ScreenUtils;
import org.example.control.Controller;
import org.example.control.SoundPlayer;
import org.example.control.enums.EntranceMenuMessages;
import org.example.control.menucontrollers.EntranceMenuController;
import org.example.model.Captcha;
import org.example.model.DataBase;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;
import org.example.view.enums.commands.EntranceMenuCommands;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;
import java.util.regex.Matcher;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class EntranceMenu extends Menu {

    private TextField loginUsername,loginPassword;

    private Label loginUsernameLabel,loginPasswordLabel,loginLabel,registerLabel
            ,loginResult,
    private Button loginSubmit;
    private CheckBox stayLogged;


    public EntranceMenu() {
        super();
        createComponents();

    }


    public void createComponents() {
        loginUsername = new TextField("",controller.getSkin());
        loginUsername.setX(1200);
        loginUsername.setY(500);
        loginUsername.setWidth(150);
        loginUsername.setHeight(100);
        loginUsername.setDisabled(false);

        stage.addActor(loginUsername);
    }


    public void render(float delta){
        ScreenUtils.clear(Color.WHITE);
        batch.begin(camera);
        timerSlider.setValue(timerSlider.getValue()+1);
        timerSlider.updateVisualValue();
        stage.draw();
        stage.act();
        batch.end();
    }



    @Override
    public void run(String input) throws IOException, UnsupportedAudioFileException, LineUnavailableException, CoordinatesOutOfMap, NotInStoragesException {
        Matcher matcher;
        while (true) {
            if (input.isEmpty()) continue;
            if ((matcher = EntranceMenuCommands.getMatcher(input, EntranceMenuCommands.CREATE_USER)) != null) {
                createNewUser(matcher, 1);
            } else if ((input.equalsIgnoreCase("open music player"))){
                controller.setScreen(Menus.MUSIC_CONTROL_MENU.getMenu());
                controller.changeMenu(this);
            } else if ((matcher = EntranceMenuCommands.getMatcher(input, EntranceMenuCommands.CREATE_USER_WITHOUT_SLOGAN)) != null) {
                createNewUser(matcher, 2);
            } else if ((matcher = EntranceMenuCommands.getMatcher(input, EntranceMenuCommands.CREATE_USER_WITH_RANDOM_PASSWORD)) != null) {
                createNewUser(matcher, 3);
            } else if ((matcher = EntranceMenuCommands.getMatcher(input, EntranceMenuCommands.LOGIN)) != null) {
                if (login(matcher, false))
                    break;
            } else if ((matcher = EntranceMenuCommands.getMatcher(input, EntranceMenuCommands.FORGOT_PASSWORD)) != null) {
                forgetPassword(matcher);
            } else if ((matcher = EntranceMenuCommands.getMatcher(input, EntranceMenuCommands.LOGIN_STAY_IN)) != null) {
                if (login(matcher, true)) {
                    EntranceMenuController.stayLogged();
                    break;
                }
            } else if (EntranceMenuCommands.getMatcher(input, EntranceMenuCommands.EXIT) != null) {
                Controller.setCurrentMenu(null);
                break;
            } else {
                SoundPlayer.play(Sounds.AKHEY);
                System.out.println("invalid command");
            }
        }
    }


    private void createNewUser(Matcher matcher, int type) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        String username = Controller.removeQuotes(matcher.group("Username"));
        String password = Controller.removeQuotes(matcher.group("Password"));
        String passwordConfirmation, slogan;
        String email = Controller.removeQuotes(matcher.group("Email"));
        String nickname = Controller.removeQuotes(matcher.group("Nickname"));

        if (password.equals("random")) {
            password = EntranceMenuController.randomPassword();
            System.out.println("your random password is: " + password);
            do {
                System.out.println("please re enter your password");
                passwordConfirmation = ""; // TODO: 6/1/2023 move to new menu
            } while (EntranceMenuController.isConfirmationNotEqual(password, passwordConfirmation));
        } else
            passwordConfirmation = Controller.removeQuotes(matcher.group("PasswordConfirmation"));
        if (type == 2) {
            slogan = "null";
        } else {
            slogan = Controller.removeQuotes(matcher.group("Slogan"));
            if (slogan.equals("random")) {
                slogan = DataBase.getRandomSlogan();
                System.out.println("your random slogan is :\n" + slogan);
            }
        }
        String result = EntranceMenuController.createNewUser(username, password, passwordConfirmation, nickname, email, slogan);
        System.out.println(result);
        if (result.equals(EntranceMenuMessages.SUCCEED.toString())) {
            pickSecurityQuestion();
            captchaChecker();
        }
    }

    private void captchaChecker() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        String input;
        while (true) {
            System.out.println(EntranceMenuMessages.ENTER_CAPTCHA);
            Captcha captcha = EntranceMenuController.createCaptcha();
            captcha.printPicture();
            do {
                input = ""; // TODO: 6/1/2023 move to screen
                if (EntranceMenuCommands.getMatcher(input, EntranceMenuCommands.NEW_CAPTCHA) != null)
                    break;
                if (!EntranceMenuController.isDigit(input))
                    System.out.println("invalid input! captcha can only be a digit");
                else if (captcha.checkCaptcha(Integer.parseInt(input))) {
                    SoundPlayer.play(Sounds.BENAZAM);
                    System.out.println(EntranceMenuMessages.SUCCEED);
                    return;
                } else System.out.println("invalid input!");
            } while (true);
        }
    }

    private void pickSecurityQuestion() {
        String command;
        while (true) {
            System.out.println(" Pick your security question:\n1. What is the first game you played?\n" +
                    "2. When did you meet mossayeb?\n3. What is your favorite patoq in university");
            command = ""; // TODO: 6/1/2023 move to screen
            Matcher matcher;
            if ((matcher = EntranceMenuCommands.getMatcher(command, EntranceMenuCommands.PICK_QUESTION)) != null) {
                String questionNumber = matcher.group("QuestionNumber");
                String answer = Controller.removeQuotes(matcher.group("Answer"));
                String answerConfirmation = Controller.removeQuotes(matcher.group("AnswerConfirmation"));
                String str = EntranceMenuController.pickSecurityQuestion(questionNumber, answer, answerConfirmation);
                System.out.println(str);
                if (str.equals(EntranceMenuMessages.SUCCEED.toString()))
                    return;
            } else
                System.out.println("invalid command!");
        }
    }

    private boolean login(Matcher matcher, boolean stayLogged) throws IOException {
        if (stayLogged) {
            String stay = Controller.removeQuotes(matcher.group("StayLoggedIn"));
            if (!stay.isEmpty()) {
                System.out.println("invalid command!");
                return false;
            }
        }
        String username = Controller.removeQuotes(matcher.group("Username"));
        String password = Controller.removeQuotes(matcher.group("Password"));
        String login = EntranceMenuController.login(username, password, stayLogged);
        System.out.println(login);
        return login.equals(EntranceMenuMessages.SUCCEED.toString());
    }

    private void forgetPassword(Matcher matcher) {
        String username = Controller.removeQuotes(matcher.group("Username"));
        if (Controller.isFieldEmpty(username)) {
            System.out.println(EntranceMenuMessages.EMPTY_FIELD);
            return;
        }
        String result = EntranceMenuController.checkForUsername(username);
        if (result.equals(EntranceMenuMessages.SUCCEED.toString())) {
            System.out.println("Answer your security question\n" + EntranceMenuController.getSecurityQuestion(username));
            String commands;
            //noinspection ConditionalBreakInInfiniteLoop
            do {
                commands = ""; // TODO: 6/1/2023 create new menu
                if (commands.matches("^back$"))
                    return;
                if (EntranceMenuController.checkSecurityAnswer(username, commands))
                    break;
                else
                    System.out.println("wrong answer");
            } while (true);
            System.out.println("that was right, now enter your new password");
            do {
                commands = ""; // TODO: 6/1/2023 create new menu
                if (commands.matches("^back$"))
                    return;
                result = EntranceMenuController.changePassword(username, commands);
                System.out.println(result);
            } while (!result.equals(EntranceMenuMessages.SUCCEED.toString()));
        } else
            System.out.println(result);
    }
}
