package com.example.fetch_android_take_home.viewModels;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fetch_android_take_home.MainActivity;
import com.example.fetch_android_take_home.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainRecyclerViewAdaptor extends RecyclerView.Adapter<MainRecyclerViewAdaptor.ViewHolder> {
    private MainViewModel mainViewModel;
    private ArrayList<HashMap<String, String>> NameList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Id;
        private TextView ListId;
        private TextView Name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.Id = (TextView) itemView.findViewById(R.id.main_view_id);
            this.ListId = (TextView) itemView.findViewById(R.id.main_view_list_id);
            this.Name = (TextView) itemView.findViewById(R.id.main_view_list_name);
        }

        public void bind(HashMap<String, String> NameItem) {
            this.Id.setText(NameItem.get("id"));
            this.ListId.setText(NameItem.get("listId"));
            this.Name.setText(NameItem.get("name"));
        }
    }
    // Initialize the dataset of the Adapter.
    public MainRecyclerViewAdaptor(MainActivity mainActivity, MainViewModel mainViewModel) {
        this.NameList = new ArrayList<>();
        this.mainViewModel = mainViewModel;
    }

    @NonNull
    @Override
    public MainRecyclerViewAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_item_activity_main, parent, false);
        return new MainRecyclerViewAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecyclerViewAdaptor.ViewHolder holder, int position) {
        HashMap<String,String> NameItem = this.NameList.get(position);
        holder.bind(NameItem);
    }

    @Override
    public int getItemCount() {
        return this.NameList.size();
    }

    public void updateNameList(ArrayList<HashMap<String, String>> NameList) {
        if(NameList != null) {
            this.NameList = NameList;
            for (HashMap<String, String> item : NameList) {
                System.out.println("update"+item.get("id"));
            }
            notifyDataSetChanged();
        }
    }
}