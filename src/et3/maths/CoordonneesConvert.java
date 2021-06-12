package et3.maths;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

public class CoordonneesConvert {


    /**
     * Convertit des coordonnées géographiques (latitude et longitude) en coordonnées 3D autour d'une sphère centrée en (0,0,0)
     * @param lat Latitude en coordonnées géographiques
     * @param lon Longitude en coordonnées géographiques
     * @param centre Coordonnées dans l'espace du centre de la sphère
     * @param radius Distance du centre à laquelle doit se trouver le Point3D
     * @param TEXTURE_LAT_OFFSET Décalage entre la latitude 0 de la texture et l'axe Z
     * @param TEXTURE_LON_OFFSET Décalage entre la longitude 0 de la texture et l'axe Z
     * @return un Point3D représentant la position du point (en considérant le centre de la sphère en (0,0,0))
     */
    public static Point3D geoCoordTo3dCoord(double lat, double lon, Point3D centre, double radius, double TEXTURE_LAT_OFFSET, double TEXTURE_LON_OFFSET) {
        double lat_coord = lat - TEXTURE_LAT_OFFSET;
        double lon_coord = lon - TEXTURE_LON_OFFSET;
        return new Point3D(
            - Math.sin( Math.toRadians(lon_coord) )
                    * Math.cos( Math.toRadians(lat_coord) ) * radius + centre.getX(),
            -Math.sin( Math.toRadians(lat_coord) ) * radius + centre.getY(),
            Math.cos( Math.toRadians(lon_coord) )
                    * Math.cos( Math.toRadians(lat_coord) ) * radius + centre.getZ()
        );
    }


    /**
     * Convertit des coordonnées dans l'espace 3D sur (ou autour de) une sphère en latitude et longitude
     * @param coord3d Coordonnées dans l'espace du point recherché (pas forcément à la surface de la sphère)
     * @param centre Coordonnées dans l'espace du centre de la sphère
     * @param TEXTURE_LAT_OFFSET Décalage entre la latitude 0 de la texture et l'axe Z
     * @param TEXTURE_LON_OFFSET Décalage entre la longitude 0 de la texture et l'axe Z
     * @return un point 2D (x=latitude, y=longitude)
     */
    public static Point2D coord3dToGeoCoord(Point3D coord3d, Point3D centre,
                                            double TEXTURE_LAT_OFFSET, double TEXTURE_LON_OFFSET) {

        // On positionne le centre de la sphère (virtuelle) en (0,0,0)
        coord3d = new Point3D(
                coord3d.getX()-centre.getX(),
                coord3d.getY()-centre.getY(),
                coord3d.getZ()-centre.getZ()
        ).normalize();


        // Calcul de la longitude
        Point2D projZXNorm = new Point2D(coord3d.getZ(), coord3d.getX()).normalize();
        double longitude = (projZXNorm.getY() > 0 ? -1:1) * Math.acos( projZXNorm.getX() );


        // Calcul de la latitude
            // 1) On fait tourner la position autour de l'axe Y pour le mettre dans le plan XY
        double angle = Math.toRadians(
                (coord3d.getZ() > 0 ? 1:-1) * new Point2D(coord3d.getX(), coord3d.getZ()).angle(1.0, 0.0));
        Point2D projXYNorm = new Point2D(
                Math.cos(angle)*coord3d.getX() + Math.sin(angle)*coord3d.getZ(),
                coord3d.getY()
        );
            // 2) Maintenant qu'on sait dans quel plan est le vecteur, on peut appliquer la
            //      trigonométrie classique (comme pour la longitude)
        projXYNorm = projXYNorm.normalize();
        double latitude = (projXYNorm.getY() > 0 ? -1:1) * Math.acos(Math.abs( projXYNorm.getX() ));


        // On met en dégrés et on corrige les décalages de la sphère de l'espace 3D
        latitude = Math.toDegrees(latitude) + TEXTURE_LAT_OFFSET;
        longitude = Math.toDegrees(longitude) + TEXTURE_LON_OFFSET;

        latitude = ((latitude + 90.0) % 180.0 ) - 90.0;
        longitude = ((longitude + 180.0) % 360.0) - 180.0;


        return new Point2D(latitude, longitude);

    }

}
