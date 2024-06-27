/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.login;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jrwie
 */
public class AdmindeletemovieController implements Initializable {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    private ObservableList<Movie> data = FXCollections.observableArrayList();
    @FXML
    private TableView<Movie> table;
    @FXML
    private TextField searchtext;
    private Connection con;
    @FXML
    private Label errortext;

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
        

        // TODO
    }    

    @FXML
    private void searchDatabase() throws SQLException{
        data.clear();
        
        con = dbconnect.connect();
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
    private void back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("adminmenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void deleteSelectedPerson(ActionEvent event) {
        Movie selectedPerson = table.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            data.remove(selectedPerson); // Aus ObservableList entfernen
            deletePersonFromDatabase(selectedPerson); // Aus der Datenbank entfernen
        } else {
            errortext.setText("no Movie selected!");
        }
    }
    
    private void deletePersonFromDatabase(Movie movie) {
    // Hier müsstest du deine Logik zur Verbindung und zum Löschen aus der Datenbank einfügen
    try (Connection conn = dbconnect.connect();
         Statement stmt = conn.createStatement()) {
        String query = "DELETE FROM userlogin.movie WHERE Movie_ID = " + movie.getId();
        int rowsAffected = stmt.executeUpdate(query);
        if (rowsAffected > 0) {
            errortext.setText("Movie deleted!");
        } else {
            errortext.setText("cant delete movie! :(");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
}
