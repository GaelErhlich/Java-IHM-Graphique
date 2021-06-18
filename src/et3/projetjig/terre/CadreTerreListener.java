package et3.projetjig.terre;

import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;

public interface CadreTerreListener {

    /**
     * Permet de notifier à la fenêtre que l'utilisateur a sélectionné un nouveau GeoHash
     * @param geoHash le nouveau GeoHash
     * @return true si le cadre 3D peut actualiser son GeoHash tout de suite, false pour le faire manuellement
     */
    boolean recoitGeoHashParUser(GeoHash geoHash);



}
