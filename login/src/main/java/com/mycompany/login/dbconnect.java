package com.mycompany.login;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;

public class dbconnect {

    private static String user= "root";
    private static String password;
    private static String road = "jdbc:mysql://localhost:3308/mysql";

    static {
        try {
            password = new String(Files.readAllBytes(Paths.get("../Secrets/db_password.txt"))).trim();
        } catch (IOException e) {
            System.out.println("Error reading database credentials from files.");
            e.printStackTrace();
        }
    }

    public dbconnect() {
    }

    public static Connection connect() {
        Connection con;
        try {
            con = DriverManager.getConnection(road, user, password);
            return con;
        } catch (Exception e) {
            System.out.println("bin in der catch von dbconnect");
            e.printStackTrace();
        }
        return null;
    }

    public static String getUser() {
        return user;
    }

    public static String getPassword() {
        return password;
    }

    public static String getRoad() {
        return road;
    }
}
