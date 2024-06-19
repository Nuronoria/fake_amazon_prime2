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
public class LoginCheck {
    
    private String user;
    private String password;
    
    
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
        Connection con;
        try{
            
            //con = connection
            con = dbconnect.connect();
            Statement stm = con.createStatement();
            
            
            String sql = "SELECT * FROM userlogin.user WHERE BINARY User_name='"+this.getUser()+"' and BINARY User_password='"+this.getPassword()+"';";
            
            //rs = resultSet
            ResultSet rs = stm.executeQuery(sql);
            
            if(rs.next()){
                System.out.println("right userlogin");
                return(true);
            }else{
                System.out.println("false userlogin");
                return(false);
            }
            
        }catch (Exception e){
            System.out.println(e);
            System.out.println("ich war in dem catch");
        }
        return(false);
    }
    
    //war eine funktion die dazu da war die ganze tabelle aus zu geben
    private static void testverbindung(){
        Connection con;
        //System.out.println("ich war in der methode");
        try{
            //Class.forName("com.mysql.cj.jdbc.driver");
            //System.out.println("ich war in der try");
            
            //con = connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql","root","asd123");
            
            //System.out.println(con);
            
            //stm = Statement
            Statement stm = con.createStatement();
            
            
            String sql = "SELECT * FROM userlogin.user_table;";
            
            //rs = resultSet
            ResultSet rs = stm.executeQuery(sql);
            
            while(rs.next()){
                System.out.println(rs.getInt("UserID")+" "+rs.getString("UserName")+" "+rs.getString("Password"));
            }
            
        }catch (Exception e){
            System.out.println(e);
            System.out.println("ich war in der chatch");
        }
    }
}
