package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    // This get a JSON object about a sandwich and return a Sandwich object
    public static Sandwich parseSandwichJson(String json) {

        Log.d("JsonUtils json:", json);

        if (json == null) {
            return null;
        }

        Sandwich sandwich = new Sandwich();

        try{
        JSONObject sandwichJSON = new JSONObject(json);

            // Get attributes from JSON object
            String mainName = sandwichJSON.getJSONObject("name").getString("mainName") ;
            String placeOfOrigin = sandwichJSON.getString("placeOfOrigin");
            String description = sandwichJSON.getString("description");
            String image = sandwichJSON.getString("image");
            List<String> alsoKnownAs = new ArrayList<>();
            List<String> ingredients = new ArrayList<>();

            // Get attributes - for array attributes
            JSONArray alsoKnownAsArray = sandwichJSON.getJSONObject("name").getJSONArray("alsoKnownAs");
            JSONArray ingredientsArray = sandwichJSON.getJSONArray("ingredients");
            for (int i=0; i<alsoKnownAsArray.length(); i++){alsoKnownAs.add(alsoKnownAsArray.getString(i));}
            for (int i=0; i<ingredientsArray.length(); i++){ingredients.add(ingredientsArray.getString(i));}

            // Set attributes of Sandwich object
            sandwich.setMainName(mainName);
            sandwich.setAlsoKnownAs(alsoKnownAs);
            sandwich.setPlaceOfOrigin(placeOfOrigin);
            sandwich.setDescription(description);
            sandwich.setImage(image);
            sandwich.setIngredients(ingredients);


        } catch (JSONException e){
            e.printStackTrace();
        }

        return sandwich;
    }
}
