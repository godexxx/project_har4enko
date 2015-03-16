package Run;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

/*    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }*/

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/mode_selection/Selection_mode_user_menu.fxml"));
        primaryStage.setTitle("АРХІТЕКТОР ПРОГРАМНИХ СИСТЕМ");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(640);
        primaryStage.show();
        primaryStage.setResizable(false);
    }


    public static void main(String[] args) throws IOException, SQLException {
        launch();
    }
}
