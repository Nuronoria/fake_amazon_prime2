/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.login;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import org.controlsfx.control.Rating;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
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
    @FXML
    private TextArea commentContent;
    @FXML
    private Rating rating0;
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
        
        double rating = rating0.getRating();
        String comment = commentContent.getText();
        // add " at the start and end of the comment
        comment = "\"" + comment + "\"";
        
        System.out.println(rating);
        System.out.println(comment);
        
        if ( rating == 0 || comment.equals("")){
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Uncompleted Rating");
            alert.setHeaderText(null);
            alert.setContentText("Please check your rating and comment.");
            alert.showAndWait();
        }else {
            Connection con;
            con = dbconnect.connect();
            LocalDate today = LocalDate.now();

            // Format the date to YYYY-MM-DD
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = today.format(formatter);
            int userID = SessionManager.userID;
            int movieID = SessionManager.movieID;
            
            sendComment(con, userID, movieID, comment, rating, formattedDate);
            
            
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
        private void sendComment(Connection con, int userId, int movieId, String comment, double rating, String date) {
     
        try {
            Statement stm = con.createStatement();
            String sql = "INSERT INTO userlogin.comment (User_ID, Movie_ID, Comment_text, Comment_rating, Comment_date) VALUES (" 
                    + userId + ", " 
                    + movieId + ", '"
                    + comment + "', " 
                    + rating + ", '"
                    + date + "')";
        int rowsAffected = stm.executeUpdate(sql);

        if (rowsAffected > 0) {
            System.out.println("Comment added successfully.");
        } else {
            System.out.println("Failed to add comment.");
        }

        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
    
}
