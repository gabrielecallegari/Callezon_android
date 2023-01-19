package it.itsar.amazon_redo.http.model;

public class Acquisti {
    private int quantitaProdotto;
    private int idProdotto;
    private String user;
    private String id;

    public Acquisti() {
    }

    public Acquisti(int quantitaProdotto, int idProdotto, String user, String id) {
        this.quantitaProdotto = quantitaProdotto;
        this.idProdotto = idProdotto;
        this.user = user;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
