package et3.projetjig.fenetre.terre.sphereterre;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import et3.maths.CoordonneesConvert;
import et3.maths.Transformations3D;
import et3.projetjig.fenetre.terre.sphereterre.exceptions.NullLocalisationPrincipale;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import kungfoo.geohash.src.main.java.ch.hsr.geohash.BoundingBox;
import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;
import kungfoo.geohash.src.main.java.ch.hsr.geohash.WGS84Point;

import java.net.URL;


public class SphereTerre extends Group {

    public final static double PAS_CARRES = 10;
    public final static double CARRES_RAYON = 1.02;
    public final static double LOCALISATION_RAYON = 0.02;

    public final static double HIST_RAYON_CYLINDRE = 0.005;
    public final static double HIST_RAYON_TERRE = 1.0;
    public final static double HIST_HAUTEUR_MAX = 0.5;

    public final static double TEXTURE_LAT_OFFSET = 0.0;
    public final static double TEXTURE_LON_OFFSET = -2.8;


    private final Group carres = new Group();
    private final Group carresGeoHash = new Group();
    private final Group localisations = new Group();
    private final Group localisationPrincipale = new Group();
    private final Group histogramme = new Group();

    private Point2D locPrincipaleCoords2d = new Point2D(48.7093, 2.1710); // MDI
    private int nombreDeBitsGeoH = 10;


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


        // D'autres ??l??ments d'initialisation
        this.getChildren().add(carres);
        this.getChildren().add(carresGeoHash);
        this.getChildren().add(localisationPrincipale);
        this.getChildren().add(localisations);
        this.getChildren().add(histogramme);

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


    private void ajouteCarreANode(double latMin, double latMax, double lonMin, double lonMax,
                                  Material material, Group listeNode) {



        double latLocalMax, lonLocalMax;
        for(double lat=latMin; lat < latMax; lat += PAS_CARRES) {
            latLocalMax = Math.min(lat+PAS_CARRES, latMax);
            for(double lon=lonMin; lon < lonMax; lon += PAS_CARRES) {
                lonLocalMax = Math.min(lon+PAS_CARRES, lonMax);
                Group carreSimple = creerCarreRigide(lat, latLocalMax, lon, lonLocalMax, material);
                listeNode.getChildren().add(carreSimple);
            }
        }
    }


    public void ajouteCarre(double latMin, double latMax, double lonMin, double lonMax, Material material) {

        Group souscarres = new Group();
        this.carres.getChildren().add(souscarres);
        ajouteCarreANode(latMin, latMax, lonMin, lonMax, material, souscarres);
        carres.setVisible(true);
        souscarres.setVisible(true);
    }

    public void ajouterGeoHash(GeoHash geoHash, Color color) {
        PhongMaterial material = new PhongMaterial( color );
        BoundingBox box = geoHash.getBoundingBox();

        ajouteCarre(
                box.getSouthLatitude(), box.getNorthLatitude(),
                box.getWestLongitude(), box.getEastLongitude(),
                material
        );
    }


    public void supprimeCarres() {
        this.carres.getChildren().clear();
    }


    private Sphere creeLocalisation(double latitude, double longitude, Color color) {

        Point3D coord3d = CoordonneesConvert.geoCoordTo3dCoord(latitude, longitude,
                Point3D.ZERO, 1.0, TEXTURE_LAT_OFFSET, TEXTURE_LON_OFFSET);

        Sphere localisation = new Sphere(LOCALISATION_RAYON);
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

    public void setLocPrincipale(double lat, double lon) {

        deselectionnerLocPrincipale();
        Sphere localisation = creeLocalisation(lat, lon, Color.RED);
        localisationPrincipale.getChildren().add(localisation);
        locPrincipaleCoords2d = new Point2D(lat, lon);
    }

    public void setLocPrincipale(Point2D latEtLon) {
        setLocPrincipale(latEtLon.getX(), latEtLon.getY());
    }

    public Point2D getLocPrincipaleCoords2d() throws NullLocalisationPrincipale {
        if(locPrincipaleCoords2d == null) {
            throw new NullLocalisationPrincipale(this);
        }
        return locPrincipaleCoords2d;
    }

    public void deselectionnerLocPrincipale() {
        carresGeoHash.getChildren().clear();
        localisationPrincipale.getChildren().clear();
    }


    public int getNombreDeBits() {
        return nombreDeBitsGeoH;
    }


    private void majCarreGeoHash() {

        PhongMaterial material = new PhongMaterial( new Color(1.0, 0.2, 0.0, 0.1) );
        BoundingBox box = this.getGeoHash().getBoundingBox();

        carresGeoHash.getChildren().clear();
        ajouteCarreANode(
                box.getSouthLatitude(), box.getNorthLatitude(),
                box.getWestLongitude(), box.getEastLongitude(),
                material, carresGeoHash
        );
    }



    public void setGeoHash(GeoHash geoHash) {

                // Si le point actuellement s??lectionn?? n'est pas dans le geohash,
                // on l'y remet pour ??viter des probl??mes plus tard.
                try {
                    if(locPrincipaleCoords2d == null || !geoHash.contains(new WGS84Point(
                            getLocPrincipaleCoords2d().getX(),
                            getLocPrincipaleCoords2d().getY())
                    )) {
                        WGS84Point coord2d = geoHash.getBoundingBoxCenter();
                        setLocPrincipale(coord2d.getLatitude(), coord2d.getLongitude());
                    }
                } catch (NullLocalisationPrincipale ignored) {}

                // On actualise aussi la pr??cision du GeoHash
                nombreDeBitsGeoH = geoHash.significantBits();

                // On met ensuite le GeoHash ?? jour sur le globe
                majCarreGeoHash();

    }


    public void setPrecision(int precision) throws NullLocalisationPrincipale {
        Point2D coords = getLocPrincipaleCoords2d();
        setGeoHash(GeoHash.withBitPrecision(coords.getX(), coords.getY(), precision));
    }



    public GeoHash getGeoHash() {
        try {
            Point2D coords = getLocPrincipaleCoords2d();
            return GeoHash.withBitPrecision(coords.getX(), coords.getY(), getNombreDeBits());
        }
        catch(NullLocalisationPrincipale e) {
            return null;
        }

    }



    public void recoitHistogramme(Point2D coords2d, int hauteur, int maxHauteur, Color couleur) {

        Cylinder cylinder = new Cylinder(HIST_RAYON_CYLINDRE, HIST_HAUTEUR_MAX*hauteur/maxHauteur);
        cylinder.setMaterial(new PhongMaterial(couleur));
        cylinder.getTransforms().addAll(
                new Rotate(90+TEXTURE_LON_OFFSET-coords2d.getY(), 0, 0, 0, new Point3D(0,1,0)),
                new Rotate(90+TEXTURE_LAT_OFFSET+coords2d.getX(), 0, 0, 0, new Point3D(0,0,1)),
                new Translate(0,1,0)
        );

        histogramme.getChildren().add(cylinder);
    }



    public void supprimerHistogramme() {
        histogramme.getChildren().clear();
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
