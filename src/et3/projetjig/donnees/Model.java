package et3.projetjig.donnees;

import et3.jsonReader.JsonReader;
import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;
import org.json.JSONArray;
import org.json.JSONObject;

public class Model {

  private final String adresse = "https://api.obis.org/v3/";

  public void getDonneesParDefaut() {}

  public void getOccurences(
    String nomEspece,
    short anneeDebut,
    short anneeFin
  ) {
    JSONObject o = JsonReader.readJsonFromUrl(
      adresse +
      "occurence/?scientificname=" +
      nomEspece +
      "&startdate=" +
      anneeDebut +
      "-01-01&enddate=" +
      anneeFin +
      "-01-01"
    );

    JSONArray results = o.getJSONArray("results");
    if (results.length() == 0) {
      if (o.has("error")) {
        //traiter le cas
      }
    } else if (results.length() == 1) {} else {}
  }

  public void getObservations(GeoHash geoH) {
    String geoStr = geoH.toBase32();
    JSONObject o = JsonReader.readJsonFromUrl(adresse + "occurence/");
  }
}
