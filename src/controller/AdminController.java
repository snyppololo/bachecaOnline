package controller;

import dao.AggiungiCategoriaDAO;
import dao.GetCategoriesDAO;
import dao.ReportAnnunciVendutiDAO;
import exception.DAOException;
import factory.ConnectionFactory;
import model.Categoria;
import utils.Role;
import view.AdminView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class AdminController implements Controller {
    //hashmap con chiave = Categoria padre - valore = lista categorie figlie
    private static final Map<String, List<String>> categoriePadreToFiglio = new HashMap<>();
    private static final Map<String, String> categorieFiglioToPadre = new HashMap<>();
    private static final int GO_BACK = -1;
    private static final int DONE = -2;

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
                    case 1 -> aggiungiCategoria();
                    case 2 -> generaReportAnnuale();
                    case 3 -> {
                        ConnectionFactory.closeConnection();
                        System.exit(0);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void aggiungiCategoria() {
        Categoria categoria = new Categoria();
        String newCategoryName = null;
        //STEP 1: Inserimento nome nuova categoria
        try{
            newCategoryName = AdminView.categoriaForm();
        }catch (IOException e){
            e.printStackTrace();
        }

        //STEP 2: Selezionare la categoria padre della categoria che si vuole aggiungere, navigando tra le categorie
        String parentCategoryName = null;
        int confirmed = 0;
        while(confirmed!=1){
            try{
                parentCategoryName = selectParentCategory();
                if (parentCategoryName == null) {
                    break;
                }
                confirmed = AdminView.confirmParentCategoryChoice(parentCategoryName);
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }

        //Se l'utente ha selezionato "Torna indietro" torno al Main Menu
        //Se l'utente ha selezionato come categoria padre "root" allora devo impostare parentCategory a NULL
        if (parentCategoryName == null) {
            adminMainMenuStart();
        }else if (parentCategoryName.equals("root")){
            parentCategoryName = null;
            categoria.setNomeCat(newCategoryName);
            categoria.setCatSup(parentCategoryName);
        }else{
            categoria.setNomeCat(newCategoryName);
            categoria.setCatSup(parentCategoryName);
        }

        //STEP 3: Eseguo la procedura
        try{
            System.out.println(new AggiungiCategoriaDAO().execute(categoria));
        }catch (DAOException e){
            e.printStackTrace();
        }

    }

    private void generaReportAnnuale() {
        try{
            System.out.println(new ReportAnnunciVendutiDAO().execute());
        }catch (DAOException e){
            e.printStackTrace();
        }
    }

    private String selectParentCategory() {
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
                int chosenCategoryIndex = AdminView.selezioneCategoriaGenitore(childrenCategories);
                if (chosenCategoryIndex == GO_BACK) {
                    selectedCategory = categorieFiglioToPadre.get(selectedCategory);
                } else if (chosenCategoryIndex == DONE){
                    break;
                }else {
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
}

