package org.example.control.menucontrollers.inGameControllers;

import org.example.control.Controller;
import org.example.control.enums.EntranceMenuMessages;
import org.example.control.enums.InGameMessages;
import org.example.control.menucontrollers.EntranceMenuController;
import org.example.model.DataBase;
import org.example.model.ingame.castle.Empire;
import org.example.model.ingame.castle.Trade;
import org.example.model.ingame.map.resourses.Resource;
import org.example.model.ingame.map.resourses.Resources;

import java.util.ArrayList;

public class TradeMenuController {

    private static final ArrayList<Trade> trades = new ArrayList<>();
    private static final ArrayList<Trade> tradeHistory = new ArrayList<>();

    private static Trade getTradeWithId(int id) {
        for (Trade trade : trades)
            if (trade.getId() == id)
                return trade;
        return null;
    }

    public static String sendNewTrade(String resource,
                                      String price,
                                      String amount,
                                      String message) {
        if (Controller.isFieldEmpty(resource) ||
                Controller.isFieldEmpty(price) ||
                Controller.isFieldEmpty(amount))
            return EntranceMenuMessages.EMPTY_FIELD.toString();
        Resources resources;
        if ((resources = Resources.getResourcesByName(resource)) == null)
            return InGameMessages.INVALID_RESOURCE.toString();
        if (!EntranceMenuController.isDigit(price) || !EntranceMenuController.isDigit(amount))
            return InGameMessages.NOT_DIGIT.toString();
        int price2 = Integer.parseInt(price);
        int amount2 = Integer.parseInt(amount);
        if (price2 < 0 || amount2 <= 0)
            return InGameMessages.INVALID_NUMBER.toString();
        if (!DataBase.getCurrentEmpire().takeResource(new Resource(Resources.GOLD, amount2 * price2)))
            return InGameMessages.NOT_ENOUGH_GOLD_FOR_TRADE.toString();
        Trade trade = new Trade(DataBase.getCurrentEmpire(), new Resource(resources, amount2), price2, message);
        trades.add(trade);
        return EntranceMenuMessages.SUCCEED.toString();
    }

    public static String tradeAccept(String id, String message) {
        Trade trade;
        if (Controller.isFieldEmpty(id) || Controller.isFieldEmpty(message))
            return EntranceMenuMessages.EMPTY_FIELD.toString();
        if (!EntranceMenuController.isDigit(id))
            return InGameMessages.ID_DIGIT.toString();
        int Id = Integer.parseInt(id);
        if ((trade = getTradeWithId(Id)) == null)
            return InGameMessages.INVALID_ID.toString();
        Empire empire = DataBase.getCurrentEmpire();
        if (empire.takeResource(trade.getResource())) {
            trade.getSender().addResource(trade.getResource());

            empire.addResource(new Resource(Resources.GOLD, trade.getResource().getAmount() * trade.getPrice()));
            trade.setAcceptor(empire);
            tradeHistory.add(trade);
            trades.remove(trade);
            return EntranceMenuMessages.SUCCEED.toString();
        }
        return InGameMessages.NOT_ENOUGH_RESOURCE_FOR_TRADE.toString();
    }

    public static String showTradeList() {
        StringBuilder result = new StringBuilder();
        for (Trade trade : trades)
            result.append(trade.getId()).append(")\nSender: ").append(trade.getSender().getOwner().getNickname())
                    .append("\nResource: ").append(trade.getResource().getResourceName()).append("\nAmount: ")
                    .append(trade.getResource().getAmount()).append("\nPrice: ").append(trade.getPrice())
                    .append("\nTotal price: ").append(trade.getResource().getAmount() * trade.getPrice()).append('\n');
        return (result.length() == 0) ? InGameMessages.TRADE_LIST_EMPTY.toString() : result.toString();
    }

    public static String showTradeHistory() {
        StringBuilder result = new StringBuilder();
        for (Trade trade : tradeHistory)
            result.append(trade.getId()).append(")\nSender: ").append(trade.getSender().getOwner().getNickname())
                    .append("\nAcceptor: ").append(trade.getAcceptor().getOwner().getNickname()).append("\nResource: ")
                    .append(trade.getResource().getResourceName()).append("\nAmount: ").append(trade.getResource().getAmount())
                    .append("\nPrice: ").append(trade.getPrice()).append("\nTotal price: ")
                    .append(trade.getResource().getAmount() * trade.getPrice());
        return (result.length() == 0) ? InGameMessages.TRADE_HISTORY_EMPTY.toString() : result.toString();
    }
}