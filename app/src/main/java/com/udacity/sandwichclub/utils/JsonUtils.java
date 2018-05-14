package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        JSONObject sandwichDetails = new JSONObject(json);

        JSONObject name = sandwichDetails.getJSONObject("name");
        String mainName = name.getString("mainName");
        JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
        List<String> alsoKnownAsList = new ArrayList<>();
        for (int i=0; i<alsoKnownAs.length(); i++) {
            alsoKnownAsList.add(alsoKnownAs.getString(i));
        }

        String placeOfOrigin = sandwichDetails.getString("placeOfOrigin");

        String description = sandwichDetails.getString("description");

        String image = sandwichDetails.getString("image");

        JSONArray ingredients = sandwichDetails.getJSONArray("ingredients");
        String[] ingredientsStringArray = new String[ingredients.length()];
        List<String> ingredientsList = new ArrayList<>();
        for (int i=0; i<ingredients.length(); i++) {
            ingredientsList.add(ingredients.getString(i));
        }

        // Create a new Sandwich object to fill details and return at the end
        Sandwich sandwich =  new Sandwich(
                mainName,
                alsoKnownAsList,
                placeOfOrigin,
                description,
                image,
                ingredientsList);

        return sandwich;

    }
}
