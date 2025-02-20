package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Messaggio {
    private int idMessaggio;
    private String mittente;
    private String destinatario;
    private int idAnnuncio;
    private String ts_messaggio;
    private String testo;

    public Messaggio() {}

    public void setIdMessaggio(int idMessaggio) {
        this.idMessaggio = idMessaggio;
    }

    public void setMittente(String mittente) {
        this.mittente = mittente;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public void setIdAnnuncio(int idAnnuncio) {
        this.idAnnuncio = idAnnuncio;
    }

    public void setTimestamp(LocalDateTime ts) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        this.ts_messaggio = ts.format(formatter);
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public int getIdMessaggio() {
        return idMessaggio;
    }

    public String getMittente() {
        return mittente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public int getIdAnnuncio() {
        return idAnnuncio;
    }

    public String getTimestamp() {
        return ts_messaggio;
    }

    public String getTesto() {
        return testo;
    }

    @Override
    public String toString() {
        return mittente + ": " + testo + " [" + ts_messaggio + "]";
    }

    /*
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
    String formattedTime = now.format(formatter);
     */
}
