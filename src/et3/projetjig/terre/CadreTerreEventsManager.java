package et3.projetjig.terre;

import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;

public class CadreTerreEventsManager {

    private CadreTerre cadreTerre;

    public CadreTerreEventsManager(CadreTerre cadreTerre) {

        initialiseEventsHandlerDEBUG(cadreTerre);
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

}
