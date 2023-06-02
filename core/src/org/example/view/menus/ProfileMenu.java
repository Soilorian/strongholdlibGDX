package org.example.view.menus;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.example.Main;
import org.example.control.Controller;
import org.example.control.SoundPlayer;
import org.example.control.enums.EntranceMenuMessages;
import org.example.control.enums.ProfileMenuMessages;
import org.example.control.menucontrollers.ProfileMenuController;
import org.example.model.DataBase;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;
import org.example.view.enums.commands.EntranceMenuCommands;
import org.example.view.enums.commands.ProfileMenuCommands;
import sun.tools.jconsole.inspector.XTextField;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;
import java.util.regex.Matcher;

import static com.badlogic.gdx.Gdx.app;
import static com.badlogic.gdx.Gdx.graphics;
import static org.example.view.enums.commands.ProfileMenuCommands.*;

public class ProfileMenu extends Menu {
    private TextField changeUser,changePass,changeEmail,changeSlogan;
    private TextField password, newCheck,username;
    private CheckBox showPass, showNew;
    private Label error;
    private Button changePassBut,back,submit,changeUserBut,logout,delete;
    //private BitmapFont errorFont = new BitmapFont(Gdx.files.internal("Fonts/Error.fnt"));


    public ProfileMenu() {
        super();
        profileMenu();
    }
    @Override
    public void run(String command) throws IOException, UnsupportedAudioFileException, LineUnavailableException,
            CoordinatesOutOfMap, NotInStoragesException {
        System.out.println("entered " + Menus.getNameByObj(this));
        Matcher matcher;
        while (true) {
            if ((matcher = ProfileMenuCommands.getMatcher(command, CHANGE_USERNAME)) != null) {
                changeUsername(matcher);
            } else if (command.equalsIgnoreCase("show menu")) {
                System.out.println(Menus.getNameByObj(this));
            } else if ((matcher = ProfileMenuCommands.getMatcher(command, CHANGE_NICKNAME)) != null) {
                changeNickname(matcher);
            } else if ((matcher = ProfileMenuCommands.getMatcher(command, CHANGE_EMAIL)) != null) {
                changeEmail(matcher);
            } else if ((matcher = ProfileMenuCommands.getMatcher(command, CHANGE_PASSWORD)) != null) {
                //changePassword(matcher);
            } else if ((matcher = ProfileMenuCommands.getMatcher(command, CHANGE_SLOGAN)) != null) {
                changeSlogan(matcher);
            } else if ((ProfileMenuCommands.getMatcher(command, REMOVE_SLOGAN)) != null) {
                System.out.println(ProfileMenuController.removeSlogan());
            } else if ((matcher = ProfileMenuCommands.getMatcher(command, DISPLAY_PROFILE)) != null) {
                showProfile(matcher);
            } else if (command.equals("back")) {
                Controller.setCurrentMenu(Menus.MAIN_MENU);
                break;
            } else if ((command.equalsIgnoreCase("open music player"))) {
                controller.setScreen(Menus.MUSIC_CONTROL_MENU.getMenu());
                controller.changeMenu(this, this);
            }else {
                SoundPlayer.play(Sounds.AKHEY);
                System.out.println("invalid command");
            }
        }
    }

    @Override
    public void create() {
        Gdx.input.setInputProcessor(stage);
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

//    private void changePassword(Matcher matcher) {
//        String oldPassword = Controller.removeQuotes(matcher.group("OldPassword"));
//        String newPassword = Controller.removeQuotes(matcher.group("NewPassword"));
//        System.out.println(ProfileMenuController.changePassword(oldPassword, newPassword));
//    }

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
    private void profileMenu(){
        changeUser = new TextField("",controller.getSkin());
        changePass = new TextField("",controller.getSkin());
        submit = new TextButton("Submit Changes",controller.getSkin());
        error = new Label("",controller.getSkin());
        back = new TextButton("Back", controller.getSkin());
        addActor(changeUser,230,430,250,50);
        addActor(changePass,230,370,250,50);
        addActor(submit,230,290,250,50);
        error.setPosition(290,70);
        stage.addActor(error);
        addActor(back,230,230,250,50);
        changeUser.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                changeUsername();
            }
        });
        changePass.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                changePassword();
            }
        });
        submit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                controller.setScreen(new MainMenu());
            }
        });
//        int type = DataBase.getCurrentPlayer().getProfImage();
//        ImageButton imageButton = new ImageButtons(ImageButtons.getPic(type),ImageButtons.getPic(type),type);
//        imageButton.setPosition(280,500);
//        imageButton.setSize(150,150);

//        imageButton.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                super.clicked(event, x, y);
//                //controller.setScreen(new ImageMenu(controller,"Profile"));
//            }
//        });
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                controller.setScreen(new MainMenu());
            }
        });
    }
    private void addActor(Actor actor,int x,int y,int width,int height){
        actor.setPosition(x,y);
        actor.setSize(width,height);
        stage.addActor(actor);
    }
    private void changeUsername(){
        stage.clear();
        username = new TextField("",Main.getController().getSkin());
        username.setMessageText("New Username");
        username.setPosition(230, 400);
        username.setSize(250,50);
        changeUserBut = new TextButton("Change", Main.getController().getSkin());
        changeUserBut.setPosition(230,250);
        changeUserBut.setSize(150,60);
        goProfile(changeUserBut);
        back = new TextButton("Back", Main.getController().getSkin());
        back.setPosition(400,250);
        back.setSize(150,60);
        goProfile(back);
        error = new Label("",Main.getController().getSkin());
        error.setPosition(180,600);
        stage.addActor(username);
        stage.addActor(changeUserBut);
        stage.addActor(back);
        stage.addActor(error);
    }
    private void changePassword(){
        stage.clear();
        Window window = new Window("Change Password",controller.getSkin());
        password = new TextField("",controller.getSkin());
        newCheck = new TextField("",controller.getSkin());
        showPass = new CheckBox("Show Password", controller.getSkin());
        showNew = new CheckBox("Show New Password", Main.getController().getSkin());
        changePassBut = new TextButton("Change", Main.getController().getSkin());
        back = new TextButton("Back", Main.getController().getSkin());
        error = new Label("",Main.getController().getSkin());
        password.setMessageText("Old Password");
        newCheck.setMessageText("New Password");
        window.add(password).pad(10, 0, 10, 0).row();
        password.setPasswordMode(true);
        password.setPasswordCharacter('*');
        window.add(showPass).pad(10,0,10,0).row();
        window.add(newCheck).pad(10, 0, 10, 0).row();
        newCheck.setPasswordMode(true);
        newCheck.setPasswordCharacter('*');
        window.add(showNew).pad(10,0,10,0).row();
        passListener2(showPass);
        passListener2(showNew);
        window.add(changePassBut).pad(10, 0, 10, 0).row();
        goProfile(changePassBut);
        window.add(back).pad(10, 0, 10, 0).row();
        goProfile(back);
        window.add(error).pad(10, 0, 10, 0).row();
        window.setBounds(700,200,500, 500);
        stage.addActor(window);
    }
    private void butListener(Button button){
//        final String type = button.toString();
//        button.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                super.clicked(event, x, y);
//                switch (type){
//                    case "logout": controller.setScreen(new LoginMenu(controller));break;
//                    case "delete": controller.setScreen(new RegisterMenu(controller));break;
//                }
//            }
//        });
    }
    private void goProfile(final Button button){
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(button.equals(changePassBut)){
                    changePass(password.getText(), newCheck.getText());
                } else if (button.equals(changeUserBut)) {
                    changeUser(username.getText());
                } else{
                    stage.clear();
                    profileMenu();
                }
            }
        });
    }

    private void changeUser(String username){
        String message = ProfileMenuController.changeUsername(username);
        if(!message.equals(ProfileMenuMessages.SUCCEED.toString())) {
            error.setText(message);
            return;
        }
        stage.clear();
        profileMenu();
    }
    private void changePass(String oldPassword, String newPassword){
        String message = ProfileMenuController.changePassword(oldPassword, newPassword);
        if(!message.equals(ProfileMenuMessages.SUCCEED.toString())) {
            error.setText(message);
            return;
        }
        stage.clear();
        profileMenu();
    }
    private void passListener2(final CheckBox box) {
        box.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (box.isChecked()) {
                    if (box.equals(showPass)) {
                        password.setPasswordMode(false);
                    } else {
                        newCheck.setPasswordMode(false);
                    }
                } else if (!box.isChecked()) {
                    if (box.equals(showPass)) {
                        password.setPasswordMode(true);
                        password.setPasswordCharacter('*');
                    } else {
                        newCheck.setPasswordMode(true);
                        newCheck.setPasswordCharacter('*');
                    }
                }
            }
        });
    }
}
