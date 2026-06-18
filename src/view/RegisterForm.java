package view;

import dao.UserDAO;
import model.User;

import javax.swing.*;

public class RegisterForm extends JFrame {

    private JTextField txtNama;
    private JTextField txtEmail;
    private JTextField txtUsername;

    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;

    private JButton btnRegister;

    public RegisterForm() {

        setTitle("Register TaskMate");

        setSize(450, 400);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(null);

        // Judul
        JLabel lblTitle =
                new JLabel("REGISTER TASKMATE");

        lblTitle.setBounds(
                140,
                20,
                200,
                30
        );

        add(lblTitle);

        // Nama
        JLabel lblNama =
                new JLabel("Nama Lengkap");

        lblNama.setBounds(
                50,
                70,
                120,
                25
        );

        add(lblNama);

        txtNama =
                new JTextField();

        txtNama.setBounds(
                180,
                70,
                180,
                25
        );

        add(txtNama);

        // Email
        JLabel lblEmail =
                new JLabel("Email");

        lblEmail.setBounds(
                50,
                110,
                120,
                25
        );

        add(lblEmail);

        txtEmail =
                new JTextField();

        txtEmail.setBounds(
                180,
                110,
                180,
                25
        );

        add(txtEmail);

        // Username
        JLabel lblUsername =
                new JLabel("Username");

        lblUsername.setBounds(
                50,
                150,
                120,
                25
        );

        add(lblUsername);

        txtUsername =
                new JTextField();

        txtUsername.setBounds(
                180,
                150,
                180,
                25
        );

        add(txtUsername);

        // Password
        JLabel lblPassword =
                new JLabel("Password");

        lblPassword.setBounds(
                50,
                190,
                120,
                25
        );

        add(lblPassword);

        txtPassword =
                new JPasswordField();

        txtPassword.setBounds(
                180,
                190,
                180,
                25
        );

        add(txtPassword);

        // Konfirmasi Password
        JLabel lblConfirmPassword =
                new JLabel("Konfirmasi Password");

        lblConfirmPassword.setBounds(
                50,
                230,
                120,
                25
        );

        add(lblConfirmPassword);

        txtConfirmPassword =
                new JPasswordField();

        txtConfirmPassword.setBounds(
                180,
                230,
                180,
                25
        );

        add(txtConfirmPassword);

        // Tombol Register
        btnRegister =
                new JButton("Register");

        btnRegister.setBounds(
                150,
                300,
                120,
                35
        );

        add(btnRegister);

        // Event Tombol Register
        btnRegister.addActionListener(e -> {

            registerUser();

        });

        setVisible(true);
    }

    private void registerUser() {

        String nama =
                txtNama.getText();

        String email =
                txtEmail.getText();

        String username =
                txtUsername.getText();

        String password =
                String.valueOf(
                        txtPassword.getPassword()
                );

        String confirmPassword =
                String.valueOf(
                        txtConfirmPassword.getPassword()
                );

        // Validasi kosong
        if (nama.isEmpty() ||
            email.isEmpty() ||
            username.isEmpty() ||
            password.isEmpty() ||
            confirmPassword.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Semua field wajib diisi!"
            );

            return;
        }

        // Validasi password minimal 8 karakter
        if (password.length() < 8) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password minimal 8 karakter!"
            );

            return;
        }

        // Validasi konfirmasi password
        if (!password.equals(confirmPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password dan Konfirmasi Password tidak sama!"
            );

            return;
        }

        // Membuat objek User
        User user = new User();

        user.setNama(nama);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("USER");

        UserDAO dao =
                new UserDAO();

        boolean berhasil =
                dao.insertUser(user);

        if (berhasil) {

            JOptionPane.showMessageDialog(
                    this,
                    "Registrasi Berhasil!"
            );

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Registrasi Gagal!"
            );
        }
    }
}