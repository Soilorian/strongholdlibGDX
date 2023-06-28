package org.example.control;

import org.example.model.Player;
import org.example.model.chat.Group;
import org.example.model.chat.PrivateChat;

public class ChatController {
    public static void MoveGroupToTop(Group chat){
        for (Player member : chat.getMembers()) {
            member.getChats().remove(chat);
            member.addChat(chat);
        }
    }
    public static void MovePrivateChatToTop(PrivateChat chat){
        for (Player member : chat.getMembers()) {
            member.getChats().remove(chat);
            member.addChat(chat);
        }
    }
}
