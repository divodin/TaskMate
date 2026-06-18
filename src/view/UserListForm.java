package view;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class UserListForm extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    private JButton btnRefresh;
    private JButton btnDelete;

    public UserListForm() {

        setTitle("Kelola User");

        setSize(800,500);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLayout(null);

        model =
                new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Nama");
        model.addColumn("Email");
        model.addColumn("Username");
        model.addColumn("Role");

        table =
                new JTable(model);

        JScrollPane scroll =
                new JScrollPane(table);

        scroll.setBounds(
                20,
                20,
                740,
                300
        );

        add(scroll);

        btnRefresh =
                new JButton("Refresh");

        btnRefresh.setBounds(
                150,
                370,
                120,
                40
        );

        add(btnRefresh);

        btnDelete =
                new JButton("Hapus");

        btnDelete.setBounds(
                500,
                370,
                120,
                40
        );

        add(btnDelete);

        loadData();

        btnRefresh.addActionListener(e -> {

            loadData();

        });

        btnDelete.addActionListener(e -> {

            deleteUser();

        });

        setVisible(true);
    }

    private void loadData() {

        model.setRowCount(0);

        UserDAO dao =
                new UserDAO();

        ArrayList<User> list =
                dao.findAll();

        for(User user : list){

            model.addRow(

                    new Object[]{

                            user.getIdUser(),
                            user.getNama(),
                            user.getEmail(),
                            user.getUsername(),
                            user.getRole()

                    }
            );
        }
    }

    private void deleteUser(){

        int row =
                table.getSelectedRow();

        if(row == -1){

            JOptionPane.showMessageDialog(
                    this,
                    "Pilih user terlebih dahulu"
            );

            return;
        }

        int idUser =
                Integer.parseInt(
                        model.getValueAt(
                                row,
                                0
                        ).toString()
                );

        UserDAO dao =
                new UserDAO();

        boolean berhasil =
                dao.deleteUser(idUser);

        if(berhasil){

            JOptionPane.showMessageDialog(
                    this,
                    "User berhasil dihapus"
            );

            loadData();

        }else{

            JOptionPane.showMessageDialog(
                    this,
                    "User gagal dihapus"
            );
        }
    }
}