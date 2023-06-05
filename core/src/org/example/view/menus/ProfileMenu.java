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
import jdk.javadoc.internal.doclets.formats.html.markup.Text;
import org.example.Main;
import org.example.control.comparetors.PlayerComparator;
import org.example.control.enums.ProfileMenuMessages;
import org.example.control.menucontrollers.ProfileMenuController;
import org.example.model.DataBase;
import org.example.model.Player;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ProfileMenu extends Menu {
    private TextField oldPass, newPass,news;
    private CheckBox showPass;
    private Label error;
    private Button backPass,submit;
    private ImageButton editBut1,editBut2,editBut3,editBut4,editBut5,board;
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
        //changeUser.setMessageText(DataBase.getCurrentPlayer().getUsername());
        TextField changeNick = new TextField("", controller.getSkin());
        TextField changePass = new TextField("", controller.getSkin());
        TextField changeEmail = new TextField("", controller.getSkin());
        TextField changeSlogan = new TextField("", controller.getSkin());
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
        stage.addActor(error);
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
        passListener(showPass);
        passListener(showNew);
        window.add(changePassBut).pad(10, 0, 10, 0).row();
        butListener(changePassBut,"password");
        backListener(backPass);
        window.add(backPass).pad(10, 0, 10, 0).row();
        window.add(error).pad(10, 0, 10, 0).row();
        window.setBounds(230,230,500, 500);
        stage.addActor(window);
    }

    private void leaderBoard(){
        stage.clear();
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
        Collections.sort(DataBase.getPlayers(),new PlayerComparator());
        //int rank = DataBase.getPlayers().indexOf(DataBase.getCurrentPlayer()) + 1;
        String[] num = new String[DataBase.getPlayers().size()-3];
        String[] user = new String[DataBase.getPlayers().size()-3];
        String[] score = new String[DataBase.getPlayers().size()-3];
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
        Table table = new Table(controller.getSkin());
        tableAdd(table);
        ScrollPane scrollPane = new ScrollPane(table,controller.getSkin());
        scrollPane.setSize(500,500);
        scrollPane.setPosition(700,200);
        scrollPane.setForceScroll(false,true);
        stage.addActor(scrollPane);
    }

    private void addActor(Actor actor,int x,int y,int width,int height){
        actor.setPosition(x,y);
        actor.setSize(width,height);
        stage.addActor(actor);
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
}
