package org.example.view.menus;


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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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

import static com.badlogic.gdx.Gdx.graphics;
import static com.badlogic.gdx.Gdx.input;

public class MainMenu extends Menu {

    private final PerspectiveCamera camera;
    private final ModelBatch modelBatch;
    private final Array<ModelInstance> instances = new Array<>();
    private final Stage stageInput = new Stage();
    private final Image backgroundImage;
    private final TextButton startGameButton, mapEditorButton, settingsButton, profileButton, exitButton;
    private Model model, model1;
    private ModelInstance modelInstance, modelInstance1;
    private Environment environment;
    private AnimationController animationController;

    public MainMenu() {
        camera = new PerspectiveCamera(75, graphics.getWidth(), graphics.getHeight());
        modelBatch = new ModelBatch();
        backgroundImage = new Image(controller.resizer(graphics.getWidth(), graphics.getHeight(), controller.getMainMenuBackground()));
        threeDPrep();
        startGameButton = new TextButton("start new game", controller.getSkin());
        mapEditorButton = new TextButton("map editor", controller.getSkin());
        settingsButton = new TextButton("settings", controller.getSkin());
        profileButton = new TextButton("profile", controller.getSkin());
        exitButton = new TextButton("exit", controller.getSkin());
        twoDPrep();
    }

    private void twoDPrep() {
        int typicalWidth = 200;
        int typicalHeight = 100;
        backgroundImage.setPosition(0, 0);

        startGameButton.setPosition(graphics.getWidth() / 3f, graphics.getHeight() / 8f * 6);
        startGameButton.setWidth(typicalWidth);
        startGameButton.setHeight(typicalHeight);
        startGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startNewGame();
            }
        });

        mapEditorButton.setPosition(graphics.getWidth() / 3f, graphics.getHeight() / 8f * 5);
        mapEditorButton.setHeight(typicalHeight);
        mapEditorButton.setWidth(typicalWidth);
        mapEditorButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    mapEditor();
                } catch (UnsupportedAudioFileException | IOException | NotInStoragesException |
                         LineUnavailableException | CoordinatesOutOfMap e) {
                    System.out.println("fuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuursi ra paas darim");
                    throw new RuntimeException(e);
                }
            }
        });

        settingsButton.setPosition(graphics.getWidth() / 3f, graphics.getHeight() / 8f * 4);
        settingsButton.setWidth(typicalWidth);
        settingsButton.setHeight(typicalHeight);
        settingsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                settings();
            }
        });

        profileButton.setPosition(graphics.getWidth() /3f, graphics.getHeight()/8f *3);
        profileButton.setHeight(typicalHeight);
        profileButton.setWidth(typicalWidth);
        profileButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                profile();
            }
        });

        exitButton.setPosition(graphics.getWidth() /3f, graphics.getHeight()/8f *2);
        exitButton.setWidth(typicalWidth);
        exitButton.setHeight(typicalHeight);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                exitApp();
            }
        });

        stage.addActor(backgroundImage);
        stageInput.addActor(startGameButton);
        stageInput.addActor(mapEditorButton);
        stageInput.addActor(settingsButton);
        stageInput.addActor(profileButton);
        stageInput.addActor(exitButton);
        input.setInputProcessor(stageInput);
    }

    private void exitApp() {
        controller.exitGame();
    }

    private void threeDPrep() {
        camera.position.set(210, 150, 270);
        camera.lookAt(0, 210, 0);
        camera.near = 0.1f;
        camera.far = 3000f;
        UBJsonReader ubJsonReader = new UBJsonReader();
        G3dModelLoader modelLoader = new G3dModelLoader(ubJsonReader);
        model = modelLoader.loadModel(Gdx.files.getFileHandle("Model/Crouch To Stand.g3db", Files.FileType.Internal));
        model1 = modelLoader.loadModel(Gdx.files.getFileHandle("Model/BlenderRetroIso.g3db", Files.FileType.Internal));
        modelInstance = new ModelInstance(model);
        modelInstance.transform.translate(160, 0, -40);
        modelInstance1 = new ModelInstance(model1);
        modelInstance1.transform.translate(200, 0, 0);
        instances.add(modelInstance);
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        animationController = new AnimationController(modelInstance);
        animationController.setAnimation("mixamo.com", 1);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glViewport(0, 0, graphics.getWidth(), graphics.getHeight());
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stage.act();
        stage.draw();
        camera.update();
        animationController.update(graphics.getDeltaTime());
        modelBatch.begin(camera);
        modelBatch.render(instances, environment);
        modelBatch.end();
        stageInput.draw();
        stageInput.act();
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
                controller.changeMenu(this, this);
            } else if (input.equalsIgnoreCase("show menu")) {
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

    @Override
    public void create() {
        controller.getRainSound().play();
        Gdx.input.setInputProcessor(stageInput);
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
        controller.changeMenu(Menus.SELECT_MAP_MENU.getMenu(), this);
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
