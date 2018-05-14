package com.koneksi;

import java.sql.Connection;
import java.sql.DriverManager;

public class koneksi {
    private static Connection koneksi;
    
    public static Connection getKoneksi() {
        if (koneksi == null) {
            try {
                String url = "jdbc:mysql://localhost/db_kasir";
                String username = "root";
                String pass = "";
                
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                koneksi = DriverManager.getConnection(url, username, pass);
            } catch (Exception e) {
                System.out.println(e);
            }
        }return koneksi;
    }
}
