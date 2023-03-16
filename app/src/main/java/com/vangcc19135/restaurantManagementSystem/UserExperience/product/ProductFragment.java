package com.vangcc19135.restaurantManagementSystem.UserExperience.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.vangcc19135.RestaurantManagementSystem.R;
import com.vangcc19135.RestaurantManagementSystem.databinding.FragmentDashboardBinding;
import com.vangcc19135.restaurantManagementSystem.UserExperience.database.DatabaseHandler;
import com.vangcc19135.restaurantManagementSystem.UserExperience.entity.Drink;

public class ProductFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private DatabaseHandler databaseHandler;
    private ProductAdapter productAdapter;


    public static ProductFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        binding = FragmentDashboardBinding.bind(view);

        databaseHandler = new DatabaseHandler(getContext(), DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);
        initRecyclerListDrink();

        initListener();

        return view;
    }

    private void initListener() {
        try{
            binding.flbAddDrink.setOnClickListener(v -> {
                UpdateProductDialog userInfoDialog = UpdateProductDialog.newInstance(null, new UpdateProductDialog.IDrink(){
                    @Override
                    public void updateDrink() {
                        initRecyclerListDrink();
                    }
                });
                userInfoDialog.show(getActivity().getSupportFragmentManager(), null);
            });
        }catch(Exception e){
          e.printStackTrace();
        }
    }

    private void initRecyclerListDrink() {
        try{
//            binding.rvData.setLayoutManager(new GridLayoutManager(getContext(), 2));

            productAdapter = new ProductAdapter(databaseHandler.getAllDrink(), new ProductAdapter.IDrinkListener() {


                @Override
                public void onSelectDrink(Drink drink) {
                    UpdateProductDialog userInfoDialog = UpdateProductDialog.newInstance(drink, new UpdateProductDialog.IDrink(){
                        @Override
                        public void updateDrink() {
                            initRecyclerListDrink();
                        }
                    });
                    userInfoDialog.show(getActivity().getSupportFragmentManager(), null);
                }
            });
            binding.rvData.setAdapter(productAdapter);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}