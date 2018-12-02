package module.Recipes;

import java.util.Scanner;

public class BasicInfoManager {
    private int time;
    private double calorie;
    private String category;

    public BasicInfoManager() {
    }

    public BasicInfoManager(int time, double calorie, String category) {
        this.time = time;
        this.calorie = calorie;
        this.category = category;
    }


    // REQUIRES: the inputted calorie should be a positive number
    // MODIFIES: this
    // EFFECT: change the calories index of the recipe
    public void setCalorie(double calorie) {
        this.calorie = calorie;
    }

    // REQUIRES: the inputted integer should be positive and in the unit of minutes
    // MODIFIES: this
    // EFFECT: change the cooking time the recipe needs
    public void setTime(int t) {
        time = t;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // REQUIRES: the recipe's calorie index should have been set up
    // EFFECT: return the calorie of a recipe
    public double getCalorie() {
        return calorie;
    }

    // REQUIRES: the recipe's time is not null
    // EFFECT: return the recipe's time
    public int getTime() {
        return time;
    }

    public String getCategory() {
        return category;
    }

    // MODIFIES: Recipe
    // EFFECTS: give instruction to users to type in basic info and construct and return the new recipe
    public void collectInfo(String name1){
        Scanner str = new Scanner(System.in);
        System.out.println("How long does it take to make "+name1 +"?");
        int theTime = str.nextInt();
        System.out.println("How much calories does "+name1 +" contain ?");
        int theCalorie = str.nextInt();
        setTime(theTime);
        setCalorie(theCalorie);
        setCategory("User");

    }

    public void printInfo(){
        System.out.println("Time needed: " + time);
        System.out.println("Calorie: " + calorie);
        System.out.println("Category: " + category);
    }
}
