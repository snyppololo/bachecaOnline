package controller;

import dao.*;
import exception.AnnuncioGiaVendutoException;
import exception.DAOException;
import factory.ConnectionFactory;
import model.Annuncio;
import model.Categoria;
import utils.Role;
import view.UserView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController implements Controller {

    //hashmap con chiave = Categoria padre - valore = lista categorie figlie
    private static final Map<String, List<String>> categoriePadreToFiglio = new HashMap<>();
    private static final Map<String, String> categorieFiglioToPadre = new HashMap<>();
    private static final int GO_BACK = -1;

    @Override
    public void start() {
        try {
            ConnectionFactory.changeRole(Role.user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        mainMenuStart();
    }

    private void mainMenuStart() {
        while (true) {
            int choice;
            try {
                choice = UserView.showMenu();
                switch (choice) {
                    case 1 -> creaAnnuncio();
                    case 2 -> visualizzaAnnunciAttivi();
                    case 3 -> visualizzaAnnunciPreferiti();
                    case 4 -> visualizzaMieiAnnunci();
                    case 5 -> {
                        ConnectionFactory.closeConnection();
                        System.exit(0);
                    }
                }
            } catch (IOException | DAOException | SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //CASE 2 dello switch case
    private void visualizzaAnnunciAttivi() {
        //throw new UnsupportedOperationException("Not supported yet.");
        int choice;
        int annuncioIndex;
        try {
            //Mostro inizialmente all'utente solo i titoli degli annunci attivi
            List<Annuncio> annunci = new ListaAnnunciAttiviDAO().execute();
            annuncioIndex = UserView.showTitoliAnnunci(annunci);

            if (annuncioIndex == GO_BACK) {
                mainMenuStart();
            }

            //Ora mostro all'utente le informazioni relative all'annuncio specifico che ha selezionato
            UserView.showAnnuncioDetails(annunci.get(annuncioIndex));

            //Prima di mostrare le scelte devo controllare se l'utente ha gia' le notifiche attive per l'annuncio (Opzione attiva/disattiva notifiche)
            boolean notificheOn = new CheckNotificheOnDAO().execute(annunci.get(annuncioIndex));
            choice = UserView.showAnnuncioOptions(notificheOn);

            switch (choice) {
                case 1 -> scriviMessaggio(annunci.get(annuncioIndex));
                case 2 -> pubblicaCommento(annunci.get(annuncioIndex));
                case 3 -> switchNotifiche(annunci.get(annuncioIndex), notificheOn);
                case 4 -> visualizzaAnnunciAttivi();
            }

        } catch (IOException | DAOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void scriviMessaggio(Annuncio annuncio) {
        throw new RuntimeException();
    }

    private void pubblicaCommento(Annuncio annuncio) {
        throw new RuntimeException();
    }

    private void switchNotifiche(Annuncio annuncio, boolean notificheAttive) {
        if (notificheAttive) {
            System.out.println(new DisattivaNotificheDAO().execute(annuncio));
        } else {
            System.out.println(new AttivaNotificheDAO().execute(annuncio));
        }
    }


    //CASE 3 dello switch case
    private void visualizzaAnnunciPreferiti() {
        int choice;
        int annuncioIndex;
        try {
            //Mostro inizialmente all'utente solo i titoli degli annunci attivi
            List<Annuncio> annunci = new ListaAnnunciNotificheAttiveDAO().execute();
            annuncioIndex = UserView.showTitoliAnnunci(annunci);

            if (annuncioIndex == GO_BACK) {
                mainMenuStart();
            }

            //Ora mostro all'utente le informazioni relative all'annuncio specifico che ha selezionato
            UserView.showAnnuncioDetails(annunci.get(annuncioIndex));

            //boolean notificheOn = new CheckNotificheOnDAO().execute(LoggedUser.getUsername(), annunci.get(annuncioIndex).getIdAnnuncio());
            choice = UserView.showAnnuncioOptions(true);

            switch (choice) {
                case 1 -> scriviMessaggio(annunci.get(annuncioIndex));
                case 2 -> pubblicaCommento(annunci.get(annuncioIndex));
                case 3 -> switchNotifiche(annunci.get(annuncioIndex), true);
                case 4 -> visualizzaAnnunciPreferiti();
            }

        } catch (IOException | DAOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //CASE 4 dello switch case
    private void visualizzaMieiAnnunci() {
        int choice;
        int annuncioIndex;
        try {
            //Mostro inizialmente all'utente solo i titoli degli annunci attivi
            List<Annuncio> annunci = new ListaAnnunciPubblicatiDAO().execute();
            annuncioIndex = UserView.showTitoliAnnunci(annunci);

            if (annuncioIndex == GO_BACK) {
                mainMenuStart();
            }

            Annuncio myAnnuncio = annunci.get(annuncioIndex);

            //Ora mostro all'utente le informazioni relative all'annuncio specifico che ha selezionato
            UserView.showAnnuncioDetails(myAnnuncio);

            //boolean notificheOn = new CheckNotificheOnDAO().execute(LoggedUser.getUsername(), annunci.get(annuncioIndex).getIdAnnuncio());
            choice = UserView.showMyAnnuncioOptions();

            switch (choice) {
                case 1 -> contrassegnaAnnuncioVenduto(myAnnuncio);
                case 2 -> visualizzaMieiAnnunci();
            }

        } catch (IOException | DAOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void contrassegnaAnnuncioVenduto(Annuncio myAnnuncio) {
        try {
            System.out.println(new AnnuncioVendutoDAO().execute(myAnnuncio));
        } catch (AnnuncioGiaVendutoException e){
            System.out.println(e.getMessage());
        }
    }


    private void creaAnnuncio() throws DAOException, SQLException {
        //STEP 1: Selezione categoria
        String category = selectCategory();
        if (category == null) {
            mainMenuStart();
        }
        System.out.println("Categoria scelta: " + category);

        //STEP 2: Form per l'annuncio
        Annuncio ann;
        try {
            ann = UserView.annuncioForm(category);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //STEP 3: Esecuzione della procedura
        System.out.println(new CreaAnnuncioDAO().execute(ann));
    }

    private String selectCategory() {
        List<Categoria> categories;
        try {
            categories = new GetCategoriesDAO().execute();
        } catch (DAOException | SQLException e) {
            throw new RuntimeException(e);
        }
        cacheCategories(categories);
        String selectedCategory = "root";
        while (categoriePadreToFiglio.containsKey(selectedCategory)) {
            try {
                List<String> childrenCategories = categoriePadreToFiglio.get(selectedCategory);
                int chosenCategoryIndex = UserView.selezioneCategoria(childrenCategories);
                if (chosenCategoryIndex == GO_BACK) {
                    selectedCategory = categorieFiglioToPadre.get(selectedCategory);
                } else {
                    selectedCategory = childrenCategories.get(chosenCategoryIndex);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return selectedCategory;
    }

    private void cacheCategories(List<Categoria> categories) {
        categoriePadreToFiglio.clear();
        categorieFiglioToPadre.clear();
        for (Categoria c : categories) {
            categoriePadreToFiglio.computeIfAbsent(c.getCatSup(), k -> new ArrayList<>()).add(c.getNomeCat());
            categorieFiglioToPadre.put(c.getNomeCat(), c.getCatSup());
        }
    }

    private List<String> getChildCategories(String catSup) {
        return categoriePadreToFiglio.get(catSup);
    }

    private String getParentCategory(String catFiglio) {
        return categorieFiglioToPadre.get(catFiglio);
    }

}
