/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.login;
import java.sql.*;

/**
 *
 * @author jrwie
 */
public class RegisterCheck {
    
    private String user;
    private String password;
    private String email;
    private Connection con;
    
    public RegisterCheck(String u,String p,String e){
        setUser(u);
        setPassword(p);
        setEmail(e);
    }
    private void setUser(String u){
        this.user = u;
    }
    private void setPassword(String p){
        this.password = p;
    }
    private void setEmail(String e){
        this.email = e;
    }
    private String getUser(){
        return this.user;
    }
    private String getPassword(){
        return this.password;
    }
    private String getEmail(){
        return this.email;
    }
    
    
    public int registerUser(){
        try{
        con = dbconnect.connect();
        Statement stm = con.createStatement();
        
        //überprüfung ob es den user schon gibt
        String sql = "SELECT * FROM userlogin.user WHERE BINARY User_name='"+this.getUser()+"';";
        ResultSet rs = stm.executeQuery(sql);
        if(rs.next()){ 
            return 1;
        }else{
        }
        //überprüfung ob es die email schon gibt
        sql = "SELECT * FROM userlogin.user WHERE User_email='"+this.getEmail()+"';";
        rs = stm.executeQuery(sql);
        if(rs.next()){   
            return 2; 
        }else{
        }
        //datensatz wird in die datenbank hinzugefügt
        sql = "INSERT INTO `userlogin`.`user` (`User_name`, `User_password`, `User_email`, `User_admin`) VALUES ('"+this.getUser()+"', '"+this.getPassword()+"', '"+this.getEmail()+"', b'0')";
        stm.executeUpdate(sql);
            return 3;
        
        }catch(Exception e){
            System.out.println("e");
            System.out.println("keine verbindung beim regestrieren");
            return 0;
        }
    }
}
