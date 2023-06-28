package org.example.view.chats;


import org.example.model.DataBase;
import org.example.model.Player;
import org.example.model.chat.Chat;
import org.example.model.chat.Group;
import org.example.model.chat.PrivateChat;

import java.util.regex.Matcher;

public class MessengerMenu {
    private Chat chat;
    private final Player currentPlayer;

    public MessengerMenu(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    
    private String showAllChannels(){
        StringBuilder result = new StringBuilder("All channels:");
        int counter=0;
        return result.toString();
    }
    private String showChats(){
        StringBuilder result = new StringBuilder("Chats:");
        int counter=0;
        for (int i = currentPlayer.getChats().size() - 1; i >= 0; i--) {
            Chat chat1 = currentPlayer.getChats().get(i);
            result.append('\n');
            result.append(++counter).append(". ").append(chat1);
        }
        return result.toString();
    }

    private String enterChat(Matcher matcher){
        switch (matcher.group("ChatType")) {
            case "group" : {
                Group temp = currentPlayer.getGroupById(matcher.group("Id"));
                if (temp != null) {
                    this.chat=temp;
                    return "You have successfully entered the chat!";
                } else return "You have no group with this id!";
            }
            case "private chat" : {
                PrivateChat temp = currentPlayer.getPrivateChatById(matcher.group("Id"));
                if (temp != null) {
                    this.chat=temp;
                    return "You have successfully entered the chat!";
                } else return "You have no private chat with this id!";
            }
            default : {
                return "You have no chat with this id!";
            }
        }
    }

    private String createPrivateChat(Matcher matcher){
        if(currentPlayer.getPrivateChatById(matcher.group("Id")) != null){
            return "You already have a private chat with this user!";
        } else if (DataBase.getPlayerByUsername(matcher.group("Id")) == null) {
            return "No user with this id exists!";
        }else {
            Player user= DataBase.getPlayerByUsername(matcher.group("Id"));
            assert user != null;
            PrivateChat pv= new PrivateChat(currentPlayer, user.getUsername(), user.getNickname());
            currentPlayer.addChat(pv);
            if (!user.equals(currentPlayer))
                user.addChat(pv);
            return "Private chat with "+user.getUsername()+" has been started successfully!";
        }
    }
}