package module.Interfaces;

import module.Exceptions.IngredientInvalidException;
import module.Exceptions.ProcedureInvalidException;
import module.Exceptions.RecipeBasicInfoInvalidException;

public interface Components {

    // EFFECTS: print out the content a component encodes
    void printing();

    // EFFECTS: return true if every field in the component is not null, false otherwise
    void checkValidity() throws IngredientInvalidException, ProcedureInvalidException, RecipeBasicInfoInvalidException;


}
