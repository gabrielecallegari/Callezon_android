package it.itsar.amazon_redo.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import it.itsar.amazon_redo.R;
import it.itsar.amazon_redo.http.model.Prodotto;

public class CarrelloAdapter extends RecyclerView.Adapter<CarrelloAdapter.CarrelloViewHolder>{
    private static ArrayList<Prodotto> cart;

    public CarrelloAdapter(ArrayList<Prodotto> cart){
        this.cart=cart;
    }

    @NonNull
    @Override
    public CarrelloViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carta_template, parent, false);
        return new CarrelloViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarrelloViewHolder holder, int position) {
        holder.bindValue(cart.get(position));
    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    static class CarrelloViewHolder extends RecyclerView.ViewHolder{
        private final TextView nomeProdotto;
        private final TextView prezzo;
        private final TextView qta;
        private final ImageView img;
        public CarrelloViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeProdotto = itemView.findViewById(R.id.nomeCarta);
            prezzo = itemView.findViewById(R.id.prezzoCarta);
            qta = itemView.findViewById(R.id.quantitaCarta);
            img = itemView.findViewById(R.id.immagineCarta);

        }

        public void bindValue(Prodotto prodotto){
            nomeProdotto.setText(prodotto.getTitle());
            prezzo.setText(Integer.toString(prodotto.getPrice())+"€");
            qta.setText("Qta selezionata: "+Integer.toString(prodotto.getStock()));
            Picasso.get().load(prodotto.getThumbnail()).into(img);
        }

    }
}
