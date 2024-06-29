/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.login;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mycompany.Utilities.EncryptionUtil;

/**
 *
 * @author jrwie
 */

/**
 * Implementation of SQL Injection security
 * @author taha
 */
public class LoginCheck {
    
    private String user;
    private String password;
    private Connection con;
    
    
    public LoginCheck(String u,String p){
        setUser(u);
        setPassword(p);
    }
    
    private void setUser(String u){
        this.user = u;
    }
    private void setPassword(String p){
        this.password = p;
    }
    private String getUser(){
        return(this.user);
    }
    private String getPassword(){
        return(this.password);
    }
    /*
    public static void main(String[] args){
        //System.out.println("ich war in der class"); 
        LoginCheck test = new LoginCheck("Admin","1234");
        test.userCompare();
    }
    */
    public boolean userCompare(){
        try{
            
            con = dbconnect.connect();
            
            // Encrypt password before comparing it with the DB        
            String encryptedPassword = EncryptionUtil.encrypt(this.getPassword());

            // Using a parametirized query to prevent SQL injections
            String sql = "SELECT * FROM userlogin.user WHERE BINARY User_name = ? AND BINARY User_password = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, this.getUser());
            pstmt.setString(2, encryptedPassword);

            ResultSet rs = pstmt.executeQuery();        
            
            if(rs.next()){
                System.out.println("right userlogin");
                con.close();
                return(true);
            }else{
                System.out.println("false userlogin");
                con.close();
                return(false);
            }
            
            
        }catch (Exception e){
            System.out.println(e);
            System.out.println("ich war in dem catch");
        }
        return(false);
    }
    
    public int getUserID(String User_name) throws SQLException {
        con = dbconnect.connect();
        
        int UserID = 0;
         String sql = "SELECT User_ID FROM userlogin.user WHERE User_name = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        // Set the value for the parameter
              pstmt.setString(1, User_name);
        
        // Execute the query
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                UserID = rs.getInt("User_ID");
            }
        }
        } catch (Exception e) {
            System.out.println(e);
        }
        con.close();
        return UserID;
    }
}
