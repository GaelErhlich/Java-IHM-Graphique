package et3.projetjig.fenetre.especes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;


public class EspecesSelecteur extends ListView {

    private EspecesSelecteurListener parent;


    public EspecesSelecteur(EspecesSelecteurListener parent, int width, int height) {
        this.parent = parent;

        this.setWidth(width);
        this.setPrefWidth(width);
        this.setHeight(height);
        this.setPrefHeight(height);

        initialiseEvents();
    }



    private void initialiseEvents() {


        this.setOnMouseClicked(event -> {

            if(event.getClickCount() >= 2) {
                parent.recoitEspeceParUser( this.getSelectionModel().getSelectedItem().toString() );
            }

        });

    }



}
