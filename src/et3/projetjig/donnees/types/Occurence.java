package et3.projetjig.donnees.types;

import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;

public class Occurence {

  public Occurence(GeoHash geohash, int taxonId, int occurences) {
    this.geohash = geohash;
    this.occurences = occurences;
  }

  private GeoHash geohash;

  public GeoHash getGeohash() {
    return geohash;
  }

  public int getOccurences() {
    return occurences;
  }

  private int occurences;
}
