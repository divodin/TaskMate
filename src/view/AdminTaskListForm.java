package view;

import dao.TaskDAO;
import model.Task;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class AdminTaskListForm extends JDialog {
    private JTable tabelSemuaTugas;
    private DefaultTableModel tableModel;
    private TaskDAO taskDAO;

    public AdminTaskListForm(JFrame parent) {
        super(parent, "Pemantauan Seluruh Tugas", true);
        this.taskDAO = new TaskDAO();

        setSize(700, 450);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Setup Tabel (Ada tambahan ID User pemilik tugas)
        String[] kolom = {"ID Task", "Pemilik (ID User)", "Judul Tugas", "Status", "Deadline"};
        tableModel = new DefaultTableModel(kolom, 0);
        tabelSemuaTugas = new JTable(tableModel);
        add(new JScrollPane(tabelSemuaTugas), BorderLayout.CENTER);

        muatSemuaTugas();
    }

    private void muatSemuaTugas() {
        tableModel.setRowCount(0);
        ArrayList<Task> listTask = taskDAO.getAllTask(); // Poin 35
        for (Task t : listTask) {
            tableModel.addRow(new Object[]{
                    t.getIdTask(), t.getIdUser(), t.getJudul(), t.getStatus(), t.getDeadline()
            });
        }
    }
}