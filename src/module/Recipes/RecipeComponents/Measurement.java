package module.Recipes.RecipeComponents;

public enum Measurement {
    GRAM,CUP,OZ,TEASPOON,TABLESPOON,LITER,POUND,SLICE,NULL,SOME;

    public static String[] stringsMeasurement(){
        String[] strings = new String[]{"Gram","Cup","OZ", "Pound","Teaspoon","Tablespoon","Liter", "some","n/a"};
        return strings;
    }
}
