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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import it.itsar.amazon_redo.Adapter.ListaAdapter;
import it.itsar.amazon_redo.MainActivity;
import it.itsar.amazon_redo.R;
import it.itsar.amazon_redo.http.model.Prodotto;
import it.itsar.amazon_redo.http.model.Prodotto_dettaglio;
import it.itsar.amazon_redo.listener.RecyclerItemClickListener;

public class SearchFragment extends Fragment {
    private RecyclerView miaListView;
    private EditText search;
    private TextView errore;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        miaListView = view.findViewById(R.id.recycleview);
        search = view.findViewById(R.id.search);
        errore = view.findViewById(R.id.notFound);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 0) {
                    miaListView.setVisibility(View.GONE);
                    errore.setVisibility(View.GONE);
                } else {
                    if (s.toString().equals("/all")) {
                        ListaAdapter adapter = new ListaAdapter(prodotti);
                        miaListView.setAdapter(adapter);
                        errore.setVisibility(View.GONE);
                        miaListView.setVisibility(View.VISIBLE);
                    } else {
                        ArrayList<Prodotto> p = new ArrayList<>();
                        String splitted[] = s.toString().split("");

                        for (int i = 0; i < prodotti.size(); i++) {
                            ArrayList<Boolean> check = new ArrayList<>();
                            for (int j = 0; j < splitted.length; j++) {
                                if (prodotti.get(i).getTitle().toLowerCase(Locale.ROOT).contains(splitted[j].toLowerCase(Locale.ROOT)) ) {
                                    check.add(true);
                                }
                            }
                            if (check.size()  == splitted.length) {
                                p.add(prodotti.get(i));
                            }
                        }

                        if (p.size() == 0) {
                            errore.setVisibility(View.VISIBLE);
                            miaListView.setVisibility(View.GONE);
                        } else {
                            ListaAdapter adapter = new ListaAdapter(p);
                            miaListView.setAdapter(adapter);
                            errore.setVisibility(View.GONE);
                            miaListView.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


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