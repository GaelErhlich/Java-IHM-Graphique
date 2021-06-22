package et3.projetjig.tests;

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

public class MainTest extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            ControllerFenetre fenetre = new ControllerFenetre();
            CadreTerre paneTerre = new CadreTerre(500,500, fenetre);


            FXMLLoader loader = new FXMLLoader( getClass().getResource("../fenetre/fenetre.fxml") );
            loader.setController(fenetre);
            Parent root = loader.load();

            primaryStage.setTitle("Projet OBIS 3D");
            primaryStage.setScene( new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.show();

            fenetre.recoitEspecesParBDD(new String[] {"Ah", "Beh", "Cè", "Dé"} );
            Taxon taxon = new Taxon(30, "Geogus", "Règle animal", "Fils de poule");
            fenetre.recoitOccurrencesParBDD(new OccurrencesPartition(
                    taxon,
                    new Occurrences[] {
                            new Occurrences(taxon, new Occurrence[] {
                                    new Occurrence(GeoHash.fromGeohashString("sp"), 12),
                                    new Occurrence(GeoHash.fromGeohashString("sq"), 65),
                                    new Occurrence(GeoHash.fromGeohashString("sr"), 17),
                                    new Occurrence(GeoHash.fromGeohashString("ss"), 150),
                                    new Occurrence(GeoHash.fromGeohashString("st"), 47),
                                    new Occurrence(GeoHash.fromGeohashString("su"), 78),
                                    new Occurrence(GeoHash.fromGeohashString("sv"), 150),
                                    new Occurrence(GeoHash.fromGeohashString("sw"), 43),
                                    new Occurrence(GeoHash.fromGeohashString("rs"), 47),
                            }, 12, 150, (short)2009, (short)2013),
                            new Occurrences(taxon, new Occurrence[] {
                                    new Occurrence(GeoHash.fromGeohashString("sp"), 18),
                                    new Occurrence(GeoHash.fromGeohashString("sq"), 156),
                                    new Occurrence(GeoHash.fromGeohashString("sr"), 10),
                                    new Occurrence(GeoHash.fromGeohashString("ss"), 123),
                                    new Occurrence(GeoHash.fromGeohashString("st"), 58),
                                    new Occurrence(GeoHash.fromGeohashString("su"), 78),
                                    new Occurrence(GeoHash.fromGeohashString("sv"), 150),
                                    new Occurrence(GeoHash.fromGeohashString("sw"), 46),
                                    new Occurrence(GeoHash.fromGeohashString("rs"), 89),

                            }, 10, 150, (short)2014, (short)2018),
                            new Occurrences(taxon, new Occurrence[] {
                                    new Occurrence(GeoHash.fromGeohashString("sp"), 89),
                                    new Occurrence(GeoHash.fromGeohashString("ss"), 89),
                                    new Occurrence(GeoHash.fromGeohashString("su"), 146),
                                    new Occurrence(GeoHash.fromGeohashString("sv"), 110),
                                    new Occurrence(GeoHash.fromGeohashString("sw"), 80),
                                    new Occurrence(GeoHash.fromGeohashString("rs"), 23),

                            }, 19, 146, (short)2019, (short)2020),
                    },
                    new Occurrences(taxon, new Occurrence[] {
                            new Occurrence(GeoHash.fromGeohashString("sp"), 30),
                            new Occurrence(GeoHash.fromGeohashString("sq"), 43),
                            new Occurrence(GeoHash.fromGeohashString("sr"), 142),
                            new Occurrence(GeoHash.fromGeohashString("ss"), 150),
                            new Occurrence(GeoHash.fromGeohashString("st"), 47),
                            new Occurrence(GeoHash.fromGeohashString("su"), 98),
                            new Occurrence(GeoHash.fromGeohashString("sv"), 15),
                            new Occurrence(GeoHash.fromGeohashString("sw"), 30),
                            new Occurrence(GeoHash.fromGeohashString("rs"), 47),
                    }, 15, 150, (short)2009, (short)2020),
                    30,
                    150
            ));
            /*fenetre.recoitErreurEspece("Gorgus");
            fenetre.recoitObservationsParBDD(new Observation[] {
                    new Observation("Geogus", "Joe", "3ème", "TS1", "Centre National pour la Recherche Scientifique"),
                    new Observation("Geogus", "Joe", "3ème", "TS1", "Centre National pour la Recherche Scientifique"),
                    new Observation("Geogus", "Joe", "3ème", "TS1", "Le LRI, et oui !"),
                    new Observation("John", "Joe", "2.0", "Archéophynes", "Centre National pour la Recherche Scientifique")
            });



            // */


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
