package com.example.lokigroupmanager.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lokigroupmanager.Modele.Group;
import com.example.lokigroupmanager.Modele.User;
import com.example.lokigroupmanager.R;

import java.util.List;

public class GroupAdapter extends ArrayAdapter<Group> {
    private List<Group> listGroups;
    private Activity context;

    public GroupAdapter(Activity context, List<Group> listGroups) {
        super(context, R.layout.group_list_item, listGroups);
        this.context = context;
        this.listGroups = listGroups;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.group_list_item, null, true);

        TextView groupView = view.findViewById(R.id.nameGroupItemView);
        TextView descriptionGroup = view.findViewById(R.id.descriptionGroupItemView);

        Group group = listGroups.get(position);

        groupView.setText(group.getGroupName());
        descriptionGroup.setText(group.getDescription());

        return view;
    }
}
