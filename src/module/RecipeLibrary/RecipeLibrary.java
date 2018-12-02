package module.RecipeLibrary;


import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


import module.Exceptions.CannotFindRecipeException;
import module.Interfaces.Storage;
import module.Recipes.*;


public class RecipeLibrary implements Storage {

    private HashMap<String,ArrayList<Recipe>> recipeLibrary;
    private LoaderAndSaver loaderAndSaver;

    //MODIFIES: this
    //EFFECT: construct a library that users can put their recipes in
    public RecipeLibrary(){
        recipeLibrary = new HashMap<>();
        ArrayList<Recipe> def = new ArrayList<>();
        ArrayList<Recipe> user = new ArrayList<>();
        recipeLibrary.put("Default",def);
        recipeLibrary.put("User",user);
        loaderAndSaver = new LoaderAndSaver(recipeLibrary);
    }

    public ArrayList<Recipe> getDefRecipes(){
        return recipeLibrary.get("Default");
    }

    public ArrayList<Recipe> getUserRecipes(){ return recipeLibrary.get("User"); }


    // EFFECT: retrieve info from the inputfile-recipe
    public void load() throws IOException {
        List<String> recipes = Files.readAllLines(Paths.get("inputfile-recipe"));
        recipeLibrary = loaderAndSaver.load(recipes);
    }


    // MODIFIES: inputfile-recipe
    // EFFECTS: save the names of User recipes into inputfile-recipe
    public void save() throws IOException {
        List<String> lines = new ArrayList<>();
        PrintWriter writer1 = new PrintWriter("inputfile-recipe","UTF-8");
        loaderAndSaver.save(lines,writer1);
    }



    // MODIFIES: this
    // EFFECT: add a specified recipe
    public void storeRecipe(Recipe r) {
        if (! (getDefRecipes().contains(r)||getUserRecipes().contains(r))) {
            if (r.getCategory().equals("Default")){
                getDefRecipes().add(r);
            }else{
                getUserRecipes().add(r);
            }
        }
    }


    // MODIFIES: this
    // EFFECT: create a new recipe and give instructions to the user to provide information for the recipe
    @Override
    public void adding() throws IOException {
        UserRecipe r = new UserRecipe();
        r.buildUserRecipe();
        getUserRecipes().add(r);
        System.out.println("You have created a new recipe for "+ r.getName() +" :)");
        r.printing();
        confirm(r);


    }



    // MODIFIES: Recipe
    // EFFECTS: ask whether the user confirms the info and save the changes
    private void confirm(Recipe r) throws IOException {
        Scanner str = new Scanner(System.in);
        System.out.println("Confirm? Enter 'y' or 'n'");
        String choice = str.nextLine();
        if(choice.equalsIgnoreCase("y")){
            save();
        }else{
            r.changing();
            save();
            System.out.println("Your changes have been saved.");
        }
    }



     // EFFECT: print the recipes in the stored library
     public void exploreLibrary() throws IOException {
         System.out.println("Do you want to look into [a] the default library or [b] the user library?");
         Scanner input = new Scanner(System.in);
         String choice = input.nextLine();
         if(choice.equalsIgnoreCase("a")){
             print(getDefRecipes());
         } else if(choice.equalsIgnoreCase("b")){
             print(getUserRecipes());
         }
         System.out.println("What recipe do you want to view?");
         System.out.println("Or enter '1' if you want to add or remove a recipe");
         String theChoice = input.nextLine();
         if(theChoice.equalsIgnoreCase("1")){
             changing();
         }else{
             try {
                 Recipe target = searching(theChoice);
                 target.viewingRecipe();
             } catch (CannotFindRecipeException e) {
                 System.out.println("Sorry, the recipe cannot be found.");
             }
         }
     }

     private void print(List<Recipe> printMaterial){
         for(Recipe r: printMaterial){
             System.out.println(r.getName());
         }
     }

     // EFFECT: return the size of a library
    @Override
    public int size(){
        return getUserRecipes().size()+getDefRecipes().size();
    }

    // EFFECTS: return the list of recipes
    public HashMap<String, ArrayList<Recipe>> getRecipeLibrary() {
        return recipeLibrary;
    }


    // REQUIRES: the recipe library should not have recipes of the same name
    // EFFECT: return the recipe with the given name; if not found, return null
    @Override
    public Recipe searching(String name) throws CannotFindRecipeException{
        Recipe dR = new DefaultRecipe();
        dR.setName(name);
        ArrayList<Recipe> recipes = new ArrayList<>();
        recipes.addAll(getDefRecipes());
        recipes.addAll(getUserRecipes());
        for (Recipe r: recipes){
            if(r.equals(dR)){
                return r;
            }
        }
        throw new CannotFindRecipeException();
    }


    // MODIFIES: this
    // EFFECT: remove or add a recipe
    @Override
    public void changing() throws IOException {
        System.out.println("Do you want to:");
        System.out.println("[a] add one recipe");
        System.out.println("[b] remove one recipe");
        Scanner input = new Scanner(System.in);
        String choice = input.nextLine();
        if(choice.equalsIgnoreCase("a")){
            adding();
        } else if(choice.equalsIgnoreCase("b")){
            removeRecipe();

        }

    }

    private void removeRecipe() {
        System.out.println("Please type in the name of the recipe you want to remove");
        Scanner input = new Scanner(System.in);
        String nameNeedRemoval = input.nextLine();
        try {
            Recipe needRemoval = searching(nameNeedRemoval);
            if(needRemoval.getCategory().equals("Default")){
                getDefRecipes().remove(needRemoval);
            }else{
                getUserRecipes().remove(needRemoval);
            }
        } catch (CannotFindRecipeException e) {
            System.out.println("Sorry, the recipe cannot be found.");
            removeRecipe();
        }

    }


}
