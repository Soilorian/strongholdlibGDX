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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.UBJsonReader;
import org.example.control.Controller;
import org.example.control.SoundPlayer;
import org.example.control.menucontrollers.inGameControllers.ShopMenuController;
import org.example.model.ingame.map.resourses.Resources;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;
import org.example.view.enums.commands.InGameMenuCommands;
import org.example.view.menus.Menu;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;

import static com.badlogic.gdx.Gdx.graphics;

public class ShopMenu extends Menu {
    private Stage backStage = new Stage();
    private Image background;
    int totalX = graphics.getWidth();
    int totalY = graphics.getHeight();
    private final PerspectiveCamera camera;
    private final ModelBatch modelBatch;

    private Model model;
    private ModelInstance modelInstance;
    private Environment environment;
    private AnimationController animationController;
    private SelectBox<String> sellBox,buyBox;
    private TextButton buy,sell;
    private TextField buyCounter,sellCounter;
    private Label buyResult , sellResult;
    private TextArea list;
    public ShopMenu() {
        camera = new PerspectiveCamera(75, graphics.getWidth(), graphics.getHeight());
        modelBatch = new ModelBatch();

        background = new Image(controller.resizer(graphics.getWidth(), graphics.getHeight(), controller.getShopBack()));
        list = new TextArea(ShopMenuController.showPriceList(),controller.getSkin());
        sellBox = new SelectBox<>(controller.getJunkSkin());
        buyBox = new SelectBox<>(controller.getJunkSkin());

        buy = new TextButton("buy",controller.getJunkSkin());
        sell = new TextButton("sell",controller.getJunkSkin());

        buyCounter = new TextField("",controller.getSkin());
        sellCounter = new TextField("",controller.getSkin());

        buyResult = new Label("",controller.getSkin());
        sellResult = new Label("",controller.getSkin());



        setSelectionItems();

    }


    public void add3D() {

        camera.position.set(0, 0, 210);
        camera.lookAt(0, 0, 0);
        camera.near = 0.1f;
        camera.far = 7000f;
        UBJsonReader ubJsonReader = new UBJsonReader();
        G3dModelLoader modelLoader = new G3dModelLoader(ubJsonReader);
        model = modelLoader.loadModel(Gdx.files.getFileHandle("ShopAssets/ShopModel/Boss.g3db", Files.FileType.Internal));
        modelInstance = new ModelInstance(model);
        modelInstance.transform.translate(30, -80, 0);
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        animationController = new AnimationController(modelInstance);
        animationController.setAnimation("mixamo.com", 1);
    }


    @Override
    public void create() {
        backStage.addActor(background);
        addActor(list,totalX/75,totalY/15,520,850);
        addActor(sellBox,totalX*3/10,totalY*3/15,140,50);
        addActor(buyBox,totalX*8/10,totalY*3/15,140,50);
        addActor(buy,totalX*3/10,totalY*4/15-5,140,90);
        addActor(sell,totalX*8/10,totalY*4/15-5,140,90);
        addActor(buyCounter,totalX*3/10+20,totalY*2/15,90,50);
        addActor(sellCounter,totalX*8/10+20,totalY*2/15,90,50);

        addActor(buyResult,totalX*3/10+20,totalY*1/15,90,50);
        addActor(sellResult,totalX*8/10+20,totalY*1/15,90,50);

        addButtonsListener();
        add3D();

        Gdx.input.setInputProcessor(behindStage);
    }

    public void addButtonsListener() {
        buy.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buyResult.setText(ShopMenuController.buyItem(buyBox.getSelected(),buyCounter.getText()));
            }
        });
        sell.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sellResult.setText(ShopMenuController.sellResource(buyBox.getSelected(),sellCounter.getText()));
            }
        });
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
        modelBatch.render(modelInstance, environment);
        modelBatch.end();
        behindStage.draw();
        behindStage.act();
    }


    public void setSelectionItems() {
        ArrayList<String> resourceList = new ArrayList<>();
        for (Resources resources : Resources.values())
            resourceList.add(resources.getType());
        String[] values = resourceList.toArray(new String[resourceList.size()]);
            buyBox.setItems(values);
            sellBox.setItems(values);
    }

    public void run(String commands) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Matcher matcher;
        while (true) {
            if ((matcher = InGameMenuCommands.getMatcher(commands, InGameMenuCommands.SHOW_SHOP_PRICES)) != null)
                showPriceList();
            else if ((matcher = InGameMenuCommands.getMatcher(commands, InGameMenuCommands.BUY_ITEM)) != null)
                buyItem(matcher);
            else if ((matcher = InGameMenuCommands.getMatcher(commands, InGameMenuCommands.SELL_ITEM)) != null)
                sellCard(matcher);
            else if (InGameMenuCommands.getMatcher(commands, InGameMenuCommands.BACK) != null)
                break;
            else if (commands.equalsIgnoreCase("show menu"))
                System.out.println(Menus.getNameByObj(this));
            else {
                SoundPlayer.play(Sounds.AKHEY);
                System.out.println("invalid command!");
            }
        }
    }
    public void showPriceList() {
        System.out.println(ShopMenuController.showPriceList());
    }

    public void buyItem(Matcher matcher) {
        String item = Controller.removeQuotes(matcher.group("Item"));
        String amount = Controller.removeQuotes(matcher.group("Amount"));
        System.out.println(ShopMenuController.buyItem(item, amount));
    }

    public void sellCard(Matcher matcher) {
        String item = Controller.removeQuotes(matcher.group("Item"));
        String amount = Controller.removeQuotes(matcher.group("Amount"));
        System.out.println(ShopMenuController.sellResource(item, amount));
    }

    public void addActor(Actor actor, int x, int y, int width, int height) {
        actor.setX(x);
        actor.setY(y);
        actor.setWidth(width);
        actor.setHeight(height);
        behindStage.addActor(actor);
    }

    public void addActor(Actor actor, int x, int y) {
        actor.setX(x);
        actor.setY(y);
        behindStage.addActor(actor);
    }
}
