package controller;

import utils.Credentials;

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

        System.out.println(cred.getUsername());
        System.out.println(cred.getPassword());
        System.out.println(cred.getRole());

        switch(cred.getRole()) {
            case user -> new UserController().start();
            case admin -> new AdminController().start();
            default -> throw new RuntimeException("Invalid credentials");
        }
    }
}
