package it.itsar.amazon_redo.fragments;

import static it.itsar.amazon_redo.http.data.JSONProducts.prodotti;
import static it.itsar.amazon_redo.http.model.Prodotto_dettaglio.carrello;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import it.itsar.amazon_redo.Adapter.ListaAdapter;
import it.itsar.amazon_redo.MainActivity;
import it.itsar.amazon_redo.R;
import it.itsar.amazon_redo.http.model.Carrello_dettaglio;
import it.itsar.amazon_redo.http.model.Prodotto;
import it.itsar.amazon_redo.http.model.Prodotto_dettaglio;
import it.itsar.amazon_redo.listener.RecyclerItemClickListener;


public class CartFragment extends Fragment {
    private RecyclerView carrelloView;
    private TextView carrelloVuoto;
    private Button acquista;
    private Button svuotaCarrello;
    private TextView prezzoTotale;

    private int prezzo = 0;

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
        prezzoTotale = view.findViewById(R.id.prezzoTotale);


        CarrelloAdapter mioAdapter = new CarrelloAdapter(carrello);
        carrelloView.setAdapter(mioAdapter);

        carrelloView.setVisibility(carrello.size()==0 ? View.GONE : View.VISIBLE);
        acquista.setVisibility(carrello.size()==0 ? View.GONE : View.VISIBLE);
        svuotaCarrello.setVisibility(carrello.size()==0 ? View.GONE : View.VISIBLE);
        prezzoTotale.setVisibility(carrello.size()==0 ? View.GONE : View.VISIBLE);
        carrelloVuoto.setVisibility(carrello.size()==0 ? View.VISIBLE : View.GONE);

        for (int i = 0; i < carrello.size(); i++) {
            prezzo = prezzo + (carrello.get(i).getPrice() * carrello.get(i).getStock());
        }

        prezzoTotale.setText("Prezzo totale: "+prezzo+"€");


        carrelloView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), carrelloView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Prodotto mioProdotto = carrello.get(position);
                        Intent intent = new Intent(getActivity(), Carrello_dettaglio.class);
                        intent.putExtra("ProdottoCarrello",mioProdotto);
                        launcher.launch(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );

        svuotaCarrello.setOnClickListener(v -> {
            Log.d("CARRELLO SIZE", "onViewCreated: "+carrello.size());

            DialogInterface.OnClickListener listener = (dialog, which) -> {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        prezzo = 0;
                        prezzoTotale.setText("Prezzo totale: 0€");
                        prezzoTotale.setVisibility(View.GONE);
                        carrelloView.setVisibility(View.GONE);
                        acquista.setVisibility(View.GONE);
                        svuotaCarrello.setVisibility(View.GONE);
                        carrelloVuoto.setVisibility(View.VISIBLE);
                        for(Prodotto i : carrello){
                            prodotti.get(i.getId()-1).setStock(prodotti.get(i.getId()-1).getStock()+i.getStock());
                        }
                        carrello.clear();
                        break;
                }
            };
            new AlertDialog.Builder(getContext()).setTitle("Sei sicuto di voler svuotare il carrello?")
                    .setPositiveButton("SVUOTA",listener)
                    .setNegativeButton("ANNULLA",listener)
                    .show();
        });

    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Log.d("RITORNO", ": ");
                ListaAdapter adapter = new ListaAdapter(carrello);
                carrelloView.setAdapter(adapter);
            });

}