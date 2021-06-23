package et3.projetjig.fenetre.terre;

import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;

public interface CadreTerreListener {

    /**
     * Permet de notifier à la fenêtre que l'utilisateur a sélectionné un nouveau GeoHash
     * @param geoHash le nouveau GeoHash
     */
    void recoitGeoHashParUser(GeoHash geoHash);


    /**
     * Permet de notifier à la fenêtre que l'utilisateur a sélectionné une nouvelle précision de GeoHash
     * @param geoHash le GeoHash sélectionné de précision correspondante
     */
    void recoitPrecisionParUser(GeoHash geoHash);



}
