package et3.outils3d;

import javafx.geometry.Point3D;

public class CoordonneesConvert {

    private final static float TEXTURE_LAT_OFFSET = 0.0f;
    private final static float TEXTURE_LON_OFFSET = -2.8f;


    public static Point3D geoCoordTo3dCoord(float lat, float lon, float radius, float TEXTURE_LAT_OFFSET, float TEXTURE_LON_OFFSET) {
        double lat_coord = lat - TEXTURE_LAT_OFFSET;
        double lon_coord = lon - TEXTURE_LON_OFFSET;
        return new Point3D(
            - Math.sin( Math.toRadians(lon_coord) )
                    * Math.cos( Math.toRadians(lat_coord) ) * radius,
            -Math.sin( Math.toRadians(lat_coord) ) * radius,
            Math.cos( Math.toRadians(lon_coord) )
                    * Math.cos( Math.toRadians(lat_coord) ) * radius
        );
    }

}
