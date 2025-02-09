package view;

import model.Annuncio;
import model.Categoria;
import utils.LoggedUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class UserView {
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


        Scanner input = new Scanner(System.in);
        int choice = 0;
        while (true) {
            System.out.print("Please enter your choice: ");
            choice = input.nextInt();
            if (choice >= 1 && choice <= 5) {
                break;
            }
            System.out.println("Invalid option");
        }

        return choice;
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

    public static String selezioneCategoria(List<String> categorie) throws IOException {

        System.out.println("Seleziona una categoria:");

        for (int i = 0; i < categorie.size(); i++) {
            System.out.println(i+1+") "+categorie.get(i));
        }
        System.out.println(categorie.size()+1+") Torna indietro");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            int choice = Integer.parseInt(reader.readLine());
            if (choice >= 1 && choice <= categorie.size()+1) {
                if (choice == categorie.size()+1) {
                    return "BACK";
                }else{
                    return categorie.get(choice-1);
                }
            }
            System.out.println("Invalid option");
        }

    }
}
