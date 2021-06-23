package et3.projetjig.donnees;

import et3.jsonReader.JsonReader;
import et3.projetjig.donnees.types.Observation;
import et3.projetjig.donnees.types.Taxon;
import et3.projetjig.fenetre.ControllerFenetre;
import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Model {

  private final String adresse = "https://api.obis.org/v3/";
  private ControllerFenetre listener = null;

  public void getDonneesParDefaut() {}

  public void getOccurences(
    String nomEspece,
    short anneeDebut,
    short anneeFin
  ) {
    JSONArray o = JsonReader.readJsonArrayFromUrl(
      adresse + "taxon/complete/verbose/" + nomEspece
    );
    //   "&startdate=" +
    //   anneeDebut +
    //   "-01-01&enddate=" +
    //   anneeFin +
    //   "-01-01"
    if (o.length() == 0) {
      listener.recoitErreurEspece(nomEspece);
    } else if (o.length() == 1) {
      JSONObject res = o.getJSONObject(0);
      String scientificName = res.getString("scientificName");
      String rank = res.getString("rank");
      rank = rank == null ? "" : rank;
      int id = res.getInt("id");
      String phylum;
      try { phylum = res.getString("phylum"); } catch(JSONException e) { phylum = ""; }
      phylum = phylum == null ? "" : phylum;

      Taxon t = new Taxon(id, scientificName, rank, phylum);

      // TODO : Envoyer une OccurrencesPartition, avec donc les occurrences globales sur tout l'intervalle
      //  de temps, et sur les différents sous-intervalles de 5 ans

    } else {
      String[] result = new String[o.length()];
      for (int i = 0; i < o.length(); i++) {
        result[i] = o.getJSONObject(i).getString("scientificName");
      }

      listener.recoitEspecesParBDD(result);
    }
  }

  public void getObservations(GeoHash geoH) {
    String geoStr = geoH.toBase32();
    JSONObject o = JsonReader.readJsonObjectFromUrl(
      adresse + "occurence?geometry=" + geoStr
    );
    JSONArray a = o.getJSONArray("results");

    Observation[] obs = new Observation[a.length()];

    for (int i = 0; i < a.length(); i++) {
      JSONObject obj = a.getJSONObject(i);
      String nomScientifique = obj.getString("scientificName");
      nomScientifique = nomScientifique == null ? "" : nomScientifique;
      String nomEspece = obj.getString("species");
      nomEspece = nomEspece == null ? "" : nomEspece;
      String ordre = obj.getString("order");
      ordre = ordre == null ? "" : ordre;
      String superClasse = obj.getString("superclass");
      superClasse = superClasse == null ? "" : superClasse;
      String origineEnregistrement = obj.getString("recordedBy");
      origineEnregistrement =
        origineEnregistrement == null ? "" : origineEnregistrement;
      obs[i] =
        new Observation(
          nomScientifique,
          nomEspece,
          ordre,
          superClasse,
          origineEnregistrement
        );
    }

    listener.recoitObservationsParBDD(geoH, obs);
  }

  public void setListener(ControllerFenetre listener) {
    this.listener = listener;
  }

  public static void main(String[] args) {
    Model model = new Model();
    model.getOccurences("a", (short) 2002, (short) 2008);
  }
}
