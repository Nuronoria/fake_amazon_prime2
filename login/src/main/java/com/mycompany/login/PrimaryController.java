/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.login;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kjh27
 */
public class PrimaryController implements Initializable {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    private int userID;

    @FXML
    private TextField inputUserID;
    @FXML
    private Label warnung;
    @FXML
    private PasswordField pass_hidden;
    @FXML
    private CheckBox pass_toggle;
    @FXML
    private TextField pass_text;
    
    public void setUserID(int userID){
        this.userID = userID;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        String username = inputUserID.getText();
        String password;
        if(pass_toggle.isSelected()){
            password = pass_text.getText();
        } else {
            password = pass_hidden.getText();
        }
        LoginCheck a = new LoginCheck(username,password);
        if(a.userCompare()){
            System.out.println("Login success.");
            Connection con;
            con = dbconnect.connect();
            setUserID(getUserID(con, username));
            SessionManager.userID = this.userID;
            System.out.println(this.userID);
             System.out.println(SessionManager.userID);


            root = FXMLLoader.load(getClass().getResource("secondary.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }else{
            System.out.println("Login failure.");
        pass_hidden.setText("");
        pass_text.setText("");
        if(pass_toggle.isSelected()){
            pass_text.requestFocus();
            pass_text.positionCaret(pass_text.getText().length()); 
        } else {
            pass_hidden.requestFocus();
            pass_hidden.positionCaret(pass_hidden.getText().length());
        }
        warnung.setText("Username or Password is wrong!");
        }
        
    }

    @FXML
    private void register(MouseEvent event) throws IOException {

        System.out.println("wechsel zu registration.");
        root = FXMLLoader.load(getClass().getResource("register.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void showpassword(ActionEvent event) {
        if(pass_toggle.isSelected()){
            pass_text.setText(pass_hidden.getText());
            pass_text.setVisible(true);
            pass_text.setFocusTraversable(true);
            pass_hidden.setVisible(false);
            pass_hidden.setFocusTraversable(false);
            pass_text.requestFocus();
            pass_text.positionCaret(pass_text.getText().length());
        } else {
            pass_hidden.setText(pass_text.getText());
            pass_hidden.setVisible(true);
            pass_hidden.setFocusTraversable(true);
            pass_text.setVisible(false);
            pass_text.setFocusTraversable(false);
            pass_hidden.requestFocus();
            pass_hidden.positionCaret(pass_hidden.getText().length());
        }
    }
    //getUserID from DB
    private int getUserID(Connection con, String User_name) {
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
        return UserID;
    }
    
}
