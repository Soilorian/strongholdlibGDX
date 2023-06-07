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
    private Label error = new Label("",controller.getSkin());
    //changes
    private TextField news;
    private ImageButton delBut;
    //changePass
    private Window window;
    private Table table;
    private TextField oldPass, newPass,captcha;
    private CheckBox showPass,showNew;
    private Button changePassBut,backBut;
    private Captcha passCaptcha;
    private Image captchaImage;
    private ImageButton captchaButton;
    //profileMenu
    private TextField changeUser,changeNick,changePass,changeEmail,changeSlogan;
    private Button submit;
    private ImageButton editBut1,editBut2,editBut3,editBut4,editBut5,board,back;
    //leaderBoard
    private String[] num,user,score;
    private Table leaderBoard;
    private ScrollPane scrollPane;
    private List listNum,listUser,listScore;
    //Pics
    private final Texture groundPic = new Texture("pictures/ProfBackground.png");
    private final Texture editPic = new Texture("pictures/edit.png");
    private final Texture trashPic = new Texture("pictures/trash.png");
    private final Texture backPic = new Texture("pictures/back.jpg");
    private final Texture boardPic = new Texture("pictures/leaderBoard.png");


    public ProfileMenu() {
        super();
        profNew();
    }
    public void run(String command) throws IOException, UnsupportedAudioFileException, LineUnavailableException,
            CoordinatesOutOfMap, NotInStoragesException {
    }
    @Override
    public void create() {
        Gdx.input.setInputProcessor(behindStage);
        profileMenu();
        newChanges();
        newChangePass();
        newLeader();
    }
    @Override
    public void render(float delta){
        batch.begin();
        batch.draw(groundPic,0,0);
        batch.end();
        behindStage.act();
        behindStage.draw();
    }

    private void profNew(){
        changeUser = new TextField("", controller.getSkin());
        changeNick = new TextField("", controller.getSkin());
        changePass = new TextField("", controller.getSkin());
        changeEmail = new TextField("", controller.getSkin());
        changeSlogan = new TextField("", controller.getSkin());
        submit = new TextButton("Submit",controller.getSkin());
        Drawable backBut = new TextureRegionDrawable(new TextureRegion(backPic));
        Drawable but = new TextureRegionDrawable(new TextureRegion(boardPic));
        Drawable edit = new TextureRegionDrawable(new TextureRegion(editPic));
        back = new ImageButton(backBut);
        board = new ImageButton(but);
        editBut1 = new ImageButton(edit);
        editBut2 = new ImageButton(edit);
        editBut3 = new ImageButton(edit);
        editBut4 = new ImageButton(edit);
        editBut5 = new ImageButton(edit);
    }
    private void newChanges(){
        news = new TextField("",controller.getSkin());
        Drawable delete = new TextureRegionDrawable(new TextureRegion(trashPic));
        delBut = new ImageButton(delete);
    }
    private void newChangePass(){
        window = new Window("Change Password",controller.getSkin());
        captcha = new TextField("", controller.getSkin());
        oldPass = new TextField("",controller.getSkin());
        newPass = new TextField("",controller.getSkin());
        showPass = new CheckBox("Show Password", controller.getSkin());
        showNew = new CheckBox("Show New Password", Main.getController().getSkin());
        changePassBut = new TextButton("Change", Main.getController().getSkin());
        backBut = new TextButton("Back", controller.getSkin());
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(controller.getCaptchaPath()));
        captchaButton = new ImageButton(drawable);
        passCaptcha = new Captcha();
        Texture captchaTexture = new Texture("saved.png");
        captchaImage = new Image(captchaTexture);
        table = new Table(controller.getSkin());
    }
    private void newLeader(){
        num = new String[DataBase.getPlayers().size()];
        user = new String[DataBase.getPlayers().size()];
        score = new String[DataBase.getPlayers().size()];
        backBut = new TextButton("Back", controller.getSkin());
        listNum = new List(controller.getSkin(),"prof");
        listUser = new List(controller.getSkin(),"prof");
        listScore = new List(controller.getSkin(),"prof");
        leaderBoard = new Table(controller.getSkin());
        scrollPane = new ScrollPane(leaderBoard,controller.getSkin());
    }
    private void profileMenu(){
        error = new Label("",controller.getSkin());
        addActor(changeUser,350,590,250,50);
        addActor(changeNick,350,530,250,50);
        addActor(changePass,350,470,250,50);
        addActor(changeEmail,350,410,250,50);
        addActor(changeSlogan,350,350,250,50);
        addActor(submit,410,285,130,50);
        error.setPosition(250,170);
        behindStage.addActor(error);
        addActor(back,355,240,45,140);
        addActor(board,550,285,45,45);
        addActor(editBut1,560,600,30,30);
        addActor(editBut2,560,540,30,30);
        addActor(editBut3,560,480,30,30);
        addActor(editBut4,560,420,30,30);
        addActor(editBut5,560,360,30,30);
        changeUser.setText(DataBase.getCurrentPlayer().getUsername());
        changeUser.setDisabled(true);
        changeNick.setText(DataBase.getCurrentPlayer().getNickname());
        changeNick.setDisabled(true);
        changePass.setText("**********");
        changePass.setDisabled(true);
        changeEmail.setText(DataBase.getCurrentPlayer().getEmail());
        changeEmail.setDisabled(true);
        changeSlogan.setText(DataBase.getCurrentPlayer().getSlogan());
        changeSlogan.setDisabled(true);
        backListener(submit);
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
        Window changeWindow = new Window("Changes " + type, controller.getSkin());
        Button changeBut = new TextButton("Changes " + type, Main.getController().getSkin());
        error = new Label("",controller.getSkin());
        changeWindow.setBounds(230,230,500, 500);
        news.setMessageText("New " + type);
        changeWindow.add(news).pad(10, 0, 10, 0);
        if(type.equals("Slogan")){
            changeWindow.add(delBut).pad(10,0,10,0);
            delBut.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    ProfileMenuController.removeSlogan();
                }
            });
        }
        changeWindow.row();
        changeWindow.add(changeBut).pad(10, 0, 10, 0).row();
        butListener(changeBut,type);
        changeWindow.add(backBut).pad(10, 0, 10, 0).row();
        backListener(backBut);
        changeWindow.add(error).pad(10, 0, 10, 0).row();
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
        behindStage.addActor(changeWindow);
    }

    private void changePass(){
        behindStage.clear();
        error = new Label("",controller.getSkin());
        window.setBounds(230,200,500, 700);
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
        backListener(backBut);
        window.add(backBut).center().colspan(5).padTop(15).row();
        window.add(error).center().colspan(5).padTop(15).row();
        behindStage.addActor(window);
    }

    private void leaderBoard(){
        behindStage.clear();
        Collections.sort(DataBase.getPlayers(),new PlayerComparator());
        int rank = DataBase.getPlayers().indexOf(DataBase.getCurrentPlayer());
        Player player;
        for(int i=0;i<DataBase.getPlayers().size();i++){
            player = DataBase.getPlayers().get(i);
            num[i] = Integer.valueOf(i + 1).toString();
            user[i] = player.getUsername();
            score[i] = player.toStringScore();
        }
        listNum.setItems(num);
        listUser.setItems(user);
        listScore.setItems(score);
        listNum.setSelected(num[rank]);
        listUser.setSelected(user[rank]);
        listScore.setSelected(score[rank]);
        leaderBoard.add(listNum).left().pad(10,0,30,70);
        leaderBoard.add(listUser).center().pad(10,10,30,70);
        leaderBoard.add(listScore).right().pad(10,10,10,0);
        scrollPane.setSize(500,500);
        scrollPane.setPosition(700,300);
        scrollPane.setForceScroll(false,true);
        addActor(backBut,850,150,200,60);
        backListener(backBut);
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
                if (button.equals(backBut)) {
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
                    changes("Username");
                }else if (imageButton.equals(editBut2)) {
                    changes("Nickname");
                }else if (imageButton.equals(editBut3)) {
                    changePass();
                }else if (imageButton.equals(editBut4)) {
                    changes("Email");
                }else if (imageButton.equals(editBut5)) {
                    changes("Slogan");
                } else if (imageButton.equals(board)) {
                    leaderBoard();
                } else{
                    controller.setScreen(new MainMenu());
                }
            }
        });
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
}
