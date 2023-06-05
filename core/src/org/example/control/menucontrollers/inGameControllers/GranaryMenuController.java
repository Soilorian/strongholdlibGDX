package org.example.control.menucontrollers.inGameControllers;

import org.example.control.Controller;
import org.example.control.enums.EntranceMenuMessages;
import org.example.control.enums.InGameMessages;
import org.example.control.menucontrollers.EntranceMenuController;
import org.example.model.DataBase;
import org.example.model.ingame.map.resourses.Resource;

public class GranaryMenuController {

    public static boolean isFoodRateValid(int foodRate) {
        return foodRate >= -2 && foodRate <= 2;
    }

    public static String changeFoodRate(String foodRate) {
        if (Controller.isFieldEmpty(foodRate))
            return EntranceMenuMessages.EMPTY_FIELD.toString();
        if (EntranceMenuController.isDigit(foodRate))
            return InGameMessages.IS_FOOD_RATE_DIGIT.toString();
        int FoodRate = Integer.parseInt(foodRate);
        if (!isFoodRateValid(FoodRate))
            return InGameMessages.IS_FOOD_RATE_VALID.toString();
        DataBase.getCurrentEmpire().setFoodRate(Integer.parseInt(foodRate));
        return EntranceMenuMessages.SUCCEED.toString();
    }


    public static String showFoodList() {
        int meat = 0, bread = 0, cheese = 0, apple = 0;
        for (Resource resource : DataBase.getCurrentEmpire().getResources()) {
            switch (resource.getResourceName().getType()) {
                case "meat":
                    meat = resource.getAmount();
                case "bread":
                    bread = resource.getAmount();
                case "cheese":
                    cheese = resource.getAmount();
                case "apple":
                    apple = resource.getAmount();
            }
        }
        return ("meat: " + meat + "\nbread: " + bread + "\ncheese: " + cheese + "\napple: " + apple);
    }

    public static String showFoodRate() {
//        return "Food Rate:"+String.valueOf(DataBase.getCurrentEmpire().getFoodRate().getFoodRate());
        return ("Food Rate:"+String.valueOf(2));
    }
}
