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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jrwie
 */
public class AdminmenuController implements Initializable {
    
    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    /**
    * Handles the action event to navigate to the "adminaddmovie.fxml" screen.
    *
    * @param event The ActionEvent triggered by the button click.
    * @throws IOException if an input or output exception occurs.
    */
    @FXML
    private void addmovie(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("adminaddmovie.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
    * Handles the action event to navigate to the "admindeletemovie.fxml" screen.
    *
    * @param event The ActionEvent triggered by the button click.
    * @throws IOException if an input or output exception occurs.
    */
    @FXML
    private void deletemovie(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("admindeletemovie.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
    * Handles the action event to navigate back to the "secondary.fxml" screen.
    *
    * @param event The ActionEvent triggered by the button click.
    * @throws IOException if an input or output exception occurs.
    */
    @FXML
    private void back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("secondary.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
    * Handles the action event to navigate to the "adminmovieextra.fxml" screen.
    *
    * @param event The ActionEvent triggered by the button click.
    * @throws IOException if an input or output exception occurs.
    */
    @FXML
    private void languagegenre(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("adminmovieextra.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void user(ActionEvent event) {
    }
    
}
