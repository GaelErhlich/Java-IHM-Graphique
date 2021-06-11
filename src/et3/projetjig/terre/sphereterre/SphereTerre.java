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


public class SphereTerre extends Group {

    public final static double PAS_CARRES = 10;
    public final static double CARRES_RAYON = 1.2;


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



    private Group creerCarreRigide(double latMin, double latMax, double lonMin, double lonMax, Material material) {

        Point3D topRight = CoordonneesConvert.geoCoordTo3dCoord((float) latMin, (float) lonMax,
                CARRES_RAYON, 0.0f, -2.8f);
        Point3D bottomRight = CoordonneesConvert.geoCoordTo3dCoord((float) latMax, (float) lonMax,
                CARRES_RAYON, 0.0f, -2.8f);
        Point3D bottomLeft = CoordonneesConvert.geoCoordTo3dCoord((float) latMax, (float) lonMin,
                CARRES_RAYON, 0.0f, -2.8f);
        Point3D topLeft = CoordonneesConvert.geoCoordTo3dCoord((float) latMin, (float) lonMin,
                CARRES_RAYON, 0.0f, -2.8f);


        final float[] points = {
                (float) topRight.getX(), (float) topRight.getY(), (float) topRight.getZ(),
                (float) bottomRight.getX(), (float) bottomRight.getY(), (float) bottomRight.getZ(),
                (float) bottomLeft.getX(), (float) bottomLeft.getY(), (float) bottomLeft.getZ(),
                (float) topLeft.getX(), (float) topLeft.getY(), (float) topLeft.getZ(),
        };
        final float[] texCoords = {
                1, 1,
                1, 0,
                0, 1,
                0, 0
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
        return new Group(meshView);
    }


    public void ajouteCarre(double latMin, double latMax, double lonMin, double lonMax, Material material) {

        Group souscarres = new Group();

        double latLocalMax, lonLocalMax;
        for(double lat=latMin; lat < latMax; lat += PAS_CARRES) {
            latLocalMax = Math.min(lat+PAS_CARRES, latMax);
            for(double lon=lonMin; lon < lonMax; lon += PAS_CARRES) {
                lonLocalMax = Math.min(lon+PAS_CARRES, lonMax);

                souscarres.getChildren().add(
                        creerCarreRigide(lat, latLocalMax, lon, lonLocalMax, material)
                );

            }
        }

        this.carres.getChildren().add(souscarres);
    }


    public void supprimeCarres() {
        this.carres.getChildren().clear();
    }



}
