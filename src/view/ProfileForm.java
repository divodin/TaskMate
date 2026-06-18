package view;

import dao.UserDAO;
import model.User;

import javax.swing.*;

public class ProfileForm extends JFrame {

    private User user;

    private JTextField txtNama;
    private JTextField txtEmail;
    private JPasswordField txtPassword;

    private JButton btnSave;

    public ProfileForm(User user) {

        this.user = user;

        setTitle("Profile");

        setSize(450,350);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLayout(null);

        JLabel lblNama =
                new JLabel("Nama");

        lblNama.setBounds(
                30,
                30,
                100,
                25
        );

        add(lblNama);

        txtNama =
                new JTextField(
                        user.getNama()
                );

        txtNama.setBounds(
                140,
                30,
                220,
                25
        );

        add(txtNama);

        JLabel lblEmail =
                new JLabel("Email");

        lblEmail.setBounds(
                30,
                80,
                100,
                25
        );

        add(lblEmail);

        txtEmail =
                new JTextField(
                        user.getEmail()
                );

        txtEmail.setBounds(
                140,
                80,
                220,
                25
        );

        add(txtEmail);

        JLabel lblPassword =
                new JLabel("Password");

        lblPassword.setBounds(
                30,
                130,
                100,
                25
        );

        add(lblPassword);

        txtPassword =
                new JPasswordField();

        txtPassword.setBounds(
                140,
                130,
                220,
                25
        );

        add(txtPassword);

        btnSave =
                new JButton(
                        "Simpan"
                );

        btnSave.setBounds(
                140,
                220,
                120,
                35
        );

        add(btnSave);

        btnSave.addActionListener(e -> {

            saveProfile();

        });

        setVisible(true);
    }

    private void saveProfile() {

        user.setNama(
                txtNama.getText()
        );

        user.setEmail(
                txtEmail.getText()
        );

        user.setPassword(
                String.valueOf(
                        txtPassword.getPassword()
                )
        );

        UserDAO dao =
                new UserDAO();

        boolean berhasil =
                dao.updateProfile(user);

        if(berhasil){

            JOptionPane.showMessageDialog(
                    this,
                    "Profil berhasil diupdate"
            );

        }else{

            JOptionPane.showMessageDialog(
                    this,
                    "Profil gagal diupdate"
            );
        }
    }
}