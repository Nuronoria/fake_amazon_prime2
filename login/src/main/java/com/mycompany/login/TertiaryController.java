/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.login;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
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
     private Label[] genres;
     private Label[] languages;

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
    @FXML
    private ImageView imagebanner;
    @FXML
    private ImageView imagelogo;
    @FXML
    private Label genre0;
    @FXML
    private Label genre1;
    @FXML
    private Label genre2;
    @FXML
    private Label language0;
    @FXML
    private Label language1;
    @FXML
    private Label language2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Random rand = new Random();
        int movieID = SessionManager.movieID;
        
        //Liste für RandomZahl ohne Wiederholung
        //random digits 
        List<Integer> numbers = new ArrayList<>();
        for(int i=2;i<5;i++){
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        
        //Datenbank aufrufen und Filmbeschreibung einsetzen.
        //call DB and set description
        Connection con;
        con = dbconnect.connect();
        String description = getMovieDescription(con, movieID);
     
        moviedescript.setText(description);

        
        //alle ratings einsetzen
        //set all ratings
        ratings = new Rating[]{rating0, rating1, rating2};
        comments = new Label[]{comment0, comment1, comment2};
        usernames = new Label[]{username0, username1, username2};
        dates = new Label[]{date0, date1, date2};
        //get all UserIDs from comments
        List<Integer> CommentUserID;
        CommentUserID = getCommentUserID(con, movieID);
        //shuffle to get random UserIDs
        Collections.shuffle(CommentUserID);
        
        for(int i = 0; i < 3; i++){
            int randUserId = CommentUserID.get(i);
            String comment = getComment(con, randUserId, movieID);
            String Username = getCommentUsername(con, randUserId, movieID);
            String Date = getCommentDate(con, randUserId, movieID);
            Double Rating = getCommentRating(con, randUserId, movieID);
            ratings[i].setRating(Rating);
            comments[i].setText(comment);
            usernames[i].setText(Username);
            dates[i].setText(Date);
        }

        
        //Update Image to DB
        String filePath = "src/main/resources/com/mycompany/login/queenbanner.jpg";
        String filePath2 = "src/main/resources/com/mycompany/login/jockerbanner.jpeg";
        String filePath3 = "src/main/resources/com/mycompany/login/usbanner.gif";
        String filePath4 = "src/main/resources/com/mycompany/login/moonlightbanner.jpg";
        updateImageInDatabase(con, 1, filePath);
        updateImageInDatabase(con, 2, filePath2);
        updateImageInDatabase(con, 3, filePath3);
        updateImageInDatabase(con, 4, filePath4);
        
        String LogofilePath = "src/main/resources/com/mycompany/login/queenlogo.png";
        String LogofilePath2 = "src/main/resources/com/mycompany/login/jokerlogo.png";
        String LogofilePath3 = "src/main/resources/com/mycompany/login/uslogo.jpg";
        String LogofilePath4 = "src/main/resources/com/mycompany/login/moonlightlogo.png";
        updateLogoInDatabase(con, 1, LogofilePath);
        updateLogoInDatabase(con, 2, LogofilePath2);
        updateLogoInDatabase(con, 3, LogofilePath3);
        updateLogoInDatabase(con,4, LogofilePath4);
           
        
        //Loading Image from DB
        Image image = loadImageFromDatabase(con,movieID);
        imagebanner.setFitWidth(900);
        imagebanner.setFitHeight(290);
        imagebanner.setPreserveRatio(false);
        imagebanner.setImage(image);
        
        Image logo = loadLogoFromDatabase(con,movieID);
        imagelogo.setFitHeight(100);
        imagelogo.setImage(logo);
    
        //Get MovieGenre and Set to Labels
        
        genres = new Label[]{genre0, genre1, genre2};
        genre0.setText("");
        genre1.setText("");
        genre2.setText("");
        
        List<String> MovieGenre;
        MovieGenre = getMovieGenre(con, movieID);
        for(int i =0 ; i < MovieGenre.size(); i++){
            if(i > 3){
                break;
            }else {
                genres[i].setText(MovieGenre.get(i));
            }
            
        }
        //get languages for subtitles and set Label
        languages = new Label[]{language0, language1, language2};
        language0.setText("");
        language1.setText("");
        language2.setText("");
        
        List<String> Languages;
        Languages = getLanguages(con, movieID);
        for(int i =0 ; i < Languages.size(); i++){
            if(i > 3){
                break;
            }else {
                languages[i].setText(Languages.get(i));
            }
            
        }
        
        moviedescript.setStyle("-fx-text-inner-color: #d4d4d4");
    }
    
        private void updateLogoInDatabase(Connection con, int movieID, String filePath) {
        String sql = "UPDATE userlogin.movie SET Movie_logo = ? WHERE Movie_ID = " + movieID;
        try (PreparedStatement pstmt = con.prepareStatement(sql);
             FileInputStream fis = new FileInputStream(new File(filePath))) {

            // Set the binary stream parameter
            pstmt.setBinaryStream(1, fis, (int) new File(filePath).length());

            // Execute the update
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("The movie_logo has been updated successfully.");
            } else {
                System.out.println("No row was updated.");
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }    

    private void updateImageInDatabase(Connection con, int movieID, String filePath) {
        String sql = "UPDATE userlogin.movie SET Movie_banner = ? WHERE Movie_ID = " + movieID;
        try (PreparedStatement pstmt = con.prepareStatement(sql);
             FileInputStream fis = new FileInputStream(new File(filePath))) {

            // Set the binary stream parameter
            pstmt.setBinaryStream(1, fis, (int) new File(filePath).length());

            // Execute the update
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("The movie_banner has been updated successfully.");
            } else {
                System.out.println("No row was updated.");
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }    
    
        private Image loadImageFromDatabase(Connection conn, int movieID) {
        String query = "SELECT Movie_banner FROM userlogin.movie WHERE movie_Id = " + movieID;
        try (
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                byte[] imgBytes = rs.getBytes("Movie_banner");
                if (imgBytes != null) {
                    try (InputStream is = new ByteArrayInputStream(imgBytes)) {
                        return new Image(is);
                    }
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private Image loadLogoFromDatabase(Connection conn, int movieID) {
        String query = "SELECT Movie_logo FROM userlogin.movie WHERE movie_Id = " + movieID;
        try (
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                byte[] imgBytes = rs.getBytes("Movie_logo");
                if (imgBytes != null) {
                    try (InputStream is = new ByteArrayInputStream(imgBytes)) {
                        return new Image(is);
                    }
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
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
    
    private List<String> getMovieGenre(Connection con, int movieId) {
        List<String> genres = new ArrayList<>();
        int GenreID ;
   
        try {
            Statement stm = con.createStatement();
            String sql = "SELECT Genre_ID FROM userlogin.moviegenre WHERE Movie_ID = " + movieId;
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                GenreID = rs.getInt("Genre_ID");
                String genresql = "SELECT Genre_name FROM userlogin.genre WHERE Genre_ID = " + GenreID;
                try{
                    Statement genreStmt = con.createStatement();
                    ResultSet genreRs = genreStmt.executeQuery(genresql);
                
                    if (genreRs.next()){
                        genres.add(genreRs.getString("Genre_name"));
                
                     }
                } catch (Exception e){
                    System.out.println("No Genres Found from DB.");
                }

            }
        } catch (Exception e) {
            System.out.println("No Genre_ID Found from DB.");
        }
        return genres;
    }
    
    private List<String> getLanguages(Connection con, int movieId) {
        List<String> languages = new ArrayList<>();
        int Language_ID ;
   
        try {
            Statement stm = con.createStatement();
            String sql = "SELECT Language_ID FROM userlogin.movielanguage WHERE Movie_ID = " + movieId;
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                Language_ID = rs.getInt("Language_ID");
                String langsql = "SELECT Language_name FROM userlogin.language WHERE Language_ID = " + Language_ID;
                try{
                    Statement langStmt = con.createStatement();
                    ResultSet langRs = langStmt.executeQuery(langsql);
                
                    if (langRs.next()){
                        languages.add(langRs.getString("Language_name"));
                
                     }
                } catch (Exception e){
                    System.out.println("No Languages Found from DB.");
                }

            }
        } catch (Exception e) {
            System.out.println("No Language_ID Found from DB.");
        }
        return languages;
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
    private List<Integer> getCommentUserID (Connection con, int movieId){
        List<Integer> userIDs = new ArrayList<>();
        
                try {
            Statement stm = con.createStatement();
            String sql = "SELECT User_ID FROM userlogin.comment WHERE Movie_ID = " + movieId;
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                userIDs.add(userID);

            }
        } catch (Exception e) {
            System.out.println("No UserID Found from DB.");
        }
        
        return userIDs;
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
