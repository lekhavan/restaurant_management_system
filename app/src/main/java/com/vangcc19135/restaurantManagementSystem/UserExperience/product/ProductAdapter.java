package com.vangcc19135.restaurantManagementSystem.UserExperience.product;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vangcc19135.RestaurantManagementSystem.R;
import com.vangcc19135.RestaurantManagementSystem.databinding.ItemDrinkBinding;
import com.vangcc19135.restaurantManagementSystem.UserExperience.entity.Drink;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Drink> drinkList;

    private IDrinkListener listener;


    public ProductAdapter(ArrayList<Drink> drinks, IDrinkListener listener) {
        this.drinkList = drinks;
        this.listener = listener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drink, parent, false);
        return new RecyclerViewViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        try{
            Drink drink = drinkList.get(position);
            RecyclerViewViewHolder viewHolder = (RecyclerViewViewHolder) holder;

            viewHolder.binding.tvDrinkCode.setText("ID: "+drink.getId());
            viewHolder.binding.tvDrinkName.setText(drink.getDrinkName());
            NumberFormat formatter = new DecimalFormat("#,###");
            viewHolder.binding.tvPrice.setText(formatter.format(drink.getPrice())+"VND");

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onSelectDrink(drink);
                }
            });
        }catch(Exception e){
          e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        if (drinkList !=null) {
            return drinkList.size();
        }else {
            return 0;
        }
    }

    public void updateDrinkList(final ArrayList<Drink> userArrayList) {
        this.drinkList.clear();
        this.drinkList = userArrayList;
        notifyDataSetChanged();
    }

    class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
        ItemDrinkBinding binding;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemDrinkBinding.bind(itemView);


        }
    }

    public interface IDrinkListener{
        void onSelectDrink(Drink drink);
    }


}
