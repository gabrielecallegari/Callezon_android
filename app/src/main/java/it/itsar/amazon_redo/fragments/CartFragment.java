package it.itsar.amazon_redo.fragments;

import static it.itsar.amazon_redo.http.model.Prodotto_dettaglio.carrello;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import it.itsar.amazon_redo.Adapter.CarrelloAdapter;
import it.itsar.amazon_redo.MainActivity;
import it.itsar.amazon_redo.R;

public class CartFragment extends Fragment {
    private RecyclerView carrelloView;
    private TextView carrelloVuoto;
    private Button acquista;
    private Button svuotaCarrello;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        carrelloView = view.findViewById(R.id.carrelloRecycler);
        acquista = view.findViewById(R.id.acquista);
        carrelloVuoto = view.findViewById(R.id.carrelloVuoto);
        svuotaCarrello = view.findViewById(R.id.svuota);


        CarrelloAdapter mioAdapter = new CarrelloAdapter(carrello);
        carrelloView.setAdapter(mioAdapter);

        carrelloView.setVisibility(carrello.size()==0 ? View.GONE : View.VISIBLE);
        acquista.setVisibility(carrello.size()==0 ? View.GONE : View.VISIBLE);
        svuotaCarrello.setVisibility(carrello.size()==0 ? View.GONE : View.VISIBLE);
        carrelloVuoto.setVisibility(carrello.size()==0 ? View.VISIBLE : View.GONE);


        svuotaCarrello.setOnClickListener(v -> {
            carrello.clear();
            Log.d("CARRELLO SIZE", "onViewCreated: "+carrello.size());

            DialogInterface.OnClickListener listener = (dialog, which) -> {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        carrelloView.setVisibility(View.GONE);
                        acquista.setVisibility(View.GONE);
                        svuotaCarrello.setVisibility(View.GONE);
                        carrelloVuoto.setVisibility(View.VISIBLE);
                        break;
                }
            };
            new AlertDialog.Builder(getContext()).setTitle("Sei sicuto di voler svuotare il carrello?")
                    .setPositiveButton("SVUOTA",listener)
                    .setNegativeButton("ANNULLA",listener)
                    .show();
        });

    }

}