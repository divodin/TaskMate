package dao;

import database.Koneksi;
import model.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TaskDAO {

// Tambah Task
public int countTodayDeadline(int idUser) {

    try {

        Connection conn =
                Koneksi.getConnection();

        String sql =
                "SELECT COUNT(*) total " +
                "FROM tasks " +
                "WHERE id_user=? " +
                "AND DATE(deadline)=CURDATE()";

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setInt(1,idUser);

        ResultSet rs =
                ps.executeQuery();

        if(rs.next()){

            return rs.getInt("total");
        }

    } catch(Exception e){

        System.out.println(e.getMessage());
    }

    return 0;

}
public int countWeekDeadline(int idUser) {

    try {

        Connection conn =
                Koneksi.getConnection();

        String sql =
                "SELECT COUNT(*) total " +
                "FROM tasks " +
                "WHERE id_user=? " +
                "AND deadline <= DATE_ADD(CURDATE(),INTERVAL 7 DAY)";

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setInt(1,idUser);

        ResultSet rs =
                ps.executeQuery();

        if(rs.next()){

            return rs.getInt("total");
        }

    } catch(Exception e){

        System.out.println(e.getMessage());
    }

    return 0;
}
public ArrayList<Task> getTaskByStatus(
        String status
) {

    ArrayList<Task> list =
            new ArrayList<>();

    try {

        Connection conn =
                Koneksi.getConnection();

        String sql =
                "SELECT * FROM tasks " +
                "WHERE status=?";

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setString(
                1,
                status
        );

        ResultSet rs =
                ps.executeQuery();

        while(rs.next()){

            Task task =
                    new Task();

            task.setIdTask(
                    rs.getInt("id_task")
            );

            task.setJudul(
                    rs.getString("judul")
            );

            task.setDeadline(
                    rs.getDate("deadline")
            );

            task.setPrioritas(
                    rs.getString("prioritas")
            );

            task.setStatus(
                    rs.getString("status")
            );

            list.add(task);
        }

    } catch(Exception e){

        System.out.println(e.getMessage());
    }

    return list;
}
public int countDeadlineSoon(int idUser) {

    try {

        Connection conn =
                Koneksi.getConnection();

        String sql =
                "SELECT COUNT(*) total " +
                "FROM tasks " +
                "WHERE id_user=? " +
                "AND deadline <= DATE_ADD(CURDATE(), INTERVAL 3 DAY) " +
                "AND status!='DONE'";

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setInt(1, idUser);

        ResultSet rs =
                ps.executeQuery();

        if(rs.next()) {

            return rs.getInt("total");
        }

    } catch(Exception e){

        System.out.println(e.getMessage());
    }

    return 0;
}
public boolean addTask(Task task) {

    try {

        Connection conn =
                Koneksi.getConnection();

        String sql =
                "INSERT INTO tasks " +
                "(id_user,id_category,judul,deskripsi,deadline,prioritas,status) " +
                "VALUES(?,?,?,?,?,?,?)";

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setInt(1, task.getIdUser());
        ps.setInt(2, task.getIdCategory());
        ps.setString(3, task.getJudul());
        ps.setString(4, task.getDeskripsi());
        ps.setDate(5, task.getDeadline());
        ps.setString(6, task.getPrioritas());
        ps.setString(7, task.getStatus());

        return ps.executeUpdate() > 0;

    } catch (Exception e) {

        System.out.println(
                "Error Add Task : "
                        + e.getMessage()
        );
    }

    return false;
}

// Update Task
public boolean updateTask(Task task) {

    try {

        Connection conn =
                Koneksi.getConnection();

        String sql =
                "UPDATE tasks SET " +
                "judul=?, " +
                "deskripsi=?, " +
                "deadline=?, " +
                "prioritas=?, " +
                "status=? " +
                "WHERE id_task=?";

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setString(1, task.getJudul());
        ps.setString(2, task.getDeskripsi());
        ps.setDate(3, task.getDeadline());
        ps.setString(4, task.getPrioritas());
        ps.setString(5, task.getStatus());
        ps.setInt(6, task.getIdTask());

        return ps.executeUpdate() > 0;

    } catch (Exception e) {

        System.out.println(
                "Error Update Task : "
                        + e.getMessage()
        );
    }

    return false;
}

// Hapus Task
public boolean deleteTask(int idTask) {

    try {

        Connection conn =
                Koneksi.getConnection();

        String sql =
                "DELETE FROM tasks WHERE id_task=?";

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setInt(1, idTask);

        return ps.executeUpdate() > 0;

    } catch (Exception e) {

        System.out.println(
                "Error Delete Task : "
                        + e.getMessage()
        );
    }

    return false;
}

// Semua Task
public ArrayList<Task> getAllTask() {

    ArrayList<Task> list =
            new ArrayList<>();

    try {

        Connection conn =
                Koneksi.getConnection();

        String sql =
                "SELECT * FROM tasks";

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ResultSet rs =
                ps.executeQuery();

        while(rs.next()){

            Task task =
                    new Task();

            task.setIdTask(
                    rs.getInt("id_task")
            );

            task.setIdUser(
                    rs.getInt("id_user")
            );

            task.setIdCategory(
                    rs.getInt("id_category")
            );

            task.setJudul(
                    rs.getString("judul")
            );

            task.setDeskripsi(
                    rs.getString("deskripsi")
            );

            task.setDeadline(
                    rs.getDate("deadline")
            );

            task.setPrioritas(
                    rs.getString("prioritas")
            );

            task.setStatus(
                    rs.getString("status")
            );

            list.add(task);
        }

    } catch(Exception e){

        System.out.println(e.getMessage());
    }

    return list;
}

// Task Berdasarkan User
public ArrayList<Task> getTaskByUser(int idUser) {

    ArrayList<Task> list =
            new ArrayList<>();

    try {

        Connection conn =
                Koneksi.getConnection();

        String sql =
                "SELECT * FROM tasks WHERE id_user=?";

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setInt(1, idUser);

        ResultSet rs =
                ps.executeQuery();

        while(rs.next()){

            Task task =
                    new Task();

            task.setIdTask(
                    rs.getInt("id_task")
            );

            task.setIdUser(
                    rs.getInt("id_user")
            );

            task.setIdCategory(
                    rs.getInt("id_category")
            );

            task.setJudul(
                    rs.getString("judul")
            );

            task.setDeskripsi(
                    rs.getString("deskripsi")
            );

            task.setDeadline(
                    rs.getDate("deadline")
            );

            task.setPrioritas(
                    rs.getString("prioritas")
            );

            task.setStatus(
                    rs.getString("status")
            );

            list.add(task);
        }

    } catch(Exception e){

        System.out.println(e.getMessage());
    }

    return list;
}

// Cari Task
public ArrayList<Task> searchTask(String keyword) {

    ArrayList<Task> list =
            new ArrayList<>();

    try {

        Connection conn =
                Koneksi.getConnection();

        String sql =
                "SELECT * FROM tasks " +
                "WHERE judul LIKE ?";

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setString(
                1,
                "%" + keyword + "%"
        );

        ResultSet rs =
                ps.executeQuery();

        while(rs.next()){

            Task task =
                    new Task();

            task.setIdTask(
                    rs.getInt("id_task")
            );

            task.setJudul(
                    rs.getString("judul")
            );

            task.setDeadline(
                    rs.getDate("deadline")
            );

            task.setPrioritas(
                    rs.getString("prioritas")
            );

            task.setStatus(
                    rs.getString("status")
            );

            list.add(task);
        }

    } catch(Exception e){

        System.out.println(e.getMessage());
    }

    return list;
}

// Statistik User

public int countTaskByUser(int idUser) {

    try {

        Connection conn =
                Koneksi.getConnection();

        String sql =
                "SELECT COUNT(*) total " +
                "FROM tasks WHERE id_user=?";

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setInt(1, idUser);

        ResultSet rs =
                ps.executeQuery();

        if(rs.next()){

            return rs.getInt("total");
        }

    } catch(Exception e){

        System.out.println(e.getMessage());
    }

    return 0;
}

public int countDoneTask(int idUser) {

    try {

        Connection conn =
                Koneksi.getConnection();

        String sql =
                "SELECT COUNT(*) total " +
                "FROM tasks " +
                "WHERE id_user=? " +
                "AND status='DONE'";

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setInt(1, idUser);

        ResultSet rs =
                ps.executeQuery();

        if(rs.next()){

            return rs.getInt("total");
        }

    } catch(Exception e){

        System.out.println(e.getMessage());
    }

    return 0;
}

public int countUndoneTask(int idUser) {

    try {

        Connection conn =
                Koneksi.getConnection();

        String sql =
                "SELECT COUNT(*) total " +
                "FROM tasks " +
                "WHERE id_user=? " +
                "AND status!='DONE'";

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setInt(1, idUser);

        ResultSet rs =
                ps.executeQuery();

        if(rs.next()){

            return rs.getInt("total");
        }

    } catch(Exception e){

        System.out.println(e.getMessage());
    }

    return 0;
}

// Statistik Admin

public int countAllTask() {

    try {

        Connection conn =
                Koneksi.getConnection();

        String sql =
                "SELECT COUNT(*) total FROM tasks";

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ResultSet rs =
                ps.executeQuery();

        if(rs.next()){

            return rs.getInt("total");
        }

    } catch(Exception e){

        System.out.println(e.getMessage());
    }

    return 0;
}

public int countAllDoneTask() {

    try {

        Connection conn =
                Koneksi.getConnection();

        String sql =
                "SELECT COUNT(*) total " +
                "FROM tasks " +
                "WHERE status='DONE'";

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ResultSet rs =
                ps.executeQuery();

        if(rs.next()){

            return rs.getInt("total");
        }

    } catch(Exception e){

        System.out.println(e.getMessage());
    }

    return 0;
}

public int countAllUndoneTask() {

    try {

        Connection conn =
                Koneksi.getConnection();

        String sql =
                "SELECT COUNT(*) total " +
                "FROM tasks " +
                "WHERE status!='DONE'";

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ResultSet rs =
                ps.executeQuery();

        if(rs.next()){

            return rs.getInt("total");
        }

    } catch(Exception e){

        System.out.println(e.getMessage());
    }

    return 0;
}

}
