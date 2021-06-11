package et3.projetjig.terre;

import et3.outils3d.CameraManager;
import et3.projetjig.terre.sphereterre.SphereTerre;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class CadreTerre extends Pane implements CadreTerreInterface {

    private Group sphereTerre;
    private Group enfantSubScene;
    private SubScene subScene;
    private Group parentSubScene;

    private Camera camera;
    private CameraManager cameraManager;


    /**
     * Définit un Pane 3D contenant la Terre, avec les dimensions (2D) spécifiées
     * @param width la largeur du Pane 3D
     * @param height la hauteur du Pane 3D
     */
    public CadreTerre(int width, int height) {

        this.initialiseFormes(width, height);
        this.initialiseCamera();
        this.initialiseEclairageTerre();
        //this.ajouter1carreDEBUG();

    }

    /**
     * Met en place une sphère représentant la Terre ainsi qu'une Subscene
     * contenant un groupe "parent" et la sphère,
     * la Subscene elle-même contenue dans un Pane.
     * @param width
     * @param height
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
     * Ajouter un carré bleu aux coordonnées indiquées avec y=0.
     * Conçu pour du debug
     * @param x la coordonnée x
     * @param z la coordonnée z
     */
    private void ajouter1carreDEBUG(double x, double z) {
        Box carre = new Box(1,1,1);

        Color color = Color.BLUE;
        PhongMaterial material = new PhongMaterial(color);
        material.setSpecularColor(color);
        material.setSpecularColor(color);
        carre.setMaterial(material);

        carre.setTranslateX(x);
        carre.setTranslateZ(z);

        enfantSubScene.getChildren().add(carre);
    }

}
