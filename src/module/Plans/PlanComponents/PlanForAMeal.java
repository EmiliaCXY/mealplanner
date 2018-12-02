package module.Plans.PlanComponents;

import module.Exceptions.CannotFindRecipeException;
import module.Exceptions.IngredientInvalidException;
import module.Exceptions.ProcedureInvalidException;
import module.Exceptions.RecipeBasicInfoInvalidException;
import module.Interfaces.Components;
import module.RecipeLibrary.RecipeLibrary;
import module.Recipes.Recipe;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.Scanner;


public class PlanForAMeal implements Components{
    private Recipe recipesForAMeal;
    private DayOfWeek theDate;
    private Meal theMeal;



    public PlanForAMeal(){

    }

    public PlanForAMeal(DayOfWeek theDate, Meal theMeal, Recipe plan){
        this.theDate = theDate;
        this.theMeal = theMeal;
        recipesForAMeal = plan;

    }

    // EFFECT: return the recipe
    public Recipe getRecipesForAMeal() {
        return recipesForAMeal;
    }

    public DayOfWeek getTheDate() {return theDate;}

    public Meal getTheMeal(){ return theMeal;}

    // MODIFIES: this
    // EFFECT: set the recipe for a meal
    public void setRecipesForAMeal(Recipe recipesForAMeal) {
        this.recipesForAMeal = recipesForAMeal;
    }

    // MODIFIES: this
    // EFFECT: specify the time of the meal
    public void setTheMeal(Meal theMeal) {
        this.theMeal = theMeal;
    }

    // MODIFIES: this
    // EFFECT: specify the date
    public void setTheDate(DayOfWeek theDate) {
        this.theDate = theDate;
    }

    // EFFECT: print out the plan's content
    @Override
    public void printing(){
        System.out.println(theDate + " "+ theMeal + ": " + recipesForAMeal.getName());
    }

    // EFFECT: if the meal is invalid, throw corresbonding exceptions
    @Override
    public void checkValidity() throws RecipeBasicInfoInvalidException, IngredientInvalidException, ProcedureInvalidException {
        recipesForAMeal.checkValidity();

    }

    public void changeRecipe() throws IOException {
        String recipeName = getRecipeWanted();
        RecipeLibrary rl = new RecipeLibrary();
        rl.load();
        try {
            Recipe r = rl.searching(recipeName);
            setRecipesForAMeal(r);
        } catch (CannotFindRecipeException e) {
            System.out.println("Sorry the recipe cannot be found, please enter the name of the recorded recipe.");
            getRecipeWanted();
        }

    }

    private String getRecipeWanted(){
        Scanner input = new Scanner(System.in);
        System.out.println("What recipe do you want to change for "+theDate +" "+ theMeal);
        return input.nextLine();
    }


}
