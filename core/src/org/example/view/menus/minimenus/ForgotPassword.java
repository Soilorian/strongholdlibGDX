package org.example.view.menus.minimenus;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.example.Main;
import org.example.control.menucontrollers.EntranceMenuController;
import org.example.model.DataBase;
import org.example.model.Player;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.view.menus.Menu;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class ForgotPassword extends Menu {

    private TextField username, qAnswer, newPass;

    private Label question,result;
    private TextButton findUsername,change;
    Player player;

    public ForgotPassword() {
        username = new TextField("", controller.getSkin());
        qAnswer = new TextField("", controller.getSkin());
        newPass = new TextField("", controller.getSkin());
        question = new Label("",controller.getSkin());
        result = new Label("",controller.getSkin());
        findUsername = new TextButton("find",controller.getSkin());

    }

    protected void run(String input) throws IOException, UnsupportedAudioFileException, LineUnavailableException, CoordinatesOutOfMap, NotInStoragesException {


    }

    @Override
    public void create() {
        addActor(username,800,650,350,50);
        username.setMessageText("username");
        addActor(qAnswer,800,600,350,50);
        qAnswer.setDisabled(true);
        qAnswer.setMessageText("Answer");
        addActor(newPass,800,550,350,50);
        newPass.setDisabled(true);
        newPass.setMessageText("New Password");
        addActor(question,500,600,50,50);
        addActor(result,800,500,350,50);
        addActor(findUsername,1150,650,100,50);

        findUsername.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if ((player = DataBase.getPlayerByUsername(username.getText())) !=null) {
                    qAnswer.setDisabled(false);
                    question.setText(player.getSecurityQuestion());

                }
                else
                    result.setText("There's no player with this username");
            }
        });

        change.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (EntranceMenuController.checkSecurityAnswer(username.getText(),
                        qAnswer.getText())) {
                }

            }
        });

    }


    public void addActor(Actor actor, int x, int y, int width, int height) {
        actor.setX(x);
        actor.setY(y);
        actor.setWidth(width);
        actor.setHeight(height);
        stage.addActor(actor);
    }
}
