package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatPreview {
    private String altroUtente;
    private int idAnnuncio;
    private String titoloAnnuncio;
    private String ultimaAttivita;

    public ChatPreview() {
    }

    public String getAltroUtente() {
        return altroUtente;
    }

    public void setAltroUtente(String altroUtente) {
        this.altroUtente = altroUtente;
    }

    public int getIdAnnuncio() {
        return idAnnuncio;
    }

    public void setIdAnnuncio(int idAnnuncio) {
        this.idAnnuncio = idAnnuncio;
    }

    public String getTitoloAnnuncio() {
        return titoloAnnuncio;
    }

    public void setTitoloAnnuncio(String titoloAnnuncio) {
        this.titoloAnnuncio = titoloAnnuncio;
    }

    public String getUltimaAttivita() {
        return ultimaAttivita;
    }

    public void setUltimaAttivita(LocalDateTime ultimaAttivita) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        this.ultimaAttivita = ultimaAttivita.format(formatter);
    }
}
