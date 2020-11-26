
package towa;

enum Direction {

    NORD,
    SUD,
    EST,
    OUEST,
    NORD_EST,
    NORD_OUEST,
    SUD_EST,
    SUD_OUEST;

    /**
     * Renvoie toutes les directions dans un tableau.
     *
     * @return tableau contenant toutes les directions.
     */
    static Direction[] toutes() {
        Direction[] directions
                = {NORD, SUD, EST, OUEST, NORD_EST, NORD_OUEST, SUD_EST, SUD_OUEST};
        return directions;
    }

    /**
     * Renvoie les quatre directions cardinales dans un tableau.
     *
     * @return tableau contenant les quatre directions cardinales.
     */
    static Direction[] cardinales() {
        Direction[] dir = {NORD, SUD, EST, OUEST};
        return dir;
    }
    /**
     * Renvoie les quatre directions diagonales dans un tableau.
     *
     * @return tableau contenant les quatre directions cardinales.
     */
    static Direction[] diagonales() {
        Direction[] diag = {NORD_EST, NORD_OUEST, SUD_EST, SUD_OUEST};
        return diag;
    }

    /**
     * Renvoie le nombre de cases parcourues horizontalement lorsqu'on suit
     * cette direction (0 pour Nord et Sud, -1 pour Ouest, 1 pour Est).
     *
     * @return nombre de cases horizontales de cette direction
     */
    int mvtHoriz() {
        int dh = -2;
        switch (this) {
            case NORD:
            case SUD:
                dh = 0;
                break;
            case EST:
            case NORD_EST:
            case SUD_EST:
                dh = 1;
                break;
            case OUEST:
            case NORD_OUEST:
            case SUD_OUEST:
                dh = -1;
                break;
        }
        return dh;
    }

    /**
     * Renvoie le nombre de cases parcourues verticalement lorsqu'on suit cette
     * direction (0 pour Est et Ouest, -1 pour Nord, 1 pour Sud).
     *
     * @return nombre de cases verticales de cette direction
     */
    int mvtVertic() {
        int dv = -2;
        switch (this) {
            case EST:
            case OUEST:
                dv = 0;
                break;
            case NORD:
            case NORD_EST:
            case NORD_OUEST:
                dv = -1;
                break;
            case SUD:
            case SUD_EST:
            case SUD_OUEST:
                dv = 1;
                break;
        }
        return dv;
    }
}