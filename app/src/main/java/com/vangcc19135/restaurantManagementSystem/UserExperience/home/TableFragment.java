package com.vangcc19135.restaurantManagementSystem.UserExperience.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.vangcc19135.RestaurantManagementSystem.R;
import com.vangcc19135.RestaurantManagementSystem.databinding.FragmentHomeBinding;
import com.vangcc19135.restaurantManagementSystem.UserExperience.database.DatabaseHandler;
import com.vangcc19135.restaurantManagementSystem.UserExperience.detailbook.DetailTableActivity;
import com.vangcc19135.restaurantManagementSystem.UserExperience.entity.Table;
import com.vangcc19135.restaurantManagementSystem.UserExperience.event.UpdateTableEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class TableFragment extends Fragment {

    private FragmentHomeBinding binding;
    private DatabaseHandler databaseHandler;

    private TableAdapter tableAdapter;

    EditText notification;

    public static TableFragment newInstance() {
        
        Bundle args = new Bundle();
        
        TableFragment fragment = new TableFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        binding = FragmentHomeBinding.bind(view);

        databaseHandler = new DatabaseHandler(getContext(), DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);
        initRecyclerListBook();

        initListener();
        return view;
    }


    private void initListener() {
        binding.flbAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHandler.addBook(new Table());
                initRecyclerListBook();
                binding.rvBook.scrollToPosition(databaseHandler.getAllBook().size()-1);
            }
        });
    }

    private void initRecyclerListBook() {
        try{
            binding.rvBook.setLayoutManager(new GridLayoutManager(getContext(), 2));

            tableAdapter = new TableAdapter(databaseHandler.getAllBook(), new TableAdapter.IBookListener() {
                @Override
                public void onClickBook(Table table) {
                    Intent intent= new Intent(getContext(), DetailTableActivity.class);
                    intent.putExtra(DetailTableActivity.DetailBook, table);
                    startActivity(intent);
                }

            });
            binding.rvBook.setAdapter(tableAdapter);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Subscribe
    public void OnEvent(UpdateTableEvent event) {
      initRecyclerListBook();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}