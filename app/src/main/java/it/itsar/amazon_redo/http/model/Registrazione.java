package it.itsar.amazon_redo.http.model;

import static it.itsar.amazon_redo.http.model.MioDatabase.salvaUtentiSuDatabase;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

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

            if(numeroCarta.getText().toString().length()!=19){
                alert("Lunghezza numero carta non corretta, prova ad aggiungere gli spazi dopo ogni 4 numeri ecceto l'ultimo quartetto");
                return;
            }

            String controllo = "abcdefghijklmnopqrstuvwxyz.,-à°#ù§òç@èé[+*]^?=)(/&%$£!\"";

            String splitted[] = controllo.split("");
            for (int i = 0; i < splitted.length; i++) {
                if(cvvCarta.getText().toString().toLowerCase().contains(splitted[i])){
                    alert("Il cvv della carta non può contenere il carattere "+splitted[i]);
                    return;
                }
                if(scadenzaCarta.getText().toString().toLowerCase().contains(splitted[i])){
                    alert("La scadenza della carta non può contenere il carattere "+splitted[i]);
                    return;
                }
            }

            DialogInterface.OnClickListener listener = (dialog, which) -> {
                if(which == DialogInterface.BUTTON_POSITIVE){
                    Intent intent = new Intent(Registrazione.this, MainActivity.class);
                    launcher.launch(intent);
                }
            };
            new AlertDialog.Builder(Registrazione.this).setTitle("COMPLETATA")
                    .setMessage("Registrazione avvenuta con successo").setPositiveButton("OK",listener).show();

            Utente utente = new Utente();
            utente.setUsername(username.getText().toString());
            utente.setPassword(password.getText().toString());
            utente.setCarta(numeroCarta.getText().toString());
            utente.setCvv(cvvCarta.getText().toString());
            utente.setScadenza(scadenzaCarta.getText().toString());
            utente.setIndirizzo(indirizzo.getText().toString());
            utente.setIslogged(true);
            salvaUtentiSuDatabase(utente);
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

}