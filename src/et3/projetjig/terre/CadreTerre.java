package et3.projetjig.terre;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import javafx.scene.Group;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.MeshView;

import java.net.URL;

public class CadreTerre implements CadreTerreInterface {

    private Group sphereTerre;
    private SubScene subScene;
    private Pane paneFond;

    public CadreTerre(int width, int height) {

        this.initialiseFormes(width, height);

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
        subScene = new SubScene(sphereTerre, width, height, true, SceneAntialiasing.BALANCED);
        paneFond = new Pane(subScene);

        subScene.setFill(Color.BLACK);
        sphereTerre.setTranslateZ(10);
    }



    @Override
    public Pane getPaneFond() {
        return paneFond;
    }
}
