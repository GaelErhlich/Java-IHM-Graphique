package et3.projetjig.fenetre.especes;

import et3.projetjig.donnees.types.Taxon;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


public class EspecesSelecteur extends ListView<String> {

    private EspecesSelecteurListener parent;
    private TextField field;

    public final static short MODE_MESSAGE = 0;
    public final static short MODE_ERREUR = 1;
    public final static short MODE_INFO_ESPECE = 2;
    public final static short MODE_SELECTION_ESPECE = 3;
    private short mode = MODE_MESSAGE;


    public EspecesSelecteur(EspecesSelecteurListener parent, int width, int height, TextField field) {
        this.parent = parent;
        this.field = field;

        this.setWidth(width);
        this.setPrefWidth(width);
        this.setHeight(height);
        this.setPrefHeight(height);

        initialiseEvents();
    }



    private void initialiseEvents() {

        /* Quand on sélectionne une ligne */
        this.setOnMouseClicked(event -> {

            // Si on veut sélectionner une espèce
            if(mode == MODE_SELECTION_ESPECE) {
                if (event.getClickCount() >= 2) {
                    parent.recoitEspeceParUser(this.getSelectionModel().getSelectedItem().toString());
                }
            }

        });



        field.setOnKeyReleased(keyEvent -> {
            parent.recoitEspeceParUser(field.getText());
        });



    }


    public void recoitEspece(Taxon espece) {
        Platform.runLater(()->{
            this.mode = MODE_INFO_ESPECE;
            this.setItems( FXCollections.observableArrayList(
                    "------------ Espèce actuelle ------------",
                    "Nom scientifique : "+espece.getNomScientifique(),
                    "Identifiant : "+espece.getId(),
                    "Rang : "+espece.getRang(),
                    "Phylum : "+espece.getPhylum()
            ));
        });
    }


    public void recoitListeEspeces(String[] especes) {
        Platform.runLater(()->{
            this.mode = MODE_SELECTION_ESPECE;
            this.setItems( FXCollections.observableArrayList(especes) );
        });
    }


    public void recoitErreurEspece(String nom) {
        Platform.runLater(()->{
            this.mode = MODE_ERREUR;
            this.setItems(FXCollections.observableArrayList(
                    "------------ ERREUR NOM ------------",
                    "L'espèce suivante n'a pas été trouvée :",
                    nom,
                    "",
                    "Vous devez fournir le nom scientifique",
                    "de l'espèce."
            ));
        });
    }





}
