package it.itsar.amazon_redo.http.model;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import it.itsar.amazon_redo.R;

public class Registrazione extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText confermaPassword;
    private Button registrati;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);
        username = findViewById(R.id.usernameRegistrazione);
        password = findViewById(R.id.passwordRegistrazione);
        confermaPassword = findViewById(R.id.confermaPasswordRegistrazione);
        registrati = findViewById(R.id.registrati);
        back = findViewById(R.id.tornaRegistrazione);

        back.setOnClickListener(v->finish());

    }
}