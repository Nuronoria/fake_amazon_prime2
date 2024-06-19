/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.login;

import java.io.IOException;
import java.net.URL;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author kjh27
 */
public class TertiaryController implements Initializable {
        private Stage stage;
    private Scene scene;
    private Parent root;
    
    private Pane commentAnch;
    
    private Rating[] ratings;
    private Label[] comments;
    private Label[] usernames;
    private Label[] dates;

    @FXML
    private TextArea moviedescript;
    @FXML
    private ImageView backbutton;
    @FXML
    private Label comment0;
    @FXML
    private Label username0;
    @FXML
    private Label date0;
    @FXML
    private Rating rating0;
    @FXML
    private Rating rating1;
    @FXML
    private Rating rating2;
    

    @FXML
    private Label username1;
    @FXML
    private Label date1;
    @FXML
    private Label comment1;
    @FXML
    private Label username2;
    @FXML
    private Label date2;
    @FXML
    private Label comment2;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //Liste für RandomZahl ohne Wiederholung
        List<Integer> numbers = new ArrayList<>();
        for(int i=2;i<5;i++){
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        
        //Datenbank aufrufen und Filmbeschreibung einsetzen.
        Connection con;
        con = dbconnect.connect();
        String description = getMovieDescription(con, 2);
      

        
        
        moviedescript.setText(description);

        
        //alle ratings einsetzen
        ratings = new Rating[]{rating0, rating1, rating2};
        comments = new Label[]{comment0, comment1, comment2};
        usernames = new Label[]{username0, username1, username2};
        dates = new Label[]{date0, date1, date2};
        
        for(int i = 0; i < 3; i++){
            int randUserId = numbers.get(i);
            String comment = getComment(con, randUserId, 2);
            String Username = getCommentUsername(con, randUserId, 2);
            String Date = getCommentDate(con, randUserId, 2);
            Double Rating = getCommentRating(con, randUserId, 2);
            ratings[i].setRating(Rating);
            comments[i].setText(comment);
            usernames[i].setText(Username);
            dates[i].setText(Date);
        }
        
        
        System.out.println(rating0.getRating());
        
        
        moviedescript.setStyle("-fx-text-inner-color: #d4d4d4");
    }    

    @FXML
    private void onBackToSecond(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("secondary.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onAddComment(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("comment.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    
    }
    
    private String getMovieDescription(Connection con, int movieId) {
        String description = "";
        try {
            Statement stm = con.createStatement();
            String sql = "SELECT Movie_description FROM userlogin.movie WHERE Movie_ID = " + movieId;
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                description = rs.getString("Movie_description");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return description;
    }
    
    private String getComment(Connection con, int userId, int movieId) {
        String comment = "";
        try {
            Statement stm = con.createStatement();
            String sql = "SELECT Comment_text FROM userlogin.comment WHERE User_ID = " + userId + " AND Movie_ID = " + movieId;
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                comment = rs.getString("Comment_text");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return comment;
    }
    private String getCommentUsername(Connection con, int userId, int movieId) {
        int oUserId;
        String Username = "";
        try {
            Statement stm = con.createStatement();
            String sql = "SELECT User_ID FROM userlogin.comment WHERE User_ID = " + userId + " AND Movie_ID = " + movieId;
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                oUserId = rs.getInt("User_ID");
                
                sql = "SELECT User_name FROM userlogin.user WHERE User_ID = " + oUserId;
                rs = stm.executeQuery(sql);
                if (rs.next()){
                    Username = rs.getString("User_name");
                }
            }
            
            
        } catch (Exception e) {
            System.out.println(e);
        }
        return Username;
    }
    private Double getCommentRating(Connection con, int userId, int movieId) {
       
        Double Rating = null;
        try {
            Statement stm = con.createStatement();
            String sql = "SELECT Comment_rating FROM userlogin.comment WHERE User_ID = " + userId + " AND Movie_ID = " + movieId;
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                Rating = rs.getDouble("Comment_rating");

            }
            
            
        } catch (Exception e) {
            System.out.println(e);
        }
        return Rating;
    }
    
    private String getCommentDate(Connection con, int userId, int movieId) {
        String dateStr = "";
        try {
            Statement stm = con.createStatement();
            String sql = "SELECT Comment_date FROM userlogin.comment WHERE User_ID = " + userId + " AND Movie_ID = " + movieId;
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                String dbDate = rs.getString("Comment_date");
                SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = dbDateFormat.parse(dbDate);
            
                 // Konvertieren von YYYY-MM-DD -> DD-MM-YYYY in SimpleDateFormat
                SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd.MMMM.yyyy", Locale.GERMAN);
                dateStr = displayDateFormat.format(date);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return dateStr;
    }
    
}
