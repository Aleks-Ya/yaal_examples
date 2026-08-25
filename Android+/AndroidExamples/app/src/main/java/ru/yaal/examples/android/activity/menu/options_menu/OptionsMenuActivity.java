package ru.yaal.examples.android.activity.menu.options_menu;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ru.yaal.examples.android.R;

public class OptionsMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TextView text = findViewById(R.id.optionsMenuTextView);
        switch (item.getItemId()) {
            case R.id.hi_item:
                text.setText(getString(R.string.options_menu_text_view_hi_text));
                return true;
            case R.id.bye_item:
                text.setText(getString(R.string.options_menu_text_view_bye_text));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
