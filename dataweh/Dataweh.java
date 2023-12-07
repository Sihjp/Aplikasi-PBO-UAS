// Handling code untuk memegang database mysql
package dataweh;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



/**
 *
 * @author widi
 */

public class Dataweh {

    /**
     * @param args the command line arguments
     */
    private static Connection mysqlconfig;
    
    public static Connection configDB()throws SQLException{
        try {
            String url = "jdbc:mysql://localhost:3306/duhbruh";//url database
            String user="root"; //user database
            String pass=""; //password database
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            mysqlconfig=DriverManager.getConnection(url, user, pass);            
        } catch (SQLException e) {
            System.err.println("koneksi gagal "+e.getMessage()); //perintah menampilkan error pada koneksi
        }
        return mysqlconfig;
    }
    
    

    
        // Add data
        // Sign Up
        public void addData(String name, String email, String password) {
            
            try {
                // Membuat query sebagai string
                String query = "INSERT INTO account (username, password, email) VALUES (?, ?, ?)";
                // Menyiapkan statement SQL dan melakukan koneksi ke database dengan query
                PreparedStatement preparedStatement = configDB().prepareStatement(query);
                
                // Set string untuk urutan, dan parameter terakhir sebagai isi
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);

                // Lakukan eksekusi
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        // Login
        public boolean login(String email, String password) throws SQLException {

            try {
                String query = "SELECT * FROM account WHERE email=? AND password=?";
                try (PreparedStatement preparedStatement = configDB().prepareStatement(query)) {
                    preparedStatement.setString(1, email);
                    preparedStatement.setString(2, password);

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        // Cek apakah akun ditemukan
                        if (resultSet.next()) {
                            System.out.println("Login berhasil!");
                            return true;
                        } else {
                            System.out.println("Login gagal. Email atau password salah.");
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

        
    
}
