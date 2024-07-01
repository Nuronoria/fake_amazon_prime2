/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.login;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jrwie
 */
public class AdminmovieextraController implements Initializable {

    
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    private int workid;
    private ObservableList<Movie> data = FXCollections.observableArrayList();
    @FXML
    private TableView<Movie> table;
    @FXML
    private TextField searchtext;
    @FXML
    private Label errortext;
    @FXML
    private CheckBox language1;
    @FXML
    private CheckBox language2;
    @FXML
    private CheckBox language3;
    @FXML
    private CheckBox language4;
    @FXML
    private CheckBox language5;
    @FXML
    private CheckBox Genre1;
    @FXML
    private CheckBox Genre3;
    @FXML
    private CheckBox Genre5;
    @FXML
    private CheckBox Genre2;
    @FXML
    private CheckBox Genre4;
    @FXML
    private CheckBox Genre6;
    @FXML
    private CheckBox Genre7;
    @FXML
    private AnchorPane pane1;
    @FXML
    private AnchorPane pane2;
    
    private ObservableList<CheckBox> languageList = FXCollections.observableArrayList(language1,language2,language3,language4,language5);
    private ObservableList<CheckBox> genreList = FXCollections.observableArrayList(Genre1,Genre2,Genre3,Genre4,Genre5,Genre6,Genre7);
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableColumn<Movie, Integer> idCol = new TableColumn<>("Movie_ID");
        idCol.setCellValueFactory(cellData -> Bindings.createObjectBinding(
            () -> cellData.getValue().idProperty().get(),
            cellData.getValue().idProperty()
        ));
        
        TableColumn<Movie, String> titleCol = new TableColumn<>("Movie_Title");
        titleCol.setCellValueFactory(cellData -> cellData.getValue().titleProperty());

        TableColumn<Movie, String> descriptionCol = new TableColumn<>("Movie_Description");
        descriptionCol.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        
        TableColumn<Movie, Image> pictureCol = new TableColumn<>("Movie_Picture");
        pictureCol.setCellValueFactory(cellData -> cellData.getValue().pictureProperty());
        pictureCol.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();
            @Override
            protected void updateItem(Image item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(item);
                    imageView.setFitHeight(50);
                    imageView.setFitWidth(50);
                    setGraphic(imageView);
                }
            }
        });
        
        TableColumn<Movie, Image> bannerCol = new TableColumn<>("Movie_Banner");
        bannerCol.setCellValueFactory(cellData -> cellData.getValue().bannerProperty());
        bannerCol.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();
            @Override
            protected void updateItem(Image item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(item);
                    imageView.setFitHeight(50);
                    imageView.setFitWidth(50);
                    setGraphic(imageView);
                }
            }
        });
        
        TableColumn<Movie, String> releaseCol = new TableColumn<>("Movie_Release");
        releaseCol.setCellValueFactory(cellData -> cellData.getValue().releaseProperty());
        
        TableColumn<Movie, Image> logoCol = new TableColumn<>("Movie_Logo");
        logoCol.setCellValueFactory(cellData -> cellData.getValue().logoProperty());
        logoCol.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();
            @Override
            protected void updateItem(Image item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(item);
                    imageView.setFitHeight(50);
                    imageView.setFitWidth(50);
                    setGraphic(imageView);
                }
            }
        });
        
        
        
        table.getColumns().addAll(idCol,titleCol, descriptionCol, pictureCol, bannerCol, releaseCol, logoCol);
        table.setItems(data);
    }    
    
    @FXML
    private void searchDatabase() throws SQLException{
        data.clear();
        
        Connection con = dbconnect.connect();
        Statement stm = con.createStatement();


        String sql = "SELECT Movie_ID,Movie_title,Movie_description,Movie_picture,Movie_banner,Movie_release,Movie_logo FROM userlogin.movie where Movie_title Like'%"+searchtext.getText()+"%';";

        //rs = resultSet
        ResultSet rs = stm.executeQuery(sql);
        
        while (rs.next()) {
            int id = rs.getInt("Movie_ID");
            
            String title = rs.getString("Movie_title");
            
            String description = rs.getString("Movie_description");
            
            byte[] imageBytes = rs.getBytes("Movie_picture");
            Image picture = (imageBytes != null) ? new Image(new ByteArrayInputStream(imageBytes)) : null;
            
            byte[] imageBytes2 = rs.getBytes("Movie_banner");
            Image banner = (imageBytes2 != null) ? new Image(new ByteArrayInputStream(imageBytes2)) : null;
            
            String release = rs.getString("Movie_release");
            
            byte[] imageBytes3 = rs.getBytes("Movie_logo");
            Image logo = (imageBytes3 != null) ? new Image(new ByteArrayInputStream(imageBytes3)) : null;
            
            data.add(new Movie(id, title, description, picture, banner, release, logo));
        }

    }

    @FXML
    private void back(ActionEvent event) {
        ObservableList<CheckBox> languageList = FXCollections.observableArrayList(language1,language2,language3,language4,language5);
        ObservableList<CheckBox> genreList = FXCollections.observableArrayList(Genre1,Genre2,Genre3,Genre4,Genre5,Genre6,Genre7);
        for(int zahl=0;zahl < 7;zahl++){
            genreList.get(zahl).setSelected(false);
        }
        for(int zahl=0;zahl < 5;zahl++){
            languageList.get(zahl).setSelected(false);
        }
        pane2.setVisible(false);
        pane1.setVisible(true);
        unselecktall();
        
    }

    @FXML
    private void SelectMovie(ActionEvent event) {
        Movie selectedMovie = table.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            workid = selectedMovie.getId();
            pane1.setVisible(false);
            pane2.setVisible(true);
            if(workid > 0){
                errortext.setText("Movie selected");
                setcheckboxes();
            }else{
                errortext.setText("ERROR MOVIE NOT SELECTED");
            }
        } else {
            errortext.setText("no Movie selected!");
        }
    }

    @FXML
    private void finish(ActionEvent event) {
        ObservableList<CheckBox> languageList = FXCollections.observableArrayList(language1,language2,language3,language4,language5);
        ObservableList<CheckBox> genreList = FXCollections.observableArrayList(Genre1,Genre2,Genre3,Genre4,Genre5,Genre6,Genre7);
        for(int zahl=0;zahl < 7;zahl++){
        int genreid = getGenreId(genreList.get(zahl).getText());
        insertMovieGenre(workid,genreid,genreList.get(zahl).isSelected());
        }
        for(int zahl=0;zahl < 5;zahl++){
        int languageid = getLanguageId(languageList.get(zahl).getText());
        insertMovieLanguage(workid,languageid,languageList.get(zahl).isSelected());
        }
        
        
        workid = 0;
        pane1.setVisible(true);
        pane2.setVisible(false);
        errortext.setText("SQLUpdate successfully!!!");
        unselecktall();
    }

    @FXML
    private void abort(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("adminmenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    private void insertMovieGenre(int movieId,int genreId, boolean hinzufuegen){
        if(genreId == 0){
            System.out.println("GenreID == 0 getGenreId broke!!!");
            return;
        }
        try(Connection con = dbconnect.connect()) {

            if(hinzufuegen){
                String sql ="INSERT INTO userlogin.moviegenre (Movie_ID, Genre_ID)\n" +
                            "SELECT ?, ?\n" +
                            "WHERE NOT EXISTS (\n" +
                            "    SELECT 1 FROM userlogin.moviegenre WHERE Movie_ID = ? AND Genre_ID = ?\n" +
                            ");";
                try(PreparedStatement pstmt = con.prepareStatement(sql)){
                    pstmt.setInt(1, movieId);
                    pstmt.setInt(2, genreId);
                    pstmt.setInt(3, movieId);
                    pstmt.setInt(4, genreId);
                    
                    pstmt.executeUpdate();
                }
            }else if(!hinzufuegen){
                String sql = "DELETE FROM userlogin.moviegenre WHERE Movie_ID = ? AND Genre_ID = ?";
                try(PreparedStatement pstmt = con.prepareStatement(sql)){
                    pstmt.setInt(1, movieId);
                    pstmt.setInt(2, genreId);
                    
                    pstmt.executeUpdate();
                }
            }else{
                System.out.println("insertMovieGenre BROKE!!!");
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    private int getGenreId(String genre){
        try(Connection con = dbconnect.connect()) {
            String sql ="SELECT Genre_ID FROM userlogin.genre WHERE Genre_name = ?";
            try(PreparedStatement pstmt = con.prepareStatement(sql)){
                pstmt.setString(1, genre);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    return rs.getInt("Genre_ID");
                }
                return 0;
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    private int getLanguageId(String language){
        try(Connection con = dbconnect.connect()) {
            String sql ="SELECT Language_ID FROM userlogin.language WHERE Language_name = ?";
            try(PreparedStatement pstmt = con.prepareStatement(sql)){
                pstmt.setString(1, language);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    return rs.getInt("Language_ID");
                }
                return 0;
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    private void insertMovieLanguage(int movieId,int languageId, boolean hinzufuegen){
        if(languageId == 0){
            System.out.println("LanguageID == 0 getLanguageId broke!!!");
            return;
        }
        try(Connection con = dbconnect.connect()) {

            if(hinzufuegen){
                String sql ="INSERT INTO userlogin.movielanguage (Movie_ID, Language_ID)\n" +
                            "SELECT ?, ?\n" +
                            "WHERE NOT EXISTS (\n" +
                            "    SELECT 1 FROM userlogin.movielanguage WHERE Movie_ID = ? AND Language_ID = ?\n" +
                            ");";
                try(PreparedStatement pstmt = con.prepareStatement(sql)){
                    pstmt.setInt(1, movieId);
                    pstmt.setInt(2, languageId);
                    pstmt.setInt(3, movieId);
                    pstmt.setInt(4, languageId);
                    
                    pstmt.executeUpdate();
                }
            }else if(!hinzufuegen){
                String sql = "DELETE FROM userlogin.movielanguage WHERE Movie_ID = ? AND Language_ID = ?";
                try(PreparedStatement pstmt = con.prepareStatement(sql)){
                    pstmt.setInt(1, movieId);
                    pstmt.setInt(2, languageId);
                    
                    pstmt.executeUpdate();
                }
            }else{
                System.out.println("insertMovieGenre BROKE!!!");
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void setcheckboxes(){
        ObservableList<CheckBox> languageList = FXCollections.observableArrayList(language1,language2,language3,language4,language5);
        ObservableList<CheckBox> genreList = FXCollections.observableArrayList(Genre1,Genre2,Genre3,Genre4,Genre5,Genre6,Genre7);
        
        try(Connection con = dbconnect.connect()) {
            String sql ="SELECT Genre_ID FROM userlogin.moviegenre WHERE Movie_ID = ?";
            try(PreparedStatement pstmt = con.prepareStatement(sql)){
                pstmt.setInt(1, workid);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    genreList.get(rs.getInt("Genre_ID")-1).setSelected(true);
                }
            }
            sql ="SELECT Language_ID FROM userlogin.movielanguage WHERE Movie_ID = ?";
            try(PreparedStatement pstmt = con.prepareStatement(sql)){
                pstmt.setInt(1, workid);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    languageList.get(rs.getInt("Language_ID")-1).setSelected(true);
                }
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * unselects all checkboxes on the second pane
     */
    private void unselecktall(){
        language1.setSelected(false);
        language2.setSelected(false);
        language3.setSelected(false);
        language4.setSelected(false);
        language5.setSelected(false);
        Genre1.setSelected(false);
        Genre2.setSelected(false);
        Genre3.setSelected(false);
        Genre4.setSelected(false);
        Genre5.setSelected(false);
        Genre6.setSelected(false);
        Genre7.setSelected(false);
    }
}
