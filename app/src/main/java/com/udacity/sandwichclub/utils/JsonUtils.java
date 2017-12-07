package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {


    private JsonUtils() {}


    public static Sandwich parseSandwichJson(String sandwichJson) {

        if (TextUtils.isEmpty(sandwichJson)) {
            return null;
        }

        Sandwich sandwichObject = null;

        try {

            JSONObject baseJsonObject = new JSONObject(sandwichJson);

            JSONObject name = baseJsonObject.getJSONObject("name");

            String mainName = name.getString("mainName");

            List<String> alsoKnownAsList = new ArrayList<>();

            JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
            int countAlsoKnownAsArray = alsoKnownAsArray.length();

            for (int i = 0; i < countAlsoKnownAsArray; i++) {
                String otherName = alsoKnownAsArray.getString(i);
                alsoKnownAsList.add(otherName);
            }

            String placeOfOrigin = baseJsonObject.getString("placeOfOrigin");

            String description = baseJsonObject.getString("description");

            String image = baseJsonObject.getString("image");

            List<String> ingredientsList = new ArrayList<>();

            JSONArray ingredientsArray = baseJsonObject.getJSONArray("ingredients");
            int countIngredientsArray = ingredientsArray.length();
            for (int j = 0; j < countIngredientsArray; j++) {
                String ingredient = ingredientsArray.getString(j);
                ingredientsList.add(ingredient);
            }

            sandwichObject = new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);

        } catch (JSONException e) {
            Log.e("JsonUtils", "There was a problem parsing the Sandwich JSON Object", e);
        }

        return sandwichObject;
    }
}
