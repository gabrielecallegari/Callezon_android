package it.itsar.amazon_redo.http.model;


import static it.itsar.amazon_redo.MainActivity.utenteLoggato;
import static it.itsar.amazon_redo.http.model.MioDatabase.salvaAcquistiSuDatabase;
import static it.itsar.amazon_redo.http.model.Prodotto_dettaglio.carrello;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

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

import org.checkerframework.checker.units.qual.A;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import it.itsar.amazon_redo.MainActivity;
import it.itsar.amazon_redo.R;
import it.itsar.amazon_redo.fragments.HomeFragment;

public class Pagamento extends AppCompatActivity {
    private EditText cvv;
    private EditText nuovoIndirizzo;

    private TextView indirizzo;
    private TextView cambiaIndirizzo;
    private TextView infocarta;

    private Button paga;

    private ImageButton back;

    private boolean change = false;

    private String cvvGiusto = utenteLoggato.getCvv();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);

        infocarta = findViewById(R.id.infocarta);
        cvv = findViewById(R.id.cvvPagamento);
        nuovoIndirizzo = findViewById(R.id.nuovoIndirizzoSpedizione);
        indirizzo = findViewById(R.id.indirizzoSpedizione);
        cambiaIndirizzo = findViewById(R.id.cambiaIndirizzo);
        paga = findViewById(R.id.effettuaPagamento);
        back = findViewById(R.id.tornaCarrello);


        /*
        try {
            mio = new JSONObject(letto);
            JSONArray arr = mio.getJSONArray("ACCOUNT");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject utente = arr.getJSONObject(i);
                if(utente.get("islogged").toString().equals("true")){
                    nomeUtente = utente.get("username").toString();
                    indirizzo.setText(utente.get("indirizzo").toString());
                    cvvGiusto = utente.get("cvv").toString();
                    String splitted[] = utente.get("carta").toString().split("");
                    String ultimeOtto = "";
                    for (int j = splitted.length-4; j < splitted.length; j++) {
                        ultimeOtto = ultimeOtto+splitted[i];
                    }


                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
         */

        String splitted[] = utenteLoggato.getCarta().split("");
        String ultimeQuattro = "";
        for (int i = splitted.length-1; i > splitted.length-5; i--) {
            ultimeQuattro = ultimeQuattro + splitted[i];
        }
        infocarta.setText("Inserisci il cvv della carta che termina per "+ultimeQuattro);
        indirizzo.setText(utenteLoggato.getIndirizzo());

        back.setOnClickListener(v->{
            finish();
        });

        paga.setOnClickListener(v->{
            String chechCvv = cvv.getText().toString();
            if(!cvvGiusto.equals(chechCvv)){
                cvv.setText("");
                cvv.setHint("Cvv errato");
                cvv.setHintTextColor(Color.RED);
                return;
            }

            boolean checked = false;
            if(change==true){
                if (cambiaIndirizzo.getText().toString().equals("") || cambiaIndirizzo.getText()==null){
                    cambiaIndirizzo.setHint("Inserisci l'indirizzo");
                    cambiaIndirizzo.setHintTextColor(Color.RED);
                    return;
                }

                /*
                try {
                    JSONArray arr = mioNuovo.getJSONArray("ACCOUNT");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject utente = arr.getJSONObject(i);
                        if(utente.get("islogged").toString().equals("true")){
                            String ind = nuovoIndirizzo.getText().toString();
                            utente.remove("insirizzo");
                            utente.remove("indirizzo");
                            utente.put("indirizzo",ind);
                            checked = true;
                        }
                        nuovo.put(utente);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                 */

            }

            for (int i = 0; i < carrello.size(); i++) {
                Acquisti acquisti = new Acquisti();
                acquisti.setIdProdotto(carrello.get(i).getId());
                acquisti.setQuantitaProdotto(carrello.get(i).getStock());
                acquisti.setUser(utenteLoggato.getUsername());
                salvaAcquistiSuDatabase(acquisti);
            }


            carrello.clear();

            DialogInterface.OnClickListener listener = (dialog, which) -> {
                if(which == DialogInterface.BUTTON_POSITIVE){
                    Intent intent = new Intent(Pagamento.this, MainActivity.class);
                    launcher.launch(intent);
                }
            };
            new AlertDialog.Builder(Pagamento.this).setTitle("COMPLETATO")
                    .setMessage("Stai per essere reindirizzato alla home").setPositiveButton("Ok",listener).show();
        });

        cambiaIndirizzo.setOnClickListener(v->{
            change = !change;
            if(change == false){
                nuovoIndirizzo.setVisibility(View.GONE);
                indirizzo.setVisibility(View.VISIBLE);
                cambiaIndirizzo.setText("Clicca qui per cambiare l'indirizzo di spedizione");
            }else{
                nuovoIndirizzo.setVisibility(View.VISIBLE);
                indirizzo.setVisibility(View.GONE);
                cambiaIndirizzo.setText("Annulla");
            }
        });
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {

            });
}