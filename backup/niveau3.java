package towa;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Joueur implémentant les actions possibles à partir d'un plateau, pour un
 * niveau donné.
 */
public class JoueurTowa implements IJoueurTowa {

    /**
     * Cette méthode renvoie, pour un plateau donné et un joueur donné, toutes
     * les actions possibles pour ce joueur.
     *
     * @param plateau le plateau considéré
     * @param joueurNoir vrai si le joueur noir joue, faux si c'est le blanc
     * @param niveau le niveau de la partie à jouer
     * @return l'ensemble des actions possibles
     */
    @Override
    public String[] actionsPossibles(Case[][] plateau, boolean joueurNoir, int niveau) {
        // afficher l'heure de lancement
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
        System.out.println("actionsPossibles : lancement le " + format.format(new Date()));
        // on compte le nombre de pions sur le plateau avant action
        final int nbPionsNoirs = nbPions(plateau, true);
        final int nbPionsBlancs = nbPions(plateau, false);
        // calculer les actions possibles
        String actions[] = new String[1028];
        int nbActions = 0;
        // pour chaque ligne
        for (int lig = 0; lig < Coordonnees.NB_LIGNES; lig++) {
            // pour chaque colonne
            for (int col = 0; col < Coordonnees.NB_COLONNES; col++) {
                Coordonnees coord = new Coordonnees(lig, col);
                // si la pose d'un pion de cette couleur est possible sur cette case
                if (posePossible3(plateau, coord, joueurNoir)) {
                    // on ajoute l'action dans les actions possibles
                    posePion(coord, plateau, joueurNoir);
                    
//                    actions[nbActions] = chaineActionPose(coord, nbPions(plateau, true), nbPions(plateau, false));
                      if(joueurNoir){
                          actions[nbActions] = chaineActionPose(coord, nbPionsNoirs + 1, nbPionsBlancs);
                      }
                      else{
                          actions[nbActions] = chaineActionPose(coord, nbPionsNoirs, nbPionsBlancs+1);
                      }
                    nbActions++;
                }
            }
        }
        System.out.println("actionsPossibles : fin");
        return Utils.nettoyerTableau(actions);
    }

    /**
     * Indique s'il est possible de poser un pion sur une case pour ce plateau,
     * ce joueur, dans ce niveau (niveau 1).
     *
     * @param plateau le plateau
     * @param coord coordonnées de la case à considérer
     * @param estNoir vrai ssi il s'agit du joueur noir
     * @return vrai ssi la pose d'un pion sur cette case est autorisée dans ce
     * niveau
     */
    boolean posePossible(Case[][] plateau, Coordonnees coord, boolean estNoir) {
        return (!plateau[coord.ligne][coord.colonne].tourPresente) && (estNoir);
    }
/**
     * Indique s'il est possible de poser un pion sur une case pour ce plateau,
     * ce joueur, dans ce niveau (niveau 2).
     *
     * @param plateau le plateau
     * @param coord coordonnées de la case à considérer
     * @param estNoir vrai ssi il s'agit du joueur noir
     * @return vrai ssi la pose d'un pion sur cette case est autorisée dans ce
     * niveau
     */
    boolean posePossible2(Case[][] plateau, Coordonnees coord, boolean estNoir) {
        Case c =plateau[coord.ligne][coord.colonne];
        return ((c.tourPresente) && (c.estNoire == estNoir)|| (!c.tourPresente));
       
    }
/**
     * Indique s'il est possible de poser un pion sur une case pour ce plateau,
     * ce joueur, dans ce niveau (niveau 3).
     *
     * @param plateau le plateau
     * @param coord coordonnées de la case à considérer
     * @param estNoir vrai ssi il s'agit du joueur noir
     * @return vrai ssi la pose d'un pion sur cette case est autorisée dans ce
     * niveau
     */
    boolean posePossible3(Case[][] plateau, Coordonnees coord, boolean estNoir) {
        Case c =plateau[coord.ligne][coord.colonne];
        return ((c.tourPresente) && (c.estNoire == estNoir) && (c.hauteur<4)|| (!c.tourPresente)) ;
       
    }
    /**
     * Nombre de pions d'une couleur donnée sur le plateau.
     *
     * @param plateau le plateau
     * @param estNoir vrai si on compte les pions noirs, faux sinon
     * @return le nombre de pions de cette couleur sur le plateau
     */
    static int nbPions(Case[][] plateau, boolean estNoir) {
        int nombrePions=0;
        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                if((plateau[i][j].tourPresente) && (estNoir == plateau[i][j].estNoire)){
                    nombrePions+=plateau[i][j].hauteur;
                }
            }
        }
        return nombrePions; // TODO il y en aura besoin à un moment !
    }
    
    /**
     * Chaîne de caractères correspondant à une action-mesure de pose.
     *
     * @param coord coordonnées de la case où poser le pion
     * @param nbPionsNoirs nombre de pions noirs si cette action était jouée
     * @param nbPionsBlancs nombre de pions blancs si cette action était jouée
     * @return la chaîne codant cette action-mesure
     */
    static String chaineActionPose(Coordonnees coord,
            int nbPionsNoirs, int nbPionsBlancs) {
        return "P" + coord.carLigne() + coord.carColonne() + ","
                + nbPionsNoirs + "," + nbPionsBlancs;
    }

    /**
     * Pose d'un pion
     * @param coord coordonnées de la case où poser le pion
     * @param plateau le plateau
     * @param estNoir vrai ssi il s'agit du tour du joueur noir
     */
    Case[][] posePion(Coordonnees coord, Case[][] plateau, boolean estNoir){
        if(this.posePossible(plateau, coord, estNoir)){ 
            plateau[coord.ligne][coord.colonne].tourPresente=true;
            plateau[coord.ligne][coord.colonne].hauteur++;
            plateau[coord.ligne][coord.colonne].estNoire=estNoir;
            
        }
        return plateau;
    }
}