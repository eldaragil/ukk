/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Koneksi;

/**
 *
 * @author dppra
 */
import java.sql.*;//memanggil plugin sql
import javax.swing.*;//memanggil mysql jdbc

public class Koneksi {
    public static Connection KoneksiDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//memanggil driver JDBC
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ukk_net2", "root", "");
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    return null;
    }    
}