package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Koneksi {

    private static Connection conn;

    public static Connection getConnection() {

        try {

            if (conn == null) {

                String url =
                    "jdbc:mysql://localhost:3306/taskmate_db";

                String user = "root";

                String password = "";

                conn = DriverManager.getConnection(
                    url,
                    user,
                    password
                );

                System.out.println(
                    "Database Connected"
                );
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