package view;

import dao.TaskDAO;
import model.User;

import javax.swing.*;

public class DashboardUser extends JFrame {

private User user;

private JLabel lblWelcome;
private JLabel lblTotalTask;
private JLabel lblDoneTask;
private JLabel lblUndoneTask;

private JButton btnTask;
private JButton btnProfile;
private JButton btnLogout;

public DashboardUser(User user) {

    this.user = user;

    setTitle("Dashboard User");

    setSize(650,450);

    setLocationRelativeTo(null);

    setDefaultCloseOperation(
            JFrame.EXIT_ON_CLOSE
    );

    setLayout(null);

    TaskDAO dao =
            new TaskDAO();

    int total =
            dao.countTaskByUser(
                    user.getIdUser()
            );

    int done =
            dao.countDoneTask(
                    user.getIdUser()
            );

    int undone =
            dao.countUndoneTask(
                    user.getIdUser()
            );

    lblWelcome =
            new JLabel(
                    "Selamat Datang, "
                            + user.getNama()
            );

    lblWelcome.setBounds(
            30,
            20,
            300,
            30
    );

    add(lblWelcome);

    lblTotalTask =
            new JLabel(
                    "Total Task : " + total
            );

    lblTotalTask.setBounds(
            30,
            70,
            250,
            25
    );

    add(lblTotalTask);

    lblDoneTask =
            new JLabel(
                    "Task Selesai : " + done
            );

    lblDoneTask.setBounds(
            30,
            100,
            250,
            25
    );

    add(lblDoneTask);

    lblUndoneTask =
            new JLabel(
                    "Task Belum Selesai : "
                            + undone
            );

    lblUndoneTask.setBounds(
            30,
            130,
            250,
            25
    );

    add(lblUndoneTask);

    btnTask =
            new JButton(
                    "Kelola Tugas"
            );

    btnTask.setBounds(
            30,
            220,
            150,
            40
    );

    add(btnTask);

    btnProfile =
            new JButton(
                    "Profil"
            );

    btnProfile.setBounds(
            230,
            220,
            150,
            40
    );

    add(btnProfile);

    btnLogout =
            new JButton(
                    "Logout"
            );

    btnLogout.setBounds(
            430,
            220,
            150,
            40
    );

    add(btnLogout);

    // Kelola Tugas
    btnTask.addActionListener(e -> {

        new TaskListForm(this.user);

    });

    // Profil
    btnProfile.addActionListener(e -> {

        JOptionPane.showMessageDialog(
                this,
                "Membuka Profil..."
        );

        new ProfileForm(this.user);

    });

    // Logout
    btnLogout.addActionListener(e -> {

        new LoginForm();

        dispose();

    });

    setVisible(true);
}


}
