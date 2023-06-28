package org.example.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import org.example.control.Controller;
import org.example.model.DataBase;
import org.example.view.enums.Menus;

import static org.example.control.Controller.manager;

public class LoadingMenu implements Screen {

    Stage behindStage = new Stage();
    Controller controller;
    Slider slider = new Slider(0, 1, 0.001f, false, new Skin(Gdx.files.internal("button/skin/sgx-ui.json")));

    public LoadingMenu(Controller controller) {
        this.controller = controller;
        slider.setValue(manager.getProgress());
        slider.updateVisualValue();
        slider.setDisabled(true);
        slider.setWidth(Gdx.graphics.getWidth() / 4f * 3);
        slider.setPosition(Gdx.graphics.getWidth() / 2f - slider.getWidth() / 2f, Gdx.graphics.getHeight() / 4f);
        behindStage.addActor(new Image(Controller.resizer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), controller.getLoadingBG())));
        behindStage.addActor(slider);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        manager.update();
        behindStage.draw();
        behindStage.act();
        slider.setValue(manager.getProgress());
        slider.updateVisualValue();
        if (manager.isFinished())
            done();
    }

    private void done() {
        controller.createMenus();
        if (DataBase.isStayLogged()) {
            controller.setScreen(Menus.MAIN_MENU.getMenu());
        }
        else {
            controller.setScreen(Menus.ENTRANCE_MENU.getMenu());
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
