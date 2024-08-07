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
import java.sql.SQLException;
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
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException, SQLException {
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
            System.out.println(SessionManager.userID);
            if (SessionManager.admin) {
                System.out.println("is admin");
            }else{
                System.out.println("no admin");
            }


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

    @FXML
    private void testseite(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("adminmenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
