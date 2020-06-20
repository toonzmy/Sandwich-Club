package com.android.example.sandwichclub.utils;

import com.android.example.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String SAND_NAME= "name";
    private static final String SAND_MAINNAME= "mainName";
    private static final String SAND_ALSOKNOWNASNAME= "alsoKnownAs";
    private static final String SAND_PLACEOFORIGIN= "placeOfOrigin";
    private static final String SAND_DESCRIPTION = "description";
    private static final String SAND_IMAGE= "image";
    private static final String SAND_INGREDIENTS= "ingredients";

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        try {
            JSONObject jsonSandwich = new JSONObject(json);

            JSONObject sandwichName = jsonSandwich.getJSONObject(SAND_NAME);
            String mainName = sandwichName.getString(SAND_MAINNAME);


            List<String> alsoKnownAsList = jsonArrayList(sandwichName.getJSONArray(SAND_ALSOKNOWNASNAME));

            String placeOfOrigin = jsonSandwich.getString(SAND_PLACEOFORIGIN);

            String description = jsonSandwich.getString(SAND_DESCRIPTION);

            String image = jsonSandwich.getString(SAND_IMAGE);

            JSONArray ingredients = jsonSandwich.getJSONArray(SAND_INGREDIENTS);
            ArrayList<String> ingredientsList = new ArrayList<>();
            for (int i = 0; i < ingredients.length(); i++) {
                ingredientsList.add(ingredients.getString(i));
            }

            return new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> jsonArrayList(JSONArray jsonArray) throws JSONException {
        List<String> jsonList = new ArrayList<String>();

        if(jsonArray != null) {
            for(int i=0; i < jsonArray.length(); i++) {
                jsonList.add(jsonArray.getString(i));
            }
        }

        return jsonList;
    }
}
