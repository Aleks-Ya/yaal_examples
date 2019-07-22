package ru.yaal.examples.android.activity.recyclerview.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.yaal.examples.android.R;

public class RecyclerViewFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_fragment);


        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String[] myDataset = {"abc", "123", "efg"};
        RecyclerView.Adapter mAdapter = new FragmentAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);
    }
}
