package it.itsar.amazon_redo.http.model;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import it.itsar.amazon_redo.R;

public class Profile extends AppCompatActivity {
    private ImageButton back;
    private EditText username;
    private EditText password;
    private Button accedi;
    private TextView senzaAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username = findViewById(R.id.usernameAccedi);
        password = findViewById(R.id.passwordAccedi);
        back = findViewById(R.id.tornaHome);
        accedi = findViewById(R.id.buttonLogin);
        senzaAccount = findViewById(R.id.senzaAccount);

        senzaAccount.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, Registrazione.class);
            launcher.launch(intent);
        });

        back.setOnClickListener(v->{
            finish();
        });
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {

            });
}