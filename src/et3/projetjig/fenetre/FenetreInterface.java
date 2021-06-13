package et3.projetjig.fenetre;

import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;

public interface FenetreInterface {

    /**
     * Permet de notifier à la fenêtre que l'utilisateur a sélectionné un nouveau GeoHash
     * @param geoHash le nouveau GeoHash
     */
    public void recoitGeoHashParUser(GeoHash geoHash);

}
