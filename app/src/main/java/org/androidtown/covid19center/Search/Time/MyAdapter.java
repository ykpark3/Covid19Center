package org.androidtown.covid19center.Search.Time;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.androidtown.covid19center.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    private final Context context;
    private final View.OnClickListener onClickItem;
    private final ArrayList<String> itemList;

    public MyAdapter(Context context, ArrayList<String> itemList, View.OnClickListener onClickItem) {

        this.context = context;
        this.itemList = itemList;
        this.onClickItem = onClickItem;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //context와 parent.getContext() 는 같다.
        View view = LayoutInflater.from(context).inflate(R.layout.tiem_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d("0133", "123");
        String item = itemList.get(position);
        holder.button.setText(item);
        holder.button.setTag(item);
        holder.button.setOnClickListener(onClickItem);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public Button button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            button = itemView.findViewById(R.id.time_button_item);

        }

    }
}