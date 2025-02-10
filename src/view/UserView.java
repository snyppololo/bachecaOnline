package view;

import model.Annuncio;
import utils.LoggedUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class UserView {

    private static final int GO_BACK = -1;

    public static int showMenu() throws IOException {
        System.out.println("*********************************************");
        System.out.println("*    BACHECA ONLINE DI ANNUNCI - USER       *");
        System.out.println("*********************************************\n");
        System.out.println("************** Cosa vuoi fare? **************\n");
        System.out.println("1) Crea un annuncio");
        System.out.println("2) Visualizza annunci attivi");
        System.out.println("3) Visualizza annunci preferiti");
        System.out.println("4) Visualizza i tuoi annunci");
        System.out.println("5) Logout");

        return getAndValidateInput(5);
    }

    public static Annuncio annuncioForm(String categoria) throws IOException {
        Annuncio ann = new Annuncio();
        ann.setCategoria(categoria);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Inserisci informazioni annuncio\n");

        System.out.print("Inserisci titolo: ");
        String titolo = reader.readLine();
        ann.setTitolo(titolo);

        System.out.print("Inserisci descrizione: ");
        String desc = reader.readLine();
        ann.setDescrizione(desc);

        ann.setUtente(LoggedUser.getUsername());
        return ann;
    }

    public static int selezioneCategoria(List<String> categorie) throws IOException {

        System.out.println("Seleziona una categoria:");

        for (int i = 0; i < categorie.size(); i++) {
            System.out.println(i+1+") "+categorie.get(i));
        }
        printBackOption(categorie.size()+1);

        //categorie.size()+1 perche' aggiungo la riga "Torna indietro"
        int choice = getAndValidateInput(categorie.size()+1);
        if (choice == categorie.size()+1) {
            return GO_BACK;
        }else{
            return choice-1;
        }
    }

    public static int showTitoliAnnunci(List<Annuncio> annunci) throws IOException {
        System.out.println("\n--- Annunci attivi ---");
        System.out.println("Seleziona un annuncio per visualizzarne le informazioni complete");
        for (int i = 0; i < annunci.size(); i++) {
            System.out.println(i+1+") "+annunci.get(i).getTitolo());
        }
        printBackOption(annunci.size()+1);
        int choice = getAndValidateInput(annunci.size()+1);

        if (choice == annunci.size()+1) {
            return GO_BACK;
        }else{
            return choice-1;
        }
    }

    public static void showAnnuncioDetails(Annuncio annuncio) throws IOException {
        System.out.println(annuncio);
    }

    public static int showAnnuncioOptions(boolean notificheAttive) throws IOException {
        String onOrOff = !notificheAttive ? "Attiva" : "Disattiva";
        System.out.println("\n1) Scrivi un messaggio privato al venditore");
        System.out.println("2) Pubblica un commento");
        System.out.println("3) "+onOrOff+" le notifiche per questo annuncio");
        printBackOption(4);
        return getAndValidateInput(4);
    }

    private static int getAndValidateInput(int maxNumber) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("Seleziona un'opzione: ");
            int choice = Integer.parseInt(reader.readLine());
            if (choice >= 1 && choice <= maxNumber) {
                return choice;
            }else{
                System.out.println("Opzione non valida");
            }
        }
    }

    private static void printBackOption(int optionNumber){
        System.out.println(optionNumber+") Torna indietro");
    }



}
