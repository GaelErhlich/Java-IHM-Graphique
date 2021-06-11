package et3.projetjig.terre;

import javafx.scene.input.MouseEvent;
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


                Material material = new PhongMaterial( new Color(0.95, 0.85, 0.6, 0.1) );
                cadreTerre.getSphereTerre().ajouteCarre(10, 50, -40, 0, material );
            }
        });
        // */



    }

}
