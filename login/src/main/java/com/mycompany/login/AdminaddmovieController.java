/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.login;

import java.io.File;
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
    private Image moviepicture;
    private Image moviebanner;
    private Image movielogo;
    
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
    private Label moviepicturename;
    @FXML
    private AnchorPane imagefield2;
    @FXML
    private ImageView image2;
    @FXML
    private AnchorPane dragfield2;
    @FXML
    private Label moviepicturename1;
    @FXML
    private AnchorPane imagefield3;
    @FXML
    private AnchorPane dragfield3;
    @FXML
    private Label moviepicturename11;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void dragenterregion(DragEvent event) {
        Node source = (Node) event.getSource();
        if(source instanceof  Region){
            Region label = (Region) source;
            label.setStyle("-fx-background-color: grey;");
        }
    }
    
    @FXML
    private void dragexitregion(DragEvent event) {
        Node source = (Node) event.getSource();
        if(source instanceof  Region){
            Region label = (Region) source;
            label.setStyle("-fx-background-color: transparent;");
        }
    }

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
                if(errorcheck4()){
                    break;
                }else{
                    errortext.setText("");
                }
                if(errorcheck()){
                    break;
                }
                upload();
                break;
            default:
                throw new AssertionError();
        }
    }

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

    @FXML
    private void canclebutton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("adminmenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
    
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

    @FXML
    private void imagedropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        switch (visiblecheck()) {
            case 2:
                if (db.hasFiles()) {
                    String filePath = null;
                    for (File file : db.getFiles()) {
                        filePath = file.getAbsolutePath();
                        moviepicture = new Image(file.toURI().toString());
                        image2.setImage(moviepicture);
                    }
                    dragfield2.setVisible(false);
                    imagefield2.setVisible(true);
                }
                event.consume();
                break;
            case 3:
                if (db.hasFiles()) {
                    String filePath = null;
                    for (File file : db.getFiles()) {
                        filePath = file.getAbsolutePath();
                        moviebanner = new Image(file.toURI().toString());
                        image3.setImage(moviebanner);
                    }
                    dragfield3.setVisible(false);
                    imagefield3.setVisible(true);
                }
                event.consume();
                break;
            case 4:
                if (db.hasFiles()) {
                    String filePath = null;
                    for (File file : db.getFiles()) {
                        filePath = file.getAbsolutePath();
                        movielogo = new Image(file.toURI().toString());
                        image4.setImage(movielogo);
                    }
                    dragfield4.setVisible(false);
                    imagefield4.setVisible(true);
                }
                event.consume();
                break;
            default:
                System.out.println("imagedropped failed");
                throw new AssertionError();
        }
            
    }

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
    
    private void upload(){
        
    }
    
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
    
}
