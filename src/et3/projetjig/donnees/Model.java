package et3.projetjig.donnees;

import et3.jsonReader.JsonReader;
import et3.projetjig.donnees.types.Observation;
import et3.projetjig.donnees.types.Occurrence;
import et3.projetjig.donnees.types.Occurrences;
import et3.projetjig.donnees.types.OccurrencesPartition;
import et3.projetjig.donnees.types.Taxon;
import et3.projetjig.donnees.types.exceptions.AuMoins1InteveralleException;
import et3.projetjig.fenetre.ControllerFenetre;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import kungfoo.geohash.src.main.java.ch.hsr.geohash.GeoHash;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Model {

  private final String adresse = "https://api.obis.org/v3/";
  private ControllerFenetre listener = null;

  /**
   * Fait charger les données par défaut et les envoyer à l'interface
   */
  public void getDonneesParDefaut() {
    JSONObject obj = JsonReader.readFromFile(
            "src/et3/projetjig/donnees/ressources.json"
    );

    JSONObject res = obj.getJSONObject("taxon");

    String scientificName;
    try {
      scientificName = res.getString("scientificName");
    } catch (JSONException e) {
      scientificName = "";
    }
    String rank;
    try {
      rank = res.getString("rank");
    } catch (JSONException e) {
      rank = "";
    }

    int id;
    try {
      id = res.getInt("id");
    } catch (JSONException e) {
      id = 0;
    }
    String phylum;
    try {
      phylum = res.getString("phylum");
    } catch (JSONException e) {
      phylum = "";
    }

    Taxon t = new Taxon(id, scientificName, rank, phylum);

    JSONArray aOccGlob = obj.getJSONArray("features");
    Occurrence[] occGlob = new Occurrence[aOccGlob.length()];
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < aOccGlob.length(); i++) {
      JSONObject o = aOccGlob.getJSONObject(i);
      int n = o.getJSONObject("properties").getInt("n");
      if (n < min) {
        min = n;
      }
      if (n > max) {
        max = n;
      }
      JSONArray coords = o
              .getJSONObject("geometry")
              .getJSONArray("coordinates")
              .getJSONArray(0);
      JSONArray nw = coords.getJSONArray(1);
      JSONArray se = coords.getJSONArray(3);

      GeoHash geohash = GeoHash.withCharacterPrecision(
              (nw.getDouble(1) + se.getDouble(1)) / 2,
              (nw.getDouble(0) + se.getDouble(0)) / 2,
              8
      );

      occGlob[i] = new Occurrence(geohash, n);
    }

    Occurrences occGlobal = new Occurrences(
            t,
            occGlob,
            min,
            max,
            (short) 2000,
            (short) 2020
    );

    Occurrences[] occs = { occGlobal };

    try {
      listener.recoitOccurrencesParBDD(
              new OccurrencesPartition(t, occs, occGlobal, min, max)
      );
    } catch (AuMoins1InteveralleException e) {
      e.printStackTrace();
    }
  }

  /**
   * Charge une OccurrencesPartition pour l'envoyer à l'interface
   * @param nomEspece Nom (optionnel) de l'espèce à filtrer
   * @param anneeDebut Année du début du grand intervalle
   * @param anneeFin Année de fin du grand intervalle
   * @param precision Précision de Geohash demandé
   */
  public void getOccurences(
    String nomEspece,
    short anneeDebut,
    short anneeFin,
    int precision
  ) {
    try {
      URI uri;
      String param;
      try {
        param = URLEncoder.encode(nomEspece, "UTF-8");
        param = param.replace("+", "%20");
      } catch (UnsupportedEncodingException e) {
        listener.recoitErreurEspece(nomEspece);
        return;
      }

      uri = new URI(adresse + "taxon/complete/verbose/" + param);
      JSONArray o = JsonReader.readJsonArrayFromUrl(uri.toString());

      if (o.length() == 0) {
        listener.recoitErreurEspece(nomEspece);
      } else if (o.length() == 1) {
        JSONObject res = o.getJSONObject(0);

        String scientificName;
        try {
          scientificName = res.getString("scientificName");
        } catch (JSONException e) {
          scientificName = "";
        }
        String rank;
        try {
          rank = res.getString("rank");
        } catch (JSONException e) {
          rank = "";
        }

        int id;
        try {
          id = res.getInt("id");
        } catch (JSONException e) {
          id = 0;
        }
        String phylum;
        try {
          phylum = res.getString("phylum");
        } catch (JSONException e) {
          phylum = "";
        }

        Taxon t = new Taxon(id, scientificName, rank, phylum);
        String scientificNameParam;
        scientificNameParam = URLEncoder.encode(nomEspece, "UTF-8");
        scientificNameParam = param.replace("+", "%20");
        URI uri2 = new URI(
          adresse +
          "occurrence/grid/"+
          precision+"?scientificname=" +
          scientificNameParam +
          "&startdate=" +
          anneeDebut +
          "-01-01&enddate=" +
          anneeFin +
          "-12-31"
        );

        JSONObject oOccGlob = JsonReader.readJsonObjectFromUrl(uri2.toString());
        JSONArray aOccGlob = oOccGlob.getJSONArray("features");
        Occurrence[] occGlob = new Occurrence[aOccGlob.length()];
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < aOccGlob.length(); i++) {
          JSONObject obj = aOccGlob.getJSONObject(i);
          int n = obj.getJSONObject("properties").getInt("n");
          if (n < min) {
            min = n;
          }
          if (n > max) {
            max = n;
          }
          JSONArray coords = obj
            .getJSONObject("geometry")
            .getJSONArray("coordinates")
            .getJSONArray(0);
          JSONArray nw = coords.getJSONArray(1);
          JSONArray se = coords.getJSONArray(3);

          GeoHash geohash = GeoHash.withCharacterPrecision(
            (nw.getDouble(1) + se.getDouble(1)) / 2,
            (nw.getDouble(0) + se.getDouble(0)) / 2,
            precision
          );

          occGlob[i] = new Occurrence(geohash, n);
        }

        if(min == Integer.MAX_VALUE) {
          min = 0;
          max = 0;
        }

        Occurrences occGlobal = new Occurrences(
          t,
          occGlob,
          min,
          max,
          anneeDebut,
          anneeFin
        );

        Occurrences[] occurrences = new Occurrences[(anneeFin - anneeDebut)/5 +1];


        int minAllInterv = Integer.MAX_VALUE;
        int maxAllInterv = Integer.MIN_VALUE;

        int anneeFinLocale;
        for (int j = anneeDebut; j <= anneeFin; j += 5) {
          anneeFinLocale = (j + 4 <= anneeFin ? j+4 : anneeFin);

          URI uri3 = new URI(
            adresse +
            "occurrence/grid/"+
            precision+"?scientificname=" +
            scientificNameParam +
            "&startdate=" +
            j +
            "-01-01&enddate=" +
            anneeFinLocale +
            "-12-31"
          );

          JSONObject oOcc = JsonReader.readJsonObjectFromUrl(uri3.toString());
          JSONArray aOcc = oOcc.getJSONArray("features");
          Occurrence[] occ = new Occurrence[aOcc.length()];
          int min1 = Integer.MAX_VALUE;
          int max1 = Integer.MIN_VALUE;
          for (int i = 0; i < aOcc.length(); i++) {
            JSONObject obj = aOcc.getJSONObject(i);
            int n = obj.getJSONObject("properties").getInt("n");
            if (n < min1) {
              min1 = n;
            }
            if (n > max1) {
              max1 = n;
            }
            JSONArray coords = obj
              .getJSONObject("geometry")
              .getJSONArray("coordinates")
              .getJSONArray(0);
            JSONArray nw = coords.getJSONArray(1);
            JSONArray se = coords.getJSONArray(3);
            GeoHash geohash = GeoHash.withCharacterPrecision(
              (nw.getDouble(1) + se.getDouble(1)) / 2,
              (nw.getDouble(0) + se.getDouble(0)) / 2,
              precision
            );

            occ[i] = new Occurrence(geohash, n);
          }

          if(min1 == Integer.MAX_VALUE) {
            min1 = 0;
            max1 = 0;
          }

          occurrences[(j-anneeDebut)/5] =
            new Occurrences(
              t,
              occ,
              min1,
              max1,
              (short) j,
              (short) anneeFinLocale
            );

          if(min1 < minAllInterv) {
            minAllInterv = min;
          }
          if(max1 > maxAllInterv) {
            maxAllInterv = max;
          }

        }

        if(minAllInterv == Integer.MAX_VALUE) {
          minAllInterv = 0;
          maxAllInterv = 0;
        }
        try {
          listener.recoitOccurrencesParBDD(
            new OccurrencesPartition(t, occurrences, occGlobal, minAllInterv, maxAllInterv)
          );
        } catch (AuMoins1InteveralleException e) { e.printStackTrace(); }
      } else {
        String[] result = new String[o.length()];
        for (int i = 0; i < o.length(); i++) {
          result[i] = o.getJSONObject(i).getString("scientificName");
          if (result[i].equalsIgnoreCase( nomEspece)) {
            JSONObject res = o.getJSONObject(i);

            String scientificName;
            try {
              scientificName = res.getString("scientificName");
            } catch (JSONException e) {
              scientificName = "";
            }
            String rank;
            try {
              rank = res.getString("rank");
            } catch (JSONException e) {
              rank = "";
            }

            int id;
            try {
              id = res.getInt("id");
            } catch (JSONException e) {
              id = 0;
            }
            String phylum;
            try {
              phylum = res.getString("phylum");
            } catch (JSONException e) {
              phylum = "";
            }

            Taxon t = new Taxon(id, scientificName, rank, phylum);
          }
        }

        listener.recoitEspecesParBDD(result);
      }
    } catch (URISyntaxException | UnsupportedEncodingException e1) {
      listener.recoitErreurEspece(nomEspece);
    }
  }

  /**
   * Fait envoyer des Observation à l'interface
   * @param geoH le GeoHash dont on veut les Observations
   * @param nom type/espèce à filtrer dans la recherche
   */
  public void getObservations(GeoHash geoH, String nom) {
    String geoStr = geoH.toBase32();
    URI uri;
    try {

      String nomScienti;
      if(!nom.equals("")) {
        nomScienti = URLEncoder.encode(nom, "UTF-8");
        nomScienti = nomScienti.replace("+", "%20");
        nomScienti = "scientificname="+nomScienti+"&";
      }
      else {
        nomScienti = "";
      }

      uri = new URI(adresse
              +"occurrence?"
              +nomScienti
              +"geometry=" + geoStr
      );

      JSONObject o = JsonReader.readJsonObjectFromUrl(uri.toString());
      JSONArray a = o.getJSONArray("results");

      Observation[] obs = new Observation[a.length()];

      for (int i = 0; i < a.length(); i++) {
        JSONObject obj = a.getJSONObject(i);
        String nomScientifique;
        try {
          nomScientifique = obj.getString("scientificName");
        } catch (JSONException e) {
          nomScientifique = "";
        }
        String nomEspece;
        try {
          nomEspece = obj.getString("species");
        } catch (JSONException e) {
          nomEspece = "";
        }
        String ordre;
        try {
          ordre = obj.getString("order");
        } catch (JSONException e) {
          ordre = "";
        }

        String superClasse;
        try {
          superClasse = obj.getString("superclass");
        } catch (JSONException e) {
          superClasse = "";
        }

        String origineEnregistrement;
        try {
          origineEnregistrement = obj.getString("recordedBy");
        } catch (JSONException e) {
          origineEnregistrement = "";
        }

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
    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      listener.recoitErreurEspece(nom);
    }
  }

  /**
   * Définit la fenêtre qui écoute ce Model
   * @param listener la fenêtre
   */
  public void setListener(ControllerFenetre listener) {
    this.listener = listener;
  }

  public static void main(String[] args) {
    Model model = new Model();
    model.getOccurences("a", (short) 2002, (short) 2008, 1);
  }
}
