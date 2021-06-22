package et3.projetjig.fenetre.especes;

import et3.projetjig.donnees.types.Observation;
import et3.projetjig.donnees.types.Taxon;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.Arrays;


public class EspecesSelecteur extends ListView<String> {

    private EspecesSelecteurListener parent;
    private TextField field;

    public final static short MODE_MESSAGE = 0;
    public final static short MODE_ERREUR = 1;
    public final static short MODE_INFO_ESPECE = 2;
    public final static short MODE_SELECTION_ESPECE = 3;
    public final static short MODE_OBSERVATIONS = 4;
    private short mode = MODE_MESSAGE;


    private Observation[] observations = null;



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
                    int indice = this.getSelectionModel().getSelectedIndices().get(0);
                    if(indice != 0) {
                        parent.recoitEspeceParUser( this.getSelectionModel().getSelectedItem() );
                    }
                }
            }

            // Si on veut sélectionner l'espèce d'une observation
            else if(mode == MODE_OBSERVATIONS) {
                if (event.getClickCount() >= 2) {
                    try {
                        int indice = this.getSelectionModel().getSelectedIndices().get(0);
                        parent.recoitEspeceParUser( observations[indice-1].getNomScientifique() );
                    } catch(IndexOutOfBoundsException ignored) {}
                }
            }

        });



        field.setOnKeyReleased(keyEvent -> {
            parent.recoitEspeceParUser(field.getText());
        });



    }


    private void setMode(short mode) {
        this.observations = null;

        this.mode = mode;
    }



    public void recoitEspece(Taxon espece) {
        Platform.runLater(()->{
            this.setMode(MODE_INFO_ESPECE);
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
            setMode(MODE_SELECTION_ESPECE);
            ObservableList<String> espObservable = FXCollections.observableArrayList("------------ Espèces suggérées ------------");
            espObservable.addAll(especes);
            this.setItems( espObservable );
        });
    }


    public void recoitErreurEspece(String nom) {
        Platform.runLater(()->{
            this.setMode(MODE_ERREUR);
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


    public void recoitObservations(Observation[] observations) {
        Platform.runLater(()-> {
            setMode(MODE_OBSERVATIONS);
            this.observations = observations;

            Object[] obsObj = Arrays.stream(observations).map(observation ->
                    ""+observation.getNomEspece()+" ("+observation.getNomScientifique()
                    +") | Ordre : "+observation.getOrdre()+", Superclasse : "+observation.getSuperClasse()
                    +", Enregistré par : "+observation.getOrigineEnregistrement()
            ).toArray();

            String[] obsString = new String[obsObj.length];
            for(int i = 0; i < obsObj.length; i++) {
                obsString[i] = (String) obsObj[i];
            }

            ObservableList<String> obsObservable =
                    FXCollections.observableArrayList("------------ Observations sur la zone ------------");
            obsObservable.addAll(obsString);
            this.setItems(obsObservable);
        });

    }





}
