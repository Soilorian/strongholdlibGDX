package org.example.view.menus;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.example.Main;
import org.example.control.comparetors.PlayerComparator;
import org.example.control.enums.ProfileMenuMessages;
import org.example.control.menucontrollers.ProfileMenuController;
import org.example.model.Captcha;
import org.example.model.DataBase;
import org.example.model.Player;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Collections;

public class ProfileMenu extends Menu {
    private TextField oldPass, newPass,news,captcha;
    private TextField changeUser,changeNick,changeEmail,changeSlogan;
    private CheckBox showPass;
    private Label error;
    private Button backPass,submit;
    private ImageButton editBut1,editBut2,editBut3,editBut4,editBut5,board,captchaButton;
    private Captcha passCaptcha;
    private Image captchaImage;
    private List listNum,listUser,listScore;
    private Label goldNum,goldUser,goldScore;
    private Label silverNum,silverUser,silverScore;
    private Label cuNum,cuUser,cuScore;
    private final Texture editPic = new Texture("pictures/edit.png");
    private final Texture trashPic = new Texture("pictures/trash.png");
    private final Texture backPic = new Texture("pictures/back.jpg");
    private final Texture boardPic = new Texture("pictures/leaderBoard.png");


    public ProfileMenu() {
        super();
        profileMenu();
    }
    public void run(String command) throws IOException, UnsupportedAudioFileException, LineUnavailableException,
            CoordinatesOutOfMap, NotInStoragesException {
    }
    @Override
    public void create() {
        Gdx.input.setInputProcessor(behindStage);
    }


    private void changeUsername() {
        if(!error.getText().equalsIgnoreCase(ProfileMenuMessages.SUCCEED.toString())) {
            return;
        }
        String message = ProfileMenuController.changeUsername(news.getText());
        if (!(message.equals(ProfileMenuMessages.SUCCEED.toString()))){
            error.setText(message);
            return;
        }
        behindStage.clear();
        profileMenu();
    }

    private void changeNickname() {
        if(!error.getText().equalsIgnoreCase(ProfileMenuMessages.SUCCEED.toString())) {
            return;
        }
        String message = ProfileMenuController.changeNickname(news.getText());
        if (!(message.equals(ProfileMenuMessages.SUCCEED.toString()))){
            error.setText(message);
            return;
        }
        behindStage.clear();
        profileMenu();
    }

    private void changeEmail() {
        if(!error.getText().equalsIgnoreCase(ProfileMenuMessages.SUCCEED.toString())) {
            return;
        }
        String message = ProfileMenuController.changeEmail(news.getText());
        if (!(message.equals(ProfileMenuMessages.SUCCEED.toString()))){
            error.setText(message);
            return;
        }
        behindStage.clear();
        profileMenu();
    }

    private void changePassword() {
        if(!error.getText().equalsIgnoreCase(ProfileMenuMessages.SUCCEED.toString())) {
            return;
        }
        String message = ProfileMenuController.changePassword(oldPass.getText(),newPass.getText());
        if (!(message.equals(ProfileMenuMessages.SUCCEED.toString()))){
            error.setText(message);
            return;
        }
        behindStage.clear();
        profileMenu();
    }

    private void changeSlogan() {
        if(!error.getText().equalsIgnoreCase(ProfileMenuMessages.SUCCEED.toString())) {
            return;
        }
        String message = ProfileMenuController.changeSlogan(news.getText());
        if (!(message.equals(ProfileMenuMessages.SUCCEED.toString()))){
            error.setText(message);
            return;
        }
        behindStage.clear();
        profileMenu();
    }

    private void profileMenu(){
        changeUser = new TextField("", controller.getSkin());
        //changeUser.setMessageText(DataBase.getCurrentPlayer().getUsername());
        changeNick = new TextField("", controller.getSkin());
        TextField changePass = new TextField("", controller.getSkin());
        changeEmail = new TextField("", controller.getSkin());
        changeSlogan = new TextField("", controller.getSkin());
        submit = new TextButton("Submit",controller.getSkin());
        error = new Label("",controller.getSkin());
        Drawable backBut = new TextureRegionDrawable(new TextureRegion(backPic));
        ImageButton back = new ImageButton(backBut);
        Drawable but = new TextureRegionDrawable(new TextureRegion(boardPic));
        board = new ImageButton(but);
        addActor(changeUser,250,610,250,50);
        addActor(changeNick,250,550,250,50);
        addActor(changePass,250,490,250,50);
        addActor(changeEmail,250,430,250,50);
        addActor(changeSlogan,250,370,250,50);
        addActor(submit,310,290,130,50);
        error.setPosition(250,170);
        behindStage.addActor(error);
        addActor(back,255,285,45,70);
        addActor(board,450,295,45,45);
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
        imageListener(back);
        imageListener(board);
    }

    private void changes(String type){
        behindStage.clear();
        Window window = new Window("Change " + type,controller.getSkin());
        window.setBounds(230,230,500, 500);
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
        news.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!news.getText().equalsIgnoreCase("")){
                    switch (type) {
                        case "Username":
                            error.setText(ProfileMenuController.userErrors(news.getText()));
                            break;
                        case "Nickname":
                            error.setText(ProfileMenuController.nickErrors(news.getText()));
                            break;
                        case "Email":
                            error.setText(ProfileMenuController.emailErrors(news.getText()));
                            break;
                        case "Slogan":
                            error.setText(ProfileMenuController.sloganErrors(news.getText()));
                            break;
                    }
                    if(error.getText().equalsIgnoreCase(ProfileMenuMessages.SUCCEED.toString())){
                        error.setText("");
                    }
                }
            }
        });
        behindStage.addActor(window);
    }

    private void changePass(){
        behindStage.clear();
        Window window = new Window("Change Password",controller.getSkin());
        window.setBounds(230,200,500, 700);
        captcha = new TextField("", controller.getSkin());
        oldPass = new TextField("",controller.getSkin());
        newPass = new TextField("",controller.getSkin());
        showPass = new CheckBox("Show Password", controller.getSkin());
        CheckBox showNew = new CheckBox("Show New Password", Main.getController().getSkin());
        Button changePassBut = new TextButton("Change", Main.getController().getSkin());
        backPass = new TextButton("Back", controller.getSkin());
        error = new Label("",Main.getController().getSkin());
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(controller.getCaptchaPath()));
        captchaButton = new ImageButton(drawable);
        passCaptcha = new Captcha();
        Texture captchaTexture = new Texture("saved.png");
        captchaImage = new Image(captchaTexture);
        oldPass.setMessageText("Old Password");
        newPass.setMessageText("New Password");
        newPass.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                error.setText(ProfileMenuController.passErrors(oldPass.getText(),newPass.getText()));
                if(error.getText().equalsIgnoreCase(ProfileMenuMessages.SUCCEED.toString())){
                    error.setText("");
                }
            }
        });
        Table table = new Table(controller.getSkin());
        table.setBounds(window.getX(), window.getY(), 450,450);
        table.add(oldPass).center().colspan(5).padBottom(15).row();
        oldPass.setPasswordMode(true);
        oldPass.setPasswordCharacter('*');
        table.add(showPass).center().colspan(5).padBottom(15).row();
        table.add(newPass).center().colspan(5).padBottom(15).row();
        newPass.setPasswordMode(true);
        newPass.setPasswordCharacter('*');
        table.add(showNew).center().colspan(5).padBottom(15).row();
        passListener(showPass);
        passListener(showNew);
        table.add(captchaButton).colspan(2);
        table.add(captchaImage).colspan(5);
        captchaButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                passCaptcha.generateCaptcha();
                captchaImage.remove();
                captchaImage = new Image(new Texture("saved.png"));
                table.addActorAfter(captchaButton,captchaImage);
               captchaImage.setPosition(100,4);
            }
        });
        table.row();
        window.add(table).colspan(5).row();
        window.add(captcha).center().colspan(5).row();
        window.add(changePassBut).center().colspan(5).padTop(15).row();
        butListener(changePassBut,"password");
        backListener(backPass);
        window.add(backPass).center().colspan(5).padTop(15).row();
        window.add(error).center().colspan(5).padTop(15).row();
        behindStage.addActor(window);
    }

    private void leaderBoard(){
        behindStage.clear();
        newLeader();
        Collections.sort(DataBase.getPlayers(),new PlayerComparator());
        //int rank = DataBase.getPlayers().indexOf(DataBase.getCurrentPlayer());
        String[] num = new String[DataBase.getPlayers().size()-3];
        String[] user = new String[DataBase.getPlayers().size()-3];
        String[] score = new String[DataBase.getPlayers().size()-3];
        backPass = new TextButton("Back", controller.getSkin());
        Player player;
        int j=0;
        for(int i=0;i<DataBase.getPlayers().size();i++){
            player = DataBase.getPlayers().get(i);
            if(i<3){
                if (i == 0) {
                    goldNum.setText(Integer.valueOf(i+1).toString());
                    goldUser.setText(player.getUsername());
                    goldScore.setText(player.toStringScore());
                } else if (i==1) {
                    silverNum.setText(Integer.valueOf(i + 1).toString());
                    silverUser.setText(player.getUsername());
                    silverScore.setText(player.toStringScore());
                }else{
                    cuNum.setText(Integer.valueOf(i + 1).toString());
                    cuUser.setText(player.getUsername());
                    cuScore.setText(player.toStringScore());
                }
            }else {
                num[j]=Integer.valueOf(i+1).toString();
                user[j] = player.getUsername();
                score[j] = player.toStringScore();
                j++;
            }
        }

        listNum.setItems(num);
        listUser.setItems(user);
        listScore.setItems(score);
        listNum.setSelected(false);
        Table table = new Table(controller.getSkin());
        tableAdd(table);
        ScrollPane scrollPane = new ScrollPane(table,controller.getSkin());
        scrollPane.setSize(500,500);
        scrollPane.setPosition(700,300);
        scrollPane.setForceScroll(false,true);
        addActor(backPass,850,150,200,60);
        backListener(backPass);
        behindStage.addActor(scrollPane);
    }

    private void addActor(Actor actor,int x,int y,int width,int height){
        actor.setPosition(x,y);
        actor.setSize(width,height);
        behindStage.addActor(actor);
    }

    private void passListener(final CheckBox box) {
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
                        if(passCaptcha.isFilledCaptchaValid(captcha.getText())){
                            changePassword();
                        }else error.setText("Invalid Captcha");
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
                    behindStage.clear();
                    profileMenu();
                }else if (button.equals(submit)) {
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
                    changes("Username");
                }else if (imageButton.equals(editBut2)) {
                    Window window = new Window("Change Nickname",controller.getSkin());
                    changes("Nickname");
                }else if (imageButton.equals(editBut3)) {
                    changePass();
                }else if (imageButton.equals(editBut4)) {
                    Window window = new Window("Change Email",controller.getSkin());
                    changes("Email");
                }else if (imageButton.equals(editBut5)) {
                    Window window = new Window("Change Slogan",controller.getSkin());
                    changes("Slogan");
                } else if (imageButton.equals(board)) {
                    leaderBoard();
                } else{
                    controller.setScreen(new MainMenu());
                }
            }
        });
    }
    private void tableAdd(Table table){
        table.add(goldNum).left().pad(10,0,-6,0);
        table.add(goldUser).center().pad(10,0,-15,70);
        table.add(goldScore).right().pad(10,10,-6,0).row();
        table.add(silverNum).left().pad(0,0,-6,0);
        table.add(silverUser).center().pad(10,0,-15,70);
        table.add(silverScore).right().pad(10,10,-6,0).row();
        table.add(cuNum).left().pad(10,0,-2,0);
        table.add(cuUser).center().pad(10,0,-8,70);
        table.add(cuScore).right().pad(20,10,-2,0).row();
        table.add(listNum).left().pad(10,0,30,70);
        table.add(listUser).center().pad(10,10,30,70);
        table.add(listScore).right().pad(10,10,10,0);
    }
    private void newLeader(){
        goldNum = new Label("",controller.getSkin(),"gold");
        goldUser = new Label("",controller.getSkin(),"gold");
        goldScore = new Label("",controller.getSkin(),"gold");
        silverNum = new Label("",controller.getSkin(),"silver");
        silverUser = new Label("",controller.getSkin(),"silver");
        silverScore = new Label("",controller.getSkin(),"silver");
        cuNum = new Label("",controller.getSkin(),"cu");
        cuUser = new Label("",controller.getSkin(),"cu");
        cuScore = new Label("",controller.getSkin(),"cu");
        listNum = new List(controller.getSkin(),"prof");
        listUser = new List(controller.getSkin(),"prof");
        listScore = new List(controller.getSkin(),"prof");
    }
}
