package com.example.lokigroupmanager.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.lokigroupmanager.Model.User;
import com.example.lokigroupmanager.R;

import java.util.List;

public class EditGroupAdapter extends ArrayAdapter<User> {
    private final Activity context;
    private final List<User> userList;

    public EditGroupAdapter(Activity context, List<User> objects) {
        super(context, R.layout.user_selection_list_item, objects);
        this.context = context;
        this.userList = objects;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.user_selection_list_item, null, false);

        TextView nameTextView = view.findViewById(R.id.nameViewSelection);

        User currentUser = userList.get(position);

        nameTextView.setText(currentUser.getFirstname()+" "+currentUser.getSurname());

        return view;
    }
}
