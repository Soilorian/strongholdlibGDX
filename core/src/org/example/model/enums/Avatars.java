package org.example.model.enums;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import org.example.Main;

import java.io.File;

public class Avatars extends ImageButton {
    private final Texture firstPic = new Texture("pictures/first.jpg");
    private final Texture secondPic = new Texture("pictures/second.jpg");
    private final Texture thirdPic = new Texture("pictures/third.jpg");
    private final Texture forthPic = new Texture("pictures/forth.jpg");
    private final Texture fifthPic = new Texture("pictures/fifth.jpg");
    private final Texture sixthPic = new Texture("pictures/sixth.jpg");
    private final Texture seventhPic = new Texture("pictures/seventh.jpg");
    private final Texture eighthPic = new Texture("pictures/eighth.jpg");
    private final Texture addPic = new Texture("pictures/addPic.png");
    public Avatars(Texture texture_up, Texture texture_down,int type)
    {
        super(new SpriteDrawable(new Sprite(texture_up)),
                new SpriteDrawable(new Sprite(texture_down)));

        switch (type){
            case 0:this.setBackground(new SpriteDrawable(new Sprite(firstPic)));break;
            case 1: this.setBackground(new SpriteDrawable(new Sprite(secondPic)));break;
            case 2: this.setBackground(new SpriteDrawable(new Sprite(thirdPic)));break;
            case 3: this.setBackground(new SpriteDrawable(new Sprite(forthPic)));break;
            case 4: this.setBackground(new SpriteDrawable(new Sprite(fifthPic)));break;
            case 5: this.setBackground(new SpriteDrawable(new Sprite(sixthPic)));break;
            case 6: this.setBackground(new SpriteDrawable(new Sprite(seventhPic)));break;
            case 7: this.setBackground(new SpriteDrawable(new Sprite(eighthPic)));break;
            case 8: this.setBackground(new SpriteDrawable(new Sprite(addPic)));break;
            default: this.setBackground(new SpriteDrawable(new Sprite(texture_up)));break;
        }
    }


    public static Texture getPic(int type){
        switch (type){
            case 0: return new Texture("pictures/first.jpg");
            case 1: return new Texture("pictures/second.jpg");
            case 2: return new Texture("pictures/third.jpg");
            case 3: return new Texture("pictures/forth.jpg");
            case 4: return new Texture("pictures/fifth.jpg");
            case 5: return new Texture("pictures/sixth.jpg");
            case 6: return new Texture("pictures/seventh.jpg");
            case 7: return new Texture("pictures/eighth.jpg");
            case 8: return new Texture("pictures/addPic.png");
            default:return null;
        }
    }

}
