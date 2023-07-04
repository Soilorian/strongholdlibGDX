package org.example.control.menucontrollers.inGameControllers;

import org.example.control.Controller;
import org.example.control.enums.EntranceMenuMessages;
import org.example.control.enums.InGameMessages;
import org.example.control.menucontrollers.EntranceMenuController;
import org.example.model.DataBase;

public class TaxMenuController {

    public static boolean isTaxRateValid(int rate) {
        return rate >= -3 && rate <= 8;
    }

    public static String setTax(String rate) {
        if (Controller.isFieldEmpty(rate))
            return EntranceMenuMessages.EMPTY_FIELD.toString();
        if (!EntranceMenuController.isDigit(rate))
            return InGameMessages.TAX_DIGIT.toString();
        int Rate = Integer.parseInt(rate);
        if (!isTaxRateValid(Rate))
            return InGameMessages.INVALID_TAX.toString();
        DataBase.getCurrentEmpire().setTaxRate(Rate);
        return EntranceMenuMessages.SUCCEED.toString();
    }
}
