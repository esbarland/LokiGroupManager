package com.example.lokigroupmanager.Adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.lokigroupmanager.Modele.Group;

import java.util.ArrayList;
import java.util.List;

public class GroupAdapter extends ArrayAdapter {

    List<Group> listGroups = new ArrayList<>();

    public GroupAdapter(Context context, int resource, List<Group> listGroups) {
        super(context, resource);
        this.listGroups = listGroups;
    }
}
