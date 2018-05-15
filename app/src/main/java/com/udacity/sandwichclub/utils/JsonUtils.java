package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String NAME = "name";
    public static final String MAINNAME = "mainName";
    public static final String ALSOKNOWNAS = "alsoKnownAs";
    public static final String PLACEOFORIGIN = "placeOfOrigin";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";
    public static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        JSONObject sandwichDetails = new JSONObject(json);

        JSONObject name = sandwichDetails.getJSONObject(NAME);
        String mainName = name.getString(MAINNAME);
        JSONArray alsoKnownAs = name.getJSONArray(ALSOKNOWNAS);
        List<String> alsoKnownAsList = new ArrayList<>();
        for (int i=0; i<alsoKnownAs.length(); i++) {
            alsoKnownAsList.add(alsoKnownAs.getString(i));
        }

        String placeOfOrigin = sandwichDetails.getString(PLACEOFORIGIN);

        String description = sandwichDetails.getString(DESCRIPTION);

        String image = sandwichDetails.getString(IMAGE);

        JSONArray ingredients = sandwichDetails.getJSONArray(INGREDIENTS);
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
