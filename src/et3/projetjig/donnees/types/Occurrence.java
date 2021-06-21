package et3.projetjig.donnees.types;

import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;

public class Occurrence {

  private GeoHash geohash;
  private int occurrences;

  public Occurrence(GeoHash geohash, int occurrences) {
    this.geohash = geohash;
    this.occurrences = occurrences;
  }

  public GeoHash getGeohash() {
    return geohash;
  }

  public int getOccurrences() {
    return occurrences;
  }

}
