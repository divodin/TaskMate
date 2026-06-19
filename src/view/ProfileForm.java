package view;

import dao.UserDAO;
import model.User;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class ProfileForm extends JDialog {
    private JTextField txtNama, txtEmail;
    private JPasswordField txtPassword;
    private User activeUser;
    private UserDAO userDAO;

    public ProfileForm(JFrame parent, User user) {
        super(parent, "Profil Pengguna", true);
        this.activeUser = user;
        this.userDAO = new UserDAO();

        setSize(350, 300);
        setLocationRelativeTo(parent);
        setLayout(null);

        JLabel lblUsername = new JLabel("Username: " + activeUser.getUsername());
        lblUsername.setBounds(20, 20, 300, 25);
        add(lblUsername); // Username sengaja dibuat label agar tidak bisa diubah (Best Practice)

        JLabel lblNama = new JLabel("Nama Lengkap:");
        lblNama.setBounds(20, 60, 100, 25);
        add(lblNama);

        txtNama = new JTextField(activeUser.getNama());
        txtNama.setBounds(130, 60, 180, 25);
        add(txtNama);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20, 100, 100, 25);
        add(lblEmail);

        txtEmail = new JTextField(activeUser.getEmail());
        txtEmail.setBounds(130, 100, 180, 25);
        add(txtEmail);

        JLabel lblPass = new JLabel("Password Baru:");
        lblPass.setBounds(20, 140, 100, 25);
        add(lblPass);

        txtPassword = new JPasswordField(activeUser.getPassword());
        txtPassword.setBounds(130, 140, 180, 25);
        add(txtPassword);

        JButton btnSimpan = new JButton("Simpan Perubahan");
        btnSimpan.setBounds(80, 200, 160, 35);
        add(btnSimpan);

        btnSimpan.addActionListener((ActionEvent e) -> updateProfil());
    }

    private void updateProfil() {
        if (txtNama.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama dan Email tidak boleh kosong!");
            return;
        }

        // Set data baru ke objek user saat ini
        activeUser.setNama(txtNama.getText().trim());
        activeUser.setEmail(txtEmail.getText().trim());
        activeUser.setPassword(new String(txtPassword.getPassword()));

        // Panggil DAO (Poin 28)
        if (userDAO.updateProfile(activeUser)) {
            JOptionPane.showMessageDialog(this, "Profil berhasil diperbarui!");
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal memperbarui profil.");
        }
    }
}