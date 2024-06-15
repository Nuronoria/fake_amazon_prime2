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
    public int registerUser(){
        return 0;
    }
}
