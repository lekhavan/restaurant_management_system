package com.vangcc19135.restaurantManagementSystem;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationBarView;
import com.vangcc19135.RestaurantManagementSystem.R;
import com.vangcc19135.RestaurantManagementSystem.databinding.ActivityMainBinding;
import com.vangcc19135.restaurantManagementSystem.UserExperience.Common;
import com.vangcc19135.restaurantManagementSystem.UserExperience.ViewPagerAdapter;
import com.vangcc19135.restaurantManagementSystem.UserExperience.database.DatabaseHandler;
import com.vangcc19135.restaurantManagementSystem.UserExperience.entity.Table;
import com.vangcc19135.restaurantManagementSystem.UserExperience.home.TableFragment;
import com.vangcc19135.restaurantManagementSystem.UserExperience.product.ProductFragment;
import com.vangcc19135.restaurantManagementSystem.UserExperience.setting.SettingFragment;
import com.vangcc19135.restaurantManagementSystem.UserExperience.statistic.StatisticFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DatabaseHandler databaseHandler;

    private static final int MENU_ITEM_ID_ONE =1;
    private static final int MENU_ITEM_ID_TWO =2;
    private static final int MENU_ITEM_ID_THREE =3;
    private static final int MENU_ITEM_ID_FOUR =4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseHandler = new DatabaseHandler(this, DatabaseHandler.DATABASE_NAME, null,DatabaseHandler.DATABASE_VERSION);
        if (!Common.getBoolean(MainActivity.this, Common.CREATE_DATABASE)) {
            addDatabase();
            Common.putBoolean(MainActivity.this, Common.CREATE_DATABASE, true);
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                binding.vpData.setCurrentItem(item.getItemId()-1);

                return true;
            }

        });
        Menu menu = binding.navView.getMenu();
        ArrayList<Fragment> listFragment = new ArrayList<>();
        listFragment.add(TableFragment.newInstance());
        menu.add(Menu.NONE, MENU_ITEM_ID_ONE, Menu.NONE, getString(R.string.title_book))
                .setIcon(R.drawable.ic_home_black_24dp);
        if (Common.getBoolean(MainActivity.this, Common.IS_MANAGER)) {

            listFragment.add(ProductFragment.newInstance());
            listFragment.add(StatisticFragment.newInstance());
            listFragment.add(SettingFragment.newInstance());

            menu.add(Menu.NONE, MENU_ITEM_ID_TWO, Menu.NONE, getString(R.string.title_menu))
                    .setIcon(R.drawable.ic_baseline_restaurant_menu_24);
            menu.add(Menu.NONE, MENU_ITEM_ID_THREE, Menu.NONE, getString(R.string.title_statistical))
                    .setIcon(R.drawable.ic_baseline_edit_note_24);

            menu.add(Menu.NONE, MENU_ITEM_ID_FOUR, Menu.NONE, getString(R.string.setting))
                    .setIcon(R.drawable.ic_baseline_settings_24);
        }else if (Common.getBoolean(MainActivity.this, Common.IS_EMPLOYEE)){
            listFragment.add(SettingFragment.newInstance());
            menu.add(Menu.NONE, MENU_ITEM_ID_FOUR, Menu.NONE, getString(R.string.setting))
                    .setIcon(R.drawable.ic_baseline_settings_24);
        }
        ViewPagerAdapter adapterViewPager = new ViewPagerAdapter(getSupportFragmentManager(), listFragment);
        binding.vpData.setOffscreenPageLimit(listFragment.size());
        binding.vpData.setAdapter(adapterViewPager);
    }


    private void addDatabase() {
        try{
            //10 bàn
            databaseHandler.addBook(new Table());
            databaseHandler.addBook(new Table());
            databaseHandler.addBook(new Table());
            databaseHandler.addBook(new Table());
            databaseHandler.addBook(new Table());
            databaseHandler.addBook(new Table());
            databaseHandler.addBook(new Table());
            databaseHandler.addBook(new Table());
            databaseHandler.addBook(new Table());
            databaseHandler.addBook(new Table());

            //databaseHandler.addDrink(new Drink("Chicken Pizza",0,40000));


        }catch(Exception e){
            e.printStackTrace();
        }
    }

}