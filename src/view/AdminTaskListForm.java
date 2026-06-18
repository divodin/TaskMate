
package view;

import dao.TaskDAO;
import model.Task;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class AdminTaskListForm extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    

    public AdminTaskListForm() {

        setTitle("Semua Task User");

        setSize(900,500);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLayout(null);

        model =
                new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("User");
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
                20,
                840,
                380
        );

        add(scroll);

        loadData();

        setVisible(true);
    }

    private void loadData() {

        model.setRowCount(0);

        TaskDAO dao =
                new TaskDAO();

        ArrayList<Task> list =
                dao.getAllTask();

        for(Task task : list){

            model.addRow(
                    new Object[]{

                            task.getIdTask(),
                            task.getIdUser(),
                            task.getJudul(),
                            task.getDeadline(),
                            task.getPrioritas(),
                            task.getStatus()

                    }
            );
        }
    }
}