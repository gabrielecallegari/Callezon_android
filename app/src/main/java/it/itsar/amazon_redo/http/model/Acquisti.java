package it.itsar.amazon_redo.http.model;

public class Acquisti {
    private int quantitaProdotto;
    private int idProdotto;
    private String user;

    public Acquisti() {
    }

    public Acquisti(int quantitaProdotto, int idProdotto, String user) {
        this.quantitaProdotto = quantitaProdotto;
        this.idProdotto = idProdotto;
        this.user = user;
    }

    public int getQuantitaProdotto() {
        return quantitaProdotto;
    }

    public void setQuantitaProdotto(int quantitaProdotto) {
        this.quantitaProdotto = quantitaProdotto;
    }

    public int getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
