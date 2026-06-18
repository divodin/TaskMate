package model;

public class User {

    private int idUser;
    private String nama;
    private String email;
    private String username;
    private String password;
    private String role;

    public User() {
    }

    public User(int idUser, String nama, String email,
                String username, String password, String role) {

        this.idUser = idUser;
        this.nama = nama;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void tampilDashboard() {
        System.out.println("Dashboard User");
    }
}