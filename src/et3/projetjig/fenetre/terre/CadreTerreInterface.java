package et3.projetjig.fenetre.terre;

import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;

public interface CadreTerreInterface {

    /**
     * Reçoit un nouveau GeoHash de l'extérieur du cadre de la Terre
     * @param geoHash Le nouveau GeoHash reçu, qu'on va appliquer à la planète
     */
    void recoitGeoHash(GeoHash geoHash);


    //public void setModeHistogramme(boolean onOrOff);

}
