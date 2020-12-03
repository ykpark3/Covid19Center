package org.androidtown.covid19center.Search.Time;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidtown.covid19center.R;

import java.util.ArrayList;

public class SubTimeAdapter extends BaseAdapter {

    ArrayList<SubTimeItem> items = new ArrayList<SubTimeItem>();
    Context context;


    public SubTimeAdapter(Context context) {

        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SubTimeItemView view = null;


        if (convertView == null) {
            view = new SubTimeItemView(context);
            view.setBackgroundResource(R.drawable.background_gray_rectangle);
            view.setPadding(8, 9, 8, 8);
            view.setGravity(Gravity.CENTER);
        } else {

            view = (SubTimeItemView) convertView;
        }

        view.setName(items.get(position).subTime);

        return view;
    }

    public void addItem(SubTimeItem item) {
        items.add(item);
    }

    public void clearItem() {
        items.clear();
    }

}
