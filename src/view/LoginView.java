package view;

import model.MetodoDiContatto;
import model.Utente;
import utils.Credentials;
import utils.TipoDiContatto;
import utils.TipoIndirizzo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class LoginView extends CLIView {

    public static int chooseLoginOption() throws IOException {
        System.out.println("1) Login");
        System.out.println("2) Registrazione");
        return getAndValidateInput(2);
    }

    public static Credentials authenticate() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("username: ");
        String username = reader.readLine();
        System.out.print("password: ");
        String password = reader.readLine();

        return new Credentials(username, password, null);
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
}