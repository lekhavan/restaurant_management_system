package com.vangcc19135.restaurantManagementSystem.UserExperience.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vangcc19135.RestaurantManagementSystem.R;
import com.vangcc19135.RestaurantManagementSystem.databinding.ItemBookBinding;
import com.vangcc19135.restaurantManagementSystem.UserExperience.entity.Table;

import java.util.ArrayList;

public class TableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Table> tableList;
    private IBookListener iBookListener;



    public TableAdapter(ArrayList<Table> userArrayList, IBookListener iStudentListener) {
        this.tableList = userArrayList;
        this.iBookListener = iStudentListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new RecyclerViewViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Table table = tableList.get(position);
        RecyclerViewViewHolder viewHolder = (RecyclerViewViewHolder) holder;

        if (table.getListDrink()==null|| table.getListDrink().isEmpty()){
            viewHolder.binding.ivBook.setImageResource(R.drawable.table_empty);

        }else {
            viewHolder.binding.ivBook.setImageResource(R.drawable.table_full);
        }
        viewHolder.binding.tvBook.setText("Table "+ table.getId());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iBookListener.onClickBook(table);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tableList.size();
    }

    public void updateUserList(final ArrayList<Table> userArrayList) {
        this.tableList.clear();
        this.tableList = userArrayList;
        notifyDataSetChanged();
    }

    class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
        ItemBookBinding binding;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemBookBinding.bind(itemView);


        }
    }

    public  interface IBookListener {
        void onClickBook(Table student);
    }
}
