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

public class MainTest extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            ControllerFenetre fenetre = new ControllerFenetre();


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
                                    new Occurrence(GeoHash.fromGeohashString("sp11"), 12),
                                    new Occurrence(GeoHash.fromGeohashString("sq11"), 65),
                                    new Occurrence(GeoHash.fromGeohashString("sr11"), 17),
                                    new Occurrence(GeoHash.fromGeohashString("ss11"), 150),
                                    new Occurrence(GeoHash.fromGeohashString("st11"), 47),
                                    new Occurrence(GeoHash.fromGeohashString("su11"), 78),
                                    new Occurrence(GeoHash.fromGeohashString("sv11"), 150),
                                    new Occurrence(GeoHash.fromGeohashString("sw11"), 43),
                                    new Occurrence(GeoHash.fromGeohashString("rs11"), 47),
                            }, 12, 150, (short)2009, (short)2013),
                            new Occurrences(taxon, new Occurrence[] {
                                    new Occurrence(GeoHash.fromGeohashString("sp1"), 18),
                                    new Occurrence(GeoHash.fromGeohashString("sq1"), 156),
                                    new Occurrence(GeoHash.fromGeohashString("sr1"), 10),
                                    new Occurrence(GeoHash.fromGeohashString("ss1"), 123),
                                    new Occurrence(GeoHash.fromGeohashString("st1"), 58),
                                    new Occurrence(GeoHash.fromGeohashString("su1"), 78),
                                    new Occurrence(GeoHash.fromGeohashString("sv1"), 150),
                                    new Occurrence(GeoHash.fromGeohashString("sw1"), 46),
                                    new Occurrence(GeoHash.fromGeohashString("rs1"), 89),

                            }, 10, 150, (short)2014, (short)2018),
                            new Occurrences(taxon, new Occurrence[] {
                                    new Occurrence(GeoHash.fromGeohashString("sp1"), 89),
                                    new Occurrence(GeoHash.fromGeohashString("ss1"), 89),
                                    new Occurrence(GeoHash.fromGeohashString("su1"), 146),
                                    new Occurrence(GeoHash.fromGeohashString("sv1"), 110),
                                    new Occurrence(GeoHash.fromGeohashString("sw1"), 80),
                                    new Occurrence(GeoHash.fromGeohashString("rs1"), 23),

                            }, 19, 146, (short)2019, (short)2020),
                    },
                    new Occurrences(taxon, new Occurrence[] {
                            new Occurrence(GeoHash.fromGeohashString("sp1"), 30),
                            new Occurrence(GeoHash.fromGeohashString("sq1"), 43),
                            new Occurrence(GeoHash.fromGeohashString("sr1"), 142),
                            new Occurrence(GeoHash.fromGeohashString("ss1"), 150),
                            new Occurrence(GeoHash.fromGeohashString("st1"), 47),
                            new Occurrence(GeoHash.fromGeohashString("su1"), 98),
                            new Occurrence(GeoHash.fromGeohashString("sv1"), 15),
                            new Occurrence(GeoHash.fromGeohashString("sw1"), 30),
                            new Occurrence(GeoHash.fromGeohashString("rs1"), 47),
                    }, 15, 150, (short)2009, (short)2020),
                    30,
                    175
            ));
            /*fenetre.recoitErreurEspece("Gorgus");
            fenetre.recoitObservationsParBDD(GeoHash.fromGeohashString("sp"), new Observation[] {
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
