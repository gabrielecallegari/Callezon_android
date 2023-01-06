package it.itsar.amazon_redo.http.model;

import static it.itsar.amazon_redo.MainActivity.nomeFile;
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

    private String cvvGiusto = "";

    private String nomeUtente = "";
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

        String letto = letturaFile();
        JSONObject mio = null;

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
                    infocarta.setText("Inserisci il cvv della carta che termina per "+ultimeOtto);

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
            String a = letturaFile();
            JSONObject mioNuovo = null;
            JSONArray acquisti = null;
            JSONArray nuovo = new JSONArray();
            try {
                mioNuovo = new JSONObject(a);
                acquisti= mioNuovo.getJSONArray("ACQUISTI");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            boolean checked = false;
            if(change==true){
                if (cambiaIndirizzo.getText().toString().equals("") || cambiaIndirizzo.getText()==null){
                    cambiaIndirizzo.setHint("Inserisci l'indirizzo");
                    cambiaIndirizzo.setHintTextColor(Color.RED);
                    return;
                }

                try {
                    JSONArray arr = mioNuovo.getJSONArray("ACCOUNT");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject utente = arr.getJSONObject(i);
                        if(utente.get("islogged").toString().equals("true")){
                            String ind = cambiaIndirizzo.getText().toString();
                            utente.remove("indirizzo");
                            utente.put("insirizzo",ind);
                            checked = true;
                        }
                        nuovo.put(utente);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            for (int i = 0; i < carrello.size(); i++) {
                JSONObject car = new JSONObject();
                try {
                    car.put("id", Integer.toString(carrello.get(i).getId()));
                    car.put("quantita",Integer.toString(carrello.get(i).getStock()));
                    car.put("utente",nomeUtente);
                    acquisti.put(car);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if(checked == true) mioNuovo.remove("ACCOUNT");
            mioNuovo.remove("ACQUISTI");
            try {
                if(checked == true) mioNuovo.put("ACCOUNT",nuovo);
                mioNuovo.put("ACQUISTI",acquisti);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                scritturaFile(mioNuovo.toString());
            } catch (IOException e) {
                e.printStackTrace();
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