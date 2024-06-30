/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.login;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jrwie
 */
public class RegisterController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private boolean passwordpressed = false;
    private boolean reppressed = false;
    private Color paintred = new Color(0.7882, 0.1176, 0.1176, 1.0);
    private Color paintgreen = new Color(0.1928, 0.78, 0.117, 1.0);
    private boolean passwordcorrect = true;
    
    @FXML
    private TextField username_text;
    @FXML
    private TextField email_text;
    @FXML
    private PasswordField password_text;
    @FXML
    private Button password_button;
    @FXML
    private PasswordField rep_text;
    @FXML
    private Button rep_button;
    @FXML
    private Button register_button;
    @FXML
    private TextField password_textshow;
    @FXML
    private TextField rep_textshow;
    @FXML
    private Label errortext;
    @FXML
    private Label repcheck;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void passwordshow(MouseEvent event) {
        if(passwordpressed == false ){
            password_textshow.setText(password_text.getText());
            password_textshow.setVisible(true);
            password_textshow.setFocusTraversable(true);
            password_text.setVisible(false);
            password_text.setFocusTraversable(false);
            password_textshow.requestFocus();
            password_textshow.positionCaret(password_textshow.getText().length());
            passwordpressed = true;
        }else if(passwordpressed == true){
            password_text.setText(password_textshow.getText());
            password_text.setVisible(true);
            password_text.setFocusTraversable(true);
            password_textshow.setVisible(false);
            password_textshow.setFocusTraversable(false);
            password_text.requestFocus();
            password_text.positionCaret(password_text.getText().length());
            passwordpressed = false;
        }
//        System.out.println("pressed");
    }

    @FXML
    private void repshow(MouseEvent event) {
        if(reppressed == false ){
            rep_textshow.setText(rep_text.getText());
            rep_textshow.setVisible(true);
            rep_textshow.setFocusTraversable(true);
            rep_text.setVisible(false);
            rep_text.setFocusTraversable(false);
            rep_textshow.requestFocus();
            rep_textshow.positionCaret(rep_textshow.getText().length());
            reppressed = true;
        }else if(reppressed == true){
            rep_text.setText(rep_textshow.getText());
            rep_text.setVisible(true);
            rep_text.setFocusTraversable(true);
            rep_textshow.setVisible(false);
            rep_textshow.setFocusTraversable(false);
            rep_text.requestFocus();
            rep_text.positionCaret(rep_text.getText().length());
            reppressed = false;
        }
    }
    
    @FXML
    private void passwordtextcheck(){
        if(reppressed == false && passwordpressed == false){
            passwordcheck(rep_text.getText(),password_text.getText());
        }else if(reppressed == true && passwordpressed == false){
            passwordcheck(rep_textshow.getText(),password_text.getText());
        }else if(reppressed == false && passwordpressed == true){
            passwordcheck(rep_text.getText(),password_textshow.getText());
        }else if(reppressed == true && passwordpressed == true){
            passwordcheck(rep_textshow.getText(),password_textshow.getText());
        }
        
    }
    private void passwordcheck(String a, String b){
        if(a.equals(b)){
            repcheck.setText("✓");
            repcheck.setTextFill(paintgreen);
            passwordcorrect = true;
        }else{
            repcheck.setText("✖");
            repcheck.setTextFill(paintred);
            passwordcorrect = false;
        }
    }

    @FXML
    private void register(ActionEvent event) throws IOException {
        String username = username_text.getText();
        String email = email_text.getText();
        String password;
        
        if(username.equals("")){
            errortext.setText("Please enter an Username!");
            return;
        }
        if(email.equals("")){
            errortext.setText("Please enter an Email!");
            return;
        }
        if(!email.contains("@")){
            errortext.setText("Your Email must contain an @ !");
            return;
        }
        if((rep_text.getText().equals("") && rep_textshow.getText().equals("")) && (passwordcorrect == true)){
            System.out.println("repPasswordfeld leer");
            errortext.setText("Please enter a Password!");
            return;
        }else if((reppressed == true && passwordpressed == false)&& (passwordcorrect == true)){
            password = rep_textshow.getText();
            System.out.println("passwortfall.2 " + password);
            errortext.setText("");
        }else if((reppressed == false && passwordpressed == true)&&(passwordcorrect == true)){
            password = rep_text.getText();
            System.out.println("passwortfall.3 " + password);
            errortext.setText("");
        }else if((reppressed == true && passwordpressed == true)&&(passwordcorrect == true)){
            password = rep_textshow.getText();
            System.out.println("passwortfall.4 " + password);
            errortext.setText("");
        }else if((reppressed == false && passwordpressed == false)&&(passwordcorrect == true)){
            password = rep_text.getText();
            System.out.println("passwortfall.1 " + password);
            errortext.setText("");
        }else{
            errortext.setText("Password does not match!");
            return;
        }
        
        RegisterCheck a = new RegisterCheck(username,password,email);
        
        switch (a.registerUser()) {
            case 0:
                System.out.println("error 0");
                break;
            case 1:
                System.out.println("error 1");
                errortext.setText("there is already an account with this Username");
                username_text.setText("");
                break;
            case 2:
                System.out.println("error 2");
                errortext.setText("there is already an account that uses this Email!");
                email_text.setText("");
                break;
            case 3:
                root = FXMLLoader.load(getClass().getResource("registerdone.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                
            default:
                System.out.println("etwas ist im switch case schief gelaufen");
        }
    }

    @FXML
    private void back(MouseEvent event) throws IOException {
        System.out.println("wechsel zu registration.");
        root = FXMLLoader.load(getClass().getResource("primary.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
}
