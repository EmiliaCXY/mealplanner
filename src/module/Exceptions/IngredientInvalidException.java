package module.Exceptions;

public class IngredientInvalidException extends RecipeInfoInvalidException {

    public IngredientInvalidException(){
        System.out.println("The ingredients are invalid.");
    }

}
