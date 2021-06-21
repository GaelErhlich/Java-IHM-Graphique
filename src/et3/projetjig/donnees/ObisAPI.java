package et3.projetjig.donnees;

import et3.jsonReader.JsonReader;
import et3.projetjig.donnees.types.CarreGrid;
import et3.projetjig.donnees.types.Observation;
import et3.projetjig.donnees.types.Taxon;
import java.util.ArrayList;
import kungfoo.geohash.src.main.java.ch.hsr.geohash.BoundingBox;
import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;
import org.json.JSONArray;
import org.json.JSONObject;

public class ObisAPI implements DonneesInterface {

  private final String adresse = "https://api.obis.org/v3/";
  private final double[] lngErr = {
    23,
    5.6,
    0.70,
    0.18,
    0.022,
    0.0055,
    0.00068,
    0.00017,
  };
  private final double[] latErr = {
    23,
    2.8,
    0.70,
    0.087,
    0.022,
    0.0027,
    0.00068,
    0.000085,
  };

  @Override
  public CarreGrid getOccurencesGeohash(
    String nomEspece,
    short precision,
    GeoHash geohash
  ) {
    BoundingBox box = geohash.getBoundingBox();
    JSONObject obj = JsonReader.readJsonFromUrl(
      adresse + "occurrence/grid/" + precision + "?scientificname=" + nomEspece
    );
    JSONObject taxonJson = JsonReader.readJsonFromUrl(
      adresse + "taxon/" + nomEspece
    );
    int taxonID = taxonJson
      .getJSONArray("results")
      .getJSONObject(0)
      .getInt("taxonID");
    JSONArray arr = obj.getJSONArray("features");
    for (int i = 0; i < arr.length(); i++) {
      JSONObject o = arr.getJSONObject(i);
      int n = o.getJSONObject("properties").getInt("n");
      JSONArray coords = o
        .getJSONObject("geometry")
        .getJSONArray("coordinates")
        .getJSONArray(0);
      JSONArray nw = coords.getJSONArray(1);
      JSONArray se = coords.getJSONArray(3);
      if (
        Math.abs(box.getNorthWestCorner().getLatitude() - nw.getDouble(0)) <
        latErr[precision - 1] &&
        Math.abs(box.getNorthWestCorner().getLongitude() - nw.getDouble(1)) <
        lngErr[precision - 1] &&
        Math.abs(box.getSouthEastCorner().getLatitude() - se.getDouble(0)) <
        latErr[precision - 1] &&
        Math.abs(box.getSouthEastCorner().getLongitude() - se.getDouble(1)) <
        lngErr[precision - 1]
      ) {
        return new CarreGrid(geohash, taxonID, n);
      }
    }
    return null;
  }

  @Override
  public CarreGrid[] getOccurences(String nomEspece, short precision) {
    JSONObject obj = JsonReader.readJsonFromUrl(
      adresse + "occurrence/grid/" + precision + "?scientificname=" + nomEspece
    );
    JSONObject taxonJson = JsonReader.readJsonFromUrl(
      adresse + "taxon/" + nomEspece
    );
    int taxonID = taxonJson
      .getJSONArray("results")
      .getJSONObject(0)
      .getInt("taxonID");
    JSONArray arr = obj.getJSONArray("features");
    CarreGrid[] result = new CarreGrid[arr.length()];

    for (int i = 0; i < arr.length(); i++) {
      JSONObject o = arr.getJSONObject(i);
      int n = o.getJSONObject("properties").getInt("n");
      JSONArray coords = o
        .getJSONObject("geometry")
        .getJSONArray("coordinates")
        .getJSONArray(0);
      JSONArray nw = coords.getJSONArray(1);
      JSONArray se = coords.getJSONArray(3);
      GeoHash geohash = GeoHash.withCharacterPrecision(
        (nw.getDouble(0) + se.getDouble(0)) / 2,
        (nw.getDouble(1) + se.getDouble(1)) / 2,
        precision
      );
      result[i] = new CarreGrid(geohash, taxonID, n);
    }

    return result;
  }

  @Override
  public Observation[] getObservations(
    String geohash,
    long tempsDebut,
    long dt,
    int N,
    String nomEspece
  ) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Taxon[] getTaxons(String debutNom) {
    // TODO Auto-generated method stub
    return null;
  }
}
