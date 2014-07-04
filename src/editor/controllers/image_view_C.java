package editor.controllers;

import Classes.Module;
import editor.classes.DerbyDBManager;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by godex_000 on 03.07.2014.
 */
public class image_view_C implements Initializable {
    public ImageView IV_IP;
    public ScrollPane SP_IP;
    public AnchorPane AP_IP;

    void initData(Image show_image) {
        IV_IP.setFitHeight(show_image.getHeight());
        IV_IP.setFitWidth(show_image.getWidth());
        IV_IP.setImage(show_image);
        AP_IP.setPrefWidth(show_image.getWidth());
        AP_IP.setPrefHeight(show_image.getHeight());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}