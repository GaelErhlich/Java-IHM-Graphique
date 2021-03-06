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
    private String nomScientifique = "";


    /**
     *
     * @param parent composant parent écoutant le sélecteur d'espèces
     * @param width largeur attendue pour ce composant
     * @param height hauteur attendue pour ce composant
     * @param field champ JavaFX dans lequel les noms d'espèces sont tapés
     */
    public EspecesSelecteur(EspecesSelecteurListener parent, int width, int height, TextField field) {
        this.parent = parent;
        this.field = field;

        this.setWidth(width);
        this.setPrefWidth(width);
        this.setHeight(height);
        this.setPrefHeight(height);

        initialiseEvents();
    }


    /**
     * Initialise les événements de ce composant
     */
    private void initialiseEvents() {

        /* Quand on sélectionne une ligne */
        this.setOnMouseClicked(event -> {

            // Si on veut sélectionner une espèce
            if(mode == MODE_SELECTION_ESPECE) {
                if (event.getClickCount() >= 2) {
                    int indice = this.getSelectionModel().getSelectedIndices().get(0);
                    if(indice != 0) {
                        String nom = this.getSelectionModel().getSelectedItem();
                        field.setText(nom);
                        parent.recoitEspeceParUser(nom);
                    }
                }
            }

            // Si on veut sélectionner l'espèce d'une observation
            else if(mode == MODE_OBSERVATIONS) {
                if (event.getClickCount() >= 2) {
                    try {
                        int indice = this.getSelectionModel().getSelectedIndices().get(0);
                        String nom = observations[indice-1].getNomScientifique();
                        field.setText(nom);
                        parent.recoitEspeceParUser(nom);
                    } catch(IndexOutOfBoundsException ignored) {}
                }
            }

        });



        field.setOnKeyReleased(keyEvent -> {
            parent.recoitEspeceParUser(field.getText());
        });

    }


    /**
     * Met un nouveau mode pour ce composant pour définir son comportement
     * @param mode code du nouveau mode (ex : MODE_INFO_ESPECE)
     */
    private void setMode(short mode) {
        this.observations = null;

        this.mode = mode;
    }


    /**
     * Getter du nom scientifique actuellement enregistré
     * @return la String du nom
     */
    public String getNomScientifique() {
        return nomScientifique;
    }


    /**
     * Notifie ce composant que son parent lui a envoyé une nouvelle espèce à afficher et enregistrer
     * @param espece la nouvelle espèce
     */
    public void recoitEspece(Taxon espece) {
        Platform.runLater(()->{
            this.setMode(MODE_INFO_ESPECE);
            this.setItems( FXCollections.observableArrayList(
                    "------------ Type actuel ------------",
                    "Nom scientifique : "+espece.getNomScientifique(),
                    "Identifiant : "+espece.getId(),
                    "Rang : "+espece.getRang(),
                    "Phylum : "+espece.getPhylum()
            ));
            nomScientifique = espece.getNomScientifique();
            field.setText(espece.getNomScientifique());
        });
    }


    /**
     * Notifie ce composant qu'il doit afficher plusieurs noms d'espèces parmi lesquels choisir
     * @param especes le tableau de noms d'espèces
     */
    public void recoitListeEspeces(String[] especes) {
        Platform.runLater(()->{
            setMode(MODE_SELECTION_ESPECE);
            ObservableList<String> espObservable = FXCollections.observableArrayList("------------ Espèces suggérées ------------");
            espObservable.addAll(especes);
            this.setItems( espObservable );
        });
    }


    /**
     * Notifie ce composant que la dernière recherche a échoué et qu'il doit l'afficher
     * @param nom nom de l'espèce n'ayant pas donné de résultat
     */
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


    /**
     * Notifie ce composant qu'il doit afficher des Observation
     * @param observations le tableau d'Observation
     */
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
