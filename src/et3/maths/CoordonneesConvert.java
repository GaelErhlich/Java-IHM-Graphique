package et3.maths;

import javafx.geometry.Point3D;

public class CoordonneesConvert {


    public static Point3D geoCoordTo3dCoord(double lat, double lon, double radius, double TEXTURE_LAT_OFFSET, double TEXTURE_LON_OFFSET) {
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
