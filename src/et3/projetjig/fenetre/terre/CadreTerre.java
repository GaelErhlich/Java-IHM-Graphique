package et3.projetjig.fenetre.terre;

import et3.outils3d.CameraManager;
import et3.projetjig.donnees.types.Occurrence;
import et3.projetjig.donnees.types.Occurrences;
import et3.projetjig.fenetre.terre.sphereterre.SphereTerre;
import et3.projetjig.fenetre.terre.sphereterre.exceptions.NullLocalisationPrincipale;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;
import kungfoo.geohash.src.main.java.ch.hsr.geohash.WGS84Point;

public class CadreTerre extends Pane {

    public static short NOMBRE_INTERVALLES = 8;



    private final CadreTerreListener fenetre;

    /**
     * Groupe contenant les Mesh de la Terre
      */
    private SphereTerre sphereTerre;
    /**
     * Groupe à la racine du graphe 3D
     */
    private Group enfantSubScene;
    /**
     * La SubScene contenant tout ça
     */
    private SubScene subScene;
    /**
     * Le Group qui contient la SubScene
     */
    private Group parentSubScene;
    /**
     * Le groupe qui contient les légendes
     */
    private final Group legendes = new Group();
    /**
     * Valeur maximale de l'échelle en haut à gauche
     */
    private final Text echelleHaut;
    /**
     * Valeur minimale de l'échelle en haut à gauche
     */
    private final Text echelleBas;

    /**
     * Bouton activant ou désactivant les histogrammes
     */
    private final Button histogBtn;



    private Camera camera;
    private CameraManager cameraManager;


    private Occurrences occurrences = null;
    private int minOcc = 0;
    private int maxOcc = 0;
    private boolean estModeHistog;



    /**
     * Définit un Pane 3D contenant la Terre, avec les dimensions (2D) spécifiées
     * @param width la largeur du Pane 3D
     * @param height la hauteur du Pane 3D
     * @param fenetre Fenêtre à laquelle correspond ce cadre, à laquelle envoyer des informations
     */
    public CadreTerre(int width, int height, CadreTerreListener fenetre, Button histogBtn) {
        this.fenetre = fenetre;
        this.histogBtn = histogBtn;

        this.initialiseFormes(width, height);
        this.initialiseCamera();
        this.initialiseEclairageTerre();


        // Echelle
        Rectangle rect;
        for(short i = 0; i < NOMBRE_INTERVALLES; i++) {
            rect = new Rectangle(10,(NOMBRE_INTERVALLES-i)*10,10,10);
            rect.setFill( couleurEchellePourNiveau(i, 1.0f) );
            this.legendes.getChildren().add(rect);
        }
        echelleHaut = new Text(25, 15, "Maximum");
        echelleBas = new Text(25, 15 + 10*NOMBRE_INTERVALLES, "Minimum");
        echelleHaut.setFill(Color.WHITE);
        echelleBas.setFill(Color.WHITE);
        this.legendes.getChildren().add(echelleHaut);
        this.legendes.getChildren().add(echelleBas);



        // Déclaration des événements
        CadreTerreEvents.declare(this, histogBtn);

    }


    /**
     * Met en place une sphère représentant la Terre ainsi qu'une Subscene
     * contenant un groupe "parent" et la sphère,
     * la Subscene elle-même contenue dans un Pane.
     * @param width largeur du cadre dans la fenêtre
     * @param height hauteur du cadre dans la fenêtre
     */
    private void initialiseFormes(int width, int height) {

        
        sphereTerre = new SphereTerre();

        enfantSubScene = new Group(sphereTerre);
        subScene = new SubScene(enfantSubScene, width, height, true, SceneAntialiasing.BALANCED);
        parentSubScene = new Group(subScene);
        this.getChildren().add(parentSubScene);

        subScene.setFill(Color.BLACK);

        this.getChildren().add(legendes);
    }

    /**
     * Initialise la caméra et le CameraManager, puis les met à la Subscene
     */
    private void initialiseCamera() {
        camera = new PerspectiveCamera(true);
        cameraManager = new CameraManager(camera, this, enfantSubScene);
        subScene.setCamera(camera);
    }


    /**
     * Met un éclairage constitué de 3 PointLight autour de la Terre
     */
    private void initialiseEclairageTerre() {

        for(float t=0f; t < 2*Math.PI; t += 2*Math.PI/3) {

                PointLight light = new PointLight(Color.WHITE);
                double x = 10*Math.cos(t);
                double z = 10*Math.sin(t);
                light.setTranslateX(x);
                light.setTranslateZ(z);
                light.getScope().add(sphereTerre);
                enfantSubScene.getChildren().add(light);
        }
    }

    /**
     * Génère une couleur JavaFX pour un certain niveau sur l'échelle de ce CadreTerre
     * @param niveau le niveau dont dépend la couleur (entre 0 et NOMBRE_INTERVALLES-1)
     * @param transparence le niveau de transparence de la couleur générée
     * @return la nouvelle couleur
     */
    private static Color couleurEchellePourNiveau(short niveau, float transparence) {
        float pas = 1.0f / NOMBRE_INTERVALLES;
        return new Color(1.0 - niveau*pas, niveau*pas, 0.0, transparence);
    }

    /**
     * Détermine l'indice d'une valeur sur l'échelle de ce CadreTerre
     * @param valeur valeur dont on veut trouver l'indice
     * @param min valeur minimale possible sur l'échelle
     * @param max valeur maximale possible sur l'échelle
     * @param tailleIntervalle taille des plages de valeurs de chaque indice (optionnel)
     * @return l'indice de la valeur
     */
    private static short niveauEchellePourValeur(int valeur, int min, int max, Float tailleIntervalle) {
        if(tailleIntervalle == null) {
            tailleIntervalle = ((float)(max - min)) / NOMBRE_INTERVALLES;
        }

        short niveau = (short)( (valeur - min) / tailleIntervalle);
        niveau = (short) Math.min(Math.max(niveau, 0),NOMBRE_INTERVALLES-1);
        return niveau;
    }



    /**
     * Notifie la fenêtre qu'un nouveau GeoHash a été demandé par l'utilisateur
     * @param geoHash le Geohash qui va être envoyé à la fenêtre
     */
    void envoiNouvGeoHashVersFenetre(GeoHash geoHash) {
        fenetre.recoitGeoHashParUser(geoHash);
    }


    /**
     * Notifie la fenêtre qu'une nouvelle précision de GeoHash a été demandée par l'utilisateur
     * @param geoHash le GeoHash actuel avec la nouvelle précision
     */
    void envoiNouvPrecisionVersFenetre(GeoHash geoHash) {
        fenetre.recoitPrecisionParUser(geoHash);
    }


    /**
     * Getter de la SphereTerre correspondante à ce CadreTerre
     * @return le Group "sphereTerre" contenant le globe
     */
    public SphereTerre getSphereTerre() {
        return sphereTerre;
    }


    /**
     * Notifie le CadreTerre qu'il doit utiliser un nouveau Geohash
     * @param geoHash le nouveau GeoHash
     */
    public void recoitGeoHash(GeoHash geoHash) {
        Platform.runLater(()->{
            sphereTerre.supprimeCarres();
            sphereTerre.supprimerHistogramme();
            sphereTerre.setGeoHash(geoHash);
        });
    }


    /**
     * Getter du GeoHash actuellement sélectionné
     * @return le GeoHash
     */
    public GeoHash getGeoHash() {
        return sphereTerre.getGeoHash();
    }


    /**
     * Active le mode histogramme sur le globe
     */
    public void activeModeHistog() {
        this.estModeHistog = true;

        Platform.runLater(()->{
            histogBtn.setText("Histogramme ON");
            dessineOccurrences();
        });
    }

    /**
     * Désactive le mode histogramme sur le globe (passe en mode carrés à la surface)
     */
    public void desactiveModeHistog() {
        this.estModeHistog = false;

        Platform.runLater(()->{
            histogBtn.setText("Histogramme OFF");
            dessineOccurrences();
        });
    }

    /**
     * Indique si le mode histogramme est actif
     * @return true si mode histogramme, false si mode carrés à la surface
     */
    public boolean estHistogramme() {
        return estModeHistog;
    }


    /**
     * Notifie le CadreTerre que des Occurrences doivent être affichées
     * @param occurrences les Occurrences à afficher
     * @param min la valeur minimale parmi les Occurrence
     * @param max la valeur maximale parmi les Occurrence
     */
    public void recoitOccurrences(Occurrences occurrences, int min, int max) {
        Platform.runLater(()->{

            this.occurrences = occurrences;
            this.minOcc = min;
            this.maxOcc = max;

            if(occurrences.getOccurrences().length > 0 && sphereTerre.getGeoHash() != null) {
                try {
                    sphereTerre.setPrecision( occurrences.getOccurrences()[0].getGeohash().significantBits() );
                } catch(NullLocalisationPrincipale ignored) { }
            }

            dessineOccurrences();
        });
    }


    /**
     * Dessine les Occurrence actuellement enregistrées
     */
    public void dessineOccurrences() {
        if(occurrences != null) {

            Platform.runLater(()->{

                echelleBas.setText( Integer.toString(minOcc) );
                echelleHaut.setText( Integer.toString(maxOcc) );

                sphereTerre.deselectionnerLocPrincipale();
                sphereTerre.supprimeCarres();
                sphereTerre.supprimerHistogramme();

                float tailleInterv = ((float)(maxOcc - minOcc)) / NOMBRE_INTERVALLES;

                for(Occurrence occ : occurrences.getOccurrences()) {
                    short niveau = niveauEchellePourValeur(occ.getNombreOccu(), minOcc, maxOcc, tailleInterv);

                    if( estHistogramme() ) {
                        WGS84Point box = occ.getGeohash().getBoundingBoxCenter();
                        sphereTerre.recoitHistogramme(new Point2D(box.getLatitude(), box.getLongitude()),
                                occ.getNombreOccu(), maxOcc, couleurEchellePourNiveau(niveau, 1.0f));
                    }
                    else {
                        sphereTerre.ajouterGeoHash(occ.getGeohash(), couleurEchellePourNiveau(niveau, 0.1f));
                    }
                }
            });
        }
    }


    /**
     * Notifie ce CadreTerre que les carrés/histogramme doivent être cachés
     */
    public void recoitCmdDeselectCarresEtHist() {
        Platform.runLater(()-> {
            sphereTerre.supprimeCarres();
            sphereTerre.supprimerHistogramme();
        } );
    }

}
