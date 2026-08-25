package ru.yaal.examples.android.activity.recyclerview.customview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.yaal.examples.android.R;

public class CustomViewRecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_custom_view);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_custom_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String[] dataSet = {"John", "Petr", "Mary"};
        RecyclerView.Adapter mAdapter = new CustomViewAdapter(dataSet);
        recyclerView.setAdapter(mAdapter);
    }
}
