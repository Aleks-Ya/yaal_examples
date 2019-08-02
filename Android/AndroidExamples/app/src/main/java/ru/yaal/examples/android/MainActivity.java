package ru.yaal.examples.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ru.yaal.examples.android.activity.fragment.plain.FragmentActivity;
import ru.yaal.examples.android.activity.fragment.viewmodel.ViewModelFragmentActivity;
import ru.yaal.examples.android.activity.progress.progressbar.ProgressBarActivity;
import ru.yaal.examples.android.activity.recyclerview.RecyclerViewActivity;
import ru.yaal.examples.android.activity.recyclerview.customview.CustomViewRecyclerViewActivity;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toRecyclerViewActivity(View view) {
        Intent intent = new Intent(this, RecyclerViewActivity.class);
        startActivity(intent);
    }

    public void toFragmentActivity(View view) {
        Intent intent = new Intent(this, FragmentActivity.class);
        startActivity(intent);
    }

    public void toViewModelFragmentActivity(View view) {
        Intent intent = new Intent(this, ViewModelFragmentActivity.class);
        startActivity(intent);
    }

    public void toRecyclerViewFragmentActivity(View view) {
        Intent intent = new Intent(this, CustomViewRecyclerViewActivity.class);
        startActivity(intent);
    }

    public void toProgressBarActivity(View view) {
        Intent intent = new Intent(this, ProgressBarActivity.class);
        startActivity(intent);
    }

}
