package ui.ConsoleInteraction;

import module.Exceptions.CannotFindRecipeException;
import module.Interfaces.Storage;
import module.RecipeLibrary.RecipeLibrary;
import module.Plans.Planner;

import java.io.IOException;

public class Instruction {

    // EFFECT: give instructions of the app
    public void instruction(){
        System.out.println("Meal prep doesn't have to be troublesome xD");
        System.out.println("Enter the corresponding letter(s) if you want to:");
        System.out.println("[A] Add a new recipe");
        System.out.println("[B] Explore the recipe library");
        System.out.println("[C] Plan your meals in a week");
        System.out.println("[D] Food and drink reviews from The Report of The Week");
        System.out.println("Enter QUIT to quit\n");
    }

    // EFFECTS: print the sizes of recipeLibrary and planner
    public void printSystemInfo() throws IOException, CannotFindRecipeException {
        Storage recipeLibrary = new RecipeLibrary();
        recipeLibrary.load();
        Storage planner = new Planner();
        System.out.println("The number of recipes in your library is "+recipeLibrary.size());
        System.out.println("The number of plans in your planner is "+ planner.size());


    }

    public void changingRecipeInstru(){
        System.out.println("What do you want to change your recipe?");
        System.out.println("[a] change the name");
        System.out.println("[b] change the time");
        System.out.println("[c] change the value of calorie");
        System.out.println("[d] change the ingredients");
        System.out.println("[e] change the procedures");
        System.out.println("[f] add an ingredient or a step");
    }
}
