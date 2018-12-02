package module.RecipeLibrary;

import module.Exceptions.RecipeInfoInvalidException;
import module.Recipes.RecipeComponents.Ingredient;
import module.Recipes.RecipeComponents.Measurement;
import module.Recipes.RecipeComponents.Procedure;
import module.Recipes.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LoaderAndSaver {

    private HashMap<String,ArrayList<Recipe>> recipeLibrary;

    public LoaderAndSaver(HashMap<String,ArrayList<Recipe>> recipeLibrary){
        this.recipeLibrary = recipeLibrary;
    }

    // EFFECTS: return the list of strings containing the info of one recipe
    private ArrayList<String> extractRecipeInfo(List<String> lines){
        int num= numOfLines(lines);
        ArrayList<String> infoForOneRecipe = new ArrayList<>();
        for(int i=0;i<num;i++){
            String line = lines.get(i);
            infoForOneRecipe.add(line);
        }
        return infoForOneRecipe;
    }

    // EFFECTS: count and return the number of lines a recipe occupies in the inputfile
    private int numOfLines(List<String> input){
        int num =0;
        for(String line: input){
            if(!line.isEmpty()){
                num++;
            }else{
                return num+1;
            }

        }
        return num+1;
    }

    // MODIFIES: Ingredient
    // EFFECTS: set up the ingredient info of a recipe
    private Ingredient assembleIngre(ArrayList<String> ingreInfo){
        Ingredient i = new Ingredient();
        i.setMaterial(ingreInfo.get(0));
        i.setAmount(Double.parseDouble(ingreInfo.get(1)));
        i.setMeasurement(Measurement.valueOf(ingreInfo.get(2)));
        return i;
    }

    // MODIFIES: Procedure
    // EFFECTS: set up the procedure info of a recipe
    private Procedure assemblePro(ArrayList<String> proInfo){
        Procedure p = new Procedure();
        p.setStepNumber(Integer.parseInt(proInfo.get(0)));
        p.setDirection(proInfo.get(1));
        return p;
    }

    // MODIFIES: Recipe
    // EFFECTS: set up the basic info of a recipe
    private Recipe assembleRecipeBasic(Recipe r, ArrayList<String> basicInfo){
        r.setName(basicInfo.get(0));
        r.setTime(Integer.parseInt(basicInfo.get(1)));
        r.setCalorie(Double.parseDouble(basicInfo.get(2)));
        return r;
    }

    // MODIFIES: this, Recipe, Ingredient, Procedure
    // EFFECTS: set up a recipe
    private void assembleRecipe(ArrayList<String> info) {
        boolean isIngre = false;
        boolean isPro = false;
        ArrayList<Ingredient> ingres = new ArrayList<>();
        ArrayList<Procedure> pros = new ArrayList<>();
        Recipe r;
        for(String line: info){
            if (line.equals("!")){
                isIngre = true;
            }
            if(isIngre && !isPro && !(line.contains("!") || line.contains("?"))) {
                ArrayList<String> ingredient = split(line);
                Ingredient i = assembleIngre(ingredient);
                ingres.add(i);
            }
            if(line.equals("?")){
                isPro = true;
            }
            if(isPro &&!(line.contains("?")||line.contains("="))){
                ArrayList<String> procedure = split(line);
                Procedure p = assemblePro(procedure);
                pros.add(p);
            }
            if(line.equals("=")){
                isIngre = false;
                isPro = false;
            }
            if(!(line.contains("!")||line.contains("=")||isIngre || isPro ||line.isEmpty())){
                ArrayList<String> basicInfo = split(line);
                if(basicInfo.get(3).equals("Default")){
                    r = new DefaultRecipe();
                    r.setCategory(basicInfo.get(3));
                } else{
                    r = new UserRecipe();
                    r.setCategory("User");
                }
                r = assembleRecipeBasic(r,basicInfo);
                r.setIngredients(ingres);
                for(Ingredient i:ingres){
                    i.addOneRecipe(r);
                }
                r.setProcedures(pros);
                if(r.getCategory().equals("Default")){
                    recipeLibrary.get("Default").add(r);
                }else{
                    recipeLibrary.get("User").add(r);
                }
            }

        }

    }


    // EFFECT: retrieve info from the inputfile-recipe
    public HashMap<String,ArrayList<Recipe>> load(List<String> recipes) throws IOException {
        while(recipes.size()>0){
            ArrayList<String> infoOneR = extractRecipeInfo(recipes);
            int numOfElimination = numOfLines(infoOneR);
            assembleRecipe(infoOneR);
            for(int i =0;i<numOfElimination;i++){
                recipes.remove(0);
            }

        }
        return recipeLibrary;


    }

    private ArrayList<String> split(String line){
        String[] splits = line.split(":");
        return new ArrayList<>(Arrays.asList(splits));
    }

    // MODIFIES: inputfile-recipe
    // EFFECTS: save the names of User recipes into inputfile-recipe
    public void save(List<String> lines, PrintWriter writer1) {
        List<String> convertedDefault = convert(recipeLibrary.get("Default"),lines);
        List<String> convertedAll = convert(recipeLibrary.get("User"),convertedDefault);
        for(String line: convertedAll){
            writer1.println(line);
        }
        writer1.close();
    }

    private List<String> convert(List<Recipe> recipes, List<String> lines){
        for(Recipe r: recipes){
            try{
                r.checkValidity();
            } catch (RecipeInfoInvalidException e){
                r.changing();
            }
            String basicInfo = r.toString();
            String split = "!";
            String split2 = "?";
            String split3 = "=";
            String split4 = "";
            lines.add(split);
            IngredientListManager ingredients = r.getIngres();
            for(Ingredient i : ingredients.getIngredients()){
                lines.add(i.toString());
            }
            lines.add(split2);
            ProcedureListManager procedures = r.getPros();
            for(Procedure p: procedures.getProcedures()){
                lines.add(p.toString());
            }
            lines.add(split3);
            lines.add(basicInfo);
            lines.add(split4);
        }
        return lines;

    }
}
