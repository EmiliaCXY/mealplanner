package ui.ConsoleInteraction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Parser {

    public void parse(String jsonData) throws JSONException {
        JSONArray inputInfo = new JSONArray(jsonData);

        for (int index = 0; index < inputInfo.length(); index++) {
            JSONObject food = inputInfo.getJSONObject(index);
            parseInfo(food);
        }
    }

    private void parseInfo(JSONObject food) throws JSONException {
        String product = food.getString("product");
        int rating;
        try{
            rating = food.getInt("rating");
        }catch (Exception e){
            rating = 0;
        }
        System.out.println("Product: " + product);
        System.out.println("Rating: " + rating);
        System.out.println();
    }
}
