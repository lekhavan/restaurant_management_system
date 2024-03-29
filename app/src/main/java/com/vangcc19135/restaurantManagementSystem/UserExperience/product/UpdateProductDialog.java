package com.vangcc19135.restaurantManagementSystem.UserExperience.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.vangcc19135.RestaurantManagementSystem.R;
import com.vangcc19135.RestaurantManagementSystem.databinding.FragmentUpdateDrinkDialogBinding;
import com.vangcc19135.restaurantManagementSystem.UserExperience.database.DatabaseHandler;
import com.vangcc19135.restaurantManagementSystem.UserExperience.entity.Drink;

public class UpdateProductDialog extends DialogFragment {
    public FragmentUpdateDrinkDialogBinding binding;
    private DatabaseHandler databaseHandler;

    private IDrink iDrinkListener;
    Drink drink ;
    public static UpdateProductDialog newInstance(Drink drink, IDrink iDrinkListener) {
        UpdateProductDialog dialog = new UpdateProductDialog();
        dialog.drink = drink;
        dialog.iDrinkListener = iDrinkListener;
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_drink_dialog, container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow()
                .setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentUpdateDrinkDialogBinding.bind(view);
        databaseHandler = new DatabaseHandler(getContext(), DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);



        if (drink==null){
            binding.tvTitle.setText(getString(R.string.add_drink));
            binding.tvAdd.setVisibility(View.VISIBLE);
            binding.tvUpdate.setVisibility(View.GONE);
            binding.tvDelete.setVisibility(View.GONE);
        }else {
            binding.tipDrinkName.setText(drink.getDrinkName());
            binding.tipPrice.setText(String.valueOf((int)drink.getPrice()));
            binding.tvTitle.setText(getString(R.string.update_drink));
            binding.tvAdd.setVisibility(View.GONE);
            binding.tvUpdate.setVisibility(View.VISIBLE);
            binding.tvDelete.setVisibility(View.VISIBLE);
        }
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dismiss();
            }
        });

        binding.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if ( !binding.tipDrinkName.getText().toString().isEmpty()
                            &&!binding.tipPrice.getText().toString().isEmpty()){
                        databaseHandler.addDrink(new Drink( binding.tipDrinkName.getText().toString(),0,Double.parseDouble(binding.tipPrice.getText().toString())));
                        iDrinkListener.updateDrink();
                        dismiss();
                    }else {
                        Toast.makeText(getContext(), "Information not enough!!!", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                  e.printStackTrace();
                }
            }
        });

        binding.tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if ( !binding.tipDrinkName.getText().toString().isEmpty()
                            &&!binding.tipPrice.getText().toString().isEmpty()){
                        databaseHandler.updateDrinkById(drink.getId(), binding.tipDrinkName.getText().toString(),Double.parseDouble(binding.tipPrice.getText().toString()));
                        iDrinkListener.updateDrink();
                        dismiss();
                    }else {
                        Toast.makeText(getContext(), "Information not enough!!!", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        binding.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    databaseHandler.deleteDrinkById(drink.getId());
                    dismiss();
                    iDrinkListener.updateDrink();
                    Toast.makeText(getContext(), "Delete Completed!", Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
//        btnClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getDialog().dismiss();
//            }
//        });
    }

    public  interface  IDrink{
        void updateDrink();
    }
}