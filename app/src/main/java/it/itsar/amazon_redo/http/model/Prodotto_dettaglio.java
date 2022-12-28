package it.itsar.amazon_redo.http.model;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.*;

import com.squareup.picasso.Picasso;

import it.itsar.amazon_redo.R;

public class Prodotto_dettaglio extends AppCompatActivity {
    private ImageView immaginePrincipale;
    private TextView nomeProdotto;
    private TextView descrizioneProdotto;

    private ImageButton tastoBack;
    private Button addCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prodotto_dettaglio);
        immaginePrincipale = findViewById(R.id.immagineDettaglio);
        nomeProdotto = findViewById(R.id.titoloDettaglio);
        descrizioneProdotto = findViewById(R.id.descrizioneDettaglio);
        tastoBack = findViewById(R.id.tornaIndietro);
        addCart = findViewById(R.id.prodottoAdd);
        Prodotto mioProdotto = (Prodotto) getIntent().getSerializableExtra("Prodotto");
        nomeProdotto.setText(mioProdotto.getTitle());
        Picasso.get().load(mioProdotto.getThumbnail()).into(immaginePrincipale);

        tastoBack.setOnClickListener(v -> {
            finish();
        });
    }


}