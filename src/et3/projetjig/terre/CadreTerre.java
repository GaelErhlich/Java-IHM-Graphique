package et3.projetjig.terre;

import et3.outils3d.CameraManager;
import et3.projetjig.fenetre.FenetreInterface;
import et3.projetjig.terre.sphereterre.SphereTerre;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;

public class CadreTerre extends Pane implements CadreTerreInterface {

    private FenetreInterface fenetre;

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

    private Camera camera;
    private CameraManager cameraManager;



    /**
     * Définit un Pane 3D contenant la Terre, avec les dimensions (2D) spécifiées
     * @param width la largeur du Pane 3D
     * @param height la hauteur du Pane 3D
     * @param fenetre Fenêtre à laquelle correspond ce cadre, à laquelle envoyer des informations
     */
    public CadreTerre(int width, int height, FenetreInterface fenetre) {
        this.fenetre = fenetre;

        this.initialiseFormes(width, height);
        this.initialiseCamera();
        this.initialiseEclairageTerre();

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
     * Notifie la fenêtre qu'un nouveau GeoHash a été demandé par l'utilisateur
     * @param geoHash le Geohash qui va être envoyé à la fenêtre
     */
    void envoiNouvGeoHashVersFenetre(GeoHash geoHash) {
        fenetre.recoitGeoHashParUser(geoHash);
    }



    public SphereTerre getSphereTerre() {
        return sphereTerre;
    }

    @Override
    public void recoitGeoHash(GeoHash geoHash) {
        sphereTerre.setGeoHash(geoHash);
    }
}
