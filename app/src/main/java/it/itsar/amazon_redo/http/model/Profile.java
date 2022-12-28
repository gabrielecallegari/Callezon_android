package it.itsar.amazon_redo.http.model;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import it.itsar.amazon_redo.R;

public class Profile extends AppCompatActivity {
    private ImageButton back;
    private Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        back = findViewById(R.id.tornaHome);

        back.setOnClickListener(v->{
            finish();
        });
    }
}