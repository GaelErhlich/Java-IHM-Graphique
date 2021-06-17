package et3.projetjig;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent content = FXMLLoader.load( getClass().getResource("fenetre/fenetre.fxml") );
            primaryStage.setTitle("Projet OBIS");
            primaryStage.setScene( new Scene(content) );
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void main(String args[]) {
        launch(args);
    }
}
