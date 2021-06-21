package et3.projetjig.fenetre.terre.tests;

import et3.projetjig.fenetre.terre.CadreTerre;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FenetreDEBUG fenetre = new FenetreDEBUG();
        CadreTerre cadreTerre = new CadreTerre(500,500, fenetre);
        fenetre.setCadreTerre(cadreTerre);

        primaryStage.setTitle("La Terre (Composant 3D seul)");
        primaryStage.setScene( new Scene(cadreTerre) );
        primaryStage.show();


        ScheduledExecutorService scheduleServ = Executors.newSingleThreadScheduledExecutor();
        scheduleServ.schedule(() -> {
            System.out.println("3 secondes se sont écoulées !");
            cadreTerre.recoitGeoHash(GeoHash.fromBinaryString("11101"));
        }, 3, TimeUnit.SECONDS);


    }

    public static void main(String[] args) {
        launch(args);
    }

}
