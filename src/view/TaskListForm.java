package view;

import dao.TaskDAO;
import model.Task;
import model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TaskListForm extends JFrame {
    private User user;
    private JTable table;
    private DefaultTableModel model;

    // Deklarasi komponen
    private JTextField txtCari;
    private JComboBox<String> cmbFilter;
    private JComboBox<String> cmbPrioritas;

    public TaskListForm(User user) {
        this.user = user;

        setTitle("Daftar Tugas - " + user.getNama());
        setSize(1000, 650); // Tinggi ditambah sedikit agar dua panel di atas muat
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // =========================================================
        // --- AREA UTARA (Panel Pencarian & Filter Dipisah) ---
        // =========================================================
        // Kita gunakan GridLayout 2 baris agar posisinya atas-bawah
        JPanel panelUtara = new JPanel(new GridLayout(2, 1, 5, 5));
        panelUtara.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        // 1. KOTAK PENCARIAN (Posisi Atas)
        JPanel panelCari = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        panelCari.setBorder(BorderFactory.createTitledBorder("Pencarian Data")); // Garis batas

        txtCari = new JTextField(30); // Kolom teks dipanjangkan
        JButton btnCari = new JButton("Cari Judul");

        panelCari.add(new JLabel("Masukkan Judul Tugas:"));
        panelCari.add(txtCari);
        panelCari.add(btnCari);

        // 2. KOTAK FILTER (Posisi Bawah)
        JPanel panelFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        panelFilter.setBorder(BorderFactory.createTitledBorder("Penyaringan Kategori")); // Garis batas

        cmbFilter = new JComboBox<>(new String[]{"SEMUA STATUS", "TO DO", "IN PROGRESS", "DONE"});
        cmbPrioritas = new JComboBox<>(new String[]{"SEMUA PRIORITAS", "LOW", "MEDIUM", "HIGH"});
        JButton btnFilter = new JButton("Terapkan Filter");

        panelFilter.add(new JLabel("Status Tugas:"));
        panelFilter.add(cmbFilter);
        panelFilter.add(new JLabel("   Tingkat Prioritas:")); // Spasi tambahan
        panelFilter.add(cmbPrioritas);
        panelFilter.add(btnFilter);

        // Masukkan kedua kotak tersebut ke panel utama bagian utara
        panelUtara.add(panelCari);
        panelUtara.add(panelFilter);

        add(panelUtara, BorderLayout.NORTH);
        // =========================================================

        // --- CENTER PANEL (Tabel) ---
        model = new DefaultTableModel(new String[]{"ID", "Judul", "Deadline", "Prioritas", "Status"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(scrollPane, BorderLayout.CENTER);

        // --- BOTTOM PANEL (Tombol Aksi) ---
        JPanel panelBawah = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        JButton btnTambah = new JButton("Tambah Tugas");
        JButton btnEdit = new JButton("Edit Tugas");
        JButton btnHapus = new JButton("Hapus Tugas");
        JButton btnRefresh = new JButton("Segarkan Tabel");
        JButton btnLogout = new JButton("Logout");

        // Styling Logout
        btnLogout.setBackground(new Color(220, 53, 69));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setOpaque(true);

        panelBawah.add(btnTambah);
        panelBawah.add(btnEdit);
        panelBawah.add(btnHapus);
        panelBawah.add(btnRefresh);
        panelBawah.add(btnLogout);
        add(panelBawah, BorderLayout.SOUTH);

        // --- EVENT LISTENERS ---
        loadData();

        btnTambah.addActionListener(e -> {
            new TaskForm(this, user, null).setVisible(true);
            loadData();
        });

        btnEdit.addActionListener(e -> editTask());
        btnHapus.addActionListener(e -> hapusTask());

        btnRefresh.addActionListener(e -> {
            txtCari.setText("");
            cmbFilter.setSelectedIndex(0);
            cmbPrioritas.setSelectedIndex(0);
            loadData();
        });

        btnCari.addActionListener(e -> cariData());
        btnFilter.addActionListener(e -> filterData());

        btnLogout.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Yakin ingin keluar?", "Logout", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                this.dispose();
                new LoginForm().setVisible(true);
            }
        });

        setVisible(true);
    }

    // --- LOGIKA LOAD DATA DEFAULT ---
    private void loadData() {
        model.setRowCount(0);
        TaskDAO dao = new TaskDAO();
        for (Task t : dao.getTaskByUser(user.getIdUser())) {
            model.addRow(new Object[]{t.getIdTask(), t.getJudul(), t.getDeadline(), t.getPrioritas(), t.getStatus()});
        }
    }

    // --- LOGIKA PENCARIAN BERDASARKAN TEKS ---
    private void cariData() {
        String keyword = txtCari.getText().trim().toLowerCase();
        model.setRowCount(0);
        TaskDAO dao = new TaskDAO();

        for (Task t : dao.getTaskByUser(user.getIdUser())) {
            if (t.getJudul().toLowerCase().contains(keyword) || keyword.isEmpty()) {
                model.addRow(new Object[]{t.getIdTask(), t.getJudul(), t.getDeadline(), t.getPrioritas(), t.getStatus()});
            }
        }
    }

    // --- LOGIKA FILTER BERDASARKAN STATUS DAN PRIORITAS ---
    private void filterData() {
        String statusDipilih = cmbFilter.getSelectedItem().toString();
        String prioritasDipilih = cmbPrioritas.getSelectedItem().toString();

        model.setRowCount(0);
        TaskDAO dao = new TaskDAO();

        for (Task t : dao.getTaskByUser(user.getIdUser())) {
            boolean cocokStatus = statusDipilih.equals("SEMUA STATUS") || t.getStatus().equalsIgnoreCase(statusDipilih);
            boolean cocokPrioritas = prioritasDipilih.equals("SEMUA PRIORITAS") || t.getPrioritas().equalsIgnoreCase(prioritasDipilih);

            if (cocokStatus && cocokPrioritas) {
                model.addRow(new Object[]{t.getIdTask(), t.getJudul(), t.getDeadline(), t.getPrioritas(), t.getStatus()});
            }
        }
    }

    // --- LOGIKA EDIT & HAPUS ---
    private void editTask() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih tugas yang ingin diedit!");
            return;
        }
        Task t = new Task();
        t.setIdTask((int) model.getValueAt(row, 0));
        new TaskForm(this, user, t).setVisible(true);
        loadData();
    }

    private void hapusTask() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih tugas yang ingin dihapus!");
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Yakin hapus tugas ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            new TaskDAO().deleteTask((int) model.getValueAt(row, 0));
            loadData();
        }
    }
}