package view;

import dao.TaskDAO;
import model.Task;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class TaskListForm extends JFrame {

private User user;

private JTable table;
private DefaultTableModel model;

private JButton btnTambah;
private JButton btnEdit;
private JButton btnHapus;
private JButton btnRefresh;
private JButton btnCari;

private JTextField txtCari;

private JComboBox<String> cmbFilter;
private JButton btnFilter;

public TaskListForm(User user) {

    this.user = user;

    setTitle("Daftar Tugas");

    setSize(950,550);

    setLocationRelativeTo(null);

    setDefaultCloseOperation(
            JFrame.DISPOSE_ON_CLOSE
    );

    setLayout(null);

    txtCari = new JTextField();

    txtCari.setBounds(
            20,
            20,
            250,
            30
    );

    add(txtCari);

    btnCari =
            new JButton("Cari");

    btnCari.setBounds(
            290,
            20,
            100,
            30
    );

    add(btnCari);

    cmbFilter =
            new JComboBox<>();

    cmbFilter.addItem("SEMUA");
    cmbFilter.addItem("TO DO");
    cmbFilter.addItem("IN PROGRESS");
    cmbFilter.addItem("DONE");

    cmbFilter.setBounds(
            420,
            20,
            150,
            30
    );

    add(cmbFilter);

    btnFilter =
            new JButton("Filter");

    btnFilter.setBounds(
            600,
            20,
            100,
            30
    );

    add(btnFilter);

    model =
            new DefaultTableModel();

    model.addColumn("ID");
    model.addColumn("Judul");
    model.addColumn("Deadline");
    model.addColumn("Prioritas");
    model.addColumn("Status");

    table =
            new JTable(model);

    JScrollPane scroll =
            new JScrollPane(table);

    scroll.setBounds(
            20,
            70,
            890,
            300
    );

    add(scroll);

    btnTambah =
            new JButton("Tambah");

    btnTambah.setBounds(
            30,
            420,
            120,
            35
    );

    add(btnTambah);

    btnEdit =
            new JButton("Edit");

    btnEdit.setBounds(
            180,
            420,
            120,
            35
    );

    add(btnEdit);

    btnHapus =
            new JButton("Hapus");

    btnHapus.setBounds(
            330,
            420,
            120,
            35
    );

    add(btnHapus);

    btnRefresh =
            new JButton("Refresh");

    btnRefresh.setBounds(
            480,
            420,
            120,
            35
    );

    add(btnRefresh);

    loadData();

    btnTambah.addActionListener(e -> {

        new TaskForm(user);

    });

    btnRefresh.addActionListener(e -> {

        loadData();

    });

    btnHapus.addActionListener(e -> {

        hapusTask();

    });

    btnEdit.addActionListener(e -> {

        editTask();

    });

    btnCari.addActionListener(e -> {

        cariTask();

    });

    btnFilter.addActionListener(e -> {

        filterTask();

    });

    setVisible(true);
}

private void loadData() {

    model.setRowCount(0);

    TaskDAO dao =
            new TaskDAO();

    ArrayList<Task> list =
            dao.getTaskByUser(
                    user.getIdUser()
            );

    for(Task task : list){

        model.addRow(
                new Object[]{

                        task.getIdTask(),
                        task.getJudul(),
                        task.getDeadline(),
                        task.getPrioritas(),
                        task.getStatus()

                }
        );
    }
}

private void cariTask() {

    model.setRowCount(0);

    TaskDAO dao =
            new TaskDAO();

    ArrayList<Task> list =
            dao.searchTask(
                    txtCari.getText()
            );

    for(Task task : list){

        model.addRow(
                new Object[]{

                        task.getIdTask(),
                        task.getJudul(),
                        task.getDeadline(),
                        task.getPrioritas(),
                        task.getStatus()

                }
        );
    }
}

private void filterTask() {

    String status =
            cmbFilter
                    .getSelectedItem()
                    .toString();

    if(status.equals("SEMUA")){

        loadData();

        return;
    }

    model.setRowCount(0);

    TaskDAO dao =
            new TaskDAO();

    ArrayList<Task> list =
            dao.getTaskByStatus(
                    status
            );

    for(Task task : list){

        model.addRow(
                new Object[]{

                        task.getIdTask(),
                        task.getJudul(),
                        task.getDeadline(),
                        task.getPrioritas(),
                        task.getStatus()

                }
        );
    }
}

private void hapusTask() {

    int row =
            table.getSelectedRow();

    if(row == -1){

        JOptionPane.showMessageDialog(
                this,
                "Pilih task terlebih dahulu"
        );

        return;
    }

    int idTask =
            Integer.parseInt(
                    model.getValueAt(
                            row,
                            0
                    ).toString()
            );

    TaskDAO dao =
            new TaskDAO();

    boolean berhasil =
            dao.deleteTask(
                    idTask
            );

    if(berhasil){

        JOptionPane.showMessageDialog(
                this,
                "Task berhasil dihapus"
        );

        loadData();

    }else{

        JOptionPane.showMessageDialog(
                this,
                "Task gagal dihapus"
        );
    }
}

private void editTask() {

    int row =
            table.getSelectedRow();

    if(row == -1){

        JOptionPane.showMessageDialog(
                this,
                "Pilih task terlebih dahulu"
        );

        return;
    }

    String judulBaru =
            JOptionPane.showInputDialog(
                    this,
                    "Masukkan Judul Baru"
            );

    if(judulBaru == null ||
            judulBaru.isEmpty()){

        return;
    }

    int idTask =
            Integer.parseInt(
                    model.getValueAt(
                            row,
                            0
                    ).toString()
            );

    Task task =
            new Task();

    task.setIdTask(idTask);
    task.setJudul(judulBaru);
    task.setDeskripsi("");
    task.setDeadline(
            java.sql.Date.valueOf(
                    "2025-12-31"
            )
    );
    task.setPrioritas("SEDANG");
    task.setStatus("TO DO");

    TaskDAO dao =
            new TaskDAO();

    boolean berhasil =
            dao.updateTask(task);

    if(berhasil){

        JOptionPane.showMessageDialog(
                this,
                "Task berhasil diupdate"
        );

        loadData();

    }else{

        JOptionPane.showMessageDialog(
                this,
                "Task gagal diupdate"
        );
    }
}


}
