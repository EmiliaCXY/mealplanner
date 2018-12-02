package module.Exceptions;

import java.time.DayOfWeek;

public class TooHighCalorieException extends CalorieException {

    public TooHighCalorieException(DayOfWeek date){
        System.out.println("Your calorie input for "+date+" is over 4000 kCal");
    }
}
