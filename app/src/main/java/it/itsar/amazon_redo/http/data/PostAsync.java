package it.itsar.amazon_redo.http.data;

import java.util.ArrayList;

import it.itsar.amazon_redo.http.model.Prodotto;

public interface PostAsync {
    void terminateProcess(ArrayList<Prodotto> prodotti);
    void failedProcess(Exception e);
}
