package editor.controllers;

import Classes.Pattern;
import editor.classes.DerbyDBManager;
import editor.classes.Modals;
import editor.classes.idLable;
import editor.interfaces.Configuration;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by godex_000 on 04.07.2014.
 */
public class patterns_manager_C implements Initializable, Configuration {
    public ComboBox cbFromArch;
    public ComboBox cbFromLay;
    public ComboBox cbFromMod;
    public ComboBox cbToArch;
    public ComboBox cbToLay;
    public ComboBox cbToMod;
    public ListView lvFromList;
    public ListView lvToList;
    public BorderPane root;
    public DerbyDBManager dbConnection;
    private String fromLastQuery, toLastQuery;
    private String fromType, toType;
    private Integer fromModId, toModId, fromArchId, toArchId;

    public void initData(final DerbyDBManager derbyDBManager) {
        dbConnection = derbyDBManager;
        ResultSet rs = null;
        try {
            //derby_DB
            rs = dbConnection.executeQuery("SELECT * FROM ARCHITECTURE ORDER BY NAME ASC");
            ObservableList<idLable> items = FXCollections.observableArrayList();
            items.add(new idLable(-1, "Базові" + " " + RB.getString("загальні.патерни")));
            while (rs.next()) {
                idLable tmp_lable = new idLable(rs.getInt("ID"), rs.getString("NAME"));
                items.add(tmp_lable);
            }
            cbFromArch.setItems(items);

            cbFromArch.valueProperty().addListener(new ChangeListener<idLable>() {
                @Override
                public void changed(ObservableValue ov, idLable t, idLable t1) {
                    cbFromMod.setItems(FXCollections.observableArrayList());
                    lvFromList.setItems(FXCollections.observableArrayList());
                    from_lay_load();
                }
            });
            cbToArch.setItems(items);
            cbToArch.valueProperty().addListener(new ChangeListener<idLable>() {
                @Override
                public void changed(ObservableValue ov, idLable t, idLable t1) {
                    cbToMod.setItems(FXCollections.observableArrayList());
                    lvToList.setItems(FXCollections.observableArrayList());
                    to_lay_load();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }/**/


        Stage thisstage = (Stage) root.getScene().getWindow();
        thisstage.getIcons().add(new Image("/res/img/uml_icon.png"));
        //thisstage.setTitle("Управління репозиторіем патернів: редактор архітектур");
        thisstage.setMinWidth(900);//Минимальная шырина
        thisstage.setMinHeight(700);//Минимальная высота окна
        thisstage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (Modals.Response.YES == Modals.showYNDialog(RB.getString("загальні.увага"), RB.getString("загальні.ви_впевнені_що_бажаете_вийти_незбережені_зміни_буде_втрачено"))) {
                } else {
                    we.consume();
                }
            }
        });
    }

    /**
     * Завантажити список звідки і зберегти його останій запит
     *
     * @param Query запит
     */
    private void fromListLoad(String Query) {
        try {
            ResultSet rs = null;
            rs = dbConnection.executeQuery(Query);
            fromLastQuery = Query;
            ObservableList<idLable> items = FXCollections.observableArrayList();
            while (rs.next()) {
                idLable tmp_lable = new idLable(rs.getInt("ID"), rs.getString("NAME"));
                items.add(tmp_lable);
            }
            lvFromList.setItems(items);
            lvFromList.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Завантажити список куди і зберегти його останій запит
     *
     * @param Query запит
     */
    private void to_list_load(String Query) {
        try {
            ResultSet rs = null;
            rs = dbConnection.executeQuery(Query);
            toLastQuery = Query;
            ObservableList<idLable> items = FXCollections.observableArrayList();
            while (rs.next()) {
                idLable tmp_lable = new idLable(rs.getInt("ID"), rs.getString("NAME"));
                items.add(tmp_lable);
            }
            lvToList.setItems(items);

            lvToList.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void from_lay_load() {
        ResultSet rs = null;
        try {
            if (((idLable) cbFromArch.getSelectionModel().getSelectedItem()).getDbId().intValue() != -1) {
                //якщо вибрав архітектуру
                rs = dbConnection.executeQuery("SELECT * FROM LAYER WHERE ARCH_ID=" + ((idLable) cbFromArch.getSelectionModel().getSelectedItem()).getDbId().intValue() + " ORDER BY NAME ASC");
                fromArchId = ((idLable) cbFromArch.getSelectionModel().getSelectedItem()).getDbId().intValue();
                ObservableList<idLable> items = FXCollections.observableArrayList();
                items.add(new idLable(-1, RB.getString("непривязані_паттерни")));
                fromModId = -1;
                while (rs.next()) {
                    idLable tmp_lable = new idLable(rs.getInt("ID"), rs.getString("NAME"));
                    items.add(tmp_lable);
                }
                cbFromLay.setItems(items);
                cbFromLay.valueProperty().addListener(new ChangeListener<idLable>() {
                    @Override
                    public void changed(ObservableValue ov, idLable t, idLable t1) {
                        lvFromList.setItems(FXCollections.observableArrayList());
                        from_mod_load();
                    }
                });
            } else {
                fromModId = -1;
                fromArchId = 0;
                //якщо глобальні патерни
                fromListLoad("SELECT * FROM PATERNS WHERE TYPE='GLOBAL' ORDER BY NAME ASC");
                fromType = "GLOBAL";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void from_mod_load() {
        ResultSet rs = null;
        try {

            if (((idLable) cbFromLay.getSelectionModel().getSelectedItem()).getDbId().intValue() != -1) {
                //derby_DB
                rs = dbConnection.executeQuery("SELECT * FROM MODULE WHERE LAY_ID=" + ((idLable) cbFromLay.getSelectionModel().getSelectedItem()).getDbId().intValue() + " ORDER BY NAME ASC");

                ObservableList<idLable> items = FXCollections.observableArrayList();
                while (rs.next()) {
                    idLable tmp_lable = new idLable(rs.getInt("ID"), rs.getString("NAME"));
                    items.add(tmp_lable);
                }
                cbFromMod.setItems(items);
                cbFromMod.valueProperty().addListener(new ChangeListener<idLable>() {
                    @Override
                    public void changed(ObservableValue ov, idLable t, idLable t1) {
                        from_pat_load();
                    }
                });
            } else {
                //непривязані патерни


                cbFromMod.setItems(FXCollections.observableArrayList());
                fromListLoad("SELECT * FROM PATERNS WHERE MOD_ID=-1 AND ARCH_ID=" + ((idLable) cbFromArch.getSelectionModel().getSelectedItem()).getDbId().intValue() + " ORDER BY NAME ASC");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void from_pat_load() {
        //derby_DB
        fromListLoad("SELECT * FROM PATERNS WHERE MOD_ID=" + ((idLable) cbFromMod.getSelectionModel().getSelectedItem()).getDbId().intValue() + " ORDER BY NAME ASC");
        fromModId = ((idLable) cbFromMod.getSelectionModel().getSelectedItem()).getDbId().intValue();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void to_lay_load() {
        ResultSet rs = null;
        try {
            //derby_DB
            rs = dbConnection.executeQuery("SELECT * FROM LAYER WHERE ARCH_ID=" + ((idLable) cbToArch.getSelectionModel().getSelectedItem()).getDbId().intValue() + " ORDER BY NAME ASC");
            toArchId = ((idLable) cbToArch.getSelectionModel().getSelectedItem()).getDbId().intValue();
            ObservableList<idLable> items = FXCollections.observableArrayList();
            while (rs.next()) {
                idLable tmp_lable = new idLable(rs.getInt("ID"), rs.getString("NAME"));
                items.add(tmp_lable);
            }
            cbToLay.setItems(items);
            cbToLay.valueProperty().addListener(new ChangeListener<idLable>() {
                @Override
                public void changed(ObservableValue ov, idLable t, idLable t1) {
                    lvToList.setItems(FXCollections.observableArrayList());
                    to_mod_load();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void to_mod_load() {
        ResultSet rs = null;
        try {
            //derby_DB
            rs = dbConnection.executeQuery("SELECT * FROM MODULE WHERE LAY_ID=" + ((idLable) cbToLay.getSelectionModel().getSelectedItem()).getDbId().intValue() + " ORDER BY NAME ASC");
            ObservableList<idLable> items = FXCollections.observableArrayList();
            while (rs.next()) {
                idLable tmp_lable = new idLable(rs.getInt("ID"), rs.getString("NAME"));
                items.add(tmp_lable);
            }
            cbToMod.setItems(items);
            cbToMod.valueProperty().addListener(new ChangeListener<idLable>() {
                @Override
                public void changed(ObservableValue ov, idLable t, idLable t1) {
                    to_pat_load();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void to_pat_load() {
        //derby_DB
        to_list_load("SELECT * FROM PATERNS WHERE MOD_ID=" + ((idLable) cbToMod.getSelectionModel().getSelectedItem()).getDbId().intValue() + " ORDER BY NAME ASC");
        toModId = ((idLable) cbToMod.getSelectionModel().getSelectedItem()).getDbId().intValue();
    }

    /**
     * Зліва на право перемістити
     * звідки-куди
     */
    public void from_move_to() {
        if (lvFromList.getSelectionModel().getSelectedItem() != null) {
            if (cbToArch.getSelectionModel().getSelectedItem() != null) {
                Pattern from = Pattern.patternLoadFromDB(((idLable) lvFromList.getSelectionModel().getSelectedItem()).getDbId(), dbConnection);
                Pattern new_p = new Pattern();
                new_p.setName(from.getName());
                if (cbToMod.getSelectionModel().getSelectedItem() != null) {
                    new_p.setModId(((idLable) cbToMod.getSelectionModel().getSelectedItem()).getDbId());
                } else {
                    new_p.setModId(-1);
                }
                new_p.setUmlText(from.getUmlText());
                new_p.setDescription(from.getDescription());
                new_p.setPreview(from.getPreview());
                new_p.setArch_id(((idLable) cbToMod.getSelectionModel().getSelectedItem()).getDbId());
                new_p.setType("");
                Pattern.pattern_save_to_DB(new_p, dbConnection);
                Pattern.delete_pattern_from_DB(from.getId(), dbConnection);
            }
        }
        fromListLoad(fromLastQuery);
        to_list_load(toLastQuery);
    }

    /**
     * Зліва направо скопіюввти
     * звідки-куди
     */
    public void from_copy_to() {
        if (lvFromList.getSelectionModel().getSelectedItem() != null) {
            if (cbToArch.getSelectionModel().getSelectedItem() != null) {
                Pattern from = Pattern.patternLoadFromDB(((idLable) lvFromList.getSelectionModel().getSelectedItem()).getDbId(), dbConnection);
                Pattern new_p = new Pattern();
                new_p.setName(from.getName());
                if (cbToMod.getSelectionModel().getSelectedItem() != null) {
                    new_p.setModId(((idLable) cbToMod.getSelectionModel().getSelectedItem()).getDbId());
                } else {
                    new_p.setModId(-1);
                }
                new_p.setUmlText(from.getUmlText());
                new_p.setDescription(from.getDescription());
                new_p.setPreview(from.getPreview());
                new_p.setArch_id(((idLable) cbToMod.getSelectionModel().getSelectedItem()).getDbId());
                new_p.setType("");

                Pattern.pattern_save_to_DB(new_p, dbConnection);
            }
        }
        fromListLoad(fromLastQuery);
        to_list_load(toLastQuery);
    }

    /**
     * Зправа на ліво перемістити
     * куди-звідки
     */
    public void to_move_from() {
    }

    /**
     * Зправа на ліво зкопіювати
     * куди-звідки
     */
    public void to_copy_from() {

    }

    public void from_list_delete() {
        if (lvFromList.getSelectionModel().getSelectedItem() != null) {
            if (Modals.Response.YES == Modals.showYNDialog(RB.getString("загальні.увага"), RB.getString("загальні.ви_впевнені_що_бажаете_видалити") + " " + RB.getString("загальні.патерн"))) {
                Pattern.delete_pattern_from_DB(((idLable) lvFromList.getSelectionModel().getSelectedItem()).getDbId(), dbConnection);
                fromListLoad(fromLastQuery);
            }
        }
    }


    public void from_list_preview() {
        if (lvFromList.getSelectionModel().getSelectedItem() != null) {

            Pattern pattern = Pattern.patternLoadFromDB(((idLable) lvFromList.getSelectionModel().getSelectedItem()).getDbId(), dbConnection);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/editor/views/image_preview.fxml"));

            Stage stage = new Stage(StageStyle.DECORATED);
            try {
                stage.setScene(new Scene((Pane) loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            image_preview_C controller = loader.<image_preview_C>getController();
            controller.initData(pattern.getPreview(), pattern.getName());
            stage.show();
        }
    }

    public void from_list_edit() {
        if (lvFromList.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/editor/views/patern_editor.fxml"));

            Stage stage = new Stage(StageStyle.DECORATED);
            try {
                stage.initModality(Modality.WINDOW_MODAL);
                stage.setScene(new Scene((Pane) loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            patern_editor_C controller = loader.<patern_editor_C>getController();
            controller.initData(Pattern.patternLoadFromDB(((idLable) lvFromList.getSelectionModel().getSelectedItem()).getDbId(), dbConnection), dbConnection);
            stage.setTitle("Редагування патерну");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            fromListLoad(fromLastQuery);
        }
    }

    public void from_list_add() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/editor/views/patern_editor.fxml"));

        Stage stage = new Stage(StageStyle.DECORATED);

        try {
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(new Scene((Pane) loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        patern_editor_C controller = loader.<patern_editor_C>getController();

//        Pattern(Integer id, Integer modId, String name, String description, String umlText, String type, Integer arch_id, Image preview) {
        controller.initData(new Pattern(null, fromModId, "", "", "", fromType, fromArchId, null), dbConnection);
        stage.setTitle("Редагування патерну");
        stage.showAndWait();
        fromListLoad(fromLastQuery);
    }

    public void to_list_preview() {
        if (lvToList.getSelectionModel().getSelectedItem() != null) {

            Pattern pattern = Pattern.patternLoadFromDB(((idLable) lvToList.getSelectionModel().getSelectedItem()).getDbId(), dbConnection);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/editor/views/image_preview.fxml"));

            Stage stage = new Stage(StageStyle.DECORATED);
            try {
                stage.setScene(new Scene((Pane) loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            image_preview_C controller = loader.<image_preview_C>getController();
            controller.initData(pattern.getPreview(), pattern.getName());
            stage.show();
        }
    }

    public void to_list_edit() {
        if (lvToList.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/editor/views/patern_editor.fxml"));

            Stage stage = new Stage(StageStyle.DECORATED);
            try {
                stage.initModality(Modality.WINDOW_MODAL);
                stage.setScene(new Scene((Pane) loader.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            patern_editor_C controller = loader.<patern_editor_C>getController();
            controller.initData(Pattern.patternLoadFromDB(((idLable) lvToList.getSelectionModel().getSelectedItem()).getDbId(), dbConnection), dbConnection);
            stage.setTitle("Редагування патерну");
            stage.showAndWait();
            to_list_load(toLastQuery);

        }
    }


    public void to_list_delete() {
        if (lvToList.getSelectionModel().getSelectedItem() != null) {
            if (Modals.Response.YES == Modals.showYNDialog(RB.getString("загальні.увага"), RB.getString("загальні.ви_впевнені_що_бажаете_видалити") + " " + RB.getString("загальні.патерн"))) {
                Pattern.delete_pattern_from_DB(((idLable) lvToList.getSelectionModel().getSelectedItem()).getDbId(), dbConnection);
                to_list_load(toLastQuery);
            }
        }
    }
}
