package editor.controllers;

import Classes.Pattern;
import editor.classes.DerbyDBManager;
import editor.services.draw_uml;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by godex_000 on 23.07.2014.
 */
public class patern_editor_C implements Initializable {
    public TextField TA_pattern_name;
    public TextArea TA_pattern_description;
    public TextArea TA_pattern_uml;
    public Image pattern_image;
    public Pattern tmp_pattern;
    public DerbyDBManager derby_DB;
    public GridPane root;

    void initData(Pattern pattern, DerbyDBManager derby_con) {
        tmp_pattern=pattern;

        derby_DB = derby_con;
        Stage thisstage = (Stage) root.getScene().getWindow();
        thisstage.getIcons().add(new Image("/editor/res/img/uml_icon.png"));
        thisstage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                Object[] options = {"Так",
                        "Ні"};
                int n = JOptionPane.showOptionDialog(null,
                        "Ви впевнені, що бажаете вийти незбережені зміни буде втрачено?",
                        "Увага",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,     //do not use a custom Icon
                        options,  //the titles of buttons
                        options[0]); //default button title
                if (n == 0) {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/editor/views/pattern_manager.fxml")
                    );

                    Stage stage = new Stage(StageStyle.DECORATED);
                    try {
                        stage.setScene(new Scene((Pane) loader.load()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    patterns_manager_C controller = loader.<patterns_manager_C>getController();
                    controller.initData(derby_DB);

                    stage.show();
                    Stage stage_this = (Stage) root.getScene().getWindow();
                    // do what you have to do
                    stage_this.close();
                } else {
                    we.consume();
                }
            }
        });
        /**/
        TA_pattern_description.setText(tmp_pattern.getDescription());
        TA_pattern_name.setText(tmp_pattern.getName());
        TA_pattern_uml.setText(tmp_pattern.getUmlText());
            }

    public void initialize(URL url, ResourceBundle rb) {

/*
//при двойном клике грузить патерн
        LV_paterns_DB.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() > 1 && derby_DB != null) {
                    load_this_patern_DB(null);
                }
            }
        });
        //list_load_DB();/**/
/**/
    }

    public void preview(ActionEvent actionEvent) {
        pattern_image = draw_uml.draw_class(TA_pattern_uml.getText());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/editor/views/image_preview.fxml"));

        Stage stage = new Stage(StageStyle.DECORATED);
        try {
            stage.setScene(new Scene((Pane) loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        image_preview_C controller = loader.<image_preview_C>getController();
        controller.initData(pattern_image, tmp_pattern.getName());
        //stage.setTitle("" + arch_old.getName());
        stage.show();
    }

    public void save(ActionEvent actionEvent) {
        tmp_pattern.setName(TA_pattern_name.getText());
        tmp_pattern.setDescription(TA_pattern_description.getText());
        tmp_pattern.setUmlText(TA_pattern_uml.getText());
        tmp_pattern.setPreview(draw_uml.draw_class(TA_pattern_uml.getText()));
        Pattern.pattern_save_to_DB(tmp_pattern,derby_DB);
    }

    public void cancel(ActionEvent actionEvent) {

    }
}
