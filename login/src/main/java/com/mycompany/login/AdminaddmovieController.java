/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.login;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author jrwie
 */
public class AdminaddmovieController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private File moviepicture;
    private File moviebanner;
    private File movielogo;
    private String moviepictureFormat;
    private String moviebannerFormat;
    private String movielogoFormat;
    
    @FXML
    private AnchorPane pane1;
    @FXML
    private AnchorPane pane2;
    @FXML
    private AnchorPane pane3;
    @FXML
    private AnchorPane pane4;
    @FXML
    private Label site;
    @FXML
    private AnchorPane imagefield2;
    @FXML
    private ImageView image2;
    @FXML
    private AnchorPane dragfield2;
    @FXML
    private AnchorPane imagefield3;
    @FXML
    private AnchorPane dragfield3;
    @FXML
    private AnchorPane imagefield4;
    @FXML
    private AnchorPane dragfield4;
    @FXML
    private ImageView image3;
    @FXML
    private ImageView image4;
    @FXML
    private TextArea descriptionfield;
    @FXML
    private Label counter;
    @FXML
    private Label errortext;
    @FXML
    private TextField movietitlefield;
    @FXML
    private DatePicker releasedatefield;
    @FXML
    private AnchorPane paneend;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    /**
    * Event handler for handling drag enter events on a Region node.
    *
    * @param event The DragEvent that triggered this handler.
    */
    @FXML
    private void dragenterregion(DragEvent event) {
        Node source = (Node) event.getSource();
        if(source instanceof  Region){
            Region label = (Region) source;
            label.setStyle("-fx-background-color: grey;");
        }
    }
    
    /**
    * Event handler for handling drag exit events on a Region node.
    *
    * @param event The DragEvent that triggered this handler.
    */
    @FXML
    private void dragexitregion(DragEvent event) {
        Node source = (Node) event.getSource();
        if(source instanceof  Region){
            Region label = (Region) source;
            label.setStyle("-fx-background-color: transparent;");
        }
    }

    /**
    * Handles the action event when the next button is clicked.
    *
    * @param event The ActionEvent triggered by clicking the button.
    */
    @FXML
    private void nextbutton(ActionEvent event) {
        int visible = visiblecheck();
        switch (visible) {
            case 0:
                System.out.println("visiblecheck hat versagt");
                break;
            case 1:
                if(errorcheck1()){
                    break;
                }else{
                    errortext.setText("");
                }
                pane1.setVisible(false);
                pane2.setVisible(true);
                site.setText("(2/4)");
                break;
            case 2:
                if(errorcheck2()){
                    break;
                }else{
                    errortext.setText("");
                }
                pane2.setVisible(false);
                pane3.setVisible(true);
                site.setText("(3/4)");
                break;
            case 3:
                if(errorcheck3()){
                    break;
                }else{
                    errortext.setText("");
                }
                pane3.setVisible(false);
                pane4.setVisible(true);
                site.setText("(4/4)");
                break;
            case 4:
                if (checkindatabase()) {
                    errortext.setText("There is already an movie with this Title!");
                    pane4.setVisible(false);
                    pane1.setVisible(true);
                    movietitlefield.setText("");
                    break;
                }
                if(errorcheck()){
                    System.out.println("irgendwas ist schief gelaufen");
                    errortext.setText("irgendwas ist schief gelaufen");
                    break;
                }
                saveToDatabase();
                pane4.setVisible(false);
                paneend.setVisible(true);
                break;
            default:
                throw new AssertionError();
        }
    }

    /**
    * Handles the action event when the back button is clicked.
    *
    * @param event The ActionEvent triggered by clicking the button.
    */
    @FXML
    private void backbutton(ActionEvent event) {
        int visible = visiblecheck();
        switch (visible) {
            case 0:
                System.out.println("visiblecheck hat versagt");
                break;
            case 1:
                
                break;
            case 2:
                pane2.setVisible(false);
                pane1.setVisible(true);
                site.setText("(1/4)");
                break;
            case 3:
                pane3.setVisible(false);
                pane2.setVisible(true);
                site.setText("(2/4)");
                break;
            case 4:
                pane4.setVisible(false);
                pane3.setVisible(true);
                site.setText("(3/4)");
                break;
            default:
                throw new AssertionError();
        }
    }

    /**
    * Handles the action event when the cancel button is clicked.
    *
    * @param event The ActionEvent triggered by clicking the button.
    * @throws IOException If an error occurs while loading the adminmenu.fxml file.
    */
    @FXML
    private void canclebutton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("adminmenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
    /**
    * Checks which pane is currently visible and returns an integer representing the visibility state.
    *
    * @return An integer indicating the visible pane:
    *         - 1 if pane1 is visible and others are not
    *         - 2 if pane2 is visible and others are not
    *         - 3 if pane3 is visible and others are not
    *         - 4 if pane4 is visible and others are not
    *         - 0 if none of the panes are visible
    */
    private int visiblecheck(){
        if(pane1.isVisible() && !pane2.isVisible() && !pane3.isVisible() && !pane4.isVisible()){
            return 1;
        }else if(!pane1.isVisible() && pane2.isVisible() && !pane3.isVisible() && !pane4.isVisible()){
            return 2;
        }else if(!pane1.isVisible() && !pane2.isVisible() && pane3.isVisible() && !pane4.isVisible()){
            return 3;
        }else if(!pane1.isVisible() && !pane2.isVisible() && !pane3.isVisible() && pane4.isVisible()){
            return 4;
        }
        return 0;
    }

    /**
    * Handles the event when an image is dropped into a specific region.
    *
    * @param event The DragEvent representing the drag and drop operation.
    */
    @FXML
    private void imagedropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        switch (visiblecheck()) {
            case 2:
                if (db.hasFiles()) {
                    String filePath = null;
                    File file = db.getFiles().get(0);
                    String fileName = file.getName().toLowerCase();
                    if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".gif")) {
                        try {
                            FileInputStream fis = new FileInputStream(file);
                            Image image = new Image(fis);
                            fis.close();

                            // Überprüfen, ob die Höhe größer ist als die Breite
                            if (image.getHeight() >= image.getWidth()) {
                                errortext.setText("");

                                // Bild im ImageView anzeigen
                                image2.setImage(image);
                                dragfield2.setVisible(false);
                                imagefield2.setVisible(true);
                                moviepicture = file;
                            } else {
                                errortext.setText("Image has the wrong parameters!");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        errortext.setText("Image file has the wrong datatype!");
                    }
                }
                event.consume();
                break;
            case 3:
                if (db.hasFiles()) {
                    String filePath = null;
                    File file = db.getFiles().get(0);
                    String fileName = file.getName().toLowerCase();
                    if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".gif")) {
                        try {
                            FileInputStream fis = new FileInputStream(file);
                            Image image = new Image(fis);
                            fis.close();

                            // Überprüfen, ob die Höhe größer ist als die Breite
                            if (image.getHeight() <= image.getWidth()) {
                                errortext.setText("");

                                // Bild im ImageView anzeigen
                                image3.setImage(image);
                                dragfield3.setVisible(false);
                                imagefield3.setVisible(true);
                                moviebanner = file;
                            } else {
                                errortext.setText("Image has the wrong parameters!");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        errortext.setText("Image file has the wrong datatype!");
                    }
                }
                event.consume();
                break;
            case 4:
                if (db.hasFiles()) {
                    String filePath = null;
                    File file = db.getFiles().get(0);
                    String fileName = file.getName().toLowerCase();
                    if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".gif")) {
                        try {
                            FileInputStream fis = new FileInputStream(file);
                            Image image = new Image(fis);
                            fis.close();

                            // Überprüfen, ob die Höhe größer ist als die Breite
                            if (image.getHeight() <= image.getWidth()) {
                                errortext.setText("");

                                // Bild im ImageView anzeigen
                                image4.setImage(image);
                                dragfield4.setVisible(false);
                                imagefield4.setVisible(true);
                                movielogo = file;
                            } else {
                                errortext.setText("Image has the wrong parameters!");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        errortext.setText("Image file has the wrong datatype!");
                    }
                }
                event.consume();
                break;
            default:
                System.out.println("imagedropped failed");
                throw new AssertionError();
        }
            
    }

    /**
    * Handles the event when an image is dragged over a specific region to check if it can be dropped.
    *
    * @param event The DragEvent representing the drag and drop operation.
    */
    @FXML
    private void imagedroppedcheck(DragEvent event) {
        switch (visiblecheck()) {
            case 2:
                if (event.getGestureSource() != image2 && event.getDragboard().hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
                break;
            case 3:
                if (event.getGestureSource() != image3 && event.getDragboard().hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
                break;
            case 4:
                if (event.getGestureSource() != image4 && event.getDragboard().hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
                break;
            default:
                System.out.println("imagedroppedcheck failed");
                throw new AssertionError();
        }
        
    }
    
    /**
    * Checks for errors in the input fields on the first pane of the movie adding process.
    * Displays appropriate error messages if any field is invalid or empty.
    *
    * @return true if there are errors, false otherwise.
    */
    private boolean errorcheck1(){
        if(movietitlefield.getText().isEmpty()){
            errortext.setText("Please enter an Movie Title!");
            return true;
        }
        if(releasedatefield.getValue() == null){
            errortext.setText("Please select an releasedate!");
            return true;
        }
        if(descriptionfield.getLength() >= 601){
            errortext.setText("Your descriptiontext is too long!");
            return true;
        }
        if(descriptionfield.getLength() == 0){
            errortext.setText("Please add a descriptiontext!");
            return true;
        }
        return false;
    }
    
    private boolean errorcheck2(){
        if(moviepicture == null){
           errortext.setText("Please add a MoviePicture!");
           return true;
        }
        return false;
    }
    
    private boolean errorcheck3(){
        if (moviebanner == null) {
            errortext.setText("Please add a Moviebanner!");
            return true;
        }
        return false;
    }
    
    private boolean errorcheck4(){
        if (movielogo == null) {
            errortext.setText("Please add a Movielogo!");
            return true;
        }
        return false;
    }
    
    private boolean errorcheck(){
        if(errorcheck1() || errorcheck2() || errorcheck3() || errorcheck4()){
            return true;
        }
        return false;
    }

    /**
    * Updates the character count for the description field and changes the color of the counter based on the length.
    *
    * @param event The KeyEvent triggering the method.
    */
    @FXML
    private void charcounter(KeyEvent event) {
        if(descriptionfield.getLength() <= 600){
            counter.setTextFill(Paint.valueOf("#979797"));
            counter.setText(""+descriptionfield.getLength());
        }else{
            counter.setTextFill(Paint.valueOf("#ff0000"));
            counter.setText(""+descriptionfield.getLength());
        }
    }
    
    /**
    * Saves the movie data to the database.
    */
    private void saveToDatabase(){
        try (Connection con = dbconnect.connect()){
            String sql = "INSERT INTO `userlogin`.`movie` (`Movie_title`, `Movie_description`, `Movie_picture`, `Movie_banner`, `Movie_release`, `Movie_trailerURL`, `Movie_logo`) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            
            LocalDate date = releasedatefield.getValue();
            FileInputStream fis1 = new FileInputStream(moviepicture);
            FileInputStream fis2 = new FileInputStream(moviebanner);
            FileInputStream fis3 = new FileInputStream(movielogo);
            
            pstmt.setString(1, movietitlefield.getText());
            pstmt.setString(2, descriptionfield.getText());
            pstmt.setBinaryStream(3, fis1, (int) moviepicture.length());
            pstmt.setBinaryStream(4, fis2, (int) moviebanner.length());
            pstmt.setString(5, date.toString());
            pstmt.setString(6, null);
            pstmt.setBinaryStream(7, fis3, (int) movielogo.length());
            
            pstmt.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("savetodatabase hat versagt");
        }
    }
    
    /**
    * Checks if a movie with the given title already exists in the database.
    * 
    * @return true if a movie with the given title exists in the database, false otherwise.
    */
    private boolean checkindatabase(){
        //con = connection
        try (Connection con = dbconnect.connect()){
            String sql = "SELECT * FROM `userlogin`.`movie` WHERE `Movie_title` = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            
            stm.setString(1, movietitlefield.getText());
            
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                con.close();
                return(true);
            }else{
                con.close();
                return(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in checkindatabase");
            return true;
        } 
    }
    
}