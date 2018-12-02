package module.Plans;


import module.Exceptions.*;
import module.Interfaces.Storage;
import module.Plans.PlanComponents.Meal;
import module.Plans.PlanComponents.PlanForAMeal;
import module.Plans.PlanComponents.PlanPresenter;
import module.RecipeLibrary.RecipeLibrary;
import module.Recipes.Recipe;
import ui.ConsoleInteraction.Instruction;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.util.*;

import static java.time.DayOfWeek.*;
import static module.Plans.PlanComponents.Meal.*;


public class Planner extends PlannerSubject implements Storage{

    private Scanner plan = new Scanner(System.in);

    private List<PlanForAMeal> planner;
    private RecipeLibrary recipeLibrary;
    private PlanPresenter pp;

    public Planner(List<PlanForAMeal> planner){
        this.planner = planner;
        recipeLibrary = new RecipeLibrary();
        try {
            recipeLibrary.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Planner() {
        planner = new ArrayList<>();
        recipeLibrary = new RecipeLibrary();
        try {
            recipeLibrary.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pp = new PlanPresenter(this);
        addPlanObserver(pp);
    }

    public List<PlanForAMeal> getPlanner() {
        return planner;
    }

    // REQUIRES: the user input should match the recipes in library
    // MODIFIES: this
    // EFFECT: put the recipes of each meal into the planner according to the user instruction
    @Override
    public void adding() {
        DayOfWeek theDate = MONDAY;
        Meal theMeal = BREAKFAST;
        for(int date=1; date<=7; date++) {
            for (int meal = 1; meal <= 3; meal++) {
                System.out.println("What do you want to eat on " + theDate +" "+ theMeal);
                PlanForAMeal plan1 = addOneMeal(theDate,theMeal);
                planner.add(plan1);
                theMeal = theMeal.plus(theMeal);
            }
            theDate = theDate.plus(1);
        }
    }

    private PlanForAMeal addOneMeal(DayOfWeek theDate, Meal theMeal) {
        PlanForAMeal plan1 = new PlanForAMeal();
        plan1.setTheDate(theDate);
        String choice = plan.nextLine();
        try {
            Recipe chosenRecipe = recipeLibrary.searching(choice);
            plan1.setRecipesForAMeal(chosenRecipe);
            plan1.setTheMeal(theMeal);
            return plan1;
        } catch(CannotFindRecipeException e){
            System.out.println("Sorry, the recipe cannot be found.");
        }
        return plan1;
    }

    public void constructingMealPlan() throws IOException{
        adding();
        try{
            notifyObjectPre();
        } catch (CalorieException e) {
            System.out.println("Calorie seems to be an issue here...");
            System.out.println("Do you want to change the plan?");
            String answer = plan.nextLine();
            if (answer.equalsIgnoreCase("yes")) {
                changing();
            }
        } catch (NotEnoughPlansException e){
            pp.printPlans();
            changing();
        }
        finally {
            confirm();
            Instruction in = new Instruction();
            in.instruction();
        }
    }
    // MODIFIES: this
    // EFFECT: add a day plan to planner
    public void storing(PlanForAMeal pfm) {
        planner.add(pfm);
    }

    private void confirm() throws IOException {
        System.out.println("Is there anything you want to change? Y or N");
        String answer = plan.nextLine();
        if(answer.equalsIgnoreCase("Y")){
            changing();
        }
        notifyObjectFinal();
        save();
        System.out.println("Your plan has been saved!");
    }


    // EFFECTS: get the info in inputfile-planner and store them as PlanForAMeal
    public void load() throws IOException, CannotFindRecipeException {
        List<String> plans = Files.readAllLines(Paths.get("inputfile-planner"));
        for (String line : plans){
            ArrayList<String> partsOfLine = split(line);
            DayOfWeek d = DayOfWeek.valueOf(partsOfLine.get(0));
            Meal m = Meal.valueOf(partsOfLine.get(1));
            Recipe r = recipeLibrary.searching(partsOfLine.get(2));
            PlanForAMeal plan = new PlanForAMeal(d,m,r);
            planner.add(plan);
        }

    }

    // MODIFIES: inputfile-planner
    // EFFECTS: store info into the inputfile-planner
    public void save() throws IOException {
        List<String> lines = new ArrayList<>();
        PrintWriter writer = new PrintWriter("inputfile-planner","UTF-8");
        for(PlanForAMeal pfm:planner){
            String s0 = pfm.getTheDate().name();
            String s1 = pfm.getTheMeal().translateMeal();
            String s2 = pfm.getRecipesForAMeal().getName();
            String s = s0 + ":" + s1 + ":" +s2 + ":";
            lines.add(s);
        }
        for(String s:lines){
            writer.println(s);
        }
        writer.close();

    }

    private ArrayList<String> split(String line){
        String[] splits = line.split(":");
        return new ArrayList<>(Arrays.asList(splits));
    }

    // REQUIRES: the planner should not be null and contains the plans that the user want to change
    // MODIFIES: this
    // EFFECT: provide instruction to change meal plan and store the changes
    @Override
     public void changing() throws IOException {
        pp.printPlans();
        DayOfWeek date = DayOfWeek.valueOf(collectingInfoForChanging("date"));
        Meal meal = Meal.valueOf(collectingInfoForChanging("meal"));
        System.out.println("Do you want to change [a] your meals or [b] add meals?");
        String choice = plan.nextLine();
        if (choice.equalsIgnoreCase("a")) {
            try {
                PlanForAMeal pfm = searching(date,meal);
                pfm.changeRecipe();
            } catch (CannotFindRecipeException e) {
                e.printStackTrace();
            }
        }
        else if (choice.equalsIgnoreCase("b")) {
            addOneMeal(date,meal);
        }
        try {
            notifyObjectPre();
        } catch (CalorieException e) {
            changing();
        }
    }

    private String collectingInfoForChanging(String infoOfInterest){
        System.out.println("What is the "+ infoOfInterest+ " of the meal you want to change?");
        try{
            return plan.nextLine();
        }catch (IllegalFormatException e){
            System.out.println("Your input is illegal.");
            collectingInfoForChanging(infoOfInterest);
        }
        return "";
    }


    public PlanForAMeal searching(String recipeName) throws CannotFindRecipeException {
        for(PlanForAMeal pfm:planner){
            String recipe = pfm.getRecipesForAMeal().getName();
            if(recipe.equals(recipeName)){
                return pfm;
            }
        }
        throw new CannotFindRecipeException();

    }



    // EFFECT: search for and return a PlanForAMeal based on its name
    private PlanForAMeal searching(DayOfWeek d, Meal m) throws CannotFindRecipeException {
        for (PlanForAMeal pfm : planner) {
            if((pfm.getTheDate().equals(d))&&(pfm.getTheMeal().equals(m))){
                return pfm;
            }
        }
        throw new CannotFindRecipeException();

    }

    // EFFECT: return the number of daily meal plans of the planner
    public int size(){
        return planner.size();
    }



}