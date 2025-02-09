package view;

import model.Annuncio;

import java.io.IOException;
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

    private Annuncio annuncioForm(){
        System.out.println();
    }
}
