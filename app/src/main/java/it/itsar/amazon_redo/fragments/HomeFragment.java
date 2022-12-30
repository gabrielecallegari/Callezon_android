package it.itsar.amazon_redo.fragments;

import static it.itsar.amazon_redo.http.data.JSONProducts.prodotti;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

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
import it.itsar.amazon_redo.R;
import it.itsar.amazon_redo.http.data.JSONProducts;
import it.itsar.amazon_redo.http.data.PostAsync;
import it.itsar.amazon_redo.http.model.Prodotto;

public class HomeFragment extends Fragment {
    private CardView cartaConsigliato;
    private TextView titoloConsigliato;
    private TextView descrizioneConsigliato;
    private ImageView immagineConsigliato;
    private Prodotto mioProdotto = new Prodotto();

    private Random random = new Random();
    private final int index = random.nextInt(prodotti.size());
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

        mioProdotto = prodotti.get(index);

        titoloConsigliato.setText(mioProdotto.getTitle());
        descrizioneConsigliato.setText(mioProdotto.getDescription());
        Picasso.get().load(mioProdotto.getThumbnail()).into(immagineConsigliato);


    }



}