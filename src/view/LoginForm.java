package view;

import dao.UserDAO;
import model.User;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class LoginForm extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnRegister;

    public LoginForm() {
        setTitle("Login TaskMate");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblUser = new JLabel("Username:"); lblUser.setBounds(30, 40, 80, 25); add(lblUser);
        txtUsername = new JTextField(); txtUsername.setBounds(120, 40, 180, 25); add(txtUsername);

        JLabel lblPass = new JLabel("Password:"); lblPass.setBounds(30, 80, 80, 25); add(lblPass);
        txtPassword = new JPasswordField(); txtPassword.setBounds(120, 80, 180, 25); add(txtPassword);

        btnLogin = new JButton("Login"); btnLogin.setBounds(120, 130, 80, 30); add(btnLogin);
        btnRegister = new JButton("Register"); btnRegister.setBounds(210, 130, 90, 30); add(btnRegister);

        btnLogin.addActionListener(e -> prosesLogin());
        btnRegister.addActionListener(e -> {
            this.dispose();
            new RegisterForm().setVisible(true);
        });
    }

    private void prosesLogin() {
        String u = txtUsername.getText().trim();
        String p = new String(txtPassword.getPassword());
        UserDAO dao = new UserDAO();
        User user = dao.login(u, p);

        if (user != null) {
            this.dispose();
            if (user.getRole().equalsIgnoreCase("ADMIN")) {
                new DashboardAdmin(user).setVisible(true);
            } else {
                new TaskListForm(user).setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Login Gagal!");
        }
    }
}