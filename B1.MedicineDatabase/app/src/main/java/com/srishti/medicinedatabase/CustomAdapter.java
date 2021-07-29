package com.srishti.medicinedatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public  class CustomAdapter extends BaseAdapter {
    private Context mContext;
    DatabaseHelper controldb;
    SQLiteDatabase db;
    private ArrayList<String> Id = new ArrayList<String>();
    private ArrayList<String> Name = new ArrayList<String>();
    private ArrayList<String> Date = new ArrayList<String>();
    private ArrayList<String> Time = new ArrayList<String>();
    public CustomAdapter(Context  context,ArrayList<String> Id,ArrayList<String> Name, ArrayList<String> Date,ArrayList<String> Time
    )
    {
        this.mContext = context;
        this.Id = Id;
        this.Name = Name;
        this.Date = Date;
        this.Time=Time;
    }
    @Override
    public int getCount() {
        return Id.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final    viewHolder holder;
        controldb =new DatabaseHelper(mContext);
        LayoutInflater layoutInflater;
        if (convertView == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout, null);
            holder = new viewHolder();
            holder.id = (TextView) convertView.findViewById(R.id.tvid);
            holder.mname = (TextView) convertView.findViewById(R.id.tvname);
            holder.mdate = (TextView) convertView.findViewById(R.id.tvdate);
            holder.mtime = (TextView) convertView.findViewById(R.id.tvtime);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder)convertView.getTag();
        }
        holder.id.setText(Id.get(position));
        holder.mname.setText(Name.get(position));
        holder.mdate.setText(Date.get(position));
        holder.mtime.setText(Time.get(position));
        return convertView;
    }
    public class viewHolder {
        TextView id;
        TextView mname;
        TextView mdate;
        TextView mtime;
    }


}
