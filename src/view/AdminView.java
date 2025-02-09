package view;

import java.io.IOException;
import java.util.Scanner;

public class AdminView {

    public static int showMenu() throws IOException {
        System.out.println("**********************************************");
        System.out.println("*    BACHECA ONLINE DI ANNUNCI - ADMIN       *");
        System.out.println("**********************************************\n");
        System.out.println("************** Cosa vuoi fare? ***************\n");
        System.out.println("1) Registra un nuovo utente");
        System.out.println("2) Aggiungi una nuova categoria");
        System.out.println("3) Genera report su percentuale annunci venduti per utente");
        System.out.println("4) Logout");


        Scanner input = new Scanner(System.in);
        int choice = 0;
        while (true) {
            System.out.print("Please enter your choice: ");
            choice = input.nextInt();
            if (choice >= 1 && choice <= 4) {
                break;
            }
            System.out.println("Invalid option");
        }

        return choice;
    }

}
