package org.example.control.menucontrollers.inGameControllers;


import org.example.control.Controller;
import org.example.control.enums.EntranceMenuMessages;
import org.example.control.enums.InGameMessages;
import org.example.control.menucontrollers.EntranceMenuController;
import org.example.model.DataBase;
import org.example.model.ingame.castle.Empire;
import org.example.model.ingame.map.resourses.Resource;
import org.example.model.ingame.map.resourses.Resources;

import java.util.ArrayList;
import java.util.Collections;

public class ShopMenuController {

    public static boolean isAmountValid(String amount) {
        if (EntranceMenuController.isDigit(amount))
            return false;
        return Integer.parseInt(amount) > 0;
    }

    public static String showPriceList() {
        StringBuilder result = new StringBuilder();
        for (Resources resources : Resources.values()) {
            result.append(resources.getType()).append(" :\nbuying price : ").append(resources.getBuyingPrice());
            result.append("\nselling price : ").append(resources.getSellingPrice());
            result.append("\n\tyour resource amount : ").append(DataBase.getCurrentEmpire()
                    .totalResources(new ArrayList<>(Collections.singleton(resources)))).append("\n");
        }
        return result.toString();
    }

    public static String buyItem(String item, String amount) {
        String res;
        if ((res = checkErrors(item, amount)) != null)
            return res;
        Resources resources;
        int Amount = Integer.parseInt(amount);
        if ((resources = Resources.getResourcesByName(item)) == null)
            return InGameMessages.INVALID_TYPE.toString();
        Resource consumingResource = new Resource(Resources.GOLD, Amount * resources.getBuyingPrice());
        Empire currentEmpire = DataBase.getCurrentEmpire();
        if (!currentEmpire.isThereNotResource(consumingResource)) {
            if (currentEmpire.addResource(new Resource(resources, Amount))) {
                currentEmpire.takeResource(consumingResource);
            }
            return EntranceMenuMessages.SUCCEED.toString();
        }

        return InGameMessages.NOT_ENOUGH_GOLD.toString();
    }

    public static String checkErrors(String item, String amount) {
        if (Controller.isFieldEmpty(item) || Controller.isFieldEmpty(amount))
            return EntranceMenuMessages.EMPTY_FIELD.toString();
        if (isAmountValid(amount))
            return InGameMessages.INVALID_AMOUNT.toString();
        return null;
    }

    public static String sellResource(String item, String amount) {
        String res;
        if ((res = checkErrors(item, amount)) != null)
            return res;
        Resources resources;
        int Amount = Integer.parseInt(amount);
        if ((resources = Resources.getResourcesByName(item)) == null)
            return InGameMessages.INVALID_TYPE.toString();
        if (DataBase.getCurrentEmpire().takeResource(new Resource(resources, Amount))) {
            DataBase.getCurrentEmpire().addResource(new Resource(Resources.GOLD,
                    Amount * resources.getSellingPrice()));
            return EntranceMenuMessages.SUCCEED.toString();
        }
        return InGameMessages.NOT_ENOUGH_RESOURCE_FOR_TRADE.toString();
    }
}