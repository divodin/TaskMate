package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Koneksi {

    private static Connection conn;

    public static Connection getConnection() {

        try {

            // Tambahkan pengecekan conn.isClosed()
            if (conn == null || conn.isClosed()) {
                String url = "jdbc:mysql://localhost:3306/taskmate_db";
                String user = "ini juga";
                String password = "sesuaikan dengan password DB kalau kosong biasrkan "; // Sesuaikan passwordmu

                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Database Connected");
            }
        } catch (Exception e) {

            System.out.println(
                "Koneksi Gagal : "
                + e.getMessage()
            );
        }

        return conn;
    }
}