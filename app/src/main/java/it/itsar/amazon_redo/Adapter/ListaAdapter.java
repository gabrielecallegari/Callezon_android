package it.itsar.amazon_redo.Adapter;

import static android.content.ContentValues.TAG;
import static android.os.FileUtils.copy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import it.itsar.amazon_redo.R;
import it.itsar.amazon_redo.http.model.Prodotto;

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ListaViewHolder> {

    private final ArrayList<Prodotto> prodotti;

    public ListaAdapter(ArrayList<Prodotto> prodotti) {
        this.prodotti = prodotti;
    }

    @NonNull
    @Override
    public ListaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carta_template, parent, false);
        return new ListaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaViewHolder holder, int position) {
        holder.bindValue(prodotti.get(position));
    }

    @Override
    public int getItemCount() {
        return prodotti.size();
    }

    static class ListaViewHolder extends RecyclerView.ViewHolder{
        private final TextView nomeProdotto;
        private final TextView prezzo;
        private final TextView qta;
        private final ImageView img;
        public ListaViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeProdotto = itemView.findViewById(R.id.nomeCarta);
            prezzo = itemView.findViewById(R.id.prezzoCarta);
            qta = itemView.findViewById(R.id.quantitaCarta);
            img = itemView.findViewById(R.id.immagineCarta);

        }

        public void bindValue(Prodotto prodotto){
            nomeProdotto.setText(prodotto.getTitle());
            prezzo.setText(Integer.toString(prodotto.getPrice())+"€");
            if(prodotto.getStock()==0) qta.setText("Prodotto disponibile prossimamente");
            else qta.setText("Qta disponibile: "+Integer.toString(prodotto.getStock()));
            Picasso.get().load(prodotto.getThumbnail()).into(img);
        }

    }

}


