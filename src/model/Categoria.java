package model;

public class Categoria {
    private String nomeCat;
    private String catSup;

    public Categoria(String nomeCat, String catSup) {
        this.nomeCat = nomeCat;
        this.catSup = catSup;
    }

    public Categoria() {}

    public String getNomeCat() {
        return nomeCat;
    }
    public void setNomeCat(String nomeCat) {
        this.nomeCat = nomeCat;
    }
    public String getCatSup() {
        return catSup;
    }
    public void setCatSup(String catSup) {
        this.catSup = catSup;
    }
}
