package com.vangcc19135.restaurantManagementSystem.UserExperience.detailbook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vangcc19135.RestaurantManagementSystem.databinding.FragmentDetailTableBinding;
import com.vangcc19135.restaurantManagementSystem.UserExperience.Common;
import com.vangcc19135.restaurantManagementSystem.UserExperience.database.DatabaseHandler;
import com.vangcc19135.restaurantManagementSystem.UserExperience.entity.Bill;
import com.vangcc19135.restaurantManagementSystem.UserExperience.entity.Drink;
import com.vangcc19135.restaurantManagementSystem.UserExperience.entity.Table;
import com.vangcc19135.restaurantManagementSystem.UserExperience.event.ListProductSelectEvent;
import com.vangcc19135.restaurantManagementSystem.UserExperience.event.UpdateTableEvent;
import com.vangcc19135.restaurantManagementSystem.UserExperience.note.NoteActivity;
import com.vangcc19135.restaurantManagementSystem.UserExperience.order.OrderActivity;
import com.vangcc19135.restaurantManagementSystem.UserExperience.selectproduct.SelectProductActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class DetailTableActivity extends AppCompatActivity {

    public static String DetailBook = "DetailBook";
    private FragmentDetailTableBinding binding;
    private DatabaseHandler databaseHandler;

    private ProductTableAdapter productTableAdapter;

    private Table table;

    private Table note;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        binding = FragmentDetailTableBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHandler = new DatabaseHandler(DetailTableActivity.this, DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);

        table = getIntent().getParcelableExtra(DetailBook);
        if (table != null){
            binding.tvBook.setText("Table " + table.getId());
        }else {
            // xử lý nếu thực thể Table null
            Toast.makeText(this, "Table is null", Toast.LENGTH_SHORT).show();
            finish(); // kết thúc Activity
        }
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

        binding.flbAddDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DetailTableActivity.this, SelectProductActivity.class);
                startActivity(intent);
            }
        });
        binding.flbAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailTableActivity.this, NoteActivity.class);
                startActivity(intent);
            }
        });

        binding.tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ArrayList<Drink> listDrink = new ArrayList<>();
                    for (Drink drink : productTableAdapter.getDrinkList()) {
                        if (drink.getAmount() > 0) {
                            listDrink.add(drink);
                        }
                    }
                    if (listDrink.size() > 0) {
                        databaseHandler.updateBookById(table.getId(), new Gson().toJson(listDrink));
                    } else {
                        databaseHandler.updateBookById(table.getId(), "");

                    }

                    EventBus.getDefault().post(new UpdateTableEvent());
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        binding.tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double totalMoney = 0;
                    ArrayList<Drink> listDrink = new ArrayList<>();
                    for (Drink drink : productTableAdapter.getDrinkList()) {
                        if (drink.getAmount() > 0) {
                            listDrink.add(drink);
                            totalMoney += drink.getAmount() * drink.getPrice();
                        }
                    }

                    Bill bill = new Bill();
                    bill.setListDrink(new Gson().toJson(listDrink));
                    bill.setTotalMoney(totalMoney);
                    Calendar calendar = Calendar.getInstance();
                    bill.setDateBill(Common.formatDateToString(calendar.getTime()));
                    //lưu hóa đơn
                    databaseHandler.addBill(bill);

                    //xóa đồ uống của bàn đó
                    databaseHandler.updateBookById(table.getId(), "");
                    EventBus.getDefault().post(new UpdateTableEvent());
                    finish();

                    Log.d("size", "" + binding.rlData.getWidth() + " " + binding.rlData.getWidth());

                    Intent intent = new Intent(DetailTableActivity.this, OrderActivity.class);
                    startActivity(intent);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        if (table != null) {
            String note = table.getNote();
            // rest of the code that uses the note variable
        } else {
            // handle the case where the object reference is null
        }

    }


    @Subscribe
    public void OnEvent(ListProductSelectEvent event) {
        ArrayList<Drink> listDrink = new ArrayList<>();
        if (table.getListDrink() != null) {
            Type listType = new TypeToken<ArrayList<Drink>>() {
            }.getType();
            listDrink = new Gson().fromJson(table.getListDrink(), listType);
            if (listDrink == null) {
                listDrink = new ArrayList<>();
            }

        }
        listDrink.addAll(event.getList());
        checkListDrink(listDrink);
        table.setListDrink(new Gson().toJson(listDrink));
        productTableAdapter.updateDrinkList(listDrink);

    }

    private void initRecyclerListDrink() {
        try {
            binding.rvDrink.setLayoutManager(new LinearLayoutManager(DetailTableActivity.this, LinearLayoutManager.VERTICAL, false));

            ArrayList<Drink> listDrink = new ArrayList<>();
            if (table.getListDrink() != null) {
                Type listType = new TypeToken<ArrayList<Drink>>() {
                }.getType();
                listDrink = new Gson().fromJson(table.getListDrink(), listType);
                if (listDrink == null) {
                    listDrink = new ArrayList<>();
                }

            }
            productTableAdapter = new ProductTableAdapter(listDrink);
            binding.rvDrink.setAdapter(productTableAdapter);

            checkListDrink(listDrink);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkListDrink(ArrayList<Drink> listDrink) {
        if (listDrink.size() > 0) {
            binding.rvDrink.setVisibility(View.VISIBLE);
            binding.lnNoData.setVisibility(View.GONE);
        } else {
            binding.rvDrink.setVisibility(View.GONE);
            binding.lnNoData.setVisibility(View.VISIBLE);
        }
    }

}