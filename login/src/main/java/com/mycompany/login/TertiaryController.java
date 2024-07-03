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
import java.util.ResourceBundle;

import org.controlsfx.control.Rating;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
    private ScrollPane scrollpane;
    @FXML
    private Label Comedy;
    @FXML
    private Label Romance;
    @FXML
    private Label Drama;
    @FXML
    private Label Crime;
    @FXML
    private Label Thriller;
    @FXML
    private Label Horror;
    @FXML
    private Label Mystery;
    @FXML
    private Label Englisch;
    @FXML
    private Label Deutsch;
    @FXML
    private Label Spanisch;
    @FXML
    private Label Chinesisch;
    @FXML
    private Label Koreanisch;
    @FXML
    private HBox genrebox;
    @FXML
    private HBox languagesbox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        scrollpane.setPrefSize(915, 800);
        int movieID = SessionManager.movieID;
        
        
        //Datenbank aufrufen und Filmbeschreibung einsetzen.
        //call DB and set description
        Connection con;
        con = dbconnect.connect();
        String description = getMovieDescription(con, movieID);
        moviedescript.setText(description);
        
        
        //Loading Image from DB
        Image image = loadImageFromDatabase(con,movieID);
        imagebanner.setFitWidth(900);
        imagebanner.setFitHeight(290);
        imagebanner.setPreserveRatio(false);
        imagebanner.setImage(image);
        
        Image logo = loadLogoFromDatabase(con,movieID);
        imagelogo.setFitHeight(100);
        imagelogo.setImage(logo);

        
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
        System.out.println(CommentUserID.size());
        
        if(CommentUserID.size() > 3){
            for (int i = 0; i < 3 ; i++){
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
 
        }else{
            for(int i = 0; i < CommentUserID.size(); i++){
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
        }

        


        
    
        //Get MovieGenre and Set to Labels
        getMovieGenre(con, movieID);
        
        //get languages for subtitles and set Label
        getLanguages(con, movieID);
        
        
        moviedescript.setStyle("-fx-text-inner-color: #d4d4d4");
    }
    /**
 * Updates the logo of a movie in the database.
 *
 * @param con      The connection to the database.
 * @param movieID  The ID of the movie whose logo is to be updated.
 * @param filePath The path to the logo file.
 *
 * This method uses a PreparedStatement to update the Movie_logo field
 * in the userlogin.movie table for the specified movie ID. The logo
 * is read from the provided file path as a binary stream.
 *
 * If the update is successful, a success message is printed to the console.
 * If no rows are affected, a message indicating that no rows were updated is printed.
 *
 * @throws SQLException If an SQL error occurs.
 * @throws IOException  If an I/O error occurs.
 */
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
/**
 * Updates the banner image of a movie in the database.
 *
 * @param con      The connection to the database.
 * @param movieID  The ID of the movie whose banner image is to be updated.
 * @param filePath The path to the banner image file.
 *
 * This method uses a PreparedStatement to update the Movie_banner field
 * in the userlogin.movie table for the specified movie ID. The banner image
 * is read from the provided file path as a binary stream.
 *
 * If the update is successful, a success message is printed to the console.
 * If no rows are affected, a message indicating that no rows were updated is printed.
 *
 * @throws SQLException If an SQL error occurs.
 * @throws IOException  If an I/O error occurs.
 */

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
    
    /**
 * Loads the banner image of a movie from the database.
 *
 * @param conn    The connection to the database.
 * @param movieID The ID of the movie whose banner image is to be loaded.
 * @return The Image object representing the movie's banner image, or null if the image is not found or an error occurs.
 *
 * This method retrieves the Movie_banner field from the userlogin.movie table for the specified movie ID.
 * The image data is read as a byte array, and an Image object is created from this data.
 *
 * If the image data is found and successfully read, the Image object is returned.
 * If the image data is not found or an error occurs, null is returned.
 *
 * @throws SQLException If an SQL error occurs.
 * @throws IOException  If an I/O error occurs.
 */

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
    /**
 * Loads the logo image of a movie from the database.
 *
 * @param conn    The connection to the database.
 * @param movieID The ID of the movie whose logo image is to be loaded.
 * @return The Image object representing the movie's logo image, or null if the image is not found or an error occurs.
 *
 * This method retrieves the Movie_logo field from the userlogin.movie table for the specified movie ID.
 * The image data is read as a byte array, and an Image object is created from this data.
 *
 * If the image data is found and successfully read, the Image object is returned.
 * If the image data is not found or an error occurs, null is returned.
 *
 * @throws SQLException If an SQL error occurs.
 * @throws IOException  If an I/O error occurs.
 */
    
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

/**
 * Handles the action of navigating back to the secondary scene when a mouse event occurs.
 *
 * @param event The MouseEvent that triggers the navigation.
 * @throws IOException If an input or output exception occurs during the loading of the FXML file.
 *
 * This method is triggered by a mouse event (such as a button click). It loads the "secondary.fxml" file,
 * sets the scene to the stage, and displays the secondary scene. The stage is retrieved from the source
 * of the mouse event.
 */
    @FXML
    private void onBackToSecond(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("secondary.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
/**
 * Handles the action of navigating to the comment scene when a mouse event occurs.
 *
 * @param event The MouseEvent that triggers the navigation.
 * @throws IOException If an input or output exception occurs during the loading of the FXML file.
 *
 * This method is triggered by a mouse event (such as a button click). It loads the "comment.fxml" file,
 * sets the scene to the stage, and displays the comment scene. The stage is retrieved from the source
 * of the mouse event.
 */
    @FXML
    private void onAddComment(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("comment.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    
    }
    /**
 * Retrieves the genres of a movie from the database and updates the visibility of genre labels.
 *
 * @param con      The connection to the database.
 * @param movieId  The ID of the movie whose genres are to be retrieved.
 *
 * This method fetches the genre IDs associated with the specified movie ID from the userlogin.moviegenre table.
 * For each genre ID, it retrieves the corresponding genre name from the userlogin.genre table.
 * If a genre label's text matches the retrieved genre name, the label is made visible.
 * After processing all genres, the labels are rearranged using the rearrangeLabels method.
 *
 * @throws SQLException If an SQL error occurs.
 * @throws IOException  If an I/O error occurs.
 */
    private void getMovieGenre(Connection con, int movieId) {
        genres = new Label[]{Comedy, Romance, Drama, Crime, Thriller, Horror, Mystery};
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
                    genreRs.next();
//                    System.out.println(genreRs.getString("Genre_name"));
                    for(int i=0;i<7;i++){
                        if(genres[i].getText().compareTo(genreRs.getString("Genre_name"))==0){
                            genres[i].setVisible(true);
                            break;
                        }
                    }
                } catch (Exception e){
                    System.out.println("No Genres Found from DB.");
                    e.printStackTrace();
                }

            }
            rearrangeLabels(genrebox);
        } catch (Exception e) {
            System.out.println("No Genre_ID Found from DB.");
        }
    }
    private void rearrangeLabels(HBox hbox) {
        // Temporäre Liste, um sichtbare Labels zu speichern
        List<Label> visibleLabels = new ArrayList<>();
        List<Label> allLabels = new ArrayList<>();

        // Alle Kinder von HBox durchlaufen und sicherstellen, dass sie vom Typ Label sind
        for (Node node : hbox.getChildren()) {
            if (node instanceof Label) {
                allLabels.add((Label) node);
            }
        }

        // Füge sichtbare Labels zur temporären Liste hinzu
        for (Label label : allLabels) {
            if (label.isVisible()) {
                visibleLabels.add(label);
            }
        }

        // Entferne alle Labels aus der HBox
        hbox.getChildren().clear();

        // Füge zuerst die sichtbaren Labels hinzu
        hbox.getChildren().addAll(visibleLabels);

        // Füge dann die unsichtbaren Labels hinzu
        for (Label label : allLabels) {
            if (!label.isVisible()) {
                hbox.getChildren().add(label);
            }
        }
    }

    /**
 * Retrieves the languages of a movie from the database and updates the visibility of language labels.
 *
 * @param con      The connection to the database.
 * @param movieId  The ID of the movie whose languages are to be retrieved.
 *
 * This method fetches the language IDs associated with the specified movie ID from the userlogin.movielanguage table.
 * For each language ID, it retrieves the corresponding language name from the userlogin.language table.
 * If a language label's text matches the retrieved language name, the label is made visible.
 * After processing all languages, the labels are rearranged using the rearrangeLabels method.
 *
 * @throws SQLException If an SQL error occurs.
 */
    private void getLanguages(Connection con, int movieId) {
        languages = new Label[]{Englisch, Deutsch, Spanisch, Chinesisch, Koreanisch};
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
                    langRs.next();
                    System.out.println(langRs.getString("Language_name"));
                    for(int i=0;i<5;i++){
                        if(languages[i].getText().compareTo(langRs.getString("Language_name"))==0){
                            languages[i].setVisible(true);
                            break;
                        }
                    }
                } catch (Exception e){
                    System.out.println("No Languages Found from DB.");
                    e.printStackTrace();
                }
            }
            rearrangeLabels(languagesbox);
        } catch (Exception e) {
            System.out.println("No Language_ID Found from DB.");
        }
    }
    
    /**
 * Retrieves the description of a movie from the database.
 *
 * @param con      The connection to the database.
 * @param movieId  The ID of the movie whose description is to be retrieved.
 * @return The description of the movie, or an empty string if not found or an error occurs.
 *
 * This method fetches the Movie_description field from the userlogin.movie table for the specified movie ID.
 * If the description is found, it is returned as a string. If no description is found or an error occurs,
 * an empty string is returned.
 *
 * @throws SQLException If an SQL error occurs.
 */
    
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
    /**
 * Retrieves the user IDs of the comments associated with a specific movie from the database.
 *
 * @param con      The connection to the database.
 * @param movieId  The ID of the movie whose comment user IDs are to be retrieved.
 * @return A list of user IDs who have commented on the specified movie.
 *
 * This method fetches the User_IDs from the userlogin.comment table for the specified movie ID.
 * The user IDs are collected in a list and returned. If no user IDs are found or an error occurs,
 * an empty list is returned.
 *
 * @throws SQLException If an SQL error occurs.
 */
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
    /**
 * Retrieves a specific comment made by a user on a movie from the database.
 *
 * @param con      The connection to the database.
 * @param userId   The ID of the user who made the comment.
 * @param movieId  The ID of the movie on which the comment was made.
 * @return The text of the comment, or an empty string if not found or an error occurs.
 *
 * This method fetches the Comment_text field from the userlogin.comment table for the specified user ID and movie ID.
 * If the comment is found, it is returned as a string. If no comment is found or an error occurs,
 * an empty string is returned.
 *
 * @throws SQLException If an SQL error occurs.
 */
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
    /**
 * Retrieves the username of a user who made a comment on a specific movie from the database.
 *
 * @param con      The connection to the database.
 * @param userId   The ID of the user who made the comment.
 * @param movieId  The ID of the movie on which the comment was made.
 * @return The username of the user who made the comment, or an empty string if not found or an error occurs.
 *
 * This method first fetches the User_ID from the userlogin.comment table for the specified user ID and movie ID.
 * If the User_ID is found, it retrieves the User_name from the userlogin.user table for the retrieved User_ID.
 * If the username is found, it is returned as a string. If no username is found or an error occurs,
 * an empty string is returned.
 *
 * @throws SQLException If an SQL error occurs.
 */
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
    /**
 * Retrieves the rating given by a user in their comment on a specific movie from the database.
 *
 * @param con      The connection to the database.
 * @param userId   The ID of the user who made the comment.
 * @param movieId  The ID of the movie on which the comment was made.
 * @return The rating given by the user in their comment, or 0.0 if not found or an error occurs.
 *
 * This method fetches the Comment_rating field from the userlogin.comment table for the specified user ID and movie ID.
 * If the rating is found, it is returned as a Double. If no rating is found or an error occurs,
 * a default value of 0.0 is returned.
 *
 * @throws SQLException If an SQL error occurs.
 */
    private Double getCommentRating(Connection con, int userId, int movieId) {
       
        Double Rating = (double)0;
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
    /**
 * Retrieves and formats the date of a comment made by a user on a specific movie from the database.
 *
 * @param con      The connection to the database.
 * @param userId   The ID of the user who made the comment.
 * @param movieId  The ID of the movie on which the comment was made.
 * @return The formatted date of the comment as a string in the format "dd.MMMM.yyyy" (e.g., "01.Januar.2020"),
 *         or an empty string if not found or an error occurs.
 *
 * This method fetches the Comment_date field from the userlogin.comment table for the specified user ID and movie ID.
 * The date is then parsed from the database format "yyyy-MM-dd" and formatted into "dd.MMMM.yyyy" in German locale.
 * If the date is found and successfully formatted, it is returned as a string. If no date is found or an error occurs,
 * an empty string is returned.
 *
 * @throws SQLException If an SQL error occurs.
 * @throws ParseException If a date parsing error occurs.
 */
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
