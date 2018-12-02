package module.Plans;

import module.Exceptions.TooHighCalorieException;
import module.Exceptions.TooLowCalorieException;
import module.Interfaces.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class PlannerSubject {

    private List<Observer> planObservers = new ArrayList<>();


    public void addPlanObserver(Observer pO){
        if(!planObservers.contains(pO)){
            planObservers.add(pO);
        }
    }

    public void notifyObjectPre() throws TooHighCalorieException, TooLowCalorieException {
        for(Observer pO : planObservers){
            pO.updatePre();
        }
    }

    public void notifyObjectFinal() throws IOException {
        for(Observer pO : planObservers){
            pO.updateFinal();
        }
    }
}
