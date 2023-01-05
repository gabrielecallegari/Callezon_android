package it.itsar.amazon_redo.http.model;

import static it.itsar.amazon_redo.MainActivity.isLogged;
import static it.itsar.amazon_redo.MainActivity.nomeFile;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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

        accedi.setOnClickListener(v->{
            if(!letturaFile().contains(username.getText().toString())){
                username.setText("");
                username.setHint("Username non valido");
                username.setHintTextColor(Color.RED);
                return;
            }
            String a = letturaFile();
            JSONObject mio = null;
            boolean trovato = false;
            try {
                mio = new JSONObject(a);
                JSONArray arr = mio.getJSONArray("ACCOUNT");
                JSONArray nuovo = new JSONArray();
                JSONArray acquisti = mio.getJSONArray("ACQUISTI");

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject utente = arr.getJSONObject(i);
                    if(utente.get("username").toString().equals(username.getText().toString())){
                        if(!utente.get("password").toString().equals(password.getText().toString())){
                            password.setText("");
                            password.setHint("Password non valida");
                            password.setHintTextColor(Color.RED);
                        }else{
                            utente.remove("isLogged");
                            utente.put("isolgged","true");
                            trovato = true;
                        }
                    }
                    nuovo.put(utente);
                }
                mio.remove("ACCOUNT");
                mio.remove("ACQUISTI");
                mio.put("ACCOUNT",nuovo);
                mio.put("ACQUISTI",acquisti);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(trovato == true) {
                try {
                    scritturaFile(mio.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        back.setOnClickListener(v->{
            finish();
        });
    }


    public Boolean scritturaFile(String testo) throws IOException {
        File file = new File(getFilesDir(),nomeFile);
        FileOutputStream stream = null;
        try{
            stream = new FileOutputStream(file);
            stream.write(testo.getBytes());
            stream.close();
            return true;
        }catch (Exception e){
            Log.d("FILE", "scriviFile: Scrittura fallita");
        }
        return false;
    }

    public String letturaFile(){
        File file = new File(getFilesDir(),nomeFile);
        int length = (int) file.length();
        byte[] bytes = new byte[length];

        try(FileInputStream in = new FileInputStream(file)) {
            int l = in.read(bytes);
        }catch (Exception e){
            Log.d("FILE", "letturaFile: Lettura Fallita");
        }

        return new String(bytes);
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {

            });
}