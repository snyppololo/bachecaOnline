package controller;

import utils.Credentials;
import utils.LoggedUser;

public class MainController implements Controller {
    Credentials cred;

    @Override
    public void start() {
        LoginController loginController = new LoginController();
        loginController.start();
        cred = loginController.getCred();

        if(cred.getRole() == null) {
            throw new RuntimeException("Invalid credentials");
        }

        LoggedUser.setUsername(cred.getUsername());
        LoggedUser.setPassword(cred.getPassword());
        LoggedUser.setRole(cred.getRole());

        switch(LoggedUser.getRole()) {
            case user -> new UserController().start();
            case admin -> new AdminController().start();
            default -> throw new RuntimeException("Invalid credentials");
        }
    }
}
