package view;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {

private JTextField txtUsername;
private JPasswordField txtPassword;

private JButton btnLogin;
private JButton btnRegister;

public LoginForm() {

    initComponents();

    setVisible(true);
}

private void initComponents() {

    setTitle("TaskMate - Login");

    setSize(450, 320);

    setLocationRelativeTo(null);

    setDefaultCloseOperation(
            JFrame.EXIT_ON_CLOSE
    );

    setLayout(null);

    JLabel lblTitle =
            new JLabel("TASKMATE LOGIN");

    lblTitle.setFont(
            new Font(
                    "Arial",
                    Font.BOLD,
                    20
            )
    );

    lblTitle.setBounds(
            120,
            20,
            250,
            30
    );

    add(lblTitle);

    JLabel lblUsername =
            new JLabel("Username");

    lblUsername.setBounds(
            50,
            90,
            100,
            25
    );

    add(lblUsername);

    txtUsername =
            new JTextField();

    txtUsername.setBounds(
            160,
            90,
            200,
            25
    );

    add(txtUsername);

    JLabel lblPassword =
            new JLabel("Password");

    lblPassword.setBounds(
            50,
            130,
            100,
            25
    );

    add(lblPassword);

    txtPassword =
            new JPasswordField();

    txtPassword.setBounds(
            160,
            130,
            200,
            25
    );

    add(txtPassword);

    btnLogin =
            new JButton("Login");

    btnLogin.setBounds(
            90,
            210,
            100,
            35
    );

    add(btnLogin);

    btnRegister =
            new JButton("Register");

    btnRegister.setBounds(
            230,
            210,
            100,
            35
    );

    add(btnRegister);

    btnLogin.addActionListener(e -> {

        loginUser();

    });

    btnRegister.addActionListener(e -> {

        new RegisterForm();

    });
}

private void loginUser() {

    String username =
            txtUsername.getText().trim();

    String password =
            String.valueOf(
                    txtPassword.getPassword()
            );

    if(username.isEmpty() ||
            password.isEmpty()) {

        JOptionPane.showMessageDialog(
                this,
                "Username dan Password wajib diisi!"
        );

        return;
    }

    UserDAO dao =
            new UserDAO();

    User user =
            dao.login(
                    username,
                    password
            );

    if(user != null){

        JOptionPane.showMessageDialog(
                this,
                "Login Berhasil!"
        );

        if(user.getRole().equals("ADMIN")){

            new DashboardAdmin();

        }else{

            new DashboardUser(user);

        }

        dispose();

    }else{

        JOptionPane.showMessageDialog(
                this,
                "Username atau Password Salah!"
        );
    }
}


}
