package et3.projetjig.donnees.types;

import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;

public class CarreGrid {


  public CarreGrid(GeoHash geohash, int taxonId, int occurences) {
    this.geohash = geohash;
    this.taxonId = taxonId;
    this.occurences = occurences;
  }

  GeoHash geohash;
  int taxonId;
  int occurences;
}
