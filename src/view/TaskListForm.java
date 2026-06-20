package view;

import dao.TaskDAO;
import model.Task;
import model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TaskListForm extends JFrame {
    private User user;
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtCari;
    private JComboBox<String> cmbFilter;

    public TaskListForm(User user) {
        this.user = user;

        setTitle("Daftar Tugas - " + user.getNama());
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // --- TOP PANEL (Cari & Filter) ---
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        txtCari = new JTextField(20);
        JButton btnCari = new JButton("Cari");
        cmbFilter = new JComboBox<>(new String[]{"SEMUA", "TO DO", "IN PROGRESS", "DONE"});
        JButton btnFilter = new JButton("Filter");

        panelTop.add(new JLabel("Cari:"));
        panelTop.add(txtCari);
        panelTop.add(btnCari);
        panelTop.add(new JLabel("Status:"));
        panelTop.add(cmbFilter);
        panelTop.add(btnFilter);
        add(panelTop, BorderLayout.NORTH);

        // --- CENTER PANEL (Tabel) ---
        model = new DefaultTableModel(new String[]{"ID", "Judul", "Deadline", "Prioritas", "Status"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- BOTTOM PANEL (Tombol Aksi) ---
        JPanel panelBawah = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        JButton btnTambah = new JButton("Tambah");
        JButton btnEdit = new JButton("Edit");
        JButton btnHapus = new JButton("Hapus");
        JButton btnRefresh = new JButton("Refresh");
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
        btnTambah.addActionListener(e -> { new TaskForm(this, user, null).setVisible(true); loadData(); });
        btnEdit.addActionListener(e -> editTask());
        btnHapus.addActionListener(e -> hapusTask());
        btnRefresh.addActionListener(e -> {
            txtCari.setText("");
            cmbFilter.setSelectedIndex(0);
            loadData();
        });

        // FIX: Sudah ditambahkan tanda kurung () dan diperbaiki panggilannya
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

    private void loadData() {
        model.setRowCount(0);
        TaskDAO dao = new TaskDAO();
        for (Task t : dao.getTaskByUser(user.getIdUser())) {
            model.addRow(new Object[]{t.getIdTask(), t.getJudul(), t.getDeadline(), t.getPrioritas(), t.getStatus()});
        }
    }

    // --- BARU: Method untuk menjalankan pencarian kata kunci judul ---
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

    // --- BARU: Method untuk menjalankan filter berdasarkan status ---
    private void filterData() {
        String statusDipilih = cmbFilter.getSelectedItem().toString();
        model.setRowCount(0);
        TaskDAO dao = new TaskDAO();

        for (Task t : dao.getTaskByUser(user.getIdUser())) {
            if (statusDipilih.equals("SEMUA") || t.getStatus().equalsIgnoreCase(statusDipilih)) {
                model.addRow(new Object[]{t.getIdTask(), t.getJudul(), t.getDeadline(), t.getPrioritas(), t.getStatus()});
            }
        }
    }

    private void editTask() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Pilih tugas!"); return; }
        Task t = new Task();
        t.setIdTask((int) model.getValueAt(row, 0));
        new TaskForm(this, user, t).setVisible(true);
        loadData();
    }

    private void hapusTask() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Pilih tugas!"); return; }
        if (JOptionPane.showConfirmDialog(this, "Yakin hapus?") == JOptionPane.YES_OPTION) {
            new TaskDAO().deleteTask((int) model.getValueAt(row, 0));
            loadData();
        }
    }
}