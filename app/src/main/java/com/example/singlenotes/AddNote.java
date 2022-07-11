package com.example.singlenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import io.realm.Realm;

public class AddNote extends AppCompatActivity {

    EditText editText_title, editText_note;
    ImageView save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        getSupportActionBar().hide();
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bg)));
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.gb));
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.gb));
        }
        editText_title = findViewById(R.id.edit_title);
        editText_note = findViewById(R.id.edit_note);
        save = findViewById(R.id.imageView_save);
        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();
        editText_note.requestFocus();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = editText_title.getText().toString();
                String description = editText_note.getText().toString();
                long createdTime =System.currentTimeMillis();
                if(description.isEmpty()){
                    Toast.makeText(AddNote.this, "Please enter Notes", Toast.LENGTH_SHORT).show();
                }
                else {
                    realm.beginTransaction();
                    Note note1 = realm.createObject(Note.class);
                    note1.setTitle(title);
                    note1.setDescription(description);
                    note1.setCreatedTime(createdTime);
                    realm.commitTransaction();
                    Toast.makeText(getApplicationContext(),"Notes saved",Toast.LENGTH_SHORT).show();
                    finish();


                }


            }
        });
    }
}