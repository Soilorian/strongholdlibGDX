package org.example.view.menus;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.example.Main;
import org.example.control.enums.ProfileMenuMessages;
import org.example.control.menucontrollers.ProfileMenuController;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class ProfileMenu extends Menu {
    private TextField oldPass, newPass,news;
    private CheckBox showPass;
    private Label error;
    private Button back,backPass,submit;
    private ImageButton editBut1,editBut2,editBut3,editBut4,editBut5;
    private final Texture editPic = new Texture("pictures/edit.png");
    private final Texture trashPic = new Texture("pictures/trash.png");


    public ProfileMenu() {
        super();
        profileMenu();
    }
    public void run(String command) throws IOException, UnsupportedAudioFileException, LineUnavailableException,
            CoordinatesOutOfMap, NotInStoragesException {
    }
    @Override
    public void create() {
        Gdx.input.setInputProcessor(stage);
    }


    private void changeUsername() {
        String message = ProfileMenuController.changeUsername(news.getText());
        if(!message.equals(ProfileMenuMessages.SUCCEED.toString())) {
            error.setText(message);
            return;
        }
        stage.clear();
        profileMenu();
    }

    private void changeNickname() {
        String message = ProfileMenuController.changeNickname(news.getText());
        if(!message.equals(ProfileMenuMessages.SUCCEED.toString())) {
            error.setText(message);
            return;
        }
        stage.clear();
        profileMenu();
    }

    private void changeEmail() {
        String message = ProfileMenuController.changeEmail(news.getText());
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
        String message = ProfileMenuController.changeSlogan(news.getText());
        if(!message.equals(ProfileMenuMessages.SUCCEED.toString())) {
            error.setText(message);
            return;
        }
        stage.clear();
        profileMenu();
    }

    private void profileMenu(){
        TextField changeUser = new TextField("", controller.getSkin());
        TextField changeNick = new TextField("", controller.getSkin());
        TextField changePass = new TextField("", controller.getSkin());
        TextField changeEmail = new TextField("", controller.getSkin());
        TextField changeSlogan = new TextField("", controller.getSkin());
        submit = new TextButton("Submit Changes",controller.getSkin());
        error = new Label("",controller.getSkin());
        back = new TextButton("Back", controller.getSkin());
        addActor(changeUser,250,610,250,50);
        addActor(changeNick,250,550,250,50);
        addActor(changePass,250,490,250,50);
        addActor(changeEmail,250,430,250,50);
        addActor(changeSlogan,250,370,250,50);
        addActor(submit,250,290,250,50);
        error.setPosition(250,170);
        stage.addActor(error);
        addActor(back,250,230,250,50);
        backListener(submit);
        Drawable edit = new TextureRegionDrawable(new TextureRegion(editPic));
        editBut1 = new ImageButton(edit);
        addActor(editBut1,460,620,30,30);
        editBut2 = new ImageButton(edit);
        addActor(editBut2,460,560,30,30);
        editBut3 = new ImageButton(edit);
        addActor(editBut3,460,500,30,30);
        editBut4 = new ImageButton(edit);
        addActor(editBut4,460,440,30,30);
        editBut5 = new ImageButton(edit);
        addActor(editBut5,460,380,30,30);
        imageListener(editBut1);
        imageListener(editBut2);
        imageListener(editBut3);
        imageListener(editBut4);
        imageListener(editBut5);
        backListener(back);
    }
    private void changePass(){
        stage.clear();
        Window window = new Window("Change Password",controller.getSkin());
        oldPass = new TextField("",controller.getSkin());
        newPass = new TextField("",controller.getSkin());
        showPass = new CheckBox("Show Password", controller.getSkin());
        CheckBox showNew = new CheckBox("Show New Password", Main.getController().getSkin());
        Button changePassBut = new TextButton("Change", Main.getController().getSkin());
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
        butListener(changePassBut,"password");
        backListener(backPass);
        window.add(backPass).pad(10, 0, 10, 0).row();
        window.add(error).pad(10, 0, 10, 0).row();
        window.setBounds(230,230,500, 500);
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
    private void butListener(Button button,String type){
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                switch (type) {
                    case "Username":
                        changeUsername();
                        break;
                    case "Nickname":
                        changeNickname();
                        break;
                    case "password":
                        changePassword();
                        break;
                    case "Email":
                        changeEmail();
                        break;
                    case "Slogan":
                        changeSlogan();
                        break;
                }
            }
        });
    }
    private void backListener(Button button){
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (button.equals(backPass)) {
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
    private void imageListener(ImageButton imageButton){
        imageButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(imageButton.equals(editBut1)){
                    Window window = new Window("Change Username",controller.getSkin());
                    changes(window,"Username");
                }else if (imageButton.equals(editBut2)) {
                    Window window = new Window("Change Nickname",controller.getSkin());
                    changes(window,"Nickname");
                }else if (imageButton.equals(editBut3)) {
                    changePass();
                }else if (imageButton.equals(editBut4)) {
                    Window window = new Window("Change Email",controller.getSkin());
                    changes(window,"Email");
                }else if (imageButton.equals(editBut5)) {
                    Window window = new Window("Change Slogan",controller.getSkin());
                    changes(window,"Slogan");
                }
            }
        });
    }
    private void changes(Window window,String type){
        stage.clear();
        news = new TextField("",controller.getSkin());
        news.setMessageText("New " + type);
        Button changeBut = new TextButton("Change " + type, Main.getController().getSkin());
        backPass = new TextButton("Back", Main.getController().getSkin());
        error = new Label("",Main.getController().getSkin());
        window.add(news).pad(10, 0, 10, 0);
        if(type.equals("Slogan")){
            Drawable delete = new TextureRegionDrawable(new TextureRegion(trashPic));
            ImageButton delBut = new ImageButton(delete);
            window.add(delBut).pad(10,0,10,0);
            delBut.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    ProfileMenuController.removeSlogan();
                }
            });
        }
        window.row();
        window.add(changeBut).pad(10, 0, 10, 0).row();
        butListener(changeBut,type);
        window.add(backPass).pad(10, 0, 10, 0).row();
        backListener(backPass);
        window.add(error).pad(10, 0, 10, 0).row();
        window.setBounds(230,230,500, 500);
        stage.addActor(window);
    }
}
