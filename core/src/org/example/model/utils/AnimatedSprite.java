package org.example.model.utils;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.io.Serializable;

public class AnimatedSprite extends Sprite  implements Serializable {
    // Attributes
    private Texture[] frames; // Your frames
    private int currentFrame; // This value will iterate over frames to display the right frame
    private float fps; // Your frame rate in frame per second
    private float timer;

    // Constructor
    public AnimatedSprite(Texture[] frames, float framePerSecond)
    {
        super(frames[0]); // Init your sprite with the first frame texture
        this.frames = frames;
        fps = framePerSecond;
        currentFrame = 0;
        timer = 0;
    }

    public void update(float elapsedTime)
    {
        if (timer < 1.0 / fps)
            timer += elapsedTime;
        else
        {
            timer -= 1.0 / fps;
            nextFrame();
        }
    }

    public void nextFrame()
    {
        // Change frame
        if (currentFrame < frames.length - 1)
            currentFrame++;
        else
            currentFrame = 0;

        // Set the good frame texture
        setTexture(frames[currentFrame]);
    }
}