package it.itsar.amazon_redo.http.model;

import static it.itsar.amazon_redo.MainActivity.isLogged;
import static it.itsar.amazon_redo.MainActivity.nomeFile;
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
                            utente.remove("islogged");
                            utente.put("islogged","true");
                            isLogged = true;
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
                setVisibility();
                setUser();
            }
        });

        logout.setOnClickListener(v->{
            DialogInterface.OnClickListener listener = (dialog, which) -> {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        String a = letturaFile();
                        JSONObject mio = null;
                        try {
                            mio = new JSONObject(a);
                            JSONArray arr = mio.getJSONArray("ACCOUNT");
                            JSONArray nuovo = new JSONArray();
                            JSONArray acquisti = mio.getJSONArray("ACQUISTI");

                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject utente = arr.getJSONObject(i);
                                if(utente.get("username").toString().equals(usernameUtente)){
                                    utente.remove("islogged");
                                    utente.put("islogged","false");
                                    isLogged=false;
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
                        try {
                            scritturaFile(mio.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
            String letto = letturaFile();
            JSONObject mio = null;
            try {
                mio = new JSONObject(letto);
                JSONArray arr = mio.getJSONArray("ACCOUNT");
                JSONArray acquistiJSon = mio.getJSONArray("ACQUISTI");
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject utente = arr.getJSONObject(i);
                    if(utente.get("islogged").toString().equals("true")){
                        usernameUtente = utente.get("username").toString();
                        nomeUtente.setText("Bentornato "+usernameUtente);
                        carta = utente.get("carta").toString();
                        scadenza = utente.get("scadenza").toString();
                        cvv = utente.get("cvv").toString();
                        indirizzo = utente.get("indirizzo").toString();
                    }
                }

                for (int i = 0; i < acquistiJSon.length(); i++) {
                    JSONObject oggetto = acquistiJSon.getJSONObject(i);
                    if (oggetto.get("utente").toString().equals(usernameUtente)){
                        Prodotto p = new Prodotto();
                        p = prodotti.get(Integer.parseInt(oggetto.get("id").toString())-1);
                        acquistati.add(p);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        indirizzoUtente.setText(indirizzo);
        numeroCarta.setText(carta);
        scadenzaCarta.setText("Scadenza: "+scadenza);
        cvvCarta.setText("Cvv: "+cvv);

        ProfiloAdapter adapter = new ProfiloAdapter(acquistati);
        acquistiLista.setAdapter(adapter);
        acquistiLista.setLayoutManager(new LinearLayoutManager(Profile.this, RecyclerView.HORIZONTAL, false));

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