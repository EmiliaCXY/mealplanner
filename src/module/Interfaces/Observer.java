package module.Interfaces;

import module.Exceptions.TooHighCalorieException;
import module.Exceptions.TooLowCalorieException;
import module.Plans.Planner;
import module.Plans.PlannerSubject;

import java.io.IOException;

public interface Observer {

    void updatePre() throws TooHighCalorieException, TooLowCalorieException;

    void updateFinal() throws IOException;
}
