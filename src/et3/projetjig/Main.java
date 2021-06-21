package et3.projetjig;

import et3.projetjig.donnees.types.*;
import et3.projetjig.fenetre.ControllerFenetre;
import et3.projetjig.fenetre.terre.CadreTerre;
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
            ControllerFenetre fenetre = new ControllerFenetre();
            CadreTerre paneTerre = new CadreTerre(500,500, fenetre);


            FXMLLoader loader = new FXMLLoader( getClass().getResource("fenetre/fenetre.fxml") );
            loader.setController(fenetre);
            Parent root = loader.load();

            primaryStage.setTitle("Projet OBIS 3D");
            primaryStage.setScene( new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.show();

            fenetre.recoitEspecesParBDD(new String[] {"Ah", "Beh", "Cè", "Dé"} );
            /*Taxon taxon = new Taxon(30, "Geogus", "Règle animal", "Fils de poule");
            fenetre.recoitOccurrencesParBDD(new OccurrencesPartition(
                    taxon,
                    new Occurrences[] {},
                    new Occurrence[] {},
                    30,
                    40,
                    (short)2019,
                    (short)2020
            ));
            fenetre.recoitErreurEspece("Gorgus");
            fenetre.recoitObservationsParBDD(new Observation[] {
                    new Observation("Geogus", "Joe", "3ème", "TS1", "Centre National pour la Recherche Scientifique"),
                    new Observation("Geogus", "Joe", "3ème", "TS1", "Centre National pour la Recherche Scientifique"),
                    new Observation("Geogus", "Joe", "3ème", "TS1", "Le LRI, et oui !"),
                    new Observation("John", "Joe", "2.0", "Archéophynes", "Centre National pour la Recherche Scientifique")
            });



            // */


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
