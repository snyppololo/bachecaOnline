package view;

import model.MetodoDiContatto;
import model.Utente;
import utils.TipoDiContatto;
import utils.TipoIndirizzo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AdminView extends CLIView {

    private static final int GO_BACK = -1;
    private static final int DONE = -2;

    public static int showMenu() throws IOException {
        System.out.println("\n**********************************************");
        System.out.println("*    BACHECA ONLINE DI ANNUNCI - ADMIN       *");
        System.out.println("**********************************************\n");
        System.out.println("************** Cosa vuoi fare? ***************");
        System.out.println("1) Registra un nuovo utente");
        System.out.println("2) Aggiungi una nuova categoria");
        System.out.println("3) Genera report su percentuale annunci venduti per utente");
        System.out.println("4) Logout");

        return getAndValidateInput(4);
    }


    public static Utente registraUtenteForm() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Utente utente = new Utente();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\nInserisci informazioni nuovo utente");

        String username = getNotEmptyInput("Username: ");
        utente.setUsername(username);

        String password = getNotEmptyInput("Password: ");
        utente.setPassword(password);

        String nome = getNotEmptyInput("Nome: ");
        utente.setNome(nome);

        String cognome = getNotEmptyInput("Cognome: ");
        utente.setCognome(cognome);

        int parsed = 0;
        while (parsed == 0) {
            try {
                System.out.print("Data di nascita (formato yyyy-MM-dd): ");
                String dataString = reader.readLine();
                LocalDate dataNascita = LocalDate.parse(dataString, formatter);
                utente.setDataNascita(dataNascita);
                parsed = 1;
            } catch (DateTimeParseException e) {
                System.out.println("Formato data errato, riprovare.");
            }
        }

        while (true) {
            String cf = getNotEmptyInput("Codice fiscale: ");
            if (((Pattern.compile("^[A-Z0-9]{16}$")).matcher(cf)).matches()) {
                utente.setCF(cf);
                break;
            }
            System.out.println("Errore: il CF e' composto da esattamente 16 caratteri alfanumerici (lettere in maiuscolo).");
        }

        indirizzoForm(TipoIndirizzo.residenza.toString(), utente);

        System.out.println("\nVuoi inserire un indirizzo di fatturazione?");
        System.out.println("1) Si");
        System.out.println("2) No");
        int choice = getAndValidateInput(2);
        if (choice == 1) {
            indirizzoForm(TipoIndirizzo.fatturazione.toString(), utente);
        }

        return utente;

    }

    private static void indirizzoForm(String tipo, Utente utente) throws IOException {
        System.out.println("\nIndirizzo di " + tipo + ":");
        String via = getNotEmptyInput("Via: ");
        String civico = getNotEmptyInput("Civico: ");

        //Controllo che il CAP sia formato da esattamente 5 cifre tramite regex
        String cap;
        while (true) {
            cap = getNotEmptyInput("CAP: ");
            if (((Pattern.compile("\\d{5}")).matcher(cap)).matches()) {
                break;
            }
            System.out.println("Il CAP deve essere formato da esattamente 5 cifre");
        }

        if (tipo.equals(TipoIndirizzo.residenza.toString())) {
            utente.setR_via(via);
            utente.setR_cap(cap);
            utente.setR_civico(civico);
        } else if (tipo.equals(TipoIndirizzo.fatturazione.toString())) {
            utente.setF_via(via);
            utente.setF_cap(cap);
            utente.setF_civico(civico);
        }
    }

    public static List<MetodoDiContatto> metodiDiContattoForm() throws IOException {
        List<MetodoDiContatto> mdcs = new ArrayList<>();
        System.out.println("\nInserisci i metodi di contatto dell'utente");
        boolean continueFlag = true;
        while (continueFlag) {
            MetodoDiContatto mdc = new MetodoDiContatto();

            TipoDiContatto tipo = selezionaTipo();
            mdc.setTipoContatto(tipo);
            String contatto = getNotEmptyInput("Inserire il contatto: ");
            mdc.setContatto(contatto);
            int preferenza = setPreferenza();
            mdc.setPreferenza(preferenza);
            mdcs.add(mdc);
            continueFlag = askUserMoreMetodiDiContatto();

        }
        return mdcs;
    }

    private static boolean askUserMoreMetodiDiContatto() throws IOException {
        System.out.println("Vuoi inserire un altro metodo di contatto?");
        System.out.println("1) Si");
        System.out.println("2) No");
        int choice = getAndValidateInput(2);
        return choice == 1;
    }

    private static int setPreferenza() throws IOException {
        System.out.println("Impostare il metodo di contatto come preferito?");
        System.out.println("1) Si");
        System.out.println("2) No");
        int choice = getAndValidateInput(2);
        if (choice == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    private static TipoDiContatto selezionaTipo() throws IOException {
        System.out.println("Seleziona il tipo di contatto:");
        for (int i = 0; i < TipoDiContatto.values().length; i++) {
            System.out.println(i + 1 + ") " + TipoDiContatto.fromInt(i + 1).toString());
        }
        int choice = getAndValidateInput(TipoDiContatto.values().length);
        return TipoDiContatto.fromInt(choice);
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

