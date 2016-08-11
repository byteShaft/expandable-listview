package com.byteshaft.expandablelistview;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<JSONObject> mTopLevelItems;
    static ExpandableListView mExpandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTopLevelItems = new ArrayList<>();
        mExpandableListView = (ExpandableListView) findViewById(R.id.list_view_expandable);
        try {
            mTopLevelItems = Helpers.populateTopLevelItems(readJsonDataFromFile().getJSONArray("result"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RootAdapter adapter = new RootAdapter(getApplicationContext(), mTopLevelItems);
        mExpandableListView.setAdapter(adapter);
    }

    private JSONObject readJsonDataFromFile() throws JSONException {
        File storageDirectory = Environment.getExternalStorageDirectory();
        File jsonDataFile = new File(storageDirectory, "jsonData2");
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(jsonDataFile));
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line).append("\n");
            }
            br.close();
        }
        catch (IOException ignore) {

        }
        return new JSONObject(builder.toString().trim());
    }
}
