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

public class ScontiAdapter extends RecyclerView.Adapter<ScontiAdapter.ScontiViewHolder> {
    private final ArrayList<Prodotto> sconto;

    public ScontiAdapter(ArrayList<Prodotto> sconto) {
        this.sconto = sconto;
    }

    @NonNull
    @Override
    public ScontiAdapter.ScontiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carta2_template, parent, false);
        return new ScontiAdapter.ScontiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScontiViewHolder holder, int position) {
        holder.bindValue(sconto.get(position));
    }

    @Override
    public int getItemCount() {
        return sconto.size();
    }

    static class ScontiViewHolder extends RecyclerView.ViewHolder{
        private final TextView nomeProdotto;
        private final TextView sconto;
        private final ImageView img;
        public ScontiViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeProdotto = itemView.findViewById(R.id.titoloCardView);
            sconto = itemView.findViewById(R.id.scontoScrollable);
            img = itemView.findViewById(R.id.scrollableImage);

        }

        public void bindValue(Prodotto prodotto){
            nomeProdotto.setText(prodotto.getTitle());
            int discount = (int) prodotto.getDiscount();
            sconto.setText("-"+Integer.toString(discount)+"%");
            Picasso.get().load(prodotto.getThumbnail()).into(img);
        }

    }
}
