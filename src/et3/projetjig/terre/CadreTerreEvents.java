package et3.projetjig.terre;

import et3.maths.CoordonneesConvert;
import et3.projetjig.terre.sphereterre.SphereTerre;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;


public class CadreTerreEvents {

    public static void declare(CadreTerre cadreTerre) {

        initialiseEventsClic( cadreTerre );

    }


    private static void initialiseEventsClic(CadreTerre cadre) {
        SphereTerre terre = cadre.getSphereTerre();


        terre.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if(event.isAltDown()) {

                // On récupère le point dans l'espace
                PickResult pickResult = event.getPickResult();
                Point3D coords3d = pickResult.getIntersectedPoint();

                // On récupère les latitude et longitude correspondantes
                Point2D coords2d = CoordonneesConvert.coord3dToGeoCoord(coords3d,
                        new Point3D(terre.getTranslateX(),terre.getTranslateY(),terre.getTranslateZ()),
                        SphereTerre.TEXTURE_LAT_OFFSET, SphereTerre.TEXTURE_LON_OFFSET);
                terre.definirLocPrincipale(coords2d);


                // On calcule le nouveau GeoHash et on l'envoie à la fenêtre (pas encore de màj)
                GeoHash nouvGeoHash = GeoHash.withBitPrecision(coords2d.getX(), coords2d.getY(), 5);
                cadre.envoiNouvGeoHashVersFenetre(nouvGeoHash);

            }
        });

    }

}
