package module.Plans.PlanComponents;

import module.Exceptions.NotEnoughPlansException;
import module.Exceptions.TooHighCalorieException;
import module.Exceptions.TooLowCalorieException;
import module.Interfaces.Observer;
import module.Plans.Planner;
import module.Recipes.Recipe;
import module.Recipes.RecipeComponents.Ingredient;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import static java.time.DayOfWeek.MONDAY;


public class PlanPresenter implements Observer {

    private Planner planner;

    public PlanPresenter(Planner planner){
        this.planner = planner;
    }


    public void printPlans(){
        System.out.println("The following is your meal plan:");
        List<PlanForAMeal> plans = planner.getPlanner();
        for (PlanForAMeal pfm : plans) {
            pfm.printing();
        }
    }

    public void printGroceryList() throws IOException {
        System.out.println("The following is what you need to prepare:");
        List<PlanForAMeal> plans = planner.getPlanner();
        ArrayList<Ingredient> ingreWithNoDuplication = new ArrayList<>();
        for (PlanForAMeal pfm : plans) {
            Recipe r = pfm.getRecipesForAMeal();
            List<Ingredient> ingredientsNeeded = r.getIngres().getIngredients();
            for (Ingredient i : ingredientsNeeded) {
                if(!ingreWithNoDuplication.contains(i)){
                    ingreWithNoDuplication.add(i);
                    System.out.println(i.getMaterial());
                    r.match();
                    if(!r.getRelatedRecipes().isEmpty()){
                        System.out.println("\t\t"+i.getMaterial()+" can be home made.");
                    }
                }
            }
        }
    }

    public Double sumCalorie(DayOfWeek date){
        List<PlanForAMeal> plans = planner.getPlanner();
        double sum = 0.0;
        for (PlanForAMeal pfm : plans) {
            if (date.equals(pfm.getTheDate())) {
                Recipe meal = pfm.getRecipesForAMeal();
                double calorie = meal.getCalorie();
                sum += calorie;
            }
        }
        return sum;
    }

    public void evaluate() throws TooHighCalorieException, TooLowCalorieException {
        List<PlanForAMeal> plans = planner.getPlanner();
        if(plans.size()!=21){
            throw new NotEnoughPlansException();
        }
        DayOfWeek date = MONDAY;
        for(int i=1;i<=7;i++){
            if(sumCalorie(date)>4000){
                throw new TooHighCalorieException(date);
            }
            if(sumCalorie(date)<1500){
                throw new TooLowCalorieException(date);
            }
            date.plus(1);
        }
        System.out.println("Your plan is great!");
    }

    @Override
    public void updatePre() throws TooHighCalorieException, TooLowCalorieException {
        System.out.println("**********************************************");
        evaluate();
        printPlans();
    }

    public void updateFinal() throws IOException {
        System.out.println("**********************************************");
        System.out.println("The final version");
        printPlans();
        System.out.println("**********************************************");
        printGroceryList();
        System.out.println("**********************************************");
    }
}
