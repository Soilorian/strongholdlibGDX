package org.example.view.menus;


import com.badlogic.gdx.ApplicationListener;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.UBJsonReader;
import org.example.control.Controller;
import org.example.control.SoundPlayer;
import org.example.control.menucontrollers.MainMenuController;
import org.example.model.DataBase;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;
import org.example.view.enums.commands.EntranceMenuCommands;
import org.example.view.enums.commands.GameStartUpMenuCommands;
import org.example.view.enums.commands.MainMenuCommands;
import org.example.view.enums.commands.MapEditorMenuCommands;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.regex.Matcher;

public class MainMenu extends Menu{

    private PerspectiveCamera camera;
    private ModelBatch modelBatch;
    private Model model;
    private Model model1;
    private ModelInstance modelInstance;
    private ModelInstance modelInstance1;
    private Array<ModelInstance> instances = new Array<>();

    private Environment environment;
    private AnimationController animationController;

//	private Array<ModelInstance> instances;
//	private Asset


    public MainMenu() {
        camera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        camera.position.set(210, 150, 270);
        camera.lookAt(0, 210, 0);

        camera.near = 0.1f;
        camera.far = 3000f;


        modelBatch = new ModelBatch();

        // fbx g3dj g3db

        UBJsonReader ubJsonReader = new UBJsonReader();

        G3dModelLoader modelLoader = new G3dModelLoader(ubJsonReader);

        model = modelLoader.loadModel(Gdx.files.getFileHandle("Model/Crouch To Stand.g3db", Files.FileType.Internal));
        model1 = modelLoader.loadModel(Gdx.files.getFileHandle("Model/BlenderRetroIso.g3db", Files.FileType.Internal));

        modelInstance = new ModelInstance(model);
        modelInstance.transform.translate(200,0,155);
//        modelInstance.transform.ro
        modelInstance1 = new ModelInstance(model1);
        modelInstance1.transform.translate(200,0,0);
        instances.add(modelInstance);
        instances.add(modelInstance1);

        environment = new Environment();

        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        animationController = new AnimationController(modelInstance);
        animationController.setAnimation("mixamo.com", 1);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        camera.update();
        animationController.update(Gdx.graphics.getDeltaTime());

        modelBatch.begin(camera);
        modelBatch.render(instances, environment);
        modelBatch.end();
    }

    @Override
    public void dispose() {
        modelBatch.dispose();
        model.dispose();

    }

    @Override
    public void run(String input) throws UnsupportedAudioFileException, LineUnavailableException, IOException,
            CoordinatesOutOfMap, NotInStoragesException {
        System.out.println("entered " + Menus.getNameByObj(this));
        do {
            if (MainMenuCommands.getMatcher(input, MainMenuCommands.START_GAME) != null) {
                startNewGame();
                break;
            } else if ((input.equalsIgnoreCase("open music player"))) {
                controller.setScreen(Menus.MUSIC_CONTROL_MENU.getMenu());
                controller.changeMenu(this);
            }else if (input.equalsIgnoreCase("show menu")) {
                System.out.println(Menus.getNameByObj(this));
            } else if (MainMenuCommands.getMatcher(input, MainMenuCommands.PROFILE) != null) {
                profile();
                break;
            } else if (MainMenuCommands.getMatcher(input, MainMenuCommands.SETTINGS) != null) {
                settings();
                break;
            } else if (MainMenuCommands.getMatcher(input, MainMenuCommands.MAP_EDITOR) != null) {
                mapEditor();
                break;
            } else if (MainMenuCommands.getMatcher(input, MainMenuCommands.LOGOUT) != null) {
                DataBase.clearStayLogged();
                logout();
                break;
            } else if (EntranceMenuCommands.getMatcher(input, EntranceMenuCommands.EXIT) != null) {
                Controller.setCurrentMenu(null);
                break;
            } else {
                SoundPlayer.play(Sounds.AKHEY);
                System.out.println("invalid command");
            }
        } while (true);
    }

    private void mapEditor() throws UnsupportedAudioFileException, CoordinatesOutOfMap, LineUnavailableException, NotInStoragesException, IOException {
        System.out.println("please select the size of your map");
        do {
            String input = ""; // TODO: 6/1/2023 new menu
            Matcher matcher;
            if ((matcher = GameStartUpMenuCommands.getMatcher(input, GameStartUpMenuCommands.SELECT_SIZE)) != null) {
                ; // TODO: 6/1/2023 select size menu first
            } else if (MapEditorMenuCommands.getMatcher(input, MapEditorMenuCommands.BACK) != null)
                return;
            else if (MapEditorMenuCommands.getMatcher(input, MapEditorMenuCommands.NO) != null)
                break;
            else System.out.println("invalid input!");
        } while (true);
        controller.setScreen(Menus.MAP_EDIT_MENU.getMenu());
    }

    public void startNewGame() {
        MainMenuController.startNewGame();
    }

    public void profile() {
        MainMenuController.profile();
    }

    public void settings() {
        // TODO: 5/11/2023 for Graphics
        System.out.println("this menu will be completed for the 2nd faz");
    }

    public void logout() {
        MainMenuController.logout();
    }


}
