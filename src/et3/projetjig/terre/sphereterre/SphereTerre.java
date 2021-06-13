package et3.projetjig.terre.sphereterre;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import et3.maths.CoordonneesConvert;
import et3.projetjig.terre.sphereterre.exceptions.NullLocalisationPrincipale;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.Paint;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Line;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;

import java.net.URL;


public class SphereTerre extends Group {

    public final static double PAS_CARRES = 10;
    public final static double CARRES_RAYON = 1.2;

    public final static double TEXTURE_LAT_OFFSET = 0.0;
    public final static double TEXTURE_LON_OFFSET = -2.8;


    Group carres = new Group();
    Group localisations = new Group();
    Group localisationPrincipale = new Group();
    Point2D locPrincipaleCoords2d = null;


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
        this.getChildren().add(localisationPrincipale);
        this.getChildren().add(localisations);

        ajouteLocalisation( 48.447911f, -4.418519f );
        //afficherAxesDEBUG();


    }



    private Group creerCarreRigide(double latMin, double latMax, double lonMin, double lonMax, Material material) {

        Point3D topRight = CoordonneesConvert.geoCoordTo3dCoord((float) latMin, (float) lonMax,
                Point3D.ZERO, CARRES_RAYON, TEXTURE_LAT_OFFSET, TEXTURE_LON_OFFSET);
        Point3D bottomRight = CoordonneesConvert.geoCoordTo3dCoord((float) latMax, (float) lonMax,
                Point3D.ZERO, CARRES_RAYON, TEXTURE_LAT_OFFSET, TEXTURE_LON_OFFSET);
        Point3D bottomLeft = CoordonneesConvert.geoCoordTo3dCoord((float) latMax, (float) lonMin,
                Point3D.ZERO, CARRES_RAYON, TEXTURE_LAT_OFFSET, TEXTURE_LON_OFFSET);
        Point3D topLeft = CoordonneesConvert.geoCoordTo3dCoord((float) latMin, (float) lonMin,
                Point3D.ZERO, CARRES_RAYON, TEXTURE_LAT_OFFSET, TEXTURE_LON_OFFSET);


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


    private Sphere creeLocalisation(double latitude, double longitude, Color color) {

        Point3D coord3d = CoordonneesConvert.geoCoordTo3dCoord(latitude, longitude,
                Point3D.ZERO, 1.0, TEXTURE_LAT_OFFSET, TEXTURE_LON_OFFSET);

        Sphere localisation = new Sphere(0.01);
        PhongMaterial material = new PhongMaterial(color);
        localisation.setMaterial(material);

        localisation.setTranslateX(coord3d.getX());
        localisation.setTranslateY(coord3d.getY());
        localisation.setTranslateZ(coord3d.getZ());

        return localisation;
    }

    public void ajouteLocalisation(double lat, double lon) {
        Sphere localisation = creeLocalisation(lat, lon, Color.YELLOW);
        localisations.getChildren().add(localisation);
    }

    public void ajouteLocalisation(Point2D latEtLon) {
        ajouteLocalisation(latEtLon.getX(), latEtLon.getY());
    }

    public void supprimerLocalisations() {
        localisations.getChildren().clear();
    }

    public void definirLocPrincipale(double lat, double lon) {
        deselectionnerLocPrincipale();
        Sphere localisation = creeLocalisation(lat, lon, Color.RED);
        localisationPrincipale.getChildren().add(localisation);
        locPrincipaleCoords2d = new Point2D(lat, lon);
    }

    public void definirLocPrincipale(Point2D latEtLon) {
        definirLocPrincipale(latEtLon.getX(), latEtLon.getY());
    }

    public Point2D getLocPrincipaleCoords2d() throws NullLocalisationPrincipale {
        if(locPrincipaleCoords2d == null) {
            throw new NullLocalisationPrincipale(this);
        }
        return locPrincipaleCoords2d;
    }

    public void deselectionnerLocPrincipale() {
        localisationPrincipale.getChildren().clear();
    }




    private void afficherAxesDEBUG() {
        Line ligneRouge = new Line(0, 0, 4, 0);
        ligneRouge.setStroke(Color.RED);
        Line ligneVerte = new Line(0, 0, 0, 4);
        ligneVerte.setStroke(Color.GREEN);
        Line ligneBleue = new Line(0, 0, 4, 0);
        ligneBleue.setStroke(Color.BLUE);
        ligneBleue.setRotationAxis(new Point3D(0, 1, 0));
        ligneBleue.setRotate(90);
        ligneBleue.setTranslateX(-2);
        ligneBleue.setTranslateZ(2);

        this.getChildren().add( ligneRouge );
        this.getChildren().add( ligneVerte );
        this.getChildren().add( ligneBleue );
    }



}
