package dao;

import database.Koneksi;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserDAO {

    // Registrasi User
    public boolean insertUser(User user) {

        try {

            Connection conn =
                    Koneksi.getConnection();

            String sql =
                    "INSERT INTO users " +
                    "(nama,email,username,password,role) " +
                    "VALUES(?,?,?,?,?)";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(1, user.getNama());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getRole());

            int result = ps.executeUpdate();

            return result > 0;

        } catch (Exception e) {

            System.out.println(
                    "Error Insert User : "
                    + e.getMessage()
            );
        }

        return false;
    }

    // Login
    public User login(
            String username,
            String password
    ) {

        try {

            Connection conn =
                    Koneksi.getConnection();

            String sql =
                    "SELECT * FROM users " +
                    "WHERE username=? " +
                    "AND password=?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                User user =
                        new User();

                user.setIdUser(
                        rs.getInt("id_user")
                );

                user.setNama(
                        rs.getString("nama")
                );

                user.setEmail(
                        rs.getString("email")
                );

                user.setUsername(
                        rs.getString("username")
                );

                user.setRole(
                        rs.getString("role")
                );

                return user;
            }

        } catch (Exception e) {

            System.out.println(
                    "Error Login : "
                    + e.getMessage()
            );
        }

        return null;
    }

    // Ambil Semua User
    public ArrayList<User> findAll() {

        ArrayList<User> list =
                new ArrayList<>();

        try {

            Connection conn =
                    Koneksi.getConnection();

            String sql =
                    "SELECT * FROM users";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                User user =
                        new User();

                user.setIdUser(
                        rs.getInt("id_user")
                );

                user.setNama(
                        rs.getString("nama")
                );

                user.setEmail(
                        rs.getString("email")
                );

                user.setUsername(
                        rs.getString("username")
                );

                user.setRole(
                        rs.getString("role")
                );

                list.add(user);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error Find All : "
                    + e.getMessage()
            );
        }

        return list;
    }
           public boolean updateProfile(User user) {

    try {

        Connection conn =
                Koneksi.getConnection();

        String sql =
                "UPDATE users SET " +
                "nama=?, " +
                "email=?, " +
                "password=? " +
                "WHERE id_user=?";

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setString(
                1,
                user.getNama()
        );

        ps.setString(
                2,
                user.getEmail()
        );

        ps.setString(
                3,
                user.getPassword()
        );

        ps.setInt(
                4,
                user.getIdUser()
        );

        return ps.executeUpdate() > 0;

    } catch(Exception e){

        System.out.println(
                e.getMessage()
        );
    }

    return false;
}
 // Hapus User
public boolean deleteUser(int idUser) {

    try {

        Connection conn =
                Koneksi.getConnection();

        String sql =
                "DELETE FROM users " +
                "WHERE id_user=?";

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setInt(1, idUser);

        int result =
                ps.executeUpdate();

        return result > 0;

    } catch (Exception e) {

        System.out.println(
                "Error Delete : "
                        + e.getMessage()
        );
    }

    return false;
}

public int countUser() {

    try {

        Connection conn =
                Koneksi.getConnection();

        String sql =
                "SELECT COUNT(*) total FROM users";

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ResultSet rs =
                ps.executeQuery();

        if(rs.next()) {

            return rs.getInt("total");
        }

    } catch(Exception e) {

        System.out.println(e.getMessage());
    }

    return 0;
}

}