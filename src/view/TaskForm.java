package view;

import dao.TaskDAO;
import model.Task;
import model.User;
import com.toedter.calendar.JDateChooser; // Import library JCalendar
import javax.swing.*;
import java.awt.event.ActionEvent;

public class TaskForm extends JDialog {
    private JTextField txtJudul;
    private JTextArea txtDeskripsi;
    private JComboBox<String> cbPrioritas, cbStatus;
    private JDateChooser dateChooser;

    private Task task;
    private User user;
    private TaskDAO taskDAO;

    // UBAH 1: Parameter parent diubah menjadi JFrame umum, bukan spesifik DashboardUser
    public TaskForm(JFrame parent, User user, Task taskToEdit) {
        // 'true' di sini berarti Modal (mengunci jendela parent sampai form ini ditutup)
        super(parent, "Form Tugas", true);
        this.user = user;
        this.task = taskToEdit;
        this.taskDAO = new TaskDAO();

        setSize(400, 450);
        setLocationRelativeTo(parent);
        setLayout(null);

        JLabel lblJudul = new JLabel("Judul:");
        lblJudul.setBounds(20, 20, 100, 25);
        add(lblJudul);

        txtJudul = new JTextField();
        txtJudul.setBounds(120, 20, 240, 25);
        add(txtJudul);

        JLabel lblDesc = new JLabel("Deskripsi:");
        lblDesc.setBounds(20, 60, 100, 25);
        add(lblDesc);

        txtDeskripsi = new JTextArea();
        JScrollPane scrollDesc = new JScrollPane(txtDeskripsi);
        scrollDesc.setBounds(120, 60, 240, 80);
        add(scrollDesc);

        JLabel lblPrio = new JLabel("Prioritas:");
        lblPrio.setBounds(20, 160, 100, 25);
        add(lblPrio);

        String[] prioritas = {"LOW", "MEDIUM", "HIGH"};
        cbPrioritas = new JComboBox<>(prioritas);
        cbPrioritas.setBounds(120, 160, 240, 25);
        add(cbPrioritas);

        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setBounds(20, 200, 100, 25);
        add(lblStatus);

        String[] statusArr = {"TODO", "IN PROGRESS", "DONE"};
        cbStatus = new JComboBox<>(statusArr);
        cbStatus.setBounds(120, 200, 240, 25);
        add(cbStatus);

        JLabel lblDeadline = new JLabel("Deadline:");
        lblDeadline.setBounds(20, 240, 100, 25);
        add(lblDeadline);

        // Komponen Kalender Visual (JDateChooser)
        dateChooser = new JDateChooser();
        dateChooser.setBounds(120, 240, 240, 25);
        dateChooser.setDateFormatString("yyyy-MM-dd");
        add(dateChooser);

        JButton btnSimpan = new JButton("Simpan");
        btnSimpan.setBounds(150, 300, 100, 35);
        add(btnSimpan);

        // UBAH 2: Logika Auto-Fill saat form dibuka untuk Edit Data
        if (this.task != null) {
            setTitle("Edit Tugas"); // Ubah judul jendela
            txtJudul.setText(this.task.getJudul());
            txtDeskripsi.setText(this.task.getDeskripsi());

            // Mengatur combobox sesuai data yang ada
            if (this.task.getPrioritas() != null) cbPrioritas.setSelectedItem(this.task.getPrioritas());
            if (this.task.getStatus() != null) cbStatus.setSelectedItem(this.task.getStatus());

            // Memasukkan tanggal dari database ke JDateChooser
            if (this.task.getDeadline() != null) {
                dateChooser.setDate(this.task.getDeadline());
            }
        }

        btnSimpan.addActionListener((ActionEvent e) -> simpanTugas());
    }

    private void simpanTugas() {
        if (txtJudul.getText().trim().isEmpty() || dateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Judul dan Deadline wajib diisi!");
            return;
        }

        // Konversi tipe tanggal Kalender Visual (util.Date) menjadi tipe SQL (sql.Date)
        java.util.Date tanggalPilih = dateChooser.getDate();
        java.sql.Date sqlDeadline = new java.sql.Date(tanggalPilih.getTime());

        boolean isUpdate = (task != null);
        if (!isUpdate) {
            task = new Task();
        }

        task.setIdUser(user.getIdUser());
        task.setIdCategory(1); // Default kategori sementara
        task.setJudul(txtJudul.getText().trim());
        task.setDeskripsi(txtDeskripsi.getText().trim());
        task.setPrioritas(cbPrioritas.getSelectedItem().toString());
        task.setStatus(cbStatus.getSelectedItem().toString());
        task.setDeadline(sqlDeadline);

        boolean sukses;
        if (isUpdate) {
            sukses = taskDAO.updateTask(task);
        } else {
            sukses = taskDAO.addTask(task);
        }

        if (sukses) {
            JOptionPane.showMessageDialog(this, "Tugas berhasil disimpan!");
            // UBAH 3: Tidak perlu memanggil muatData() parent di sini.
            // Karena form ini JDialog modal, saat di-dispose, parent otomatis jalan lagi.
            this.dispose(); // Tutup form
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan tugas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}