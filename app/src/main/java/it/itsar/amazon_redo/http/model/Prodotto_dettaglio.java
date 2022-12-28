package it.itsar.amazon_redo.http.model;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.*;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import it.itsar.amazon_redo.R;

import it.itsar.amazon_redo.MainActivity.*;


public class Prodotto_dettaglio extends AppCompatActivity {
    private ImageView immaginePrincipale;
    private TextView nomeProdotto;
    private TextView descrizioneProdotto;
    private TextView scontoProdotto;
    private TextView prezzoProdotto;

    public static ArrayList<Prodotto> carrello = new ArrayList<>();
    private ImageButton tastoBack;
    private Button addCart;
    private int quantita = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prodotto_dettaglio);

        prezzoProdotto = findViewById(R.id.prezzoProdotto);
        immaginePrincipale = findViewById(R.id.immagineDettaglio);
        nomeProdotto = findViewById(R.id.titoloDettaglio);
        descrizioneProdotto = findViewById(R.id.descrizioneDettaglio);
        tastoBack = findViewById(R.id.tornaIndietro);
        addCart = findViewById(R.id.prodottoAdd);
        scontoProdotto = findViewById(R.id.scontoProdotto);


        Prodotto mioProdotto = (Prodotto) getIntent().getSerializableExtra("Prodotto");
        nomeProdotto.setText(mioProdotto.getTitle());
        Picasso.get().load(mioProdotto.getThumbnail()).into(immaginePrincipale);
        descrizioneProdotto.setText(mioProdotto.getDescription());
        scontoProdotto.setText("Sconto applicato: "+mioProdotto.getDiscount()+"%");
        prezzoProdotto.setText("Prezzo: "+mioProdotto.getPrice()+"€");

        tastoBack.setOnClickListener(v -> {
            finish();
        });

        addCart.setOnClickListener(v -> {
            mioProdotto.setStock(quantita);
            carrello.add(mioProdotto);

            Toast.makeText(Prodotto_dettaglio.this, "Prodotto aggiunto al carrelllo", Toast.LENGTH_SHORT).show();
        });
    }


}