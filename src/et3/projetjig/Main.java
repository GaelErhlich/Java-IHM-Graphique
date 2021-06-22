package et3.projetjig;

import et3.projetjig.donnees.types.*;
import et3.projetjig.fenetre.ControllerFenetre;
import et3.projetjig.fenetre.terre.CadreTerre;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;

import java.io.IOException;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            ControllerFenetre fenetre = new ControllerFenetre();
            CadreTerre paneTerre = new CadreTerre(500,500, fenetre);


            FXMLLoader loader = new FXMLLoader( getClass().getResource("fenetre/fenetre.fxml") );
            loader.setController(fenetre);
            Parent root = loader.load();

            primaryStage.setTitle("Projet OBIS 3D");
            primaryStage.setScene( new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
