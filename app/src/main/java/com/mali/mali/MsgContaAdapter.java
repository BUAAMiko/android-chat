package com.mali.mali;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MsgContaAdapter extends ArrayAdapter<Contact> {
    private Activity activity;
    private List<Contact> taskList;

    public MsgContaAdapter(Activity a, List<Contact> taskList) {
        super(a, R.layout.contact_list, taskList);
        this.activity = a;
        this.taskList=taskList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ListTaskViewHolder holder = null;
        if (convertView == null) {
            holder = new ListTaskViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.contact_list, parent, false);
            holder.task_name = (TextView) convertView.findViewById(R.id.task_name);
            //holder.task_time = (TextView) convertView.findViewById(R.id.task_time);
            holder.share_task = (ImageView) convertView.findViewById(R.id.task_headImg);
            convertView.setTag(holder);
        } else {
            holder = (ListTaskViewHolder) convertView.getTag();
        }

        holder.task_name.setId(position);
        //holder.task_time.setId(position);
        holder.share_task.setId(position);

        final Contact task = taskList.get(position);

        try{
            holder.task_name.setText(task.getName());
            //holder.task_time.setText(task.getPhoneNum());

        }catch(Exception e) {}
        return convertView;
    }
}

class MsgContaViewHolder {
    TextView task_name;
    ImageView share_task;
}
