package et3.projetjig.terre.tests;

import et3.projetjig.terre.CadreTerreListener;
import et3.projetjig.terre.CadreTerre;
import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;

class FenetreDEBUG implements CadreTerreListener {

    private CadreTerre cadreTerre;

    public FenetreDEBUG() {
        System.out.println("[FenetreDEBUG] En attente d'un CadreTerre.");
    }

    public void setCadreTerre(CadreTerre cadreTerre) {
        this.cadreTerre = cadreTerre;
        System.out.println("[FenetreDEBUG] Nouveau CadreTerre reçu avec succès.");
    }

    @Override
    public boolean recoitGeoHashParUser(GeoHash geoHash) {
        System.out.println("[FenetreDEBUG] L'utilisateur a défini un nouveau GeoHash : "+geoHash.toString());
        //cadreTerre.recoitGeoHash(geoHash);
        return true;
    }
}
