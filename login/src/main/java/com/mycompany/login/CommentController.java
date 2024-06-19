/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author kjh27
 */
public class CommentController implements Initializable {


    @FXML
    private AnchorPane commentAnch;
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

    @FXML
    private void onBack(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("tertiary.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onSend(MouseEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thank You");
        alert.setHeaderText(null);
        alert.setContentText("Thank you for your comment! :)");

        alert.showAndWait();
        
        root = FXMLLoader.load(getClass().getResource("tertiary.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
