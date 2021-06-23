package et3.projetjig.fenetre.terre.sphereterre.exceptions;

import et3.projetjig.fenetre.terre.sphereterre.SphereTerre;

/**
 * Exception levée lorsqu'on essaye d'obtenir la localisation principale du globe lorsque celle-ci
 * n'est pas définie
 */
public class NullLocalisationPrincipale extends Exception {

    public SphereTerre sphereTerre;

    public NullLocalisationPrincipale(SphereTerre sphereTerre) {
        super("Cette SphereTerre n'a pas de localisation principale.");
        this.sphereTerre = sphereTerre;
    }

}
