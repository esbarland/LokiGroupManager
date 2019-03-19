package com.example.lokigroupmanager.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lokigroupmanager.Model.User;
import com.example.lokigroupmanager.R;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    private final Activity context;
    private final List<User> userList;

    public UserAdapter(Activity context, List<User> objects) {
        super(context, R.layout.user_list_item, objects);
        this.context = context;
        this.userList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        // RISQUE DE CRASH
        View view = inflater.inflate(R.layout.user_list_item, null, true);

        TextView nameTextView = view.findViewById(R.id.nameView);
        TextView emailTextView = view.findViewById(R.id.emailView);

        User currentUser = userList.get(position);

        nameTextView.setText(currentUser.getFirstname()+" "+currentUser.getSurname());
        emailTextView.setText(currentUser.getEmail());

        return view;
    }
}
