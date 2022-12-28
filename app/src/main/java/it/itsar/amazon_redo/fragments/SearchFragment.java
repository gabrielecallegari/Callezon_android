package it.itsar.amazon_redo.fragments;

import static it.itsar.amazon_redo.http.data.JSONProducts.prodotti;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.os.MessageQueue;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import it.itsar.amazon_redo.Adapter.ListaAdapter;
import it.itsar.amazon_redo.MainActivity;
import it.itsar.amazon_redo.R;
import it.itsar.amazon_redo.http.model.Prodotto;
import it.itsar.amazon_redo.http.model.Prodotto_dettaglio;
import it.itsar.amazon_redo.listener.RecyclerItemClickListener;

public class SearchFragment extends Fragment {
    private RecyclerView miaListView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        miaListView = view.findViewById(R.id.recycleview);
        ListaAdapter adapter = new ListaAdapter(prodotti);
        miaListView.setAdapter(adapter);
        miaListView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), miaListView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Prodotto mioProdotto = prodotti.get(position);
                        Intent intent = new Intent(getActivity(),Prodotto_dettaglio.class);
                        intent.putExtra("Prodotto",mioProdotto);
                        launcher.launch(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );


    }


    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                ListaAdapter adapter = new ListaAdapter(prodotti);
                miaListView.setAdapter(adapter);
            });


}