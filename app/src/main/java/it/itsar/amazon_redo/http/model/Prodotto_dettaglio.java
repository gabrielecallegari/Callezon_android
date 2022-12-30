package it.itsar.amazon_redo.http.model;

import static it.itsar.amazon_redo.http.data.JSONProducts.prodotti;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    private ImageButton add;
    private ImageButton delete;
    private TextView qta;
    private Button addCart;
    private TextView info;
    private int quantita = 1;
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
        add =findViewById(R.id.addQta);
        delete = findViewById(R.id.removeQTA);
        qta = findViewById(R.id.quantitaProdotto);
        delete.setClickable(false);
        info = findViewById(R.id.infoProdotto);


        Prodotto mioProdotto = (Prodotto) getIntent().getSerializableExtra("Prodotto");
        nomeProdotto.setText(mioProdotto.getTitle());
        Picasso.get().load(mioProdotto.getThumbnail()).into(immaginePrincipale);
        descrizioneProdotto.setText(mioProdotto.getDescription());
        scontoProdotto.setText("Sconto applicato: "+mioProdotto.getDiscount()+"%");
        prezzoProdotto.setText("Prezzo: "+mioProdotto.getPrice()+"€");

        if (mioProdotto.getStock()<=0){
            add.setVisibility(View.GONE);
            qta.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
            addCart.setVisibility(View.GONE);
            info.setText(""+mioProdotto.getTitle()+" attualmente non \nè disponibile");
            info.setTextColor(Color.RED);
        }

        tastoBack.setOnClickListener(v -> {
            finish();
        });

        addCart.setOnClickListener(v -> {
            boolean check = false;
            int stock = prodotti.get(mioProdotto.getId()-1).getStock();
            prodotti.get(mioProdotto.getId()-1).setStock(stock-quantita);
            mioProdotto.setStock(quantita);
            for(int i=0; i<carrello.size(); i++){
                if (carrello.get(i).getId() == mioProdotto.getId()){
                    carrello.get(i).setStock(carrello.get(i).getStock()+mioProdotto.getStock());
                    check = true;
                }
            }
            if(check == false) carrello.add(mioProdotto);
            finish();
            Toast.makeText(Prodotto_dettaglio.this, mioProdotto.getTitle()+" aggiunto al carrelllo", Toast.LENGTH_SHORT).show();
        });

        add.setOnClickListener(v->{
            delete.setClickable(true);
            quantita++;
            if(quantita>=mioProdotto.getStock()) add.setClickable(false);
            qta.setText(Integer.toString(quantita));
        });

        delete.setOnClickListener(v->{
            add.setClickable(true);
            if (quantita<2) delete.setClickable(false);
            else {
                quantita--;
                qta.setText(Integer.toString(quantita));
            }
        });
    }


}