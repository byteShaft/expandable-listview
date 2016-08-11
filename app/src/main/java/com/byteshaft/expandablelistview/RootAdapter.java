package com.byteshaft.expandablelistview;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RootAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<JSONObject> mRootItemsList;
    private ArrayList<ArrayList<JSONObject>> mChildItemsList;
    private TextView mTextViewChild;

    public RootAdapter(Context context, ArrayList<JSONObject> rootItems) {
        mContext = context;
        mRootItemsList = rootItems;
        mChildItemsList = Helpers.getChildrenForObjects(mRootItemsList);
    }

    @Override
    public int getGroupCount() {
        return mRootItemsList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 0;
//        return Helpers.getChildren(mRootItemsList.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return mRootItemsList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return Helpers.getChildren(mRootItemsList.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    static class ViewHolder {
        TextView textView;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             view = inflater.inflate(R.layout.list_group, null);
            ViewHolder holder = new ViewHolder();
            holder.textView = (TextView) view.findViewById(R.id.lblListHeader);
            holder.textView.setTypeface(null, Typeface.BOLD);
            view.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        String headerTitle = getNameForItem((JSONObject) getGroup(i));
        holder.textView.setText(headerTitle);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
//        JSONObject child = (JSONObject) getChild(i, i1);
//        boolean isExpandable = child.optBoolean("populateExpandableList", false);
//        if (isExpandable) {
//
//        }
//        MainActivity.mExpandableListView.invalidate();
//        ExpandableListView level = new ExpandableListView(mContext.getApplicationContext());
//        level.setLayoutParams(new ListView.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//        level.setAdapter(new RootAdapter(mContext, mChildItemsList.get(i)));
        return view;
//        JSONObject group = mRootItemsList.get(i);
//        if (isGroupExpandable(group)) {
//            MainActivity.mExpandableListView.invalidate();
//            ExpandableListView level = new ExpandableListView(mContext.getApplicationContext());
//            level.setLayoutParams(new ListView.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//            level.setAdapter(new RootAdapter(mContext, mChildItemsList.get(i)));
//            return view;
//        } else {
//            if (mTextViewChild == null) {
//                mTextViewChild= new TextView(mContext);
//                mTextViewChild.setTextColor(Color.BLACK);
//                mTextViewChild.setPadding(180, 5, 5, 5);
//                mTextViewChild.setTextSize(20);
//            }
//            mTextViewChild.setText(mChildItemsList.get(i).get(i1).optString("name"));
//            mTextViewChild.setLayoutParams(new ListView.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.MATCH_PARENT));
//            return mTextViewChild;
//        }
    }

    private boolean isGroupExpandable(JSONObject groupObject) {
        return groupObject.optBoolean("populateExpandableList", false);
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private String getNameForItem(JSONObject item) {
        String output = null;
        try {
            output = item.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return output;
    }
}
