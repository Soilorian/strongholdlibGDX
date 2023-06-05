package org.example.view.menus.ingamemenus;


import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.UBJsonReader;
import org.example.control.SoundPlayer;
import org.example.control.menucontrollers.inGameControllers.TaxMenuController;
import org.example.model.DataBase;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;
import org.example.view.enums.commands.InGameMenuCommands;
import org.example.view.menus.Menu;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.regex.Matcher;

import static com.badlogic.gdx.Gdx.graphics;

public class TaxMenu extends Menu {
    int totalX = graphics.getWidth();
    int totalY = graphics.getHeight();
        private final PerspectiveCamera camera;
    private final ModelBatch modelBatch;
    private Model model;
    private ModelInstance modelInstance;
    private Environment environment;
    private AnimationController animationController;
    private Stage backStage = new Stage();
    private Image background;
    private Slider rateSlider;
    private Label taxRate;


    public TaxMenu() {
        camera = new PerspectiveCamera(75, graphics.getWidth(), graphics.getHeight());
        modelBatch = new ModelBatch();
        background = new Image(controller.resizer(graphics.getWidth(), graphics.getHeight(), controller.getTaxBack()));
        rateSlider = new Slider(-3, 8, 1, false, controller.getJunkSkin());
//        rateSlider.setValue(DataBase.getCurrentEmpire().getTax().getTaxRate());
//        taxRate = new Label("Tax Rate: " + DataBase.getCurrentEmpire().getTax().getTaxRate(),
//                controller.getJunkSkin());
        taxRate = new Label("Tax Rate: " + 2,
                controller.getJunkSkin());
    }

    @Override
    public void create() {
        backStage.addActor(background);
        addActor(rateSlider, totalX * 38 / 100, totalY / 4, 520, 50);
        addActor(taxRate, totalX * 50 / 100, totalY / 2, 520, 50);
        addListenerToSlider();
        Gdx.input.setInputProcessor(stage);
    }

    public void add3D() {
        //TODO add animation
        camera.position.set(0, 0, 600);
        camera.lookAt(0, 0, 0);
        camera.near = 0.1f;
        camera.far = 7000f;
        UBJsonReader ubJsonReader = new UBJsonReader();
        G3dModelLoader modelLoader = new G3dModelLoader(ubJsonReader);
        model = modelLoader.loadModel(Gdx.files.getFileHandle("GraneryAssets/granaryModel/Catwalk Walk.g3db", Files.FileType.Internal));
        modelInstance = new ModelInstance(model);
        modelInstance.transform.translate(-0, -100, 0);
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        animationController = new AnimationController(modelInstance);
        animationController.setAnimation("mixamo.com", -1);
    }


    public void addListenerToSlider() {
        rateSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                taxRate.setText("Tax Rate: " + String.valueOf((int) rateSlider.getValue()));
                TaxMenuController.setTax("Tax Rate: " + String.valueOf((int) rateSlider.getValue()));
            }
        });
    }

    public void run(String commands) throws UnsupportedAudioFileException, LineUnavailableException, IOException {

    }

    public void render(float delta) {
        Gdx.gl.glViewport(0, 0, graphics.getWidth(), graphics.getHeight());
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        backStage.act();
        backStage.draw();
        camera.update();
//        animationController.update(graphics.getDeltaTime());
//        modelBatch.begin(camera);
//        modelBatch.render(modelInstance, environment);
//        modelBatch.end();
        stage.draw();
        stage.act();
    }

//    public void setTax(Matcher matcher) {
//        String rate = matcher.group("Rate");
//        System.out.println(TaxMenuController.setTax(rate));
//    }


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


//    public String showTax() {
//        return null;
//    }
}
