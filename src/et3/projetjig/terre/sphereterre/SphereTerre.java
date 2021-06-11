package et3.projetjig.terre.sphereterre;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import et3.outils3d.CoordonneesConvert;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Material;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

import java.net.URL;
import java.util.ArrayList;

public class SphereTerre extends Group {


    Group carres = new Group();


    public SphereTerre() {


        // Importation de la Terre
        ObjModelImporter objImporter = new ObjModelImporter();
        try {
            URL modelURL = this.getClass().getResource("earth.obj");
            objImporter.read(modelURL);
        } catch (ImportException e) {
            e.printStackTrace();
        }
        MeshView[] meshViews = objImporter.getImport();

        this.getChildren().addAll(meshViews);


        // D'autres éléments d'initialisation
        this.getChildren().add(carres);


    }






    public void ajouteCarre(double latMin, double latMax, double lonMin, double lonMax, Material material) {


        Point3D topRight = CoordonneesConvert.geoCoordTo3dCoord( (float) latMin, (float) lonMax,
                2.0f, 0.0f, -2.8f);
        Point3D bottomRight = CoordonneesConvert.geoCoordTo3dCoord( (float)latMax, (float)lonMax,
                2.0f, 0.0f, -2.8f);
        Point3D bottomLeft = CoordonneesConvert.geoCoordTo3dCoord( (float)latMax, (float)lonMin,
                2.0f, 0.0f, -2.8f);
        Point3D topLeft = CoordonneesConvert.geoCoordTo3dCoord( (float)latMin, (float)lonMin,
                2.0f, 0.0f, -2.8f);


        final float[] points = {
                (float)topRight.getX(), (float)topRight.getY(), (float)topRight.getZ(),
                (float)bottomRight.getX(), (float)bottomRight.getY(), (float)bottomRight.getZ(),
                (float)bottomLeft.getX(), (float)bottomLeft.getY(), (float)bottomLeft.getZ(),
                (float)topLeft.getX(), (float)topLeft.getY(), (float)topLeft.getZ(),
        };
        final float[] texCoords = {
                1,1,
                1,0,
                0,1,
                0,0
        };
        final int[] faces = {
                0, 1, 1, 0, 2, 2,
                0, 1, 2, 2, 3, 3
        };


        final TriangleMesh triangleMesh = new TriangleMesh();
        triangleMesh.getPoints().setAll(points);
        triangleMesh.getTexCoords().setAll(texCoords);
        triangleMesh.getFaces().setAll(faces);

        final MeshView meshView = new MeshView(triangleMesh);

        meshView.setMaterial(material);
        Group meshGroup = new Group(meshView);
        this.carres.getChildren().add(meshGroup);

        System.out.println("Carré posé");
    }



}
