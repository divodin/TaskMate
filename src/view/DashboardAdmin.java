package view;

import dao.TaskDAO;
import dao.UserDAO;
import model.User;
import javax.swing.*;
import java.awt.*;

public class DashboardAdmin extends JFrame {
    private User activeAdmin;
    private TaskDAO taskDAO;
    private UserDAO userDAO;

    public DashboardAdmin(User user) {
        this.activeAdmin = user;
        this.taskDAO = new TaskDAO();
        this.userDAO = new UserDAO();

        setTitle("Pusat Kendali Admin - " + activeAdmin.getNama());
        setSize(500, 400); // Sedikit diperbesar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Judul
        JLabel lblJudul = new JLabel("DASHBOARD ADMINISTRATOR", SwingConstants.CENTER);
        lblJudul.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblJudul, BorderLayout.NORTH);

        // Statistik
        JPanel panelStats = new JPanel(new GridLayout(4, 1, 10, 10));
        panelStats.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelStats.add(new JLabel("Total Pengguna Aktif : " + userDAO.countUser()));
        panelStats.add(new JLabel("Total Seluruh Tugas : " + taskDAO.countAllTask()));
        panelStats.add(new JLabel("Total Tugas Selesai : " + taskDAO.countAllDoneTask()));
        panelStats.add(new JLabel("Total Tugas Tertunda : " + taskDAO.countAllUndoneTask()));
        add(panelStats, BorderLayout.CENTER);

        // Panel Bawah untuk Tombol Aksi
        JPanel panelBawah = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));

        JButton btnKelolaUser = new JButton("Kelola User");
        JButton btnLogout = new JButton("Logout");

        // Aksi Kelola User
        btnKelolaUser.addActionListener(e -> {
            new UserListForm().setVisible(true); // Membuka modul yang kamu buat
        });

        // Aksi Logout
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin keluar?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                this.dispose();
                new LoginForm().setVisible(true);
            }
        });

        panelBawah.add(btnKelolaUser);
        panelBawah.add(btnLogout);
        add(panelBawah, BorderLayout.SOUTH);
    }
}