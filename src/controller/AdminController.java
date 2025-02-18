package controller;

import dao.RegistraUtenteDAO;
import dao.TestDAO;
import exception.DAOException;
import factory.ConnectionFactory;
import model.MetodoDiContatto;
import model.Utente;
import utils.Role;
import view.AdminView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.StringJoiner;

public class AdminController implements Controller {
    @Override
    public void start() {
        try {
            ConnectionFactory.changeRole(Role.admin);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        adminMainMenuStart();
    }

    private void adminMainMenuStart() {
        while (true) {
            int choice;
            try {
                choice = AdminView.showMenu();
                switch (choice) {
                    case 1 -> registraUtente();
                    case 2 -> aggiungiCategoria();
                    case 3 -> generaReportAnnuale();
                    case 4 -> {
                        ConnectionFactory.closeConnection();
                        System.exit(0);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void registraUtente() {
        Utente utente;
        List<MetodoDiContatto> metodiDiContatto;
        try {
            //STEP 1: Form informazioni utente
            utente = AdminView.registraUtenteForm();

            //STEP 2: Inserimento metodi di contatto
            metodiDiContatto = AdminView.metodiDiContattoForm();

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

    private void aggiungiCategoria() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void generaReportAnnuale() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}

