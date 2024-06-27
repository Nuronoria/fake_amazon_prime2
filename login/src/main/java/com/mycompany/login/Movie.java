/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.login;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

/**
 *
 * @author jrwie
 */
public class Movie {
    private final StringProperty title;
    private final StringProperty description;
    private final StringProperty release;
    private final SimpleObjectProperty<Image> picture;
    private final SimpleObjectProperty<Image> banner;
    private final SimpleObjectProperty<Image> logo;

    public Movie(String title,String description,Image picture,Image banner,String release,Image logo) {
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.release = new  SimpleStringProperty(release);
        this.picture = new SimpleObjectProperty(picture);
        this.banner = new SimpleObjectProperty(banner);
        this.logo = new SimpleObjectProperty(logo);
    }
    
     public String getTitle() {
        return this.title.get();
    }
    public String getDescription(){
        return this.description.get();
    }
    public String getRelease(){
        return this.release.get();
    }
    public Image getPicture(){
        return this.picture.get();
    }
    public Image getBanner(){
        return this.banner.get();
    }
    public Image getLogo(){
        return this.logo.get();
    }
    
    public void setTitle(String title){
        this.title.set(title);
    }
    public void setDescription(String description){
        this.description.set(description);
    }
    public void setRelease(String release){
        this.release.set(release);
    }
    public void setPicture(Image picture){
        this.picture.set(picture);
    }
    public void setbanner(Image banner){
        this.banner.set(banner);
    }
    public void setLogo(Image logo){
        this.logo.set(logo);
    }
    
    public StringProperty titleProperty() {
        return title;
    }
    public StringProperty descriptionProperty() {
        return description;
    }
    public StringProperty releaseProperty() {
        return release;
    }
    public SimpleObjectProperty<Image> pictureProperty(){
        return picture;
    }
    public SimpleObjectProperty<Image> bannerProperty(){
        return banner;
    }
    public SimpleObjectProperty<Image> logoProperty(){
        return logo;
    }
}
