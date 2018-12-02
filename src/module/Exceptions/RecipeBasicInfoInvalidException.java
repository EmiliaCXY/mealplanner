package module.Exceptions;

public class RecipeBasicInfoInvalidException extends RecipeInfoInvalidException {
    public RecipeBasicInfoInvalidException(){
        System.out.println("The basic information in the recipe is invalid.");
    }

}
