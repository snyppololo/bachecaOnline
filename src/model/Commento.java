package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Commento {
    private int idCommento;
    private String utente;
    private int idAnnuncio;
    private String ts_commento;
    private String testo;

    public Commento() {}

    public void setIdCommento(int idCommento) {
        this.idCommento = idCommento;
    }

    public void setUtente(String utente) {
        this.utente = utente;
    }

    public void setIdAnnuncio(int idAnnuncio) {
        this.idAnnuncio = idAnnuncio;
    }

    public void setTimestamp(LocalDateTime ts) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        this.ts_commento = ts.format(formatter);
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public int getIdCommento() {
        return idCommento;
    }

    public String getUtente() {
        return utente;
    }

    public int getIdAnnuncio() {
        return idAnnuncio;
    }

    public String getTimestamp() {
        return ts_commento;
    }

    public String getTesto() {
        return testo;
    }

    @Override
    public String toString() {
        return utente + ": " + testo + " [" + ts_commento + "]";
    }

    /*
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
    String formattedTime = now.format(formatter);
     */
}
