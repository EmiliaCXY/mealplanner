package ui.ConsoleInteraction;



import java.io.IOException;
import java.util.Scanner;

import module.Exceptions.CannotFindRecipeException;
import module.RecipeLibrary.RecipeLibrary;
import module.Plans.Planner;


public class Main {


    public static void main(String[] args) throws IOException, CannotFindRecipeException {
        Scanner choice = new Scanner(System.in);
        RecipeLibrary recipeLibrary = new RecipeLibrary();
        Planner plan = new Planner();
        Instruction in = new Instruction();

        recipeLibrary.load();
        in.instruction();
        in.printSystemInfo();

        while (true) {
            String input = choice.nextLine();

            if (input.equalsIgnoreCase("A")) {
                System.out.println("You selected:" + input);
                recipeLibrary.adding();

            } else if (input.equalsIgnoreCase("B")) {
                System.out.println("You selected:" + input);
                recipeLibrary.exploreLibrary();

            } else if (input.equalsIgnoreCase("C")) {
                System.out.println("You selected:" + input);
                plan.constructingMealPlan();

            } else if(input.equalsIgnoreCase("D")){
                System.out.println("The available ratings are as follows: ");
                ReadWebPageEx.main(null);

            } else if (input.equalsIgnoreCase("QUIT")){
                System.out.println("You selected:" + input);
                recipeLibrary.save();
                break;

            }

        }
    }
}