package model;

import java.time.LocalDate;

public class Utente {
    private String username;
    private String password;
    private String cf;
    private String nome;
    private String cognome;
    private LocalDate dataNascita;
    private String r_via;
    private String r_civico;
    private String r_cap;
    private String f_via;
    private String f_civico;
    private String f_cap;

    public Utente() {}

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

    public String getCF() {
        return cf;
    }

    public void setCF(String cf) {
        this.cf = cf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getR_via() {
        return r_via;
    }

    public void setR_via(String r_via) {
        this.r_via = r_via;
    }

    public String getR_civico() {
        return r_civico;
    }

    public void setR_civico(String r_civico) {
        this.r_civico = r_civico;
    }

    public String getR_cap() {
        return r_cap;
    }

    public void setR_cap(String r_cap) {
        this.r_cap = r_cap;
    }

    public String getF_via() {
        return f_via;
    }

    public void setF_via(String f_via) {
        this.f_via = f_via;
    }

    public String getF_civico() {
        return f_civico;
    }

    public void setF_civico(String f_civico) {
        this.f_civico = f_civico;
    }

    public String getF_cap() {
        return f_cap;
    }

    public void setF_cap(String f_cap) {
        this.f_cap = f_cap;
    }
}
