package model;

public class Admin extends User {

    @Override
    public void tampilDashboard() {
        System.out.println("Dashboard Admin");
    }
}