package et3.projetjig.fenetre.annees;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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


    public AnneesSelecteur(AnneesSelecteurListener parent, short anneeDebut, short anneeFin) throws IOException {
        this.parent = parent;
        this.anneeDebut = anneeDebut;
        this.anneeFin = anneeFin;

        FXMLLoader loader = new FXMLLoader( getClass().getResource("anneesSelecteur.fxml") );
        loader.setController(this);
        Parent root = loader.load();
        this.getChildren().add(root);
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



        debutSlider.valueProperty().addListener( (ObservableValue<? extends Number> observableValue, Number number, Number t1) -> {
            debutSlider.setValue( Math.min(t1.doubleValue(), finSlider.getValue()) );
            this.anneeDebut = (short)debutSlider.getValue();
            debutLabel.setText("Début : "+anneeDebut);
        });

        finSlider.valueProperty().addListener( (ObservableValue<? extends Number> observableValue, Number number, Number t1) -> {
            finSlider.setValue( Math.max(t1.doubleValue(), debutSlider.getValue()) );
            this.anneeFin = (short)finSlider.getValue();
            finLabel.setText("Fin : "+anneeFin);
        });
    }

    public void setDebut(short annee) {
        debutSlider.setValue(annee);
    }
    public short getDebut() {
        return (short)debutSlider.getValue();
    }


    public void setFin(short annee) {
        finSlider.setValue(annee);
    }
    public short getFin() {
        return (short)finSlider.getValue();
    }
}
