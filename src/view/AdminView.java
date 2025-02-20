package view;

import java.io.IOException;
import java.util.List;

public class AdminView extends CLIView {

    private static final int GO_BACK = -1;
    private static final int DONE = -2;

    public static int showMenu() throws IOException {
        System.out.println("\n**********************************************");
        System.out.println("*    BACHECA ONLINE DI ANNUNCI - ADMIN       *");
        System.out.println("**********************************************\n");
        System.out.println("************** Cosa vuoi fare? ***************");
        //System.out.println("1) Registra un nuovo utente");
        System.out.println("1) Aggiungi una nuova categoria");
        System.out.println("2) Genera report su percentuale annunci venduti per utente");
        System.out.println("3) Logout");

        return getAndValidateInput(3);
    }

    public static int selezioneCategoriaGenitore(List<String> categorie) throws IOException {

        System.out.println("\nSeleziona la categoria superiore di appartenenza:");

        for (int i = 0; i < categorie.size(); i++) {
            System.out.println(i + 1 + ") " + categorie.get(i));
        }
        System.out.println(categorie.size() + 1 + ") Inserisci qui la nuova categoria");
        printBackOption(categorie.size() + 2);

        //categorie.size()+2 perche' aggiungo le righe "Inserisci qui la nuova categoria" e "Torna indietro"
        int choice = getAndValidateInput(categorie.size() + 2);
        if (choice == categorie.size() + 2) {
            return GO_BACK;
        } else if (choice == categorie.size() + 1) {
            return DONE;
        } else {
            return choice - 1;
        }
    }

    public static String categoriaForm() throws IOException {
        return getNotEmptyInput("Inserisci il nome della nuova categoria: ");
    }

    public static int confirmParentCategoryChoice(String parentCategoryName) throws IOException {
        System.out.println("\nLa categoria padre selezionata è " + parentCategoryName);
        System.out.println("Confermare la scelta?");
        System.out.println("1) Si");
        System.out.println("2) No");
        return getAndValidateInput(2);
    }
}

