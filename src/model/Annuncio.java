package model;

import java.util.Date;

public class Annuncio {
    private int idAnnuncio;
    private String utente;
    private String titolo;
    private String descrizione;
    private String categoria;
    private Date dataPubblicazione;
    private Date dataVendita;

    public Annuncio(int idAnnuncio, String utente, String titolo, String descrizione, String categoria, Date dataPubblicazione ) {
        this.idAnnuncio = idAnnuncio;
        this.utente = utente;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.dataPubblicazione = dataPubblicazione;
    }

    public Annuncio() {}

    public int getIdAnnuncio() {
        return idAnnuncio;
    }
}
