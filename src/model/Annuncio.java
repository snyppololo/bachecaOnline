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

    //SETTERS
    public void setIdAnnuncio(int idAnnuncio) {
        this.idAnnuncio = idAnnuncio;
    }

    public void setUtente(String utente) {
        this.utente = utente;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setDataPubblicazione(Date dataPubblicazione) {
        this.dataPubblicazione = dataPubblicazione;
    }
    public void setDataVendita(Date dataVendita) {
        this.dataVendita = dataVendita;
    }

    //GETTERS
    public int getIdAnnuncio() {
        return idAnnuncio;
    }

    public String getUtente() {
        return utente;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getCategoria() {
        return categoria;
    }

    public Date getDataPubblicazione() {
        return dataPubblicazione;
    }

    public Date getDataVendita() {
        return dataVendita;
    }
}
