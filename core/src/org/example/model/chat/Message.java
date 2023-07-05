package org.example.model.chat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import org.example.model.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
    private final Player owner;
    private String content;
    private String timeSent;
    private boolean seen;
    private ArrayList<String> emojis;
    private Label label, bottomLabel;
    private Texture resized;
    private Chat chat;

    public Message(Player owner, String content, Chat chat, Player owner1) {
        this.owner = owner1;
    }

    private void setupBottomLabel() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String emoji : emojis) {
            stringBuilder.append(emoji).append(" ");
        }
        stringBuilder.append("✔");
        if (seen)
            stringBuilder.append("✔");
        stringBuilder.append("   ").append(timeSent);
    }

    public void addEmoji(String emoji) {
        if (emojis.contains(emoji))
            emojis.remove(emoji);
        else
            emojis.add(emoji);
        setupBottomLabel();
    }

    public void seen() {
        seen = true;
        setupBottomLabel();
    }

    public void edit(String text){
        content = text;
        label.setText(text);
    }

    public Player getOwner() {
        return owner;
    }

    public String getContent() {
        return content;
    }

    public String toString() {
        return owner.getNickname() + "(" + owner.getUsername() + "): \"" + content + "\"";
    }

    public void draw(SpriteBatch batch, float x, float y) {
        batch.draw(resized, x + resized.getWidth() / 2f, y + resized.getHeight() / 2f);
        label.setPosition(x + resized.getWidth(), y + 40);
        bottomLabel.setPosition(x + resized.getWidth(), y);
    }

    public Chat getChat() {
        return chat;
    }
}
