package view;

import dao.TaskDAO;
import dao.UserDAO;

import javax.swing.*;

public class DashboardAdmin extends JFrame {

private JLabel lblTotalUser;
private JLabel lblTotalTask;
private JLabel lblDoneTask;
private JLabel lblUndoneTask;

private JButton btnUser;
private JButton btnTask;
private JButton btnLogout;

public DashboardAdmin() {

    setTitle("Dashboard Admin");

    setSize(700,450);

    setLocationRelativeTo(null);

    setDefaultCloseOperation(
            JFrame.EXIT_ON_CLOSE
    );

    setLayout(null);

    UserDAO userDAO =
            new UserDAO();

    TaskDAO taskDAO =
            new TaskDAO();

    int totalUser =
            userDAO.countUser();

    int totalTask =
            taskDAO.countAllTask();

    int doneTask =
            taskDAO.countAllDoneTask();

    int undoneTask =
            taskDAO.countAllUndoneTask();

    JLabel lblTitle =
            new JLabel(
                    "DASHBOARD ADMIN"
            );

    lblTitle.setBounds(
            250,
            20,
            250,
            30
    );

    add(lblTitle);

    lblTotalUser =
            new JLabel(
                    "Total User : "
                            + totalUser
            );

    lblTotalUser.setBounds(
            50,
            70,
            250,
            25
    );

    add(lblTotalUser);

    lblTotalTask =
            new JLabel(
                    "Total Task : "
                            + totalTask
            );

    lblTotalTask.setBounds(
            50,
            100,
            250,
            25
    );

    add(lblTotalTask);

    lblDoneTask =
            new JLabel(
                    "Task Selesai : "
                            + doneTask
            );

    lblDoneTask.setBounds(
            50,
            130,
            250,
            25
    );

    add(lblDoneTask);

    lblUndoneTask =
            new JLabel(
                    "Task Belum Selesai : "
                            + undoneTask
            );

    lblUndoneTask.setBounds(
            50,
            160,
            250,
            25
    );

    add(lblUndoneTask);

    btnUser =
            new JButton(
                    "Kelola User"
            );

    btnUser.setBounds(
            50,
            250,
            150,
            40
    );

    add(btnUser);

    btnTask =
            new JButton(
                    "Semua Task"
            );
            btnTask.addActionListener(e -> {

    new AdminTaskListForm();

});

    btnTask.setBounds(
            260,
            250,
            150,
            40
    );

    add(btnTask);

    btnLogout =
            new JButton(
                    "Logout"
            );

    btnLogout.setBounds(
            470,
            250,
            150,
            40
    );

    add(btnLogout);

    btnUser.addActionListener(e -> {

        new UserListForm();

    });

    btnLogout.addActionListener(e -> {

        new LoginForm();

        dispose();

    });

    setVisible(true);
}

}
