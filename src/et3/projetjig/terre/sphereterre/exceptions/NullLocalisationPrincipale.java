package et3.projetjig.terre.sphereterre.exceptions;

import et3.projetjig.terre.sphereterre.SphereTerre;

public class NullLocalisationPrincipale extends Exception {

    public SphereTerre sphereTerre;

    public NullLocalisationPrincipale(SphereTerre sphereTerre) {
        super("Cette SphereTerre n'a pas de localisation principale.");
        this.sphereTerre = sphereTerre;
    }

}
