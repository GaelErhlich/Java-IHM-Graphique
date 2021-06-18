package et3.projetjig.fenetre;

import et3.projetjig.fenetre.annees.AnneesSelecteur;
import et3.projetjig.fenetre.annees.AnneesSelecteurListener;
import et3.projetjig.terre.CadreTerre;
import et3.projetjig.terre.CadreTerreListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerFenetre implements Initializable, CadreTerreListener, AnneesSelecteurListener {

    @FXML AnchorPane pane3dAnchor;
    @FXML Pane paneAnnees;
    @FXML ListView listView; // TODO : Changer en Pane


    @FXML Button animerBtn;



    private CadreTerre terre = null;
    private AnneesSelecteur annees = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Mise du cadreTerre
        terre = new CadreTerre(500, 500, this);
        pane3dAnchor.getChildren().add(terre);

        // Mise du sélecteur d'année (composant graphique AnneesSelecteur)
        annees = new AnneesSelecteur(this, (short)1980, (short)2020);
        paneAnnees.getChildren().add(annees);


    }

    @Override
    public boolean recoitGeoHashParUser(GeoHash geoHash) {
        return true;
    }


    private void majAnimerBtn(short debutAnnee, short finAnnee) {
        if (debutAnnee == finAnnee) {
            animerBtn.setText("Année " + debutAnnee);
            animerBtn.setDisable(true);
        } else {
            animerBtn.setText("Animer (" + debutAnnee + "-" + finAnnee + ")");
            animerBtn.setDisable(false);
        }
    }

    @Override
    public void recoitAnneesParUser(short debutAnnee, short finAnnee) {
        majAnimerBtn(debutAnnee, finAnnee);
    }
}
