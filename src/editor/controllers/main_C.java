package editor.controllers;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import editor.classes.DerbyDBManager;
import editor.services.zip;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * @author godex_000
 */
public class main_C implements Initializable {
    public Label selected_DB;
    public Button B_connect;
    public Button B_create;
    public Button B_disconnect;
    public Pane B_G_editors;
    /*Кнопки*/

    DerbyDBManager derby_DB;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void connect_DB(ActionEvent actionEvent) {
        disconnect_DB(null);
        JFileChooser db_dir = new JFileChooser(new File(System.getProperty("user.dir")));
        db_dir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        db_dir.setAcceptAllFileFilterUsed(false);
        db_dir.setDialogTitle("Выберете каталог с базой");
        db_dir.showDialog(null, "Выбрать...");
        // существет ли база(создана ли)

        derby_DB = new DerbyDBManager(db_dir.getSelectedFile().getAbsolutePath());
        selected_DB.setText(db_dir.getName(db_dir.getSelectedFile()));
        B_connect.setDisable(true);
        B_create.setDisable(true);
        B_disconnect.setDisable(false);
        B_G_editors.setDisable(false);
    }

    public void disconnect_DB(ActionEvent actionEvent) {//отключиться от БД
        if (derby_DB != null) {
            derby_DB.disconectDB();
            derby_DB = null;
        }
        selected_DB.setText("<не обрана>");
        B_connect.setDisable(false);
        B_create.setDisable(false);
        B_disconnect.setDisable(true);
        B_G_editors.setDisable(true);
    }

    public void creat_DB(ActionEvent actionEvent) { //Создать БД
        JFileChooser db_dir = new JFileChooser(new File(System.getProperty("user.dir")));
        db_dir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        db_dir.setAcceptAllFileFilterUsed(false);
        db_dir.setDialogTitle("Выберете каталог під базу");
        db_dir.showDialog(null, "Обрати...");
        String new_db_name = JOptionPane.showInputDialog("Введіть імя нової бази");
        new DerbyDBManager(db_dir.getSelectedFile().getAbsolutePath() + "\\" + new_db_name);
    }


    public void close_mw(ActionEvent actionEvent) {
        System.exit(1);
    }

    public void show_help(ActionEvent actionEvent) {// Отобразить помощь
        try {
            Parent Parent = FXMLLoader.load(getClass().getResource("../views/help.fxml"));
            Stage Stage = new Stage();
            Stage.setTitle("Допомога");
            Stage.setScene(new Scene(Parent));
            Stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show_about(ActionEvent actionEvent) {


    }

    public void start_arch_editor(ActionEvent actionEvent) {
        try {
            final Stage PS = (Stage) B_disconnect.getScene().getWindow();
            PS.setIconified(true);

            Parent Parent = FXMLLoader.load(getClass().getResource("../views/arch_editor_din.fxml"));
            Stage Stage = new Stage();
            Stage.setTitle("Редактор архітектур");
            Stage.setScene(new Scene(Parent));
            Stage.show();

            Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    PS.setIconified(false);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start_module_editor(ActionEvent actionEvent) {
        try {
            final Stage PS = (Stage) B_disconnect.getScene().getWindow();
            PS.setIconified(true);

            Parent Parent = FXMLLoader.load(getClass().getResource("../views/modules_editor.fxml"));
            Stage Stage = new Stage();
            Stage.setTitle("Редактор модулів");
            Stage.setScene(new Scene(Parent));
            Stage.show();

            Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    PS.setIconified(false);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start_patern_editor(ActionEvent actionEvent) {
        try {
            final Stage PS = (Stage) B_disconnect.getScene().getWindow();
            PS.setIconified(true);

            Parent Parent = FXMLLoader.load(getClass().getResource("../views/paterns_editor.fxml"));
            Stage Stage = new Stage();
            Stage.setTitle("Редактор патенів");
            Stage.setScene(new Scene(Parent));
            Stage.show();

            Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    PS.setIconified(false);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void start_layer_editor(ActionEvent actionEvent) {
        try {
            final Stage PS = (Stage) B_disconnect.getScene().getWindow();
            PS.setIconified(true);

            Parent Parent = FXMLLoader.load(getClass().getResource("../views/layer_editor.fxml"));
            Stage Stage = new Stage();
            Stage.setTitle("Редактор слоїв");
            Stage.setScene(new Scene(Parent));
            Stage.show();

            Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    // PS.setIconified(false);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void create_backup(ActionEvent actionEvent) throws IOException {
        File mydir = new File("DB");
        File myfile = new File("DB_backup/" + new SimpleDateFormat("dd.MM.yyyy_HH_mm_ss").format(new Date()) + ".zip");
        zip.zip_dir(mydir, myfile);
        System.out.println(mydir.toURI().relativize(myfile.toURI()).getPath());
    }


    public void clear_db(ActionEvent actionEvent) {
        //TODO del
        derby_DB = new DerbyDBManager("DB/paterns_DB");
        //TODO Уборка в базе
        ArrayList<Integer> arch_id = new ArrayList<>();
        ArrayList<Integer> lay_id = new ArrayList<>();
        ArrayList<Integer> mod_id = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = derby_DB.executeQuery("SELECT * FROM ARCHITECTURE");
            while (rs.next()) {
                arch_id.add(rs.getInt("ID"));
            }
            rs = derby_DB.executeQuery("SELECT * FROM LAYER");
            while (rs.next()) {
                Boolean svyazan = false;
                for (int s = 0; s < arch_id.size(); s++) {
                    if(rs.getInt("ARCH_ID")==arch_id.get(s))
                        svyazan=true;
                }
                if (svyazan==true)
                    lay_id.add(rs.getInt("ID"));
                 else {
                    derby_DB.executeUpdate("DELETE FROM LAYER WHERE ID="+rs.getInt("ID"));
                }
            }

            rs = derby_DB.executeQuery("SELECT * FROM MODULE");
            while (rs.next()) {
                Boolean svyazan = false;
                for (int s = 0; s < lay_id.size(); s++) {
                    if(rs.getInt("LAY_ID")==lay_id.get(s))
                        svyazan=true;
                }
                if (svyazan==true)
                    mod_id.add(rs.getInt("ID"));
                else {
                    derby_DB.executeUpdate("DELETE FROM MODULE WHERE ID="+rs.getInt("ID"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        derby_DB.disconectDB();
    }
}