package com.byteshaft.expandablelistview;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MultiLevelExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<JSONObject> mRootItemsList;

    public MultiLevelExpandableListAdapter(Context context, ArrayList<JSONObject> rootItems) {
        mContext = context;
        mRootItemsList = rootItems;
    }

    @Override
    public int getGroupCount() {
        return mRootItemsList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return Helpers.getChildren(mRootItemsList.get(i)).size();
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
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_group, null);
            holder = new ViewHolder();
            holder.textView = (TextView) view.findViewById(R.id.lblListHeader);
            holder.textView.setTypeface(null, Typeface.BOLD);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        String headerTitle = getNameForItem((JSONObject) getGroup(i));
        holder.textView.setText(headerTitle);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        JSONObject item = (JSONObject) getChild(i, i1);
        boolean expandable = item.optBoolean("populateExpandableList", false);
        if (expandable) {
            CustomList listView = new CustomList(mContext);
            listView.setLayoutParams(new ListView.LayoutParams(
                    ListView.LayoutParams.MATCH_PARENT,
                    ListView.LayoutParams.WRAP_CONTENT));
            ArrayList<JSONObject> something = new ArrayList<>();
            something.add(item);
            listView.setAdapter(new MultiLevelExpandableListAdapter(mContext, something));
            return listView;
        } else {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);
            TextView textView = (TextView) view.findViewById(R.id.lblListItem);
            textView.setText(getNameForItem(item));
            return view;
        }
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

    private class CustomList extends ExpandableListView {

        public CustomList(Context context) {
            super(context);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
            int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(parentWidth, MeasureSpec.AT_MOST);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(parentHeight, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
