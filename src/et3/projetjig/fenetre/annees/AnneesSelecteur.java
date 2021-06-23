package et3.projetjig.fenetre.annees;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AnneesSelecteur extends Pane implements Initializable {

    AnneesSelecteurListener parent;

    @FXML Label debutLabel;
    @FXML Slider debutSlider;
    @FXML Label finLabel;
    @FXML Slider finSlider;

    short anneeDebut;
    short anneeFin;


    /**
     *
     * @param parent composant parent écoutant le sélecteur d'année
     * @param anneeDebut date plus ancienne autorisée
     * @param anneeFin date plus récente autorisée
     */
    public AnneesSelecteur(AnneesSelecteurListener parent, short anneeDebut, short anneeFin) {
        this.parent = parent;
        this.anneeDebut = anneeDebut;
        this.anneeFin = anneeFin;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("anneesSelecteur.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            this.getChildren().add(root);
        }
        catch (IOException e) { e.printStackTrace(); }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Initialisation des limites des sliders
        debutSlider.setMin(anneeDebut);
        debutSlider.setMax(anneeFin);
        finSlider.setMin(anneeDebut);
        finSlider.setMax(anneeFin);

        // Initialisation des valeurs des sliders
        setDebut(anneeFin);
        setFin(anneeFin);
        parent.recoitAnneesParUser(anneeDebut, anneeFin);



        // Sélection d'un nouveau début
        debutSlider.addEventHandler(MouseEvent.MOUSE_DRAGGED, (event) -> onDebutSliderValue());
        debutSlider.addEventHandler(MouseEvent.MOUSE_RELEASED, (event) -> onDebutSliderValue());

        // Sélection d'une nouvelle fin
        finSlider.addEventHandler(MouseEvent.MOUSE_DRAGGED, (event) -> onFinSliderValue());
        finSlider.addEventHandler(MouseEvent.MOUSE_RELEASED, (event) -> onFinSliderValue());
    }

    /**
     * Handler de l'événement de mouvement du slider du début de l'intervalle
     */
    private void onDebutSliderValue() {
        debutSlider.setValue( Math.min(debutSlider.getValue(), finSlider.getValue()) );
        setDebut( (short)debutSlider.getValue() );
        parent.recoitAnneesParUser(anneeDebut, anneeFin);
    }

    /**
     * Handler de l'événement de mouvement du slider de la fin de l'intervalle
     */
    private void onFinSliderValue() {
        finSlider.setValue( Math.max(finSlider.getValue(), debutSlider.getValue()) );
        setFin( (short)finSlider.getValue() );
        parent.recoitAnneesParUser(anneeDebut, anneeFin);
    }


    /**
     * Setter de l'année au début de l'intervalle
     * @param annee nouvelle année
     */
    public void setDebut(short annee) {
        this.anneeDebut = annee;

        Platform.runLater(()->{
            debutSlider.setValue(annee);
            debutLabel.setText("Début : "+annee);
        });
    }

    /**
     * Getter de l'année au début de l'intervalle
     * @return année de début
     */
    public short getDebut() {
        return (short)debutSlider.getValue();
    }


    /**
     * Setter de l'année à la fin de l'intervalle
     * @param annee nouvelle année de fin
     */
    public void setFin(short annee) {
        this.anneeFin = annee;

        Platform.runLater(()-> {
            finSlider.setValue(annee);
            finLabel.setText("Fin : "+annee);
        });
    }

    /**
     * Getter de l'année à la fin de l'intervalle
     * @return année de fin
     */
    public short getFin() {
        return (short)finSlider.getValue();
    }
}
