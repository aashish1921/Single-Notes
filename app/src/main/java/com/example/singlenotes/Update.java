package com.example.singlenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

public class Update extends AppCompatActivity {

    ImageView btn;
    EditText title_update, description_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.gb));
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.gb));
        }

        title_update=findViewById(R.id.Text_update);
        description_update=findViewById(R.id.des_update);
        getSupportActionBar().hide();
        title_update.setText(getIntent().getStringExtra("titleName"));
        title_update.setSelection(title_update.getText().length());
        description_update.setText(getIntent().getStringExtra("description"));
        description_update.setSelection(description_update.getText().length());
        description_update.setMovementMethod(LinkMovementMethod.getInstance());
        description_update.requestFocus();
  }
}