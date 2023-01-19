package it.itsar.amazon_redo.http.model;

public class Utente {
    private String username;
    private String password;
    private String carta;
    private String cvv;
    private String scadenza;
    private String indirizzo;
    private Boolean islogged;
    private String id;

    public Utente(){}

    public Utente(String username, String password, String carta, String cvv, String scadenza, String indirizzo, Boolean islogged, String id) {
        this.username = username;
        this.password = password;
        this.carta = carta;
        this.cvv = cvv;
        this.scadenza = scadenza;
        this.indirizzo = indirizzo;
        this.islogged = islogged;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCarta() {
        return carta;
    }

    public void setCarta(String carta) {
        this.carta = carta;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getScadenza() {
        return scadenza;
    }

    public void setScadenza(String scadenza) {
        this.scadenza = scadenza;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public Boolean getIslogged() {
        return islogged;
    }

    public void setIslogged(Boolean islogged) {
        this.islogged = islogged;
    }
}
