/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.login;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mycompany.Utilities.EncryptionUtil;


/**
 *
 * @author jrwie
 */
public class RegisterCheck {

    private String user;
    private String password;
    private String email;
    private Connection con;

    public RegisterCheck(String u, String p, String e) {
        setUser(u);
        setPassword(p);
        setEmail(e);
    }

    private void setUser(String u) {
        this.user = u;
    }

    private void setPassword(String p) {
        this.password = p;
    }

    private void setEmail(String e) {
        this.email = e;
    }

    private String getUser() {
        return this.user;
    }

    private String getPassword() {
        return this.password;
    }

    private String getEmail() {
        return this.email;
    }

    /**
    * Registers a new user in the database.
    *
    * @return An integer indicating the registration status:
    *         - 0 if an error occurred during registration or database connection.
    *         - 1 if a user with the same username already exists.
    *         - 2 if a user with the same email already exists.
    *         - 3 if registration is successful.
    */
    public int registerUser() {
        try {
            con = dbconnect.connect();

            // Check if the username already exists
            String sql = "SELECT * FROM userlogin.user WHERE BINARY User_name = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, this.getUser());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                con.close();
                return 1;
            }

            // Check if the email already exists
            sql = "SELECT * FROM userlogin.user WHERE User_email = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, this.getEmail());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                con.close();
                return 2;
            }

            // Encrypt the password before saving to the database
            String encryptedPassword = EncryptionUtil.encrypt(this.getPassword());

            // Insert the new user into the database
            sql = "INSERT INTO `userlogin`.`user` (`User_name`, `User_password`, `User_email`, `User_admin`) VALUES (?, ?, ?, b'0')";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, this.getUser());
            pstmt.setString(2, encryptedPassword);
            pstmt.setString(3, this.getEmail());
            pstmt.executeUpdate();
            
            con.close();
            return 3;

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("keine verbindung beim registrieren");
            return 0;
        }
    }
}