package module.Plans.PlanComponents;

public enum Meal {
    BREAKFAST,LUNCH,DINNER;

    public Meal plus(Meal m){
        if(m.equals(BREAKFAST)){
            return LUNCH;
        }else if(m.equals(LUNCH)){
            return DINNER;
        }else if(m.equals(DINNER)){
            return BREAKFAST;
        }
        return null;
    }

    public String translateMeal(){
        if(this.equals(BREAKFAST)){
            return "BREAKFAST";
        } else if(this.equals(LUNCH)){
            return "LUNCH";
        } else if(this.equals(DINNER)){
            return "DINNER";
        } else
            return null;
    }

}
