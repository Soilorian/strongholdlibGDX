package org.example.view.menus;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.example.Main;
import org.example.control.enums.ProfileMenuMessages;
import org.example.control.menucontrollers.ProfileMenuController;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class ProfileMenu extends Menu {
    private TextField changeUser,changePass,changeEmail,changeSlogan,changeNick;
    private TextField oldPass, newPass;
    private CheckBox showPass, showNew;
    private Label error;
    private Button changePassBut,back,backPass,submit;


    public ProfileMenu() {
        super();
        profileMenu();
    }
    @Override
    public void run(String command) throws IOException, UnsupportedAudioFileException, LineUnavailableException,
            CoordinatesOutOfMap, NotInStoragesException {
    }

    @Override
    public void create() {
        Gdx.input.setInputProcessor(stage);
    }


    private void changeUsername() {
        String message = ProfileMenuController.changeUsername(changeUser.getText());
        if(!message.equals(ProfileMenuMessages.SUCCEED.toString())) {
            error.setText(message);
            return;
        }
        stage.clear();
        profileMenu();
    }

    private void changeNickname() {
        String message = ProfileMenuController.changeNickname(changeNick.getText());
        if(!message.equals(ProfileMenuMessages.SUCCEED.toString())) {
            error.setText(message);
            return;
        }
        stage.clear();
        profileMenu();
    }

    private void changeEmail() {
        String message = ProfileMenuController.changeEmail(changeEmail.getText());
        if(!message.equals(ProfileMenuMessages.SUCCEED.toString())) {
            error.setText(message);
            return;
        }
        stage.clear();
        profileMenu();
    }

    private void changePassword() {
        String message = ProfileMenuController.changePassword(oldPass.getText(), newPass.getText());
        if(!message.equals(ProfileMenuMessages.SUCCEED.toString())) {
            error.setText(message);
            return;
        }
        stage.clear();
        profileMenu();
    }

    private void changeSlogan() {
        String message = ProfileMenuController.changeSlogan(changeSlogan.getText());
        if(!message.equals(ProfileMenuMessages.SUCCEED.toString())) {
            error.setText(message);
            return;
        }
        stage.clear();
        profileMenu();
    }

    private void profileMenu(){
        changeUser = new TextField("",controller.getSkin());
        changeNick = new TextField("",controller.getSkin());
        changePass = new TextField("",controller.getSkin());
        changeEmail = new TextField("",controller.getSkin());
        changeSlogan = new TextField("",controller.getSkin());
        submit = new TextButton("Submit Changes",controller.getSkin());
        error = new Label("",controller.getSkin());
        back = new TextButton("Back", controller.getSkin());
        addActor(changeUser,230,550,250,50);
        addActor(changeNick,230,610,250,50);
        addActor(changePass,230,490,250,50);
        addActor(changeEmail,230,430,250,50);
        addActor(changeSlogan,230,370,250,50);
        addActor(submit,230,290,250,50);
        error.setPosition(230,170);
        stage.addActor(error);
        addActor(back,230,230,250,50);
        textListener(changeUser);
        textListener(changeNick);
        textListener(changePass);
        textListener(changeEmail);
        textListener(changeSlogan);
        butListener(submit);
//        int type = DataBase.getCurrentPlayer().getProfImage();
//        ImageButton imageButton = new ImageButtons(ImageButtons.getPic(type),ImageButtons.getPic(type),type);
//        imageButton.setPosition(280,500);
//        imageButton.setSize(150,150);

//        imageButton.textListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                super.clicked(event, x, y);
//                //controller.setScreen(new ImageMenu(controller,"Profile"));
//            }
//        });
        butListener(back);
    }
    private void changePass(){
        stage.clear();
        Window window = new Window("Change Password",controller.getSkin());
        oldPass = new TextField("",controller.getSkin());
        newPass = new TextField("",controller.getSkin());
        showPass = new CheckBox("Show Password", controller.getSkin());
        showNew = new CheckBox("Show New Password", Main.getController().getSkin());
        changePassBut = new TextButton("Change", Main.getController().getSkin());
        backPass = new TextButton("Back", Main.getController().getSkin());
        error = new Label("",Main.getController().getSkin());
        oldPass.setMessageText("Old Password");
        newPass.setMessageText("New Password");
        window.add(oldPass).pad(10, 0, 10, 0).row();
        oldPass.setPasswordMode(true);
        oldPass.setPasswordCharacter('*');
        window.add(showPass).pad(10,0,10,0).row();
        window.add(newPass).pad(10, 0, 10, 0).row();
        newPass.setPasswordMode(true);
        newPass.setPasswordCharacter('*');
        window.add(showNew).pad(10,0,10,0).row();
        passListener2(showPass);
        passListener2(showNew);
        window.add(changePassBut).pad(10, 0, 10, 0).row();
        butListener(changePassBut);
        butListener(backPass);
        window.add(backPass).pad(10, 0, 10, 0).row();
        window.add(error).pad(10, 0, 10, 0).row();
        window.setBounds(700,200,500, 500);
        stage.addActor(window);
    }

    private void passListener2(final CheckBox box) {
        box.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (box.isChecked()) {
                    if (box.equals(showPass)) {
                        oldPass.setPasswordMode(false);
                    } else {
                        newPass.setPasswordMode(false);
                    }
                } else if (!box.isChecked()) {
                    if (box.equals(showPass)) {
                        oldPass.setPasswordMode(true);
                        oldPass.setPasswordCharacter('*');
                    } else {
                        newPass.setPasswordMode(true);
                        newPass.setPasswordCharacter('*');
                    }
                }
            }
        });
    }
    private void addActor(Actor actor,int x,int y,int width,int height){
        actor.setPosition(x,y);
        actor.setSize(width,height);
        stage.addActor(actor);
    }
    private void textListener(TextField textField){
        textField.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (textField.equals(changeUser)) {
                    changeUsername();
                } else if (textField.equals(changeNick)) {
                    changeNickname();
                } else if (textField.equals(changePass)) {
                    changePass();
                } else if (textField.equals(changeEmail)) {
                    changeEmail();
                } else if (textField.equals(changeSlogan)) {
                    changeSlogan();
                }
            }
        });
    }
    private void butListener(Button button){
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(button.equals(changePassBut)){
                    changePassword();
                } else if (button.equals(backPass)) {
                    stage.clear();
                    profileMenu();
                } else if (button.equals(back)) {
                    controller.setScreen(new MainMenu());
                } else if (button.equals(submit)) {
                    controller.setScreen(new MainMenu());
                }
            }
        });
    }
}
