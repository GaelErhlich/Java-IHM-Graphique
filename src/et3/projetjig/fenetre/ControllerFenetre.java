package et3.projetjig.fenetre;

import et3.projetjig.terre.CadreTerre;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerFenetre implements Initializable, FenetreInterface {

    @FXML AnchorPane pane3dAnchor;

    private CadreTerre terre = new CadreTerre(500, 500, this);


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Mise du cadreTerre
        pane3dAnchor.getChildren().add(terre);

    }

    @Override
    public boolean recoitGeoHashParUser(GeoHash geoHash) {
        return true;
    }


}
