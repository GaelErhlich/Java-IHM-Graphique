package et3.projetjig.donnees.types;

import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;

public class Occurrence {

  private GeoHash geohash;
  private int nbOccurrences;

  public Occurrence(GeoHash geohash, int nbOccurrences) {
    this.geohash = geohash;
    this.nbOccurrences = nbOccurrences;
  }

  public GeoHash getGeohash() {
    return geohash;
  }

  public int getNombreOccu() {
    return nbOccurrences;
  }

}
