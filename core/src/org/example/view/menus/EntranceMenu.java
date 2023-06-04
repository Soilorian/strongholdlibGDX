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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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
import org.example.view.enums.commands.Slogans;

public class EntranceMenu extends Menu {

    private final TextField loginUsernameText, loginPasswordText, loginCaptchaText,
            registerUsernameText, registerPasswordText, registerPasswordConfirmationText,
            registerCaptchaText, registerEmailText, registerNicknameText, registerSloganText,
            registerAnswerText;
    private final Label login, register, loginResult, registerResult;
    private final TextButton loginSubmit, registerSubmit;
    private final ImageButton randomPassword, randomSlogan, loginCaptchaButton, registerCaptchaButton,
    showPassword;
    private final CheckBox stayLogged;
    private final SelectBox<String> questions;
    private final Image loginUsernameImage;
    private final Image loginPasswordImage;
    private Image loginCaptchaImage;
    private final Image registerUsernameImage;
    private final Image registerPasswordImage;
    private Image registerCaptchaImage;
    private final Image backgroundImage;
    private final Captcha loginCaptcha, registerCaptcha;


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
        registerSloganText = new TextField("", controller.getSkin());
        registerAnswerText = new TextField("", controller.getSkin());


        stayLogged = new CheckBox("Remember Me", controller.getSkin());
        questions = new SelectBox<>(controller.getSkin());
        questions.setItems("What is the first game you played?","When did you meet Mossayeb?","What is your favorite patoq in university?");
        questions.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });


        loginSubmit = new TextButton("Submit", controller.getSkin());
        registerSubmit = new TextButton("register", controller.getSkin());

        Drawable drawable = new TextureRegionDrawable(new TextureRegion(controller.getCaptchaPath()));
        Drawable refresh = new TextureRegionDrawable(new TextureRegion(controller.getRefreshPath()));
        Drawable showPass = new TextureRegionDrawable(new TextureRegion(controller.getShowPassPath()));
        loginCaptchaButton = new ImageButton(drawable);
        registerCaptchaButton = new ImageButton(drawable);
        randomPassword = new ImageButton(refresh);
        randomSlogan = new ImageButton(refresh);
        showPassword = new ImageButton(showPass);

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
        backgroundImage = new Image(controller.resizer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),controller.getEntranceBackground()));
    }

    public void create() {
        createTextFields();
        createButtons();
        createLabels();
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

    public void addActor(Actor actor, int x, int y) {
        actor.setX(x);
        actor.setY(y);
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
        addActor(backgroundImage, 0, 0);
        addActor(loginUsernameImage, 1200, 850, 50, 50);
        addActor(loginPasswordImage, 1200, 800, 50, 40);
        addActor(registerUsernameImage, 1200, 550, 50, 50);
        addActor(registerPasswordImage, 1200, 500, 50, 40);
        addActor(loginCaptchaImage, 1290, 750, 70, 40);
        addActor(registerCaptchaImage, 1290, 250, 70, 40);
    }

    private void createLabels() {
        addActor(login, 1380, 900, 350, 50);
        addActor(loginResult, 1250, 630, 350, 50);
        login.setFontScale(2f, 2f);
        addActor(register, 1340, 600, 350, 50);
        register.setFontScale(2f, 2f);
        addActor(registerResult, 1250, 50, 350, 50);
        registerResult.setText("");
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
        createText(registerCaptchaText, 1360, 250, 240, 50, "Captcha");
        createText(registerSloganText, 1250, 300, 350, 50, "Slogan");
        createText(registerAnswerText, 1250, 150, 350, 50, "Answer");

        registerPasswordText.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (EntranceMenuController.isPasswordWeak(registerPasswordText.getText()))
                    registerResult.setText(EntranceMenuController.findPasswordProblem(registerPasswordText.getText()));
                else
                    registerResult.setText("");
            }
        });

    }

    public void createButtons() {
        createButton(loginSubmit, 1250, 670, 350, 50);
        createButton(registerSubmit, 1250, 100, 350, 50);
        createButton(loginCaptchaButton, 1080, 740, 350, 50);
        createButton(registerCaptchaButton, 1080, 250, 350, 50);
        createButton(showPassword, 1540, 810, 70, 40);
        addActor(randomPassword, 1540, 500, 70, 40);
        addActor(randomSlogan, 1540, 300, 70, 40);

        showPassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loginPasswordText.setPasswordMode(!loginPasswordText.isPasswordMode());
                loginPasswordText.setPasswordCharacter('*');
            }
        });

        loginSubmit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    login();
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
                addActor(registerCaptchaImage, 1290, 250, 70, 40);
            }
        });

        randomPassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String str = EntranceMenuController.randomPassword();
                registerPasswordText.setText(str);
            }
        });

        randomSlogan.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                registerSloganText.setText(Slogans.getRandomSlogan());
            }
        });

        registerSubmit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int i = 0;
                i += questions.getSelectedIndex();
                if (registerCaptcha.isFilledCaptchaValid(registerCaptchaText.getText())) {
                    try {
                        String result = EntranceMenuController.createNewUser(registerUsernameText.getText(),
                                registerPasswordText.getText(),
                                registerPasswordConfirmationText.getText(),
                                registerNicknameText.getText(),
                                registerEmailText.getText(),
                                registerSloganText.getText(),
                                String.valueOf(i),
                                registerAnswerText.getText(),
                                registerAnswerText.getText()
                        );
                        registerResult.setText(result);
                        if (result.equals(EntranceMenuMessages.SUCCEED.toString())) {
                            registerUsernameText.setText("");
                            registerPasswordText.setText("");
                            registerPasswordConfirmationText.setText("");
                            registerNicknameText.setText("");
                            registerEmailText.setText("");
                            registerSloganText.setText("");
                            registerAnswerText.setText("");
                            registerCaptchaText.setText("");

                        }

                    } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                        throw new RuntimeException(e);
                    }

                } else registerResult.setText("Invalid Captcha");
            }
        });
    }

    private void login() throws IOException {
            if (loginCaptcha.isFilledCaptchaValid(loginCaptchaText.getText())) {
                String result = EntranceMenuController.login(loginUsernameText.getText(),
                        loginPasswordText.getText(), stayLogged.isChecked());
                loginResult.setText(result);
                if (result.equals(EntranceMenuMessages.SUCCEED.toString()))
                    controller.changeMenu(Menus.MAIN_MENU.getMenu(), this);
            } else loginResult.setText("Invalid Captcha");
    }

    private void generateNewCaptcha(Captcha captcha, Image image,
                                    int x , int y , int width , int height) {
        captcha.generateCaptcha();
        image.remove();
        image = new Image(new Texture("saved.png"));
        addActor(image, x, y, width, height);
    }

    private void createNewUser(Matcher matcher, int type) throws UnsupportedAudioFileException, LineUnavailableException, IOException {

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