package controller;

import dao.LoginProcedureDAO;
import dao.RegistraUtenteDAO;
import exception.DAOException;
import model.MetodoDiContatto;
import model.Utente;
import utils.Credentials;
import view.LoginView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.StringJoiner;

public class LoginController implements Controller {
    Credentials cred = null;

    @Override
    public void start() {
        try {
            int choice = LoginView.chooseLoginOption();
            switch (choice) {
                case 1 -> loginAuthentication();
                case 2 -> {
                    registration();
                    start();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loginAuthentication() {
        try {
            cred = LoginView.authenticate();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            cred = new LoginProcedureDAO().execute(cred.getUsername(), cred.getPassword());
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }

    private void registration() {
        Utente utente;
        List<MetodoDiContatto> metodiDiContatto;

        try {
            //STEP 1: Form informazioni utente
            utente = LoginView.registraUtenteForm();

            //STEP 2: Inserimento metodi di contatto
            metodiDiContatto = LoginView.metodiDiContattoForm();

            //STEP 3: Formattazione dei metodi di contatto nel formato "tipo:contatto:preferenza"
            StringJoiner joiner = new StringJoiner(";");
            for (MetodoDiContatto mdc : metodiDiContatto) {
                joiner.add(mdc.toFormat());
            }

            String mdcFormatted = joiner.toString();
            //STEP 4: Esecuzione della procedura
            System.out.println(new RegistraUtenteDAO().execute(utente, mdcFormatted));

        } catch (IOException | SQLException | DAOException e) {
            e.printStackTrace();
        }
    }

    public Credentials getCred() {
        return cred;
    }
}
