package com.digiconvalley.tailordesk.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.digiconvalley.tailordesk.R;

import java.util.List;
import java.util.Map;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Map<String, List<String>> mStringListHashMap;
    private String[] mListHeaderGroup;

    public ExpandableListAdapter(Map<String, List<String>> mStringListHashMap) {
        this.mStringListHashMap = mStringListHashMap;
        mListHeaderGroup = mStringListHashMap.keySet().toArray(new String[0]);
    }


    @Override
    public int getGroupCount() {
        return mListHeaderGroup.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mStringListHashMap.get(mListHeaderGroup[groupPosition]).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mListHeaderGroup[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mStringListHashMap.get(mListHeaderGroup[groupPosition]).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition*childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
      if(convertView == null)
          convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_list_group,parent,false);
        TextView textView = convertView.findViewById(R.id.expandable_list_group_text);


        textView.setText(String.valueOf(getGroup(groupPosition)));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_list_view,parent,false);
        TextView textView = convertView.findViewById(R.id.expandable_item);
        textView.setText(String.valueOf(getChild(groupPosition,childPosition)));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
