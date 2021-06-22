package et3.projetjig.fenetre;

import et3.projetjig.donnees.types.Observation;
import et3.projetjig.donnees.types.OccurrencesPartition;
import et3.projetjig.fenetre.annees.AnneesSelecteur;
import et3.projetjig.fenetre.annees.AnneesSelecteurListener;
import et3.projetjig.fenetre.especes.EspecesSelecteur;
import et3.projetjig.fenetre.especes.EspecesSelecteurListener;
import et3.projetjig.fenetre.terre.CadreTerre;
import et3.projetjig.fenetre.terre.CadreTerreListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerFenetre
        implements Initializable, FenetreInterface,
        CadreTerreListener, AnneesSelecteurListener, EspecesSelecteurListener {


    @FXML AnchorPane pane3dAnchor;
    @FXML Pane paneAnnees;
    @FXML AnchorPane paneEspeces;

    @FXML TextField especeFld;

    @FXML Button animerBtn;



    private CadreTerre terre = null;
    private AnneesSelecteur annees = null;
    private EspecesSelecteur especes = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Mise du cadreTerre
        terre = new CadreTerre(500, 500, this);
        pane3dAnchor.getChildren().add(terre);

        // Mise du sélecteur d'année (composant graphique AnneesSelecteur)
        annees = new AnneesSelecteur(this, (short)1980, (short)2020);
        paneAnnees.getChildren().add(annees);

        // Mise du sélecteur d'espèce
        especes = new EspecesSelecteur(this, 320, 150, especeFld);
        paneEspeces.getChildren().add(especes);


    }

    @Override
    public boolean recoitGeoHashParUser(GeoHash geoHash) {
        return true;
    }


    private void majAnimerBtn(short debutAnnee, short finAnnee) {
        if (debutAnnee == finAnnee) {
            animerBtn.setText("Année " + debutAnnee);
            animerBtn.setDisable(true);
        } else {
            animerBtn.setText("Animer (" + debutAnnee + "-" + finAnnee + ")");
            animerBtn.setDisable(false);
        }
    }

    @Override
    public void recoitAnneesParUser(short debutAnnee, short finAnnee) {
        majAnimerBtn(debutAnnee, finAnnee);
    }


    @Override
    public void recoitEspeceParUser(String nom) {
        System.out.println("Nouvelle espèce sélectionnée : "+nom);
        // TODO : demander les informations sur l'espèce

    }


    @Override
    public void recoitOccurrencesParBDD(OccurrencesPartition op) {
        especes.recoitEspece(op.getEspece());
        annees.setDebut(op.getAnneeDebut());
        annees.setFin(op.getAnneeFin());
        terre.recoitOccurrences(op.getOccsGlobales(), op.getMinGlobales(), op.getMaxGlobales()); // TODO : TEMPORAIRE
        // TODO : Afficher sur le globe
        // TODO : Stocker
    }

    @Override
    public void recoitEspecesParBDD(String[] nomsEspeces) {
        especes.recoitListeEspeces(nomsEspeces);
    }

    @Override
    public void recoitErreurEspece(String nomInvalide) {
        especes.recoitErreurEspece(nomInvalide);
    }

    @Override
    public void recoitObservationsParBDD(GeoHash geoHash, Observation[] obs) {
        especes.recoitObservations(obs);
        terre.recoitGeoHash(geoHash);
    }
}
