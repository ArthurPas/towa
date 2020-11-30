package towa;

import java.util.Arrays;

/**
 * Coordonnées d'une case du plateau.
 */
class Coordonnees {
    
    /**
     * Numéro de la ligne.
     */
    int ligne;
    
    /**
     * Numéro de la colonne.
     */
    int colonne;

    /**
     * Nombre de lignes du plateau.
     * NE PAS MODIFIER.
     */
    static final int NB_LIGNES = 16;
    /**
     * Nombre de colonnes du plateau.
     * NE PAS MODIFIER.
     */
    static final int NB_COLONNES = 16;
    
    /**
     * Caractère de la première ligne.
     */
    static final char CAR_PREMIERE_LIGNE = 'a';

    /**
     * Caractère de la première colonne.
     */
    static final char CAR_PREMIERE_COLONNE = 'A';

    /**
     * Constructeur prenant les numéros de ligne/colonne en paramètre.
     * 
     * @param numLigne numéro de la ligne
     * @param numColonne numéro de la colonne
     */
    Coordonnees(int numLigne, int numColonne) {
        ligne = numLigne;
        colonne = numColonne;
    }

    /**
     * Retourne un objet Coordonnees à partir des caractères des coordonnées 
     * donnés en paramètre.
     * 
     * @param carLigne caractère de la ligne
     * @param carColonne caractère de la colonne
     * @return coordonnées correspondant à ces caractères
     */
    static Coordonnees depuisCars(char carLigne, char carColonne) {
        return new Coordonnees(
                carLigneVersNum(carLigne), carColonneVersNum(carColonne));
    }

    /**
     * Renvoie le caractère correspondant à la ligne.
     * 
     * @return le caractère correspondant à la ligne
     */
    char carLigne() {
        if ((ligne < 0) || (ligne >= NB_LIGNES)) {
            throw new IllegalArgumentException(
                    "Appel incorrect à carLigne, avec ligne = " + ligne
                    + ". Les valeurs autorisées sont les entiers entre "
                    + "0 et " + (NB_LIGNES - 1));
        }
        return (char) (CAR_PREMIERE_LIGNE + ligne);
    }

    /**
     * Renvoie le caractère correspondant à la colonne.
     * 
     * @return le caractère correspondant à la colonne
     */
    char carColonne() {
        if ((colonne < 0) || (colonne >= NB_COLONNES)) {
            throw new IllegalArgumentException(
                    "Appel incorrect à carColonne, avec colonne = " + colonne
                    + ". Les valeurs autorisées sont les entiers entre "
                    + "0 et " + (NB_COLONNES - 1));
        }
        return (char) (CAR_PREMIERE_COLONNE + colonne);
    }
    /**
     * Renvoie le caractère correspondant au bord
     * 
     * @return le caractère correspondant au bord
     */
    char carBord(int indice){
        char bord;
        if(indice==1){
            return bord='O';
        }
        if(indice==4){
            return bord='E';
        }
        if(indice==2){
            return bord='N';
        }
        if(indice==3){
            return bord='S';
        }
        return bord='A';
    }
    /**
     * Convertit un nom de ligne (par exemple 'c') en numéro de ligne (ici 2).
     *
     * @param nomLigne le nom de ligne à convertir
     * @return le numéro de cette ligne
     */
    static int carLigneVersNum(char nomLigne) {
        final char carMin = CAR_PREMIERE_LIGNE;
        final char carMax = CAR_PREMIERE_LIGNE + NB_LIGNES - 1;
        if ((nomLigne < carMin) || (nomLigne > carMax)) {
            throw new IllegalArgumentException(
                    "Appel incorrect à carVersNum, avec car = " + nomLigne
                    + ". Les valeurs autorisées sont les caractères entre "
                    + carMin + " et " + carMax + ".");
        }
        return nomLigne - CAR_PREMIERE_LIGNE;
    }

    /**
     * Convertit un nom de colonnes (par exemple 'C') en numéro de colonne (ici
     * 2).
     *
     * @param nomColonne le nom de colonne à convertir
     * @return le numéro de cette colonne
     */
    static int carColonneVersNum(char nomColonne) {
        final char carMin = CAR_PREMIERE_COLONNE;
        final char carMax = CAR_PREMIERE_COLONNE + NB_COLONNES - 1;
        if ((nomColonne < carMin) || (nomColonne > carMax)) {
            throw new IllegalArgumentException(
                    "Appel incorrect à carVersNum, avec car = " + nomColonne
                    + ". Les valeurs autorisées sont les caractères entre "
                    + carMin + " et " + carMax + ".");
        }
        return nomColonne - CAR_PREMIERE_COLONNE;
    }
    /**
     * Renvoie les coordonnées de la case suivante, en suivant une direction
     * donnée.
     *
     * @param d la direction à suivre
     * @return les coordonnées de la case suivante
     */
    Coordonnees suivante(Direction d, int nbCaseACompter) {
        return new Coordonnees(ligne + d.mvtVertic()*nbCaseACompter, colonne + d.mvtHoriz()*nbCaseACompter);
    }
    

    /**
     * Indique si ces coordonnées sont dans le plateau.
     *
     * @return vrai ssi ces coordonnées sont dans le plateau
     */
    boolean estDansPlateau() {
        return(ligne>=0 && ligne<NB_LIGNES && colonne>=0 && colonne<NB_COLONNES);
    /**
     * Indique si ces coordonnées sont dans le plateau.
     *
     * @return vrai ssi ces coordonnées sont dans le plateau
     */    
    }
    static boolean estBord(Coordonnees coord){
        return(coord.ligne==0 || coord.colonne==0 || coord.colonne==NB_COLONNES-1 
                || coord.ligne==NB_LIGNES-1);
    }

    /**
     * Retourne les coordonnées de toutes les cases voisines dans une direction 
     * donnée
     * 
     * @return les coordonnées de toutes les cases voisines
     */
    Coordonnees[] voisinesDirection(Direction[] directionASuivre) {
        Coordonnees[] voisines = new Coordonnees[8];        
        int nbVoisines = 0;
        for (Direction d: directionASuivre) {
            Coordonnees voisine = suivante(d,1);
         if(voisine.estDansPlateau()) {
             voisines[nbVoisines]=voisine;
             nbVoisines++;
         }
        }
        return voisines;
    }
    /**
     * Retourne les coordonnées de toutes les case présente sur les mêmes ligne et colonnes 
     * 
     *@return un tableau des coordonnées
     */
    Coordonnees[] ligneEtCol(){
        int nbMax=2*NB_LIGNES-1+2*NB_COLONNES-1;
        Coordonnees[] lignes = new Coordonnees[nbMax];
        int nb = 0;
        for (Direction d: Direction.cardinales()) {
            for (int i = 2; i < NB_LIGNES; i++) {
                Coordonnees ligneEtCol = suivante(d, i);
                if(ligneEtCol.estDansPlateau()) {         
                    lignes[nb]=ligneEtCol;
                     nb++;
                 }
            }
        }
        return lignes;
    }
    /**
     * Retourne les coordonnées de la première tour trouvée (si elle existe) dans 
     * chacunes des directions cardinales
     * @param plateau le plateau
     * @return un tableau de 4 coordonnées
     */
    Coordonnees[] premiereSuivante(Case [][] plateau){
        Coordonnees[] premiereTour =new Coordonnees[4];
        int j=0;
        for (Direction d : Direction.cardinales()) {
            boolean tourTrouve=false;
            int i=1;
            while(i<NB_LIGNES && !tourTrouve){
                Coordonnees s = suivante(d, i);               
                if(s.estDansPlateau()) {
                    if(plateau[s.ligne][s.colonne].tourPresente){
                        premiereTour[j]=s;
                        tourTrouve=true;
                        j++;
                    }
                }
                i++;    
            }
        }
        return premiereTour;
    }
    /**
     * Retourne les coordonnées de la première tour trouvée (si elle existe) dans 
     * une des direction
     * @param plateau le plateau
     * @return un tableau de 4 coordonnées
     */
    Coordonnees[] premiereSuivanteDirection(Case [][] plateau, Direction directionASuivre){
        Coordonnees[] premiereTour =new Coordonnees[4];
        int j=0;
            boolean tourTrouve=false;
            int i=1;
            while(i<=NB_LIGNES && !tourTrouve){
                Coordonnees s = suivante(directionASuivre, i);               
                if(s.estDansPlateau()) {
                    if(plateau[s.ligne][s.colonne].tourPresente){
                        premiereTour[j]=s;
                        tourTrouve=true;
                        j++;
                    }
                }
                i++;    
            
        }
        return premiereTour;
    }
    /**
     * Retourne les coordonnées de la case symetrique par rapport aux coordonnées
     * @param coord les coordonées de la case dont on cherche la symetrie
     * @return un tableau de 4 coordonnées
     */
    static Coordonnees coordSymetrique(Coordonnees coord){
       Coordonnees symetrie;
       if (coord.estDansPlateau()){
       symetrie =new Coordonnees (((NB_LIGNES-1)-coord.ligne),((NB_COLONNES-1)-coord.colonne));
       return symetrie;
       }
       return null;
    }
    /**
     * Test d'égalité entre coordonnées.
     * 
     * @param obj les coordonnées avec lesquelles comparer ces coordonnées
     * 
     * 
     * 
     * 
     * @return vrai ssi les coordonnées représentent la même case
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Coordonnees other = (Coordonnees) obj;
        if (this.ligne != other.ligne) {
            return false;
        }
        if (this.colonne != other.colonne) {
            return false;
        }
        return true;
    }

    /**
     * Code de hachage, si besoin (pas nécessaire).
     * 
     * @return code de hachage de ces coordonnées
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.ligne;
        hash = 79 * hash + this.colonne;
        return hash;
    }
}
