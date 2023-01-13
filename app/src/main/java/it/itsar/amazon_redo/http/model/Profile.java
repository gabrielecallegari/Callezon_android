package it.itsar.amazon_redo.http.model;

import static it.itsar.amazon_redo.MainActivity.isLogged;
import static it.itsar.amazon_redo.MainActivity.nomeFile;
import static it.itsar.amazon_redo.MainActivity.utenteLoggato;
import static it.itsar.amazon_redo.http.data.JSONProducts.prodotti;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import java.util.ArrayList;

import it.itsar.amazon_redo.Adapter.ProfiloAdapter;
import it.itsar.amazon_redo.MainActivity;
import it.itsar.amazon_redo.R;
import it.itsar.amazon_redo.listener.RecyclerItemClickListener;

public class Profile extends AppCompatActivity {
    private ImageButton back;

    private EditText username;
    private EditText password;

    private TextView senzaAccount;
    private TextView labelLogin;
    private TextView nomeUtente;
    private TextView labelDati;
    private TextView cartaLabel;
    private TextView numeroCarta;
    private TextView scadenzaCarta;
    private TextView cvvCarta;
    private TextView labelIndirizzo;
    private TextView indirizzoUtente;
    private TextView acquistiLabel;

    private RecyclerView acquistiLista;

    private Button logout;
    private Button accedi;

    private String usernameUtente="";
    private String indirizzo;
    private String carta="";
    private String scadenza="";
    private String cvv="";

    public static ArrayList<Prodotto> acquistati = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        nomeUtente = findViewById(R.id.bentornato);
        labelLogin = findViewById(R.id.labelLogin);
        username = findViewById(R.id.usernameAccedi);
        password = findViewById(R.id.passwordAccedi);
        back = findViewById(R.id.tornaHome);
        accedi = findViewById(R.id.buttonLogin);
        senzaAccount = findViewById(R.id.senzaAccount);
        labelDati = findViewById(R.id.datiLabel);
        cartaLabel = findViewById(R.id.ccLabel);
        numeroCarta = findViewById(R.id.cartaCreditoPersonale);
        scadenzaCarta = findViewById(R.id.scadenzaPersonale);
        cvvCarta = findViewById(R.id.cvvPersonale);
        logout = findViewById(R.id.logoutPersonale);
        labelIndirizzo = findViewById(R.id.labelIndirizzo);
        indirizzoUtente = findViewById(R.id.indirizzoPersonale);
        acquistiLabel = findViewById(R.id.ituoiacquisti);
        acquistiLista = findViewById(R.id.recycleviewAcquisti);

        setVisibility();
        setUser();

        senzaAccount.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, Registrazione.class);
            launcher.launch(intent);
        });

        acquistiLista.addOnItemTouchListener(
                new RecyclerItemClickListener(Profile.this, acquistiLista ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Prodotto mioProdotto = prodotti.get(acquistati.get(position).getId()-1);
                        Intent intent = new Intent(Profile.this, Prodotto_dettaglio.class);
                        intent.putExtra("Prodotto",mioProdotto);
                        launcher.launch(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );

        accedi.setOnClickListener(v->{
            /*
            if(!letturaFile().contains(username.getText().toString())){
                username.setText("");
                username.setHint("Username non valido");
                username.setHintTextColor(Color.RED);
                return;
            }

             */
            if(username.getText().equals("") || username.getText()==null){
                username.setHint("Inserisci username");
                username.setHintTextColor(Color.RED);
                return;
            }
            if(password.getText().equals("") || password.getText()==null){
                password.setHint("Inserisci password");
                password.setHintTextColor(Color.RED);
                return;
            }
        });

        logout.setOnClickListener(v->{
            DialogInterface.OnClickListener listener = (dialog, which) -> {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:

                        setVisibility();
                        break;
                }
            };
            new AlertDialog.Builder(Profile.this).setTitle("LOGOUT")
                    .setMessage("Sei sicuro di voler effettuare il logut?").setPositiveButton("Conferma",listener).setNegativeButton("Annulla",listener).show();


        });

        back.setOnClickListener(v->{
            finish();
        });
    }

    private void setUser(){
        acquistati.clear();
        if (isLogged==true){
            usernameUtente = utenteLoggato.getUsername();
            nomeUtente.setText("Bentornato "+usernameUtente);
            carta = utenteLoggato.getCarta();
            scadenza = utenteLoggato.getScadenza();
            cvv = utenteLoggato.getCvv();
            indirizzo = utenteLoggato.getIndirizzo();
            indirizzoUtente.setText(indirizzo);
            numeroCarta.setText(carta);
            scadenzaCarta.setText("Scadenza: "+scadenza);
            cvvCarta.setText("Cvv: "+cvv);
        }
        /*
        ProfiloAdapter adapter = new ProfiloAdapter(acquistati);
        acquistiLista.setAdapter(adapter);
        acquistiLista.setLayoutManager(new LinearLayoutManager(Profile.this, RecyclerView.HORIZONTAL, false));
         */

    }



    void setVisibility(){
        labelLogin.setVisibility(isLogged==true?View.GONE:View.VISIBLE);
        username.setVisibility(isLogged==true?View.GONE:View.VISIBLE);
        password.setVisibility(isLogged==true?View.GONE:View.VISIBLE);
        senzaAccount.setVisibility(isLogged==true?View.GONE:View.VISIBLE);
        accedi.setVisibility(isLogged==true?View.GONE:View.VISIBLE);

        nomeUtente.setVisibility(isLogged==true?View.VISIBLE:View.GONE);
        labelDati.setVisibility(isLogged==true?View.VISIBLE:View.GONE);
        cartaLabel.setVisibility(isLogged==true?View.VISIBLE:View.GONE);
        numeroCarta.setVisibility(isLogged==true?View.VISIBLE:View.GONE);
        scadenzaCarta.setVisibility(isLogged==true?View.VISIBLE:View.GONE);
        cvvCarta.setVisibility(isLogged==true?View.VISIBLE:View.GONE);
        logout.setVisibility(isLogged==true?View.VISIBLE:View.GONE);
        labelIndirizzo.setVisibility(isLogged==true?View.VISIBLE:View.GONE);
        indirizzoUtente.setVisibility(isLogged==true?View.VISIBLE:View.GONE);
        acquistiLabel.setVisibility(isLogged==true?View.VISIBLE:View.GONE);
        acquistiLista.setVisibility(isLogged==true?View.VISIBLE:View.GONE);
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {

            });
}