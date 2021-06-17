package et3.projetjig.terre;

import javafx.scene.layout.Pane;
import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;

public interface CadreTerreInterface {

    /**
     * Reçoit un nouveau GeoHash de l'extérieur du cadre de la Terre
     * @param geoHash Le nouveau GeoHash reçu, qu'on va appliquer à la planète
     */
    public void recoitGeoHashParInterface(GeoHash geoHash);

}
