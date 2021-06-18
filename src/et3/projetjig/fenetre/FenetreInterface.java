package et3.projetjig.fenetre;

import et3.projetjig.terre.CadreTerre;
import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;

public interface FenetreInterface {

    /**
     * Permet de notifier à la fenêtre que l'utilisateur a sélectionné un nouveau GeoHash
     * @param geoHash le nouveau GeoHash
     * @return true si le cadre 3D peut actualiser son GeoHash tout de suite, false pour le faire manuellement
     */
    public boolean recoitGeoHashParUser(GeoHash geoHash);



}
