package controller;

import dao.LoginProcedureDAO;
import exception.DAOException;
import utils.Credentials;
import view.LoginView;

import java.io.IOException;

public class LoginController implements Controller{
    Credentials cred = null;

    @Override
    public void start() {
        try {
            cred = LoginView.authenticate();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        try {
            cred = new LoginProcedureDAO().execute(cred.getUsername(), cred.getPassword());
        } catch(DAOException e) {
            throw new RuntimeException(e);
        }
    }

    public Credentials getCred() {
        return cred;
    }
}
