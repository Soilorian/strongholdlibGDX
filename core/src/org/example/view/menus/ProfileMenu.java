package org.example.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.example.control.Controller;
import org.example.control.comparetors.PlayerComparator;
import org.example.control.enums.ProfileMenuMessages;
import org.example.control.menucontrollers.ProfileMenuController;
import org.example.model.Captcha;
import org.example.model.DataBase;
import org.example.model.Player;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.view.enums.Avatars;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Collections;

import static com.badlogic.gdx.Gdx.graphics;

public class ProfileMenu extends Menu {
    private Label error;

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
    private ImageButton editBut1,editBut2,editBut3,editBut4,editBut5,board,back,avatar;

    //leaderBoard
    private String[] num,user,score;
    private Table leaderBoard;
    private ScrollPane scrollPane;
    private Button select;
    private List listNum,listUser,listScore;
    private ImageButton defaultPic,pic1,pic2,pic3,pic4,pic5,pic6,pic7;

    //selectedProf
    private Window windowSelect;
    private ImageButton prof,copy;
    private TextField username,nickname,slogan;
    private Button backBoard;

    //Pics
    private final Texture groundPic = new Texture("pictures/ProfBackground.png");
    private final Texture editPic = new Texture("pictures/edit.png");
    private final Texture trashPic = new Texture("pictures/trash.png");
    private final Texture backPic = new Texture("pictures/back.jpg");
    private final Texture boardPic = new Texture("pictures/leaderBoard.png");
    private final Texture copyPic = new Texture("pictures/copy.png");


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
        newSelect();
        newImage();
    }
    @Override
    public void render(float delta){
        Gdx.gl.glViewport(0, 0, graphics.getWidth(), graphics.getHeight());
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        batch.begin();
        batch.draw(groundPic,0,0);
        batch.end();
        behindStage.act();
        behindStage.draw();
    }

    private void profNew(){
        changeUser = new TextField("", Controller.getSkin());
        changeNick = new TextField("", Controller.getSkin());
        changePass = new TextField("", Controller.getSkin());
        changeEmail = new TextField("", Controller.getSkin());
        changeSlogan = new TextField("", Controller.getSkin());
        submit = new TextButton("Slogan", Controller.getSkin());
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
        news = new TextField("", Controller.getSkin());
        Drawable delete = new TextureRegionDrawable(new TextureRegion(trashPic));
        delBut = new ImageButton(delete);
    }
    private void newChangePass(){
        captcha = new TextField("", Controller.getSkin());
        oldPass = new TextField("", Controller.getSkin());
        newPass = new TextField("", Controller.getSkin());
        showPass = new CheckBox("Show Password", Controller.getSkin());
        showNew = new CheckBox("Show New Password", Controller.getSkin());
        changePassBut = new TextButton("Change", Controller.getSkin());
        backBut = new TextButton("Back", Controller.getSkin());
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(controller.getCaptchaPath()));
        captchaButton = new ImageButton(drawable);
        passCaptcha = new Captcha();
        Texture captchaTexture = new Texture("saved.png");
        captchaImage = new Image(captchaTexture);
    }
    private void newLeader(){
        num = new String[DataBase.getPlayers().size()];
        user = new String[DataBase.getPlayers().size()];
        score = new String[DataBase.getPlayers().size()];
        backBut = new TextButton("Back", Controller.getSkin());
        select = new TextButton("Select", Controller.getSkin());
        listNum = new List(Controller.getSkin(),"prof");
        listUser = new List(Controller.getSkin(),"prof");
        listScore = new List(Controller.getSkin(),"prof");
        leaderBoard = new Table(Controller.getSkin());
        scrollPane = new ScrollPane(leaderBoard, Controller.getSkin());
    }
    private void newSelect(){
        username = new TextField("", Controller.getSkin());
        nickname = new TextField("", Controller.getSkin());
        slogan = new TextField("", Controller.getSkin());
        backBoard = new TextButton("Back", Controller.getSkin());
        Drawable cop = new TextureRegionDrawable(new TextureRegion(copyPic));
        copy = new ImageButton(cop);
    }
    private void newImage(){
        defaultPic = new Avatars(Avatars.getPic(0),Avatars.getPic(0),0);
        pic1 = new Avatars(Avatars.getPic(1),Avatars.getPic(1),1);
        pic2 = new Avatars(Avatars.getPic(2),Avatars.getPic(2),2);
        pic3 = new Avatars(Avatars.getPic(3),Avatars.getPic(3),2);
        pic4 = new Avatars(Avatars.getPic(4),Avatars.getPic(4),4);
        pic5 = new Avatars(Avatars.getPic(5),Avatars.getPic(5),5);
        pic6 = new Avatars(Avatars.getPic(6),Avatars.getPic(6),6);
        pic7 = new Avatars(Avatars.getPic(7),Avatars.getPic(7),7);
    }
    private void profileMenu(){
        behindStage.clear();
        int type = DataBase.getCurrentPlayer().getProfImage();
        avatar = new Avatars(Avatars.getPic(type),Avatars.getPic(type),type);
        addActor(avatar,graphics.getWidth()/4.7f,graphics.getHeight()/1.5f,130,100);
        addActor(changeUser,graphics.getWidth()/5.5f,graphics.getHeight()/1.65f,250,50);
        addActor(changeNick,graphics.getWidth()/5.5f,graphics.getHeight()/1.84f,250,50);
        addActor(changePass,graphics.getWidth()/5.5f,graphics.getHeight()/2.07f,250,50);
        addActor(changeEmail,graphics.getWidth()/5.5f,graphics.getHeight()/2.38f,250,50);
        addActor(changeSlogan,graphics.getWidth()/5.5f,graphics.getHeight()/2.8f,250,50);
        addActor(submit,graphics.getWidth()/4.7f,graphics.getHeight()/3.4f,130,50);
        addActor(back,graphics.getWidth()/5.4f,graphics.getHeight()/4f,45,140);
        addActor(board,graphics.getWidth()/3.5f,graphics.getHeight()/3.38f,45,45);
        addActor(editBut1,graphics.getWidth()/3.43f,graphics.getHeight()/1.62f,30,30);
        addActor(editBut2,graphics.getWidth()/3.43f,graphics.getHeight()/1.8f,30,30);
        addActor(editBut3,graphics.getWidth()/3.43f,graphics.getHeight()/2.02f,30,30);
        addActor(editBut4,graphics.getWidth()/3.43f,graphics.getHeight()/2.32f,30,30);
        addActor(editBut5,graphics.getWidth()/3.43f,graphics.getHeight()/2.73f,30,30);
        changeUser.setText(DataBase.getCurrentPlayer().getUsername());
        changeUser.setDisabled(true);
        changeNick.setText(DataBase.getCurrentPlayer().getNickname());
        changeNick.setDisabled(true);
        changePass.setText("**********");
        changePass.setDisabled(true);
        changeEmail.setText(DataBase.getCurrentPlayer().getEmail());
        changeEmail.setDisabled(true);
        changeSlogan.setText(ProfileMenuController.showSlogan());
        changeSlogan.setDisabled(true);
        imageListener(avatar);
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
        Window changeWindow = new Window("Changes " + type, Controller.getSkin());
        Button changeBut = new TextButton("Changes " + type, Controller.getSkin());
        error = new Label("", Controller.getSkin());
        changeWindow.setBounds(graphics.getWidth()/6f,graphics.getHeight()/3f,400, 400);
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
        window = new Window("Change Password", Controller.getSkin());
        table = new Table(Controller.getSkin());
        error = new Label("", Controller.getSkin());
        window.setBounds(graphics.getWidth()/6f,graphics.getHeight()/5f,500, 600);
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
                captchaImage.setPosition(window.getX()/3.8f, window.getY()/15f);
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
        int rank = DataBase.getPlayers().indexOf(DataBase.getCurrentPlayer())+1;
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
        scrollPane.setSize(400,500);
        scrollPane.setPosition(graphics.getWidth()/5.5f,graphics.getHeight()/3.7f);
        scrollPane.setForceScroll(false,true);
        addActor(backBut,graphics.getWidth()/5f,graphics.getHeight()/5f,100,60);
        addActor(select,graphics.getWidth()/3.2f,graphics.getHeight()/5f,100,60);
        backListener(backBut);
        select.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(listUser.getSelected().toString().equals(DataBase.getCurrentPlayer().getUsername())){
                    profileMenu();
                }else{
                    selectedProf(listUser.getSelected().toString());
                }
            }
        });
        behindStage.addActor(scrollPane);
    }
    private void selectedProf(String user){
        behindStage.clear();
        windowSelect = new Window(user + " Profile",Controller.getSkin());
        windowSelect.setBounds(graphics.getWidth()/5.5f,graphics.getHeight()/3.7f,500,500);
        Player player = DataBase.getPlayerByUsername(user);
        int type = player.getProfImage();
        prof = new Avatars(Avatars.getPic(type),Avatars.getPic(type),type);
        windowSelect.add(prof).center().colspan(2).padBottom(30);
        windowSelect.addActorAfter(prof,copy);
        copy.setPosition(windowSelect.getWidth()/1.27f,windowSelect.getHeight()/2.04f);
        windowSelect.row();
        username.setText(player.getUsername());
        username.setDisabled(true);
        nickname.setText(player.getNickname());
        nickname.setDisabled(true);
        slogan.setText(player.getSlogan());
        slogan.setDisabled(true);
        windowSelect.add(username).size(250,50).padBottom(20).row();
        windowSelect.add(nickname).size(250,50).padBottom(20).row();
        windowSelect.add(slogan).size(250,50);
        addActor(backBoard,graphics.getWidth()/4f,graphics.getHeight()/6.5f,200,80);
        backListener(backBoard);
        copy.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                DataBase.getCurrentPlayer().setProfImage(type);
            }
        });
        behindStage.addActor(windowSelect);
        behindStage.addActor(backBoard);
    }
    private void avatarMenu(){
        behindStage.clear();
        Table tablePic = new Table(Controller.getSkin());
        ScrollPane scroll = new ScrollPane(tablePic, Controller.getSkin());
        tablePic.add(defaultPic).pad(10,10,10,10);
        tablePic.add(pic1).pad(10,10,10,10).row();
        tablePic.add(pic2).pad(10,10,10,10);
        tablePic.add(pic3).pad(10,10,10,10).row();
        tablePic.add(pic4).pad(10,10,10,10);
        tablePic.add(pic5).pad(10,10,10,10).row();
        tablePic.add(pic6).pad(10,10,10,10);
        tablePic.add(pic7).pad(10,10,10,10).row();
        scroll.setSize(700,600);
        scroll.setPosition(graphics.getWidth()/3.1f,graphics.getHeight()/3.7f);
        scroll.setForceScroll(false,true);
        addActor(backBut,graphics.getWidth()/2.2f,graphics.getHeight()/6.5f,200,80);
        backListener(backBut);
        picListener(defaultPic);
        picListener(pic1);
        picListener(pic2);
        picListener(pic3);
        picListener(pic4);
        picListener(pic5);
        picListener(pic6);
        picListener(pic7);
        behindStage.addActor(scroll);
    }

    private void addActor(Actor actor,float x,float y,int width,int height){
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
                    profileMenu();
                }else if (button.equals(backBoard)){
                    leaderBoard();
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
                } else if (imageButton.equals(avatar)) {
                    avatarMenu();
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
    private void picListener(final ImageButton imageButton){
        imageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (imageButton.equals(pic1)) {
                    DataBase.getCurrentPlayer().setProfImage(1);
                } else if (imageButton.equals(pic2)) {
                    DataBase.getCurrentPlayer().setProfImage(2);
                } else if (imageButton.equals(pic3)) {
                    DataBase.getCurrentPlayer().setProfImage(3);
                } else if (imageButton.equals(pic4)) {
                    DataBase.getCurrentPlayer().setProfImage(4);
                } else if (imageButton.equals(pic5)) {
                    DataBase.getCurrentPlayer().setProfImage(5);
                } else if (imageButton.equals(pic6)) {
                    DataBase.getCurrentPlayer().setProfImage(6);
                }else if (imageButton.equals(pic7)) {
                    DataBase.getCurrentPlayer().setProfImage(7);
                }else {
                    DataBase.getCurrentPlayer().setProfImage(0);
                }
            }
        });
    }
}
