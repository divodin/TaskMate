package view;

import dao.TaskDAO;
import model.Task;
import model.User;

import javax.swing.*;
import java.sql.Date;

public class TaskForm extends JFrame {

    private User user;

    private JTextField txtJudul;
    private JTextArea txtDeskripsi;
    private JTextField txtDeadline;

    private JComboBox<String> cbPrioritas;
    private JComboBox<String> cbStatus;

    private JButton btnSimpan;

    public TaskForm(User user) {

        this.user = user;

        setTitle("Tambah Tugas");

        setSize(500,500);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLayout(null);

        JLabel lblJudul =
                new JLabel("Judul");

        lblJudul.setBounds(
                30,30,100,25
        );

        add(lblJudul);

        txtJudul =
                new JTextField();

        txtJudul.setBounds(
                150,30,250,25
        );

        add(txtJudul);

        JLabel lblDeskripsi =
                new JLabel("Deskripsi");

        lblDeskripsi.setBounds(
                30,80,100,25
        );

        add(lblDeskripsi);

        txtDeskripsi =
                new JTextArea();

        JScrollPane scroll =
                new JScrollPane(
                        txtDeskripsi
                );

        scroll.setBounds(
                150,80,250,100
        );

        add(scroll);

        JLabel lblDeadline =
                new JLabel(
                        "Deadline"
                );

        lblDeadline.setBounds(
                30,210,100,25
        );

        add(lblDeadline);

        txtDeadline =
                new JTextField();

        txtDeadline.setBounds(
                150,210,250,25
        );

        add(txtDeadline);

        JLabel lblPrioritas =
                new JLabel(
                        "Prioritas"
                );

        lblPrioritas.setBounds(
                30,260,100,25
        );

        add(lblPrioritas);

        cbPrioritas =
                new JComboBox<>();

        cbPrioritas.addItem(
                "TINGGI"
        );

        cbPrioritas.addItem(
                "SEDANG"
        );

        cbPrioritas.addItem(
                "RENDAH"
        );

        cbPrioritas.setBounds(
                150,260,250,25
        );

        add(cbPrioritas);

        JLabel lblStatus =
                new JLabel(
                        "Status"
                );

        lblStatus.setBounds(
                30,310,100,25
        );

        add(lblStatus);

        cbStatus =
                new JComboBox<>();

        cbStatus.addItem(
                "TO DO"
        );

        cbStatus.addItem(
                "IN PROGRESS"
        );

        cbStatus.addItem(
                "DONE"
        );

        cbStatus.setBounds(
                150,310,250,25
        );

        add(cbStatus);

        btnSimpan =
                new JButton(
                        "Simpan"
                );

        btnSimpan.setBounds(
                180,380,120,40
        );

        add(btnSimpan);

        btnSimpan.addActionListener(
                e -> simpanTask()
        );

        setVisible(true);
    }

    private void simpanTask() {

        try {

            Task task =
                    new Task();

            task.setIdUser(
                    user.getIdUser()
            );

            // sementara kategori default
            task.setIdCategory(1);

            task.setJudul(
                    txtJudul.getText()
            );

            task.setDeskripsi(
                    txtDeskripsi.getText()
            );

            task.setDeadline(
                    Date.valueOf(
                            txtDeadline.getText()
                    )
            );

            task.setPrioritas(
                    cbPrioritas
                            .getSelectedItem()
                            .toString()
            );

            task.setStatus(
                    cbStatus
                            .getSelectedItem()
                            .toString()
            );

            TaskDAO dao =
                    new TaskDAO();

            boolean berhasil =
                    dao.addTask(task);

            if(berhasil){

                JOptionPane.showMessageDialog(
                        this,
                        "Task berhasil disimpan"
                );

            }else{

                JOptionPane.showMessageDialog(
                        this,
                        "Task gagal disimpan"
                );
            }

        } catch(Exception e){

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }
}