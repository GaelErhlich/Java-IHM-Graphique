package et3.maths;

public class GeoHash {
/*



    private static final int MAX_BIT_PRECISION = 64;
    private static final int MAX_GEOHASH_CHARACTER_PRECISION = 12;
    private static final int[] BITS = { 16, 8, 4, 2, 1 };
    private static final int BASE32_BITS = 5;

    public static String getGeohash(double latitude, double longitude, int numberOfCharacters) {
        int desiredPrecision = getDesiredPrecsion(numberOfCharacters);
        GeoHash geohash = new GeoHash();

        boolean isEvenBit = true;
        double[] latitudeRange = { -90, 90 };
        double[] longitudeRange = { -180, 180 };

        while (geohash.getSignificantBits() < desiredPrecision) {
            if (isEvenBit) {
                divideRangeEncode(geohash, longitudeRange, longitude);
            } else {
                divideRangeEncode(geohash, latitudeRange, latitude);
            }
            isEvenBit = !isEvenBit;
        }

        geohash.leftShift(MAX_BIT_PRECISION - desiredPrecision);
        return geohash.toBase32();
    }

    private static int getDesiredPrecsion(int numberOfCharacters) {
        if (numberOfCharacters > MAX_GEOHASH_CHARACTER_PRECISION) {
            throw new IllegalArgumentException("A geohash can only be " + MAX_GEOHASH_CHARACTER_PRECISION + " character long.");
        }
        int desiredPrecision = 60;
        if (numberOfCharacters * 5 <= 60) {
            desiredPrecision = numberOfCharacters * 5;
        }
        return desiredPrecision = Math.min(desiredPrecision, MAX_BIT_PRECISION);
    }

    private static void divideRangeEncode(GeoHash geohash, double[] range, double value) {
        double mid = (range[0] + range[1]) / 2;
        if (value >= mid) {
            geohash.addOnBitToEnd();
            range[0] = mid;
        } else {
            geohash.addOffBitToEnd();
            range[1] = mid;
        }
    }

    public String toBase32() {
        if (significantBits % 5 != 0) {
            throw new IllegalStateException("Cannot convert a geohash to base32 if the precision is not a multiple of 5.");
        }
        StringBuilder buf = new StringBuilder();
        long firstFiveBitsMask = 0xf800000000000000l;
        int numberOfCharacters = (int) Math.ceil(((double) significantBits / 5));
        for (int i = 0; i < numberOfCharacters; i++) {
            int pointer = (int) ((bits & firstFiveBitsMask) >>> 59);
            buf.append(Constants.BASE32[pointer]);
            bits <<= 5;
        }
        return buf.toString();
    }
// */
}
