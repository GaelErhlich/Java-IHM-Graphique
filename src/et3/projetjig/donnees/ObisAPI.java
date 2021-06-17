package et3.projetjig.donnees;

import et3.jsonReader.JsonReader;
import et3.projetjig.donnees.types.CarreGrid;
import et3.projetjig.donnees.types.Observation;
import et3.projetjig.donnees.types.Taxon;
import kungfoo.geohash.src.main.java.ch.hsr.geohash.BoundingBox;
import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;
import org.json.JSONArray;
import org.json.JSONObject;

public class ObisAPI implements DonneesInterface {

  private final String adresse = "https://api.obis.org/v3/";

  @Override
  public CarreGrid getOccurences(
    String nomEspece,
    short precision,
    GeoHash geohash
  ) {
    BoundingBox box = geohash.getBoundingBox();
    JSONObject obj = JsonReader.readJsonFromUrl(
      adresse + "occurrence/grid/" + precision + "?scientificname=" + nomEspece
    );
    JSONArray arr = obj.getJSONArray("features");
    for (int i = 0; i < arr.length(); i++) {
      JSONObject o = arr.getJSONObject(i);
      int n = o.getJSONObject("properties").getInt("n");
      JSONArray coords = o.getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(0);
      JSONArray nw = coords.getJSONArray(1);
      JSONArray se = coords.getJSONArray(3);
      if(box.getNorthWestCorner().getLatitude())
    }
    return null;
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
