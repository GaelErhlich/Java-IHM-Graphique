package et3.projetjig.fenetre;

import et3.projetjig.donnees.Model;
import et3.projetjig.donnees.types.Observation;
import et3.projetjig.donnees.types.Occurrences;
import et3.projetjig.donnees.types.OccurrencesPartition;
import et3.projetjig.fenetre.animateurobs.AnimObsPartitionListener;
import et3.projetjig.fenetre.animateurobs.AnimateurOccsPartition;
import et3.projetjig.fenetre.annees.AnneesSelecteur;
import et3.projetjig.fenetre.annees.AnneesSelecteurListener;
import et3.projetjig.fenetre.especes.EspecesSelecteur;
import et3.projetjig.fenetre.especes.EspecesSelecteurListener;
import et3.projetjig.fenetre.terre.CadreTerre;
import et3.projetjig.fenetre.terre.CadreTerreListener;
import et3.util.InactiviteDetect;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerFenetre
        implements Initializable, FenetreInterface,
        CadreTerreListener, AnneesSelecteurListener, EspecesSelecteurListener,
        AnimObsPartitionListener {


    public final static int DUREE_CHARGEMENT_MESS_MS = 5000;


    @FXML AnchorPane pane3dAnchor;
    @FXML Pane paneAnnees;
    @FXML AnchorPane paneEspeces;

    @FXML TextField especeFld;

    @FXML Button lireBtn;
    @FXML Button globalBtn;
    @FXML Button histogBtn;

    @FXML Label chargementTxt;
    InactiviteDetect chargeTxtInactiver =
            new InactiviteDetect(()-> setChargementTxt(""), DUREE_CHARGEMENT_MESS_MS);


    private CadreTerre terre = null;
    private AnneesSelecteur annees = null;
    private EspecesSelecteur especes = null;
    private AnimateurOccsPartition animateur = null;
    private Model donnees = null;


    private final static short MODE_INACTIF = 0;
    private final static short MODE_OCCURRENCES = 1;
    private final static short MODE_OBSERVATIONS = 2;
    private short mode = MODE_INACTIF;

    public void setMode(short mode) {
        this.mode = mode;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Mise du cadreTerre
        terre = new CadreTerre(500, 500, this, histogBtn);
        pane3dAnchor.getChildren().add(terre);

        // Mise du s??lecteur d'ann??e (composant graphique AnneesSelecteur)
        annees = new AnneesSelecteur(this, (short)1950, (short)2020);
        paneAnnees.getChildren().add(annees);

        // Mise du s??lecteur d'esp??ce
        especes = new EspecesSelecteur(this, 320, 150, especeFld);
        paneEspeces.getChildren().add(especes);

        // Mise en place de l'animateur d'occurrences
        animateur = new AnimateurOccsPartition(this, lireBtn, globalBtn, new Button[] {
                histogBtn
        });

        // Mise en place des donn??es
        donnees = new Model();
        donnees.setListener(this);

        // On lance les donn??es par d??faut
        donnees.getDonneesParDefaut();
        chargementTxt.setText("Chargement des occurrences par d??faut...");
    }


    /**
     * Change le texte de chargement en bas de la fen??tre
     * @param nouvTexte le texte de remplacement
     */
    private void setChargementTxt(String nouvTexte) {
        Platform.runLater(()-> chargementTxt.setText(nouvTexte));
    }

    /**
     * Change le texte de chargement en bas de la fen??tre et programme sa suppression apr??s quelques secondes
     * @param nouvText le texte de remplacement
     */
    private void setChargementTxtTemp(String nouvText) {
        setChargementTxt(nouvText);
        chargeTxtInactiver.ping();
    }



    InactiviteDetect geoHashUserInactiver = new InactiviteDetect(null, 500);

    /**
     * Notifie cette fen??tre qu'un nouvelle pr??cision de Geohash a ??t?? s??lectionn?? par l'utilisateur
     * @param geoHash le GeoHash s??lectionn?? de pr??cision correspondante
     */
    @Override
    public void recoitPrecisionParUser(GeoHash geoHash) {

        if(mode == MODE_OBSERVATIONS || mode == MODE_INACTIF) {
            setChargementTxt("Chargement des observations sur "+geoHash.toBase32()+"...");
            geoHashUserInactiver.ping(()-> donnees.getObservations(geoHash, especes.getNomScientifique()) );
        }


        else if(mode == MODE_OCCURRENCES) {
            String espece = especes.getNomScientifique();
            setChargementTxt("Chargement des occurrences de "+espece+" ("
                    +annees.getDebut()+"-"+annees.getFin()+")...");

            anneeUserInactiver.ping(()-> {
                donnees.getOccurences(espece, annees.getDebut(), annees.getFin(),
                        terre.getGeoHash().getCharacterPrecision());
            });
        }
    }


    /**
     * Notifie la fen??tre qu'un nouveau GeoHash a ??t?? s??lectionn?? par l'utilisateur
     * @param geoHash le nouveau GeoHash
     */
    @Override
    public void recoitGeoHashParUser(GeoHash geoHash) {
        setChargementTxt("Chargement des observations sur "+geoHash.toBase32()+"...");
        geoHashUserInactiver.ping(()-> donnees.getObservations(geoHash, especes.getNomScientifique()) );
    }


    InactiviteDetect anneeUserInactiver = new InactiviteDetect(null, 500);
    /**
     * Notifie la fen??tre qu'une ou deux nouvelles ann??es ont ??t?? s??lectionn??es par l'utilisateur
     * @param debutAnnee ann??e du d??but de l'intervalle
     * @param finAnnee ann??e de la fin de l'intervalle
     */
    @Override
    public void recoitAnneesParUser(short debutAnnee, short finAnnee) {
        if(mode == MODE_OCCURRENCES) {
            String espece = especes.getNomScientifique();
            setChargementTxt("Chargement des occurrences de "+espece+" ("+debutAnnee+"-"+finAnnee+")...");

            anneeUserInactiver.ping(()-> {
                donnees.getOccurences(espece, annees.getDebut(), annees.getFin(),
                        terre.getGeoHash().getCharacterPrecision());
            });
        }
    }


    InactiviteDetect especeUserInactiver = new InactiviteDetect(null, 500);

    /**
     * Notifie la fen??tre qu'un nouveau nom d'esp??ce a ??t?? s??lectionn?? par l'utilisateur
     * @param nom le nouveau nom d'esp??ce
     */
    @Override
    public void recoitEspeceParUser(String nom) {
        setChargementTxt("Recherche de l'esp??ce "+nom+"...");
        especeUserInactiver.ping(()-> donnees.getOccurences(nom, annees.getDebut(), annees.getFin(),
                terre.getGeoHash().getCharacterPrecision()) );
    }


    /**
     * Notifie la fen??tre que la base de donn??es a envoy?? une OccurrencesPartition
     * @param op re??ue
     */
    @Override
    public void recoitOccurrencesParBDD(OccurrencesPartition op) {
        setChargementTxtTemp("Esp??ce charg??e : "+op.getEspece().getNomScientifique());
        setMode(MODE_OCCURRENCES);

        especes.recoitEspece(op.getEspece());
        annees.setDebut(op.getAnneeDebut());
        annees.setFin(op.getAnneeFin());
        animateur.setOccPartition(op);
    }

    /**
     * Notifie la fen??tre que la base de donn??es a envoy?? des noms d'esp??ces possibles
     * @param nomsEspeces un tableau de noms d'esp??ces possibles
     */
    @Override
    public void recoitEspecesParBDD(String[] nomsEspeces) {
        setChargementTxtTemp("Plusieurs esp??ces trouv??es");
        especes.recoitListeEspeces(nomsEspeces);
    }

    /**
     * Notifie la fen??tre que la base de donn??es a envoy?? une erreur dans la recherche d'esp??ce
     * @param nomInvalide nom n'ayant donn?? aucun r??sultat
     */
    @Override
    public void recoitErreurEspece(String nomInvalide) {
        setChargementTxtTemp("Erreur...");
        especes.recoitErreurEspece(nomInvalide);
    }

    /**
     * Notifie la fen??tre que la base de donn??es a envoy?? des Observations
     * @param geoHash GeoHash ??tudi??
     * @param obs tableau d'Observation re??u
     */
    @Override
    public void recoitObservationsParBDD(GeoHash geoHash, Observation[] obs) {
        setChargementTxtTemp("Observation re??ue pour le geohash : "+geoHash.toBase32());
        setMode(MODE_OBSERVATIONS);

        animateur.attente();
        terre.recoitCmdDeselectCarresEtHist();
        especes.recoitObservations(obs);
        terre.recoitGeoHash(geoHash);
    }

    /**
     * Notifie la fen??tre qu'une nouvelle occurrence doit ??tre affich??e (sur le globe)
     * @param occurrences la nouvelle occurrence ?? afficher
     * @param min la valeur minimale parmi ces occurrences
     * @param max la valeur maximale parmi ces occurrences
     */
    @Override
    public void recoitOccurrencesParAnim(Occurrences occurrences, int min, int max) {
        setMode(MODE_OCCURRENCES);

        especes.recoitEspece(occurrences.getEspece());
        terre.recoitOccurrences(occurrences, min, max);
    }
}
