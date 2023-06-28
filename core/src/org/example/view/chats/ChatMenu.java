package org.example.view.chats;

import org.example.model.DataBase;
import org.example.model.Player;
import org.example.model.chat.Chat;
import org.example.model.chat.Group;
import org.example.model.chat.Message;
import org.example.model.chat.PrivateChat;

import java.util.regex.Matcher;

import static org.example.control.ChatController.MoveGroupToTop;
import static org.example.control.ChatController.MovePrivateChatToTop;

public class ChatMenu {
    private final Player currentPlayer;
    private Chat chat;

    public ChatMenu(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public String showMessages() {
        StringBuilder result = new StringBuilder("Messages:");
        for (Message message : chat.getMessages()) {
            result.append('\n');
            result.append(message);
        }
        return result.toString();
    }

    public String showMembers() {
        if (chat instanceof PrivateChat)
            return "Invalid command!";
        StringBuilder result = new StringBuilder("Members:");
        for (Player user : chat.getMembers()) {
            result.append('\n');
            result.append(user);
            if (user.equals(chat.getOwner()) && !(chat instanceof PrivateChat)) {
                result.append(" *owner");
            }
        }
        return result.toString();
    }

    public String addMember(Matcher matcher) {
        if (chat instanceof PrivateChat) {
            return "Invalid command!";
        } else if (!chat.getOwner().equals(currentPlayer)) {
            return "You don't have access to add a member!";
        } else if (DataBase.getPlayerByUsername(matcher.group("Id")) == null) {
            return "No user with this id exists!";
        } else if (chat.getMembers().contains(DataBase.getPlayerByUsername(matcher.group("Id")))) {
            return "This user is already in the chat!";
        } else {
            Player user = DataBase.getPlayerByUsername(matcher.group("Id"));
            assert user != null;
            if (chat instanceof Group) {
                user.addChat(chat);
                chat.addMessage(new Message(currentPlayer, user.getUsername() + " has been added to the group!"));
                MoveGroupToTop((Group) chat);
                chat.addMember(user);
            }
        }
        return "Player has been added successfully!";
    }

    public String sendMessage(Matcher matcher) {

        chat.addMessage(new Message(currentPlayer, matcher.group("Message")));
        if (chat instanceof Group)
            MoveGroupToTop((Group) chat);
        if (chat instanceof PrivateChat)
            MovePrivateChatToTop((PrivateChat) chat);
        return "Message has been sent successfully!";

    }
}
