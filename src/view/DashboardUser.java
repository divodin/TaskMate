package view;

import dao.TaskDAO;
import model.Task;
import model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class DashboardUser extends JFrame {
    private User activeUser;
    private TaskDAO taskDAO;
    private JTable tabelTugas;
    private DefaultTableModel tableModel;
    private JLabel lblStatistik;

    public DashboardUser(User user) {
        this.activeUser = user;
        this.taskDAO = new TaskDAO();

        setTitle("Dashboard User - " + activeUser.getNama());
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 1. HEADER PANEL
        JPanel panelHeader = new JPanel(new GridLayout(2, 1));
        panelHeader.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel lblWelcome = new JLabel("Selamat Datang, " + activeUser.getNama(), SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 20));
        lblStatistik = new JLabel("Memuat statistik...", SwingConstants.CENTER);
        panelHeader.add(lblWelcome);
        panelHeader.add(lblStatistik);
        add(panelHeader, BorderLayout.NORTH);

        // 2. TABEL DATA
        String[] kolom = {"ID", "Judul", "Kategori", "Deadline", "Prioritas", "Status"};
        tableModel = new DefaultTableModel(kolom, 0);
        tabelTugas = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelTugas);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Daftar Tugas Anda"));
        add(scrollPane, BorderLayout.CENTER);

        // 3. ACTION PANEL (Bawah) - Menggunakan FlowLayout agar pasti muncul
        JPanel panelAksi = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelAksi.setPreferredSize(new Dimension(900, 70));
        panelAksi.setBorder(BorderFactory.createEtchedBorder());

        JButton btnRefresh = new JButton("Refresh");
        JButton btnTambah = new JButton("Tambah Tugas");
        JButton btnEdit = new JButton("Edit Tugas");
        JButton btnHapus = new JButton("Hapus Tugas");
        JButton btnLogout = new JButton("Logout");

        // Styling Logout agar mencolok
        btnLogout.setOpaque(true);
        btnLogout.setBackground(new Color(220, 53, 69));
        btnLogout.setForeground(Color.WHITE);

        panelAksi.add(btnRefresh);
        panelAksi.add(btnTambah);
        panelAksi.add(btnEdit);
        panelAksi.add(btnHapus);
        panelAksi.add(btnLogout);

        add(panelAksi, BorderLayout.SOUTH);

        // 4. EVENT LISTENERS
        btnRefresh.addActionListener(e -> muatData());

        btnTambah.addActionListener(e -> {
            new TaskForm(this, activeUser, null).setVisible(true);
            muatData();
        });

        btnEdit.addActionListener(e -> {
            int baris = tabelTugas.getSelectedRow();
            if (baris == -1) {
                JOptionPane.showMessageDialog(this, "Pilih tugas yang ingin diedit!");
                return;
            }
            int idTask = (int) tableModel.getValueAt(baris, 0);
            Task taskToEdit = new Task();
            taskToEdit.setIdTask(idTask);
            new TaskForm(this, activeUser, taskToEdit).setVisible(true);
            muatData();
        });

        btnHapus.addActionListener(e -> hapusTugas());

        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin keluar?", "Konfirmasi Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                this.dispose();
                new LoginForm().setVisible(true);
            }
        });

        // Eksekusi awal
        muatData();
    }

    public void muatData() {
        tableModel.setRowCount(0);
        ArrayList<Task> listTugas = taskDAO.getTaskByUser(activeUser.getIdUser());
        for (Task t : listTugas) {
            tableModel.addRow(new Object[]{
                    t.getIdTask(), t.getJudul(), t.getIdCategory(),
                    t.getDeadline(), t.getPrioritas(), t.getStatus()
            });
        }
        updateStatistik();
    }

    private void updateStatistik() {
        int total = taskDAO.countTaskByUser(activeUser.getIdUser());
        int done = taskDAO.countDoneTask(activeUser.getIdUser());
        lblStatistik.setText("Total Tugas: " + total + " | Selesai: " + done);
    }

    private void hapusTugas() {
        int baris = tabelTugas.getSelectedRow();
        if (baris == -1) {
            JOptionPane.showMessageDialog(this, "Pilih tugas yang ingin dihapus!");
            return;
        }
        int idTask = (int) tableModel.getValueAt(baris, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus tugas ini?", "Hapus", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (taskDAO.deleteTask(idTask)) {
                JOptionPane.showMessageDialog(this, "Tugas berhasil dihapus!");
                muatData();
            }
        }
    }
}