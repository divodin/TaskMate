package view;

import dao.UserDAO;
import model.User;
import javax.swing.*;

public class RegisterForm extends JFrame {
    private JTextField txtNama, txtEmail, txtUsername;
    private JPasswordField txtPassword, txtConfirm;
    private JButton btnRegister, btnBack;

    public RegisterForm() {
        setTitle("Register TaskMate");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblNama = new JLabel("Nama:"); lblNama.setBounds(50, 30, 100, 25); add(lblNama);
        txtNama = new JTextField(); txtNama.setBounds(160, 30, 180, 25); add(txtNama);

        JLabel lblEmail = new JLabel("Email:"); lblEmail.setBounds(50, 70, 100, 25); add(lblEmail);
        txtEmail = new JTextField(); txtEmail.setBounds(160, 70, 180, 25); add(txtEmail);

        JLabel lblUser = new JLabel("Username:"); lblUser.setBounds(50, 110, 100, 25); add(lblUser);
        txtUsername = new JTextField(); txtUsername.setBounds(160, 110, 180, 25); add(txtUsername);

        JLabel lblPass = new JLabel("Password:"); lblPass.setBounds(50, 150, 100, 25); add(lblPass);
        txtPassword = new JPasswordField(); txtPassword.setBounds(160, 150, 180, 25); add(txtPassword);

        JLabel lblConf = new JLabel("Konfirmasi:"); lblConf.setBounds(50, 190, 100, 25); add(lblConf);
        txtConfirm = new JPasswordField(); txtConfirm.setBounds(160, 190, 180, 25); add(txtConfirm);

        btnBack = new JButton("Back"); btnBack.setBounds(80, 280, 100, 35); add(btnBack);
        btnRegister = new JButton("Register"); btnRegister.setBounds(200, 280, 120, 35); add(btnRegister);

        btnBack.addActionListener(e -> {
            this.dispose();
            new LoginForm().setVisible(true);
        });

        btnRegister.addActionListener(e -> prosesDaftar());
        setVisible(true);
    }

    private void prosesDaftar() {
        String p = new String(txtPassword.getPassword());
        String c = new String(txtConfirm.getPassword());

        if (!p.equals(c)) {
            JOptionPane.showMessageDialog(this, "Password tidak cocok!");
            return;
        }

        User u = new User();
        u.setNama(txtNama.getText());
        u.setEmail(txtEmail.getText());
        u.setUsername(txtUsername.getText());
        u.setPassword(p);
        u.setRole("USER");

        if (new UserDAO().insertUser(u)) {
            JOptionPane.showMessageDialog(this, "Berhasil! Silakan Login.");
            this.dispose();
            new LoginForm().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Gagal Daftar!");
        }
    }
}