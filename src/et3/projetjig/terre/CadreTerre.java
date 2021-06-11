package et3.projetjig.terre;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import et3.outils3d.CameraManager;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;

import java.net.URL;

public class CadreTerre implements CadreTerreInterface {

    private Group sphereTerre;
    private Group parent;
    private SubScene subScene;
    private Pane paneFond;

    public CadreTerre(int width, int height) {

        this.initialiseFormes(width, height);
        this.initialiseCamera();
        //this.ajouter1carreDEBUG();

    }

    private void initialiseFormes(int width, int height) {
        ObjModelImporter objImporter = new ObjModelImporter();
        try {
            URL modelURL = this.getClass().getResource("Earth/earth.obj");
            objImporter.read(modelURL);
        } catch (ImportException e) {
            e.printStackTrace();
        }
        MeshView[] meshViews = objImporter.getImport();

        sphereTerre = new Group(meshViews);
        parent = new Group(sphereTerre);
        subScene = new SubScene(parent, width, height, true, SceneAntialiasing.BALANCED);
        paneFond = new Pane(subScene);

        subScene.setFill(Color.BLACK);
    }

    private void initialiseCamera() {
        Camera camera = new PerspectiveCamera(true);
        CameraManager cameraManager = new CameraManager(camera, paneFond, parent);
        subScene.setCamera(camera);
    }

    private void ajouter1carreDEBUG() {
        Box carre = new Box(1,1,1);

        Color color = Color.BLUE;
        PhongMaterial material = new PhongMaterial(color);
        material.setSpecularColor(color);
        material.setSpecularColor(color);
        carre.setMaterial(material);

        carre.setTranslateZ(2);
        carre.setTranslateX(2);

        parent.getChildren().add(carre);
    }



    @Override
    public Pane getPaneFond() {
        return paneFond;
    }
}
