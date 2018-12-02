package module.Exceptions;

import java.time.DayOfWeek;

public class TooLowCalorieException extends CalorieException {

    public TooLowCalorieException(DayOfWeek date){
        System.out.println("Your meal plan for " + date+ " is lower than 1500 kcal");
    }
}
