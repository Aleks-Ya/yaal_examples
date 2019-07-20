package ru.yaal.examples.android.activity.fragment.plain;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ru.yaal.examples.android.R;

public class FragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plain_fragment);
    }
}
