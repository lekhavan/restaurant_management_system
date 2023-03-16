package com.vangcc19135.restaurantManagementSystem.UserExperience.selectproduct;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.vangcc19135.RestaurantManagementSystem.databinding.ActivitySelectDrinkBinding;
import com.vangcc19135.restaurantManagementSystem.UserExperience.database.DatabaseHandler;
import com.vangcc19135.restaurantManagementSystem.UserExperience.detailbook.ProductTableAdapter;
import com.vangcc19135.restaurantManagementSystem.UserExperience.entity.Drink;
import com.vangcc19135.restaurantManagementSystem.UserExperience.event.ListProductSelectEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class SelectProductActivity extends AppCompatActivity {

    private ActivitySelectDrinkBinding binding;
    private DatabaseHandler databaseHandler;

    private ProductTableAdapter drinkBookAdapter;

    private  ArrayList<Drink> listDrink = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectDrinkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHandler = new DatabaseHandler(SelectProductActivity.this, DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);

        initRecyclerListDrink();

        initListener();
    }

    private void initListener() {
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Drink> listDrinkSelect= new ArrayList<>();
                for (Drink drink : listDrink) {
                    if (drink.getAmount()>0){
                        listDrinkSelect.add(drink);
                    }
                }

                EventBus.getDefault().post(new ListProductSelectEvent(listDrinkSelect));
                finish();
                
            }
        });
    }

    private void initRecyclerListDrink() {
        try {
            listDrink = databaseHandler.getAllDrink();
            binding.rvDrink.setLayoutManager(new LinearLayoutManager(SelectProductActivity.this, LinearLayoutManager.VERTICAL, false));
            drinkBookAdapter = new ProductTableAdapter(listDrink);
            binding.rvDrink.setAdapter(drinkBookAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}