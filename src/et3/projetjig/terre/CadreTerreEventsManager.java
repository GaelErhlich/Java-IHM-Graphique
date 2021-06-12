package et3.projetjig.terre;

import et3.maths.CoordonneesConvert;
import et3.projetjig.terre.sphereterre.SphereTerre;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;


public class CadreTerreEventsManager {

    public CadreTerreEventsManager(CadreTerre cadreTerre, SphereTerre terre) {

        //initialiseEventsHandlerDEBUG(cadreTerre);
        initialiseEventsClic(terre);
    }


    private void initialiseEventsHandlerDEBUG(CadreTerre cadreTerre) {


        cadreTerre.setOnMousePressed(event -> {
            if(event.isPrimaryButtonDown()) {

                short latMin = (short)(Math.random()*180 - 90);
                short latMax = (short)(latMin + Math.random()*50);
                short lonMin = (short)(Math.random()*360 - 180);
                short lonMax = (short)(lonMin + Math.random()*50);

                Material material = new PhongMaterial( new Color(0.95, 0.85, 0.6, 0.1) );
                cadreTerre.getSphereTerre().ajouteCarre(latMin, latMax, lonMin, lonMax, material );
            }
            else if(event.isSecondaryButtonDown()) {
                cadreTerre.getSphereTerre().supprimeCarres();
            }
        });
        // */

    }

    private void initialiseEventsClic(SphereTerre terre) {

        terre.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            System.out.println("Clic sur la Terre !");

            // On récupère le point dans l'espace
            PickResult pickResult = event.getPickResult();
            Point3D coords3D = pickResult.getIntersectedPoint();

            // On le convertit en coordonnées 2D
            Point2D coords2D = CoordonneesConvert.coord3dToGeoCoord(coords3D, new Point3D(0,0,0),
                    SphereTerre.TEXTURE_LAT_OFFSET, SphereTerre.TEXTURE_LON_OFFSET);

            terre.ajouteCarre( coords2D.getX()-10, coords2D.getX()+10,
                    coords2D.getY()-10, coords2D.getY()+10, new PhongMaterial(Color.BLUE) );
            //terre.ajouteLocalisation(coords2D.getX(), coords2D.getY());
            // TODO A compléter quand la conversion sera possible
        });

    }

}
