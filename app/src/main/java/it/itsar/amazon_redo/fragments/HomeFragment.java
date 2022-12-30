package it.itsar.amazon_redo.fragments;

import static it.itsar.amazon_redo.http.data.JSONProducts.prodotti;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

import it.itsar.amazon_redo.Adapter.ListaAdapter;
import it.itsar.amazon_redo.Adapter.ScontiAdapter;
import it.itsar.amazon_redo.R;
import it.itsar.amazon_redo.http.data.JSONProducts;
import it.itsar.amazon_redo.http.data.PostAsync;
import it.itsar.amazon_redo.http.model.Prodotto;
import it.itsar.amazon_redo.http.model.Prodotto_dettaglio;

public class HomeFragment extends Fragment {
    private CardView cartaConsigliato;
    private TextView titoloConsigliato;
    private TextView descrizioneConsigliato;
    private ImageView immagineConsigliato;
    private Prodotto mioProdotto = new Prodotto();
    private RecyclerView listaSconti;
    private ArrayList<Prodotto> sconti = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cartaConsigliato = view.findViewById(R.id.cartaConsigliata);
        titoloConsigliato = view.findViewById(R.id.titoloConsigliata);
        descrizioneConsigliato = view.findViewById(R.id.descrizioneConsigliata);
        immagineConsigliato = view.findViewById(R.id.immagineConsigliata);
        listaSconti = view.findViewById(R.id.scontiRecycle);

        mioProdotto = prodotti.get(0);
        findDiscount();

        titoloConsigliato.setText(mioProdotto.getTitle());
        descrizioneConsigliato.setText(mioProdotto.getDescription());
        Picasso.get().load(mioProdotto.getThumbnail()).into(immagineConsigliato);

        cartaConsigliato.setOnClickListener(v -> {
            Log.d("PREMUTO", "onViewCreated: ");
            Intent intent = new Intent(getContext(), Prodotto_dettaglio.class);
            intent.putExtra("Prodotto",mioProdotto);
            launcher.launch(intent);
        });


    }

    private void findDiscount(){
        sconti = prodotti;
        for(int i=0; i<sconti.size(); i++){
            for (int j = 0; j < sconti.size(); j++) {
                if(sconti.get(i).getDiscount()<sconti.get(j).getDiscount()){
                    Prodotto p = new Prodotto();
                    p= sconti.get(i);
                    sconti.set(i,sconti.get(j));
                    sconti.set(j,p);
                }
            }
        }
        for (int i = 0; i < sconti.size(); i++) {
            Log.d("SCONTI", " "+sconti.get(i).getDiscount());
        }
        ArrayList<Prodotto> dieciSconti = new ArrayList<>();
        for(int i=sconti.size()-1; i>sconti.size()-11; i--) dieciSconti.add(sconti.get(i));

        ScontiAdapter adapter = new ScontiAdapter(dieciSconti);
        listaSconti.setAdapter(adapter);
        listaSconti.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {

            });

}