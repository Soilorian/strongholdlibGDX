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
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.UBJsonReader;
import org.example.control.Controller;
import org.example.control.SoundPlayer;
import org.example.control.enums.GameStartUpMenuMessages;
import org.example.control.menucontrollers.EntranceMenuController;
import org.example.control.menucontrollers.GameMenuController;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;
import org.example.view.enums.commands.InGameMenuCommands;
import org.example.view.menus.Menu;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.regex.Matcher;

import static com.badlogic.gdx.Gdx.graphics;

public class UnitCreatingMenu extends Menu {
    int totalX = graphics.getWidth();
    int totalY = graphics.getHeight();
    private final PerspectiveCamera camera;
    private final ModelBatch modelBatch;
    private Model model;
    private ModelInstance currentInstance;
    private ModelInstance modelInstance;
    private Environment environment;
    private AnimationController animationController;
    private Stage backStage = new Stage();
    private Image background;
    private SelectBox<String> troops;
    String[] paths = {"Capoeira(Macemen).g3db",
            "Catwalk Walk Forward(new Horse Archer).g3db",
            "Grab And Slam(Assassin).g3db",
            "Ninja Idle(Black Monk).g3db",
            "Rifle Aiming Idle(CroosBow).g3db",
            "Standing Aim Walk Back(Archer).g3db",
            "Standing Idle(Pikemen).g3db",
            "Standing Taunt Battlecry(Spearmen).g3db",
            "Standing Walk Forward(Arab Archer).g3db",
            "Sword And Shield Attack(Knight).g3db",
            "Throw In(Fire thrower).g3db",
            "Throw Object(Slinger).g3db",
            "Unarmed Idle(Arabian Swordsman).g3db",
    };

    public void addItems() {
        troops.setItems("Maceman",
                "Horse Archer",
                "Assassin",
                "Black Monk",
                "Crossbowmen",
                "Archer",
                "Pikeman",
                "Spearman",
                "Archer Bow",
                "Knight",
                "Fire Thrower",
                "Slinger",
                "Arabian Swordsman"
        );
    }


    public UnitCreatingMenu() {
        camera = new PerspectiveCamera(75, graphics.getWidth(), graphics.getHeight());
        modelBatch = new ModelBatch();
        background = new Image(controller.resizer(graphics.getWidth(), graphics.getHeight(), controller.getUnitBack()));
        troops = new SelectBox<>(controller.getJunkSkin());
    }

    public void create() {
        backStage.addActor(background);
        addActor(troops, totalX / 15, totalY * 2 / 3, 200, 80);
        addItems();
        addListenerToSelection();
        add3D();
        Gdx.input.setInputProcessor(stage);
    }

    public void addListenerToSelection() {
        troops.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setTroop(troops.getSelectedIndex());
            }
        });
    }


    public void add3D() {

        camera.position.set(0, 150, 250);
        camera.lookAt(0, 150, 0);

        camera.near = 0.1f;
        camera.far = 3000f;


        environment = new Environment();

        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));


    }


    public void setTroop(int i) {
        UBJsonReader ubJsonReader = new UBJsonReader();
        G3dModelLoader modelLoader = new G3dModelLoader(ubJsonReader);
        model = modelLoader.loadModel(Gdx.files.getFileHandle("UnitCreatingAssets/Model/" + paths[i], Files.FileType.Internal));
        currentInstance = new ModelInstance(model);
        currentInstance.transform.translate(200, -40, -50);
        currentInstance.transform.rotate(0f, 1f, 0, -40);
        animationController = new AnimationController(currentInstance);
        animationController.setAnimation("mixamo.com", -1);
    }


    public void run(String input) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        do {
//            Matcher matcher;
//            if ((matcher = InGameMenuCommands.getMatcher(input, InGameMenuCommands.CREATE_UNIT)) != null)
//                createUnit(matcher);
//            else if (InGameMenuCommands.getMatcher(input, InGameMenuCommands.BACK) != null)
//                return;
//            else if (input.equalsIgnoreCase("show menu"))
//                System.out.println(Menus.getNameByObj(this));
//            else {
//                SoundPlayer.play(Sounds.AKHEY);
//                System.out.println("invalid command!");
//            }
//        } while (true);
    }

    public void render(float delta) {
        Gdx.gl.glViewport(0, 0, graphics.getWidth(), graphics.getHeight());
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        backStage.act();
        backStage.draw();
        camera.update();
        animationController.update(graphics.getDeltaTime());
        modelBatch.begin(camera);
        modelBatch.render(currentInstance, environment);
        modelBatch.end();
        stage.draw();
        stage.act();
    }


    public void createUnit(Matcher matcher) {
        String type = Controller.removeQuotes(matcher.group("Type"));
        String count = Controller.removeQuotes(matcher.group("Count"));
        if (Controller.isFieldEmpty(type, count))
            System.out.println(GameStartUpMenuMessages.EMPTY_FIELD);
        else if (!EntranceMenuController.isDigit(count))
            System.out.println("not valid count!\ncount filed should only contain digits");
        else System.out.println(GameMenuController.createUnit(type, Integer.parseInt(count)));
    }


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
}