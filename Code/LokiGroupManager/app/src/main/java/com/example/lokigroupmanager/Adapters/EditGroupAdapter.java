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
    private List<User> userList;

    class ViewHolder {
        CheckBox checkBox;
        TextView textView;
    }

    public EditGroupAdapter(Activity context, List<User> objects) {
        super(context, R.layout.user_selection_list_item, objects);
        this.context = context;
        this.userList = objects;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
<<<<<<< HEAD
        ViewHolder holder = null;
=======
        View view = inflater.inflate(R.layout.user_selection_list_item, null, false);
>>>>>>> a48f4fd7f7b3787fa566eac132fe8c2617552d96

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.user_selection_list_item, null, true);
            holder = new ViewHolder();
            holder.checkBox = convertView.findViewById(R.id.checkBoxSelection);
            holder.textView = convertView.findViewById(R.id.nameViewSelection);
            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder) convertView.getTag();

        User currentUser = userList.get(position);
        String fullname = currentUser.getFirstname()+" "+currentUser.getSurname();
        holder.textView.setText(fullname);

        if(currentUser.selected())
            holder.checkBox.setChecked(true);
        else
            holder.checkBox.setChecked(false);

        return convertView;
    }

    public void updateAdapter(List<User> userList){
        this.userList = userList;
        notifyDataSetChanged();
    }
}
