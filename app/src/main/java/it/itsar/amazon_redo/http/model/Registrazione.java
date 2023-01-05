package it.itsar.amazon_redo.http.model;

import static it.itsar.amazon_redo.MainActivity.nomeFile;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import it.itsar.amazon_redo.MainActivity;
import it.itsar.amazon_redo.R;

public class Registrazione extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText confermaPassword;
    private EditText numeroCarta;
    private EditText scadenzaCarta;
    private EditText cvvCarta;
    private EditText indirizzo;
    private CheckBox condizioni;
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
        numeroCarta = findViewById(R.id.numeroCartaRegistrazione);
        scadenzaCarta = findViewById(R.id.scadenzaCartaRegistrazione);
        cvvCarta = findViewById(R.id.cvvCartaRegistrazione);
        indirizzo = findViewById(R.id.indirizzoRegistrazione);
        condizioni = findViewById(R.id.checkboxRegistrazione);

        registrati.setOnClickListener(v->{
            if(username.getText().toString().equals("") || username.getText().toString()==null){
                alert("Il campo USERNAME non può essere vuoto");
                username.setHintTextColor(Color.RED);
                return;
            }

            if(password.getText().toString().equals("") || password.getText().toString()==null){
                alert("Il campo PASSWORD non può essere vuoto");
                password.setHintTextColor(Color.RED);
                return;
            }

            if(confermaPassword.getText().toString().equals("") || confermaPassword.getText().toString()==null){
                alert("Il campo CONFERMA PASSWORD non può essere vuoto");
                confermaPassword.setHintTextColor(Color.RED);
                return;
            }

            if(numeroCarta.getText().toString().equals("") || numeroCarta.getText().toString()==null){
                alert("Il campo NUMERO CARTA non può essere vuoto");
                numeroCarta.setHintTextColor(Color.RED);
                return;
            }

            if(scadenzaCarta.getText().toString().equals("") || scadenzaCarta.getText().toString()==null){
                alert("Il campo DATA SCADENZA non può essere vuoto");
                scadenzaCarta.setHintTextColor(Color.RED);
                return;
            }

            if(cvvCarta.getText().toString().equals("") || cvvCarta.getText().toString()==null){
                alert("Il campo CVV non può essere vuoto");
                cvvCarta.setHintTextColor(Color.RED);
                return;
            }

            if(indirizzo.getText().toString().equals("") || indirizzo.getText().toString()==null){
                alert("Il campo INDIRIZZO non può essere vuoto");
                indirizzo.setHintTextColor(Color.RED);
                return;
            }

            if(!condizioni.isChecked()){
                alert("Devi accettare le condizioni per registrarti");
                return;
            }

            if(!confermaPassword.getText().toString().equals(password.getText().toString())){
                alert("Le Password non coincidono");
                return;
            }

            String letto = letturaFile();
            if(letto.contains(username.getText().toString())){
                alert("USERNAME già esistente");
                return;
            }


            DialogInterface.OnClickListener listener = (dialog, which) -> {
                if(which == DialogInterface.BUTTON_POSITIVE){
                    Intent intent = new Intent(Registrazione.this, MainActivity.class);
                    launcher.launch(intent);
                }
            };
            new AlertDialog.Builder(Registrazione.this).setTitle("COMPLETATA")
                    .setMessage("Registrazione avvenuta con successo").setPositiveButton("OK",listener).show();

            if(letto.length()==0){
                JSONObject mioDatabase = new JSONObject();
                try {
                    JSONArray array = new JSONArray();
                    JSONArray acquisti = new JSONArray();
                    JSONObject utente = new JSONObject();
                    utente.put("username",username.getText().toString());
                    utente.put("password",password.getText().toString());
                    utente.put("carta",numeroCarta.getText().toString());
                    utente.put("scadenza",scadenzaCarta.getText().toString());
                    utente.put("cvv",cvvCarta.getText().toString());
                    utente.put("indirizzo",indirizzo.getText().toString());
                    array.put(utente);
                    mioDatabase.put("ACCOUNT",array);
                    mioDatabase.put("ACQUISTI",acquisti);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    scritturaFile(mioDatabase.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                JSONObject mioDatabase = new JSONObject();
                try {
                    String a = letturaFile();
                    JSONObject mio = new JSONObject(a);
                    JSONArray array = mio.getJSONArray("ACCOUNT");
                    JSONArray acquisti = mio.getJSONArray("ACQUISTI");
                    JSONObject utente = new JSONObject();
                    utente.put("username",username.getText().toString());
                    utente.put("password",password.getText().toString());
                    utente.put("carta",numeroCarta.getText().toString());
                    utente.put("scadenza",scadenzaCarta.getText().toString());
                    utente.put("cvv",cvvCarta.getText().toString());
                    utente.put("indirizzo",indirizzo.getText().toString());
                    array.put(utente);
                    mioDatabase.put("ACCOUNT",array);
                    mioDatabase.put("ACQUISTI",acquisti);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    scritturaFile(mioDatabase.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("FILE", letturaFile());
            }




        });

        back.setOnClickListener(v->finish());

    }

    private void alert(String messaggio){
        DialogInterface.OnClickListener listener = (dialog, which) -> {};
        new AlertDialog.Builder(Registrazione.this).setTitle("Errore")
                .setMessage(messaggio).setPositiveButton("OK",listener).show();
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {

            });
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
}