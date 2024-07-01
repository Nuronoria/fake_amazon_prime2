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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * FXML Controller class
 *
 * @author kjh27
 */
public class SecondaryController implements Initializable {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    private int movieID;
    private Connection con;
    
    @FXML
    private JFXButton MenuButton1;
    @FXML
    private JFXButton MenuButton0;
    @FXML
    private ImageView movie1;
    @FXML
    private Pane movieanch1;
    @FXML
    private Pane slider1;
    @FXML
    private ImageView movie2;
    @FXML
    private Pane movieanch2;
    @FXML
    private Pane slider2;
    @FXML
    private ImageView movie3;
    @FXML
    private Pane movieanch3;
    @FXML
    private Pane slider3;
    @FXML
    private ImageView movie4;
    @FXML
    private Pane movieanch4;
    @FXML
    private Pane slider4;
    @FXML
    private JFXTextArea moviedescrip1;
    @FXML
    private JFXTextArea moviedescrip2;
    @FXML
    private JFXTextArea moviedescrip3;
    @FXML
    private JFXTextArea moviedescrip4;
    @FXML
    private ImageView nextSlideButton;
    @FXML
    private AnchorPane mainslide;
    @FXML
    private Pane movieanch5;
    @FXML
    private ImageView movie5;
    @FXML
    private Pane slider5;
    @FXML
    private JFXTextArea moviedescrip5;
    @FXML
    private Pane movieanch6;
    @FXML
    private ImageView movie6;
    @FXML
    private Pane slider6;
    @FXML
    private JFXTextArea moviedescrip6;
    @FXML
    private Pane movieanch7;
    @FXML
    private ImageView movie7;
    @FXML
    private Pane slider7;
    @FXML
    private JFXTextArea moviedescrip7;
    @FXML
    private Pane movieanch8;
    @FXML
    private ImageView movie8;
    @FXML
    private Pane slider8;
    @FXML
    private JFXTextArea moviedescrip8;
    @FXML
    private ImageView nextSlideButton1;
    @FXML
    private JFXButton MenuButtonItem0;
    @FXML
    private JFXButton MenuButton2;
    @FXML
    private JFXButton MenuButton21;
    @FXML
    private JFXButton MenuButtonItem1;
    @FXML
    private JFXButton MenuButtonItem11;
    @FXML
    private JFXButton MenuButtonItem01;
    @FXML
    private JFXButton adminbutton;
    @FXML
    private Label title1;
    @FXML
    private Label title2;
    @FXML
    private Label title3;
    @FXML
    private Label title4;
    @FXML
    private Label title5;
    @FXML
    private Label title6;
    @FXML
    private Label title7;
    @FXML
    private Label title8;
    @FXML
    private ImageView button1;
    @FXML
    private ImageView button2;
    @FXML
    private ImageView button3;
    @FXML
    private ImageView button4;
    @FXML
    private ImageView button5;
    @FXML
    private ImageView button6;
    @FXML
    private ImageView button7;
    @FXML
    private ImageView button8;
    
    
    private ObservableList<Integer> idList = FXCollections.observableArrayList();
    private ObservableList<JFXTextArea> descriptionList = FXCollections.observableArrayList(moviedescrip1,moviedescrip2,moviedescrip3,moviedescrip4,moviedescrip5,moviedescrip6,moviedescrip7,moviedescrip8);
    private ObservableList<ImageView> pictureList = FXCollections.observableArrayList(movie1,movie2,movie3,movie4,movie5,movie6,movie7,movie8);
    private ObservableList<Label> titleList = FXCollections.observableArrayList(title1,title2,title3,title4,title5,title6,title7,title8);
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        setUsername();

        if (SessionManager.admin) {
            adminbutton.setVisible(true);
        }else{
            adminbutton.setVisible(false);
        }
        
        ScaleTransition scaleIn = new ScaleTransition(Duration.seconds(0.5), movie1);
        scaleIn.setToX(1.2);
        scaleIn.setToY(1.2);
        
        ScaleTransition scaleOut = new ScaleTransition(Duration.seconds(0.5), movie1);
        scaleOut.setToX(1);
        scaleOut.setToY(1);
        
        ScaleTransition scaleIn2 = new ScaleTransition(Duration.seconds(0.5), movie2);
        scaleIn2.setToX(1.2);
        scaleIn2.setToY(1.2);
        
        ScaleTransition scaleOut2 = new ScaleTransition(Duration.seconds(0.5), movie2);
        scaleOut2.setToX(1);
        scaleOut2.setToY(1);
        
        ScaleTransition scaleIn3 = new ScaleTransition(Duration.seconds(0.5), movie3);
        scaleIn3.setToX(1.2);
        scaleIn3.setToY(1.2);
        
        ScaleTransition scaleOut3 = new ScaleTransition(Duration.seconds(0.5), movie3);
        scaleOut3.setToX(1);
        scaleOut3.setToY(1);
        
        ScaleTransition scaleIn4 = new ScaleTransition(Duration.seconds(0.5), movie4);
        scaleIn4.setToX(1.2);
        scaleIn4.setToY(1.2);
        
        ScaleTransition scaleOut4 = new ScaleTransition(Duration.seconds(0.5), movie4);
        scaleOut4.setToX(1);
        scaleOut4.setToY(1);
        
        ScaleTransition scaleIn5 = new ScaleTransition(Duration.seconds(0.5), movie5);
        scaleIn5.setToX(1.2);
        scaleIn5.setToY(1.2);
        
        ScaleTransition scaleOut5 = new ScaleTransition(Duration.seconds(0.5), movie5);
        scaleOut5.setToX(1);
        scaleOut5.setToY(1);
        
        ScaleTransition scaleIn6 = new ScaleTransition(Duration.seconds(0.5), movie6);
        scaleIn6.setToX(1.2);
        scaleIn6.setToY(1.2);
        
        ScaleTransition scaleOut6 = new ScaleTransition(Duration.seconds(0.5), movie6);
        scaleOut6.setToX(1);
        scaleOut6.setToY(1);
        
        ScaleTransition scaleIn7 = new ScaleTransition(Duration.seconds(0.5), movie7);
        scaleIn7.setToX(1.2);
        scaleIn7.setToY(1.2);
        
        ScaleTransition scaleOut7 = new ScaleTransition(Duration.seconds(0.5), movie7);
        scaleOut7.setToX(1);
        scaleOut7.setToY(1);
        
        ScaleTransition scaleIn8 = new ScaleTransition(Duration.seconds(0.5), movie8);
        scaleIn8.setToX(1.2);
        scaleIn8.setToY(1.2);
        
        ScaleTransition scaleOut8 = new ScaleTransition(Duration.seconds(0.5), movie8);
        scaleOut8.setToX(1);
        scaleOut8.setToY(1);
        
         slider1.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), slider1);
        fadeIn.setToValue(1);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), slider1);
        fadeOut.setToValue(0);
        
        slider2.setOpacity(0);
        FadeTransition fadeIn2 = new FadeTransition(Duration.seconds(0.5), slider2);
        fadeIn2.setToValue(1);

        FadeTransition fadeOut2 = new FadeTransition(Duration.seconds(0.5), slider2);
        fadeOut2.setToValue(0);
        
        slider3.setOpacity(0);
        FadeTransition fadeIn3 = new FadeTransition(Duration.seconds(0.5), slider3);
        fadeIn3.setToValue(1);

        FadeTransition fadeOut3 = new FadeTransition(Duration.seconds(0.5), slider3);
        fadeOut3.setToValue(0);
        
        slider4.setOpacity(0);
        FadeTransition fadeIn4 = new FadeTransition(Duration.seconds(0.5), slider4);
        fadeIn4.setToValue(1);

        FadeTransition fadeOut4 = new FadeTransition(Duration.seconds(0.5), slider4);
        fadeOut4.setToValue(0);
        
                slider5.setOpacity(0);
        FadeTransition fadeIn5 = new FadeTransition(Duration.seconds(0.5), slider5);
        fadeIn5.setToValue(1);

        FadeTransition fadeOut5 = new FadeTransition(Duration.seconds(0.5), slider5);
        fadeOut5.setToValue(0);
        
         slider6.setOpacity(0);
        FadeTransition fadeIn6 = new FadeTransition(Duration.seconds(0.5), slider6);
        fadeIn6.setToValue(1);

        FadeTransition fadeOut6 = new FadeTransition(Duration.seconds(0.5), slider6);
        fadeOut6.setToValue(0);
        
                slider7.setOpacity(0);
        FadeTransition fadeIn7 = new FadeTransition(Duration.seconds(0.5), slider7);
        fadeIn7.setToValue(1);

        FadeTransition fadeOut7 = new FadeTransition(Duration.seconds(0.5), slider7);
        fadeOut7.setToValue(0);
        
                slider8.setOpacity(0);
        FadeTransition fadeIn8 = new FadeTransition(Duration.seconds(0.5), slider8);
        fadeIn8.setToValue(1);

        FadeTransition fadeOut8 = new FadeTransition(Duration.seconds(0.5), slider8);
        fadeOut8.setToValue(0);
        
        FadeTransition fadeInButton = new FadeTransition(Duration.seconds(0.2), nextSlideButton);
        fadeInButton.setToValue(0.3);

        FadeTransition fadeOutButton = new FadeTransition(Duration.seconds(0.2), nextSlideButton);
        fadeOutButton.setToValue(0);
        
        FadeTransition fadeInButton1 = new FadeTransition(Duration.seconds(0.2), nextSlideButton1);
        fadeInButton1.setToValue(0.3);

        FadeTransition fadeOutButton1 = new FadeTransition(Duration.seconds(0.2), nextSlideButton1);
        fadeOutButton1.setToValue(0);
        
        slider1.setDisable(true);
        
        
        
        movie1.setOnMouseEntered(event -> {
            scaleIn.play(); // 확대 애니메이션 재생
            fadeIn.play(); 
            
            slider1.setDisable(false);

        });
             
        movieanch1.setOnMouseExited(event -> {
            scaleOut.play(); // 확대 애니메이션 재생
            fadeOut.play(); 
            slider1.setDisable(true);
        });
        
                movie2.setOnMouseEntered(event -> {
            scaleIn2.play(); // 확대 애니메이션 재생
            fadeIn2.play(); 

        });
             
        movieanch2.setOnMouseExited(event -> {
            scaleOut2.play(); // 확대 애니메이션 재생
            fadeOut2.play(); 
        });
        
        movie3.setOnMouseEntered(event -> {
            scaleIn3.play(); // 확대 애니메이션 재생
            fadeIn3.play(); 

        });
             
        movieanch3.setOnMouseExited(event -> {
            scaleOut3.play(); // 확대 애니메이션 재생
            fadeOut3.play(); 
        });
        
        movie4.setOnMouseEntered(event -> {
            scaleIn4.play(); // 확대 애니메이션 재생
            fadeIn4.play(); 

        });
             
        movieanch4.setOnMouseExited(event -> {
            scaleOut4.play(); // 확대 애니메이션 재생
            fadeOut4.play(); 
        });
        
                movie5.setOnMouseEntered(event -> {
            scaleIn5.play(); // 확대 애니메이션 재생
            fadeIn5.play(); 

        });
             
        movieanch5.setOnMouseExited(event -> {
            scaleOut5.play(); // 확대 애니메이션 재생
            fadeOut5.play(); 
        });
                movie6.setOnMouseEntered(event -> {
            scaleIn6.play(); // 확대 애니메이션 재생
            fadeIn6.play(); 

        });
             
        movieanch6.setOnMouseExited(event -> {
            scaleOut6.play(); // 확대 애니메이션 재생
            fadeOut6.play(); 
        });
                movie7.setOnMouseEntered(event -> {
            scaleIn7.play(); // 확대 애니메이션 재생
            fadeIn7.play(); 

        });
             
        movieanch7.setOnMouseExited(event -> {
            scaleOut7.play(); // 확대 애니메이션 재생
            fadeOut7.play(); 
        });
                movie8.setOnMouseEntered(event -> {
            scaleIn8.play(); // 확대 애니메이션 재생
            fadeIn8.play(); 

        });
             
        movieanch8.setOnMouseExited(event -> {
            scaleOut8.play(); // 확대 애니메이션 재생
            fadeOut8.play(); 
        });
        
        mainslide.setOnMouseEntered(event -> {
    
                fadeInButton.play(); 
           
            

        });
             
        mainslide.setOnMouseExited(event -> {

                fadeOutButton.play(); 
            
        });
        if (SessionManager.admin == true) {
            adminbutton.setVisible(true);
        }else{
            adminbutton.setVisible(false);
        }
        
            
            

            try {
                fillMovies();
            } catch (SQLException ex) {
                System.out.println("fillMovies failed");
            }
        
     
    }
    
    @FXML
    void onUserButton(MouseEvent event) {
        MenuButton1.setOpacity(0.7);
        MenuButton1.setStyle("-fx-background-color: #494949");
        MenuButton0.setStyle("-fx-background-color: #494949");
        
    }
    
    @FXML
    void outUserButton(MouseEvent event) {
        MenuButton1.setOpacity(0);
        MenuButton1.setStyle("-fx-background-color: transparent");
        MenuButton0.setStyle("-fx-background-color: transparent");

    } 
    
    @FXML
    void onClickedLogout(MouseEvent event) throws IOException {
        System.out.println("Logout was successful.");
        root = FXMLLoader.load(getClass().getResource("primary.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        SessionManager.userID = 0;
        SessionManager.admin = false;

    }
    
    @FXML
    void onClickedMovie(MouseEvent event) throws IOException {
        
    Node sourceNode = (Node) event.getSource();
    
    if (sourceNode instanceof ImageView) {
        ImageView clickedImageView = (ImageView) sourceNode;
        // Get the fx:id or any other property
        String imageViewId = clickedImageView.getId();
        String numericPart = imageViewId.replaceAll("[^0-9]", "");
        int conv_MovieID = Integer.parseInt(numericPart);
        System.out.println(idList.get(conv_MovieID-1)+"ist die ID des Filmes! der code zum seite wechseln ist noch im comment!!!!!!!!!!!!!");
        SessionManager.movieID = idList.get(conv_MovieID-1);
        
        // Do something with conv_MovieID
    }else {
        System.out.println("funktioniert nicht!");
        return;
    }
        System.out.println(SessionManager.movieID);
        
        root = FXMLLoader.load(getClass().getResource("tertiary.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void nextSlide(MouseEvent event) {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(1));
        transition.setNode(mainslide);
        transition.setByX(-900);
        
        transition.play();
    
    }

    @FXML
    private void previousSlide(MouseEvent event) {
                TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(1));
        transition.setNode(mainslide);
        transition.setByX(900);
        
        transition.play();
    }

    @FXML
    private void outMenuButton2(MouseEvent event) {
        MenuButton2.setOpacity(0.7);
        MenuButtonItem0.setOpacity(0);
        MenuButtonItem1.setOpacity(0);
        MenuButtonItem0.setStyle("-fx-background-color: transparent");
        MenuButtonItem1.setStyle("-fx-background-color: transparent");
        MenuButton2.setStyle("-fx-background-color: transparent");
    } 
    
    @FXML
    private void outMenuButton21(MouseEvent event) {
        MenuButton21.setOpacity(0.7);
        MenuButtonItem01.setOpacity(0);
        MenuButtonItem11.setOpacity(0);
        MenuButtonItem01.setStyle("-fx-background-color: transparent");
        MenuButtonItem11.setStyle("-fx-background-color: transparent");
        MenuButton21.setStyle("-fx-background-color: transparent");
    }

    @FXML
    private void onMenuButton2(MouseEvent event) {
        MenuButtonItem0.setOpacity(1);
        MenuButtonItem1.setOpacity(1);
        MenuButton2.setOpacity(1);
        MenuButtonItem0.setStyle("-fx-background-color: #494949");
        MenuButtonItem1.setStyle("-fx-background-color: #494949");
        MenuButton2.setStyle("-fx-background-color: #494949");
    }
    
    @FXML
    private void onMenuButton21(MouseEvent event) {
        MenuButtonItem01.setOpacity(1);
        MenuButtonItem11.setOpacity(1);
        MenuButton21.setOpacity(1);
        MenuButtonItem01.setStyle("-fx-background-color: #494949");
        MenuButtonItem11.setStyle("-fx-background-color: #494949");
        MenuButton21.setStyle("-fx-background-color: #494949");
    }


    @FXML
    private void toAdmin(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("adminmenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onRefresh(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("secondary.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void offAdminbutton(MouseEvent event) {
        adminbutton.setStyle("-fx-background-color: transparent");
    }

    @FXML
    private void onAdminbutton(MouseEvent event) {
        adminbutton.setStyle("-fx-background-color: #494949");
    }

    /**
     * fills the javafx elements with the movies from the database
     * in a random order.
     * all movie_ids from the quarry are saved in ObservableList<Integer> idList
     * 
     * @throws SQLException 
     */
    @FXML
    private void fillMovies() throws SQLException{
        
        ObservableList<JFXTextArea> descriptionList = FXCollections.observableArrayList(moviedescrip1,moviedescrip2,moviedescrip3,moviedescrip4,moviedescrip5,moviedescrip6,moviedescrip7,moviedescrip8);
        ObservableList<ImageView> pictureList = FXCollections.observableArrayList(movie1,movie2,movie3,movie4,movie5,movie6,movie7,movie8);
        ObservableList<Label> titleList = FXCollections.observableArrayList(title1,title2,title3,title4,title5,title6,title7,title8);
        
        
        //check if the labels imageViews and textFields exists
        int zahl =0;
        if (titleList.get(zahl) == null || pictureList.get(zahl) == null || descriptionList.get(zahl) == null) {
            return;
        }
        
        //database quarry
        con = dbconnect.connect();
        Statement stm = con.createStatement();
        String sql = "SELECT Movie_ID,Movie_title,Movie_description,Movie_picture FROM userlogin.movie";
        ResultSet rs = stm.executeQuery(sql);
        
            //creating a List with the resultset content. and shuffeling it at the end
            try {
                List<Map<String, Object>> rows = new ArrayList<>();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    for (int i = 1; i <= columnCount; i++) {
                        row.put(metaData.getColumnName(i), rs.getObject(i));
                    }
                    rows.add(row);
                }
                Collections.shuffle(rows);
                
                // putting the content of the list in the corresponding javafx elements
                for (Map<String, Object> row : rows) {
                    int id = (int)row.get("Movie_ID");
                    String title  = (String)row.get("Movie_title");
                    String description = (String)row.get("Movie_description");
                    byte[] imageBytes = (byte[])row.get("Movie_picture");

                    Image picture = (imageBytes != null) ? new Image(new ByteArrayInputStream(imageBytes)) : null;

                    idList.add(id);
                    titleList.get(zahl).setText(title);
                    descriptionList.get(zahl).setText(description);
                    pictureList.get(zahl).setImage(picture);

                    zahl++;
                    if (zahl == 8) {
                        break;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
    }
    
    private void setUsername(){
        
        if(SessionManager.userID == 0){
        MenuButton0.setText("");
        }else{
            String sql = "SELECT User_name FROM userlogin.user WHERE User_ID = ?;";
            try (Connection con = dbconnect.connect();PreparedStatement pstmt = con.prepareStatement(sql)){
                pstmt.setInt(1, SessionManager.userID);
                ResultSet rs = pstmt.executeQuery();
                rs.next();
                MenuButton0.setText(rs.getString("User_name"));
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        
    }
            
            
//        //der code wurde benutzt als die filme noch nihct geshuffelt wurden
//        while (rs.next()) {
//            
//            int id = rs.getInt("Movie_ID");
//            
//            String title = rs.getString("Movie_title");
//            
//            String description = rs.getString("Movie_description");
//            
//            byte[] imageBytes = rs.getBytes("Movie_picture");
//            Image picture = (imageBytes != null) ? new Image(new ByteArrayInputStream(imageBytes)) : null;
//            
//            idList.add(id);
//            titleList.get(zahl).setText(title);
//            descriptionList.get(zahl).setText(description);
//            pictureList.get(zahl).setImage(picture);
//            
//            zahl++;
//            if (zahl == 8) {
//                break;
//            }
//        }

    
}