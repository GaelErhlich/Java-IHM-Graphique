package et3.projetjig.fenetre.terre;

import et3.outils3d.CameraManager;
import et3.projetjig.donnees.types.Occurrence;
import et3.projetjig.donnees.types.Occurrences;
import et3.projetjig.fenetre.terre.sphereterre.SphereTerre;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import kungfoo.geohash.src.main.java.ch.hsr.geohash.BoundingBox;
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
    private Group legendes = new Group();
    /**
     * Valeur maximale de l'échelle en haut à gauche
     */
    private Text echelleHaut;
    /**
     * Valeur minimale de l'échelle en haut à gauche
     */
    private Text echelleBas;



    private Camera camera;
    private CameraManager cameraManager;



    /**
     * Définit un Pane 3D contenant la Terre, avec les dimensions (2D) spécifiées
     * @param width la largeur du Pane 3D
     * @param height la hauteur du Pane 3D
     * @param fenetre Fenêtre à laquelle correspond ce cadre, à laquelle envoyer des informations
     */
    public CadreTerre(int width, int height, CadreTerreListener fenetre) {
        this.fenetre = fenetre;

        this.initialiseFormes(width, height);
        this.initialiseCamera();
        this.initialiseEclairageTerre();


        // Echelle
        Rectangle rect;
        for(short i = (short)(NOMBRE_INTERVALLES-1); i >= 0; i--) {
            rect = new Rectangle(10,10 + i*10,10,10);
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
        CadreTerreEvents.declare(this);



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


    private static Color couleurEchellePourNiveau(short niveau, float transparence) {
        float pas = 1.0f / NOMBRE_INTERVALLES;
        return new Color(1.0 - niveau*pas, niveau*pas, 0.0, transparence);
    }

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
    boolean envoiNouvGeoHashVersFenetre(GeoHash geoHash) {
        return fenetre.recoitGeoHashParUser(geoHash);
    }



    public SphereTerre getSphereTerre() {
        return sphereTerre;
    }


    public void recoitGeoHash(GeoHash geoHash) {
        sphereTerre.setGeoHash(geoHash);
    }




    public void recoitOccurrences(Occurrences occurrences, int min, int max) {
        Platform.runLater(()->{

            echelleBas.setText( Integer.toString(min) );
            echelleHaut.setText( Integer.toString(max) );

            sphereTerre.supprimeCarres();
            sphereTerre.supprimerHistogramme();

            float tailleInterv = ((float)(max - min)) / NOMBRE_INTERVALLES;

            for(Occurrence occ : occurrences.getOccurrences()) {
                short niveau = niveauEchellePourValeur(occ.getNombreOccu(), min, max, tailleInterv);

                sphereTerre.ajouterGeoHash(occ.getGeohash(), couleurEchellePourNiveau(niveau, 0.1f));

                WGS84Point box = occ.getGeohash().getBoundingBoxCenter();
                sphereTerre.recoitHistogramme(new Point2D(box.getLatitude(), box.getLongitude()),
                        occ.getNombreOccu(), max, couleurEchellePourNiveau(niveau, 1.0f));
            }
        });
    }


    public void recoitCmdDeselectCarres() {
        Platform.runLater(()-> sphereTerre.supprimeCarres() );
    }

}
