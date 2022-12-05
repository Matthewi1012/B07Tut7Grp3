package com.example.admintoolsUTSC;

public class admin_presenter {


    private admin_model model;
    private Admin_Login view;
    private String userid;
    public admin_presenter(admin_model model, Admin_Login view) {
        this.model = model;
        this.view = view;
    }


    public void login(String email, String password) {

        model.login(email, password, (String userID) -> {
            if (userID == null) view.displayError();
            else view.goToStudentPage(userid);
        });
    }
}
