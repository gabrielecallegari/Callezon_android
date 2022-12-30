package it.itsar.amazon_redo.http.model;

import static it.itsar.amazon_redo.http.data.JSONProducts.prodotti;
import static it.itsar.amazon_redo.http.model.Prodotto_dettaglio.carrello;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import it.itsar.amazon_redo.R;

public class Carrello_dettaglio extends AppCompatActivity {
    private ImageView immaginePrincipale;
    private TextView nomeProdotto;
    private TextView descrizioneProdotto;
    private TextView scontoProdotto;
    private TextView prezzoProdotto;

    private ImageButton tastoBack;
    private ImageButton add;
    private ImageButton delete;
    private TextView qta;
    private Button updateCart;
    private TextView info;
    private int quantita = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrello_dettaglio);

        prezzoProdotto = findViewById(R.id.infoProdottoCart);
        immaginePrincipale = findViewById(R.id.immagineDettaglioCart);
        nomeProdotto = findViewById(R.id.titoloDettaglioCart);
        descrizioneProdotto = findViewById(R.id.descrizioneDettaglioCart);
        tastoBack = findViewById(R.id.backLista);
        updateCart = findViewById(R.id.carrelloUpdate);
        scontoProdotto = findViewById(R.id.scontoProdottoCart);
        add = findViewById(R.id.addQtaCart);
        delete = findViewById(R.id.removeQTACart);
        qta = findViewById(R.id.quantitaProdottoCart);
        info = findViewById(R.id.infoProdottoCart);


        Prodotto mioProdotto = (Prodotto) getIntent().getSerializableExtra("ProdottoCarrello");
        nomeProdotto.setText(mioProdotto.getTitle());
        Picasso.get().load(mioProdotto.getThumbnail()).into(immaginePrincipale);
        descrizioneProdotto.setText(mioProdotto.getDescription());
        scontoProdotto.setText("Sconto applicato: " + mioProdotto.getDiscount() + "%");
        prezzoProdotto.setText("Prezzo: " + mioProdotto.getPrice() + "€");
        qta.setText(Integer.toString(mioProdotto.getStock()));
        quantita = mioProdotto.getStock();

        if (mioProdotto.getStock() <= 0) {
            add.setVisibility(View.GONE);
            qta.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
            updateCart.setVisibility(View.GONE);
            info.setText("" + mioProdotto.getTitle() + " attualmente non \nè disponibile");
            info.setTextColor(Color.RED);
        }

        add.setOnClickListener(v->{
            delete.setClickable(true);
            int stock = prodotti.get(mioProdotto.getId()-1).getStock()+mioProdotto.getStock();
            Log.d("QUANTITA", "onCreate: "+quantita);

            if(quantita>=stock) add.setClickable(false);
            else{
                quantita++;
                qta.setText(Integer.toString(quantita));
            }
        });

        delete.setOnClickListener(v->{
            add.setClickable(true);
            quantita--;
            if (quantita<1) delete.setClickable(false);
            qta.setText(Integer.toString(quantita));
        });


        updateCart.setOnClickListener(v->{
            for (int i = 0; i<carrello.size(); i++){
                if (carrello.get(i).getId()==mioProdotto.getId()){
                    if(quantita>=1){
                        carrello.get(i).setStock(quantita);
                    }else{
                        carrello.remove(i);
                    }
                }
            }
            prodotti.get(mioProdotto.getId()-1).setStock(prodotti.get(mioProdotto.getId()-1).getStock()+(mioProdotto.getStock()-quantita));
            finish();
        });
        tastoBack.setOnClickListener(v -> {
            finish();
        });

    }



}