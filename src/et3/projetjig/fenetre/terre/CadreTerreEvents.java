package et3.projetjig.fenetre.terre;

import et3.maths.CoordonneesConvert;
import et3.projetjig.fenetre.terre.sphereterre.SphereTerre;
import et3.projetjig.fenetre.terre.sphereterre.exceptions.NullLocalisationPrincipale;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.input.ScrollEvent;
import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;


public class CadreTerreEvents {

    /**
     * Initialise les événements du CadreTerre et de la SphereTerre
     * @param cadreTerre le CadreTerre concerné
     * @param histogBtn le bouton JavaFX permettant d'activer/désactiver le mode histogramme
     */
    public static void declare(CadreTerre cadreTerre, Button histogBtn) {

        initialiseEventsClic(cadreTerre);
        initialiseEventsMolette(cadreTerre);
        initialiseEventsBoutons(cadreTerre, histogBtn);

    }


    private static void initialiseEventsClic(CadreTerre cadre) {
        SphereTerre terre = cadre.getSphereTerre();


        terre.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if(event.isShiftDown()) {

                // On récupère le point dans l'espace
                PickResult pickResult = event.getPickResult();
                Point3D coords3d = pickResult.getIntersectedPoint();

                // On récupère les latitude et longitude correspondantes
                Point2D coords2d = CoordonneesConvert.coord3dToGeoCoord(coords3d,
                        new Point3D(terre.getTranslateX(),terre.getTranslateY(),terre.getTranslateZ()),
                        SphereTerre.TEXTURE_LAT_OFFSET, SphereTerre.TEXTURE_LON_OFFSET);
                terre.setLocPrincipale(coords2d);


                // On calcule le nouveau GeoHash et on l'envoie à la fenêtre (pas encore de màj)
                // On prend en compte le fait que des erreurs peuvent survenir dans la sélection
                double latitude = Math.min(Math.max( coords2d.getX() , -90),90);
                double longitude = Math.min(Math.max( coords2d.getY() , -180),180);
                GeoHash nouvGeoHash = GeoHash.withBitPrecision(latitude, longitude, terre.getNombreDeBits());

                cadre.envoiNouvGeoHashVersFenetre(nouvGeoHash);
                terre.setGeoHash(nouvGeoHash);
            }
        });
    }



    private static void initialiseEventsMolette(CadreTerre cadreTerre) {
        SphereTerre terre = cadreTerre.getSphereTerre();

        cadreTerre.addEventHandler(ScrollEvent.SCROLL, event -> {
            if(event.isShiftDown()) {
                try {
                    int nombreDeBits = terre.getNombreDeBits() + (int)(event.getDeltaX()/30)*5;
                    nombreDeBits = Math.min(Math.max(nombreDeBits, 0),60);
                    Point2D latEtLon = terre.getLocPrincipaleCoords2d();
                    GeoHash nouvGeoHash = GeoHash.withBitPrecision(latEtLon.getX(), latEtLon.getY(), nombreDeBits);

                    cadreTerre.envoiNouvPrecisionVersFenetre(nouvGeoHash);
                    terre.setGeoHash(nouvGeoHash);
                }
                catch(NullLocalisationPrincipale ignored) {}
            }
        });
    }


    private static void initialiseEventsBoutons(CadreTerre cadreTerre, Button histogBtn) {

        histogBtn.setOnMousePressed((event)->{

            if(cadreTerre.estHistogramme()) {
                cadreTerre.desactiveModeHistog();
            }
            else {
                cadreTerre.activeModeHistog();
            }

        });

    }


}
