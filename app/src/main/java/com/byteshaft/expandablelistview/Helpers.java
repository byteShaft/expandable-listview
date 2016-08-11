package com.byteshaft.expandablelistview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Helpers {

    protected static ArrayList<JSONObject> getChildren(JSONObject parentObject) {
        ArrayList<JSONObject> result = new ArrayList<>();
        JSONArray childSections = parentObject.optJSONArray("childSections");
        JSONArray items = parentObject.optJSONArray("items");
        if (childSections != null && childSections.length() > 0) {
            addDataToArrayList(result, childSections);
        } else if (items != null && items.length() > 0) {
            addDataToArrayList(result, items);
        }
        return result;
    }

    protected static ArrayList<JSONObject> populateTopLevelItems(JSONArray jsonArray) throws JSONException {
        ArrayList<JSONObject> result = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            result.add((JSONObject) jsonArray.opt(i));
        }
        return result;
    }

    private static void addDataToArrayList(ArrayList<JSONObject> resultContainer, JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                resultContainer.add(jsonArray.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
