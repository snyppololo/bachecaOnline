package model;

import utils.TipoDiContatto;

public class MetodoDiContatto {
    private TipoDiContatto tipoContatto;
    private String contatto;
    private int preferenza;

    public MetodoDiContatto(){}

    public String getTipoContatto() {
        return tipoContatto.toString();
    }

    public String getContatto() {
        return contatto;
    }

    public int getPreferenza() {
        return preferenza;
    }

    public void setTipoContatto(TipoDiContatto tipoContatto){
        this.tipoContatto = tipoContatto;
    }

    public void setContatto(String contatto){
        this.contatto = contatto;
    }

    public void setPreferenza(int preferenza){
        this.preferenza = preferenza;
    }

    public String toFormat(){
        return tipoContatto.toString()+":"+contatto+":"+preferenza;
    }
}
