package org.example.view.menus;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
import java.io.IOException;
import java.util.regex.Matcher;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class EntranceMenu extends Menu {

    private TextField loginUsernameText, loginPasswordText, loginCaptchaText,
            registerUsernameText, registerPasswordText, registerPasswordConfirmationText,
            registerCaptchaText, registerEmailText, registerNicknameText;
    private Label login, register, loginResult, registerResult;
    private TextButton loginSubmit, registerSubmit;
    private ImageButton randomPassword, randomSlogan, loginCaptchaButton, registerCaptchaButton;
    private CheckBox stayLogged;
    private Image loginUsernameImage, loginPasswordImage, loginCaptchaImage,
            registerUsernameImage, registerPasswordImage, registerCaptchaImage;
    private Captcha loginCaptcha, registerCaptcha;


    public EntranceMenu() {
        super();
        loginUsernameText = new TextField("", controller.getSkin());
        loginPasswordText = new TextField("", controller.getSkin());
        loginCaptchaText = new TextField("", controller.getSkin());

        registerUsernameText = new TextField("", controller.getSkin());
        registerPasswordText = new TextField("", controller.getSkin());
        registerPasswordConfirmationText = new TextField("", controller.getSkin());
        registerCaptchaText = new TextField("", controller.getSkin());
        registerEmailText = new TextField("", controller.getSkin());
        registerNicknameText = new TextField("", controller.getSkin());


        stayLogged = new CheckBox("Remember Me", controller.getSkin());


        loginSubmit = new TextButton("Submit", controller.getSkin());
        registerSubmit = new TextButton("register", controller.getSkin());

        Drawable drawable = new TextureRegionDrawable(new TextureRegion(controller.getCaptchaPath()));
        Drawable drawable1 = new TextureRegionDrawable(new TextureRegion(controller.getCaptchaPath()));
        Drawable drawable2 = new TextureRegionDrawable(new TextureRegion(controller.getCaptchaPath()));
        loginCaptchaButton = new ImageButton(drawable);
        registerCaptchaButton = new ImageButton(drawable);

        login = new Label("login", controller.getSkin());
        loginResult = new Label("", controller.getSkin());
        register = new Label("register", controller.getSkin());
        registerResult = new Label("", controller.getSkin());

        loginCaptcha = new Captcha();
        Texture captchaTexture = new Texture("saved.png");

        loginUsernameImage = new Image(controller.getUserAvatar());
        registerUsernameImage = new Image(controller.getUserAvatar());
        loginPasswordImage = new Image(controller.getLock());
        registerPasswordImage = new Image(controller.getLock());
        loginCaptchaImage = new Image(captchaTexture);
        registerCaptcha = new Captcha();
        captchaTexture = new Texture("saved.png");
        registerCaptchaImage = new Image(captchaTexture);
    }

    public void create() {
        createTextFields();
        createButtons();
        createLabels();
        createCheckBoxes();
        createImages();
        Gdx.input.setInputProcessor(stage);
    }

    public void addActor(Actor actor, int x, int y, int width, int height) {
        actor.setX(x);
        actor.setY(y);
        actor.setWidth(width);
        actor.setHeight(height);
        stage.addActor(actor);
    }

    public void createText(TextField textField, int x, int y, int width, int height, String text) {
        addActor(textField, x, y, width, height);
        textField.setDisabled(false);
        textField.setMessageText(text);
    }

    public void createButton(TextButton textButton, int x, int y, int width, int height) {
        addActor(textButton, x, y, width, height);
        textButton.setDisabled(false);
    }

    public void createButton(ImageButton imageButton, int x, int y, int width, int height) {
        addActor(imageButton, x, y, width, height);
        imageButton.setDisabled(false);
    }

    private void createImages() {
        addActor(loginUsernameImage, 1200, 850, 50, 50);
        addActor(loginPasswordImage, 1200, 800, 50, 40);
        addActor(registerUsernameImage, 1200, 550, 50, 50);
        addActor(registerPasswordImage, 1200, 500, 50, 40);
        addActor(loginCaptchaImage, 1290, 750, 70, 40);
        addActor(registerCaptchaImage, 1290, 300, 70, 40);

    }

    private void createLabels() {
        addActor(login, 1380, 900, 350, 50);
        addActor(loginResult, 1250, 630, 350, 50);
        login.setFontScale(2f, 2f);
        addActor(register, 1340, 600, 350, 50);
        register.setFontScale(2f, 2f);
        addActor(loginResult, 1250, 630, 350, 50);
    }

    public void createTextFields() {
        createText(loginUsernameText, 1250, 850, 350, 50, "Username");
        createText(loginPasswordText, 1250, 800, 350, 50, "Password");
        createText(loginCaptchaText, 1360, 750, 240, 50, "Enter Captcha");

        createText(registerUsernameText, 1250, 550, 350, 50, "Username");
        createText(registerPasswordText, 1250, 500, 350, 50, "Password");
        createText(registerPasswordConfirmationText, 1250, 450, 350, 50, "Password Confirmation");
        createText(registerNicknameText, 1250, 400, 350, 50, "Nickname");
        createText(registerEmailText, 1250, 350, 350, 50, "Email");
        createText(registerCaptchaText, 1360, 300, 240, 50, "Captcha");


    }

    public void createCheckBoxes() {
        stayLogged.setX(1350);
        stayLogged.setY(720);
        stage.addActor(stayLogged);
    }

    public void createButtons() {
        createButton(loginSubmit, 1250, 670, 350, 50);
        createButton(loginCaptchaButton, 1080, 740, 350, 50);
        createButton(registerCaptchaButton, 1080, 300, 350, 50);
        loginSubmit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    if (loginCaptcha.isFilledCaptchaValid(loginCaptchaText.getText())) {
                        loginResult.setText(EntranceMenuController.login(loginUsernameText.getText(),
                                loginPasswordText.getText(), stayLogged.isChecked()));
                    } else loginResult.setText("Invalid Captcha");

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        loginCaptchaButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loginCaptcha.generateCaptcha();
                loginCaptchaImage.remove();
                loginCaptchaImage = new Image(new Texture("saved.png"));
                addActor(loginCaptchaImage, 1290, 750, 70, 40);
            }
        });
        registerCaptchaButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                registerCaptcha.generateCaptcha();
                registerCaptchaImage.remove();
                registerCaptchaImage = new Image(new Texture("saved.png"));
                addActor(registerCaptchaImage, 1290, 300, 70, 40);
            }
        });
    }

    @Override
    public void run(String input) throws IOException, UnsupportedAudioFileException, LineUnavailableException, CoordinatesOutOfMap, NotInStoragesException {
        Matcher matcher;
        while (true) {
            if (input.isEmpty()) continue;
            if ((matcher = EntranceMenuCommands.getMatcher(input, EntranceMenuCommands.CREATE_USER)) != null) {
                createNewUser(matcher, 1);
            } else if ((input.equalsIgnoreCase("open music player"))) {
                controller.setScreen(Menus.MUSIC_CONTROL_MENU.getMenu());
                controller.changeMenu(this, this);
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
//            captcha.printPicture();
            do {
                input = ""; // TODO: 6/1/2023 move to screen
                if (EntranceMenuCommands.getMatcher(input, EntranceMenuCommands.NEW_CAPTCHA) != null)
                    break;
                if (!EntranceMenuController.isDigit(input))
                    System.out.println("invalid input! captcha can only be a digit");
//                else if (captcha.checkCaptcha(Integer.parseInt(input))) {
//                    SoundPlayer.play(Sounds.BENAZAM);
//                    System.out.println(EntranceMenuMessages.SUCCEED);
//                    return;
//                }
                else System.out.println("invalid input!");
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