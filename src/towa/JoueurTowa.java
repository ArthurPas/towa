package towa;

import java.text.SimpleDateFormat;
import java.util.Arrays;
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
                if (posePossible(plateau, coord, joueurNoir)) {
                    // on ajoute l'action dans les actions possible
                    if(joueurNoir){
                        if(estCouvrant(plateau, coord, joueurNoir)){
                            actions[nbActions] = chaineActionPose(coord, 
                                    nbPionsNoirs+nbPionsApresMax(plateau, coord),
                                    nbPionsBlancs);
                        }
                        else if(poseDeDeuxPossible(plateau, coord, joueurNoir)){
                            actions[nbActions] = chaineActionPose(coord, nbPionsNoirs + 2, nbPionsBlancs);
                        }
                        else{
                            actions[nbActions] = chaineActionPose(coord, nbPionsNoirs + 1, nbPionsBlancs);
                        
                        }
                    }
                    else{
                        if(estCouvrant(plateau, coord, joueurNoir)){
                            actions[nbActions] = chaineActionPose(coord, 
                                    nbPionsNoirs,
                                    nbPionsBlancs+nbPionsApresMax(plateau, coord));
                        }
                        else if(poseDeDeuxPossible(plateau, coord, joueurNoir)){
                             actions[nbActions] = chaineActionPose(coord, nbPionsNoirs, nbPionsBlancs+2);
                        }
                        else{
                      actions[nbActions] = chaineActionPose(coord, nbPionsNoirs, nbPionsBlancs+1);
                        }
                    }
                nbActions++;
                }
                if (activeTourPossible(plateau, coord, joueurNoir)){
                    
                    int nbPionsNoirsDetruits = nbPionsDetruits(plateau, coord, false);
                    int nbPionsBlancsDetruits = nbPionsDetruits(plateau, coord, true);
                    if(joueurNoir){
                          actions[nbActions] = chaineActionActive(coord, nbPionsNoirs, nbPionsBlancs-nbPionsBlancsDetruits);
                    }
                    else{
                        actions[nbActions]= chaineActionActive(coord, nbPionsNoirs-nbPionsNoirsDetruits, nbPionsBlancs);
                    }
                nbActions++;
                }
                if(magiePossible(plateau, coord, joueurNoir)){
                    if(joueurNoir){
                        actions[nbActions] = chaineActionMagie(coord,nbPionsNoirs,nbPionsBlancs);
                    }
                    else{
                        actions[nbActions] = chaineActionMagie(coord,nbPionsNoirs,nbPionsBlancs);
                    }
                nbActions++;
                }   
                if (fusionTourPossible(plateau, coord, joueurNoir)){
                    int nbNoirsApresFusion = nbPionsSupApresFusion(plateau, coord, true);
                    int nbBlancsApresFusion = nbPionsSupApresFusion(plateau, coord, false);
                    if(joueurNoir){
                          actions[nbActions] = chaineActionFusion(coord, nbPionsNoirs-nbNoirsApresFusion, nbPionsBlancs);
                    }
                    else{
                        actions[nbActions]= chaineActionFusion(coord, nbPionsNoirs, nbPionsBlancs-nbBlancsApresFusion);
                    }
                nbActions++;
                }
                if(chatPossible(coord)){
                    if(joueurNoir){
                        
                        for (int i = 1; i < 5; i++) {
                            int nbNoirDetruit = nbPionsSupNoirApresChat(plateau, coord, true, i);
                            int nbBlancsDetruit = nbPionsSupNoirApresChat(plateau, coord, false, i);
                            actions[nbActions] = chaineActionChat(coord, nbPionsNoirs-nbNoirDetruit, nbPionsBlancs-nbBlancsDetruit,i);
                            nbActions++;
                        }
                    }
                    else{
                        for (int i = 1; i < 5; i++) {
                            int nbNoirDetruit = nbPionsSupNoirApresChat(plateau, coord, true, i);
                            int nbBlancsDetruit = nbPionsSupNoirApresChat(plateau, coord, false, i);
                            actions[nbActions] = chaineActionChat(coord, nbPionsNoirs-nbNoirDetruit, nbPionsBlancs-nbBlancsDetruit,i);
                            nbActions++;
                            
                        }
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
     * ce joueur, dans ce niveau (niveau 10).
     *
     * @param plateau le plateau
     * @param coord coordonnées de la case à considérer
     * @param estNoir vrai ssi il s'agit du joueur noir
     * @return vrai ssi la pose d'un pion sur cette case est autorisée dans ce
     * niveau
     */
    boolean posePossible(Case[][] plateau, Coordonnees coord, boolean estNoir) {
        Case c =plateau[coord.ligne][coord.colonne];
        if(c.tourPresente){
            if(voisineEnemiePresente(plateau, coord, estNoir)){
                if(c.estNoire == estNoir && (c.hauteur+c.altitude)<4){
                return true;
                }
            }
           if(c.estNoire == estNoir && (c.hauteur+c.altitude)<4){
               return true;
           } 
               
        }
        else{
            if(c.nature=='T'){
                if(voisineEnemiePresente(plateau, coord, estNoir)){
                    if(c.altitude==3){
                        return false;
                    }
                    if(c.altitude<3){
                        return true;
                    }
                }
                if(c.altitude<4){
                   return true;
                }
            }
        }   
        return false;
    }
    /**
     * Indique s'il est possible de poser deux pion sur une case pour ce plateau,
     * ce joueur
     *
     * @param plateau le plateau
     * @param coord coordonnées de la case à considérer
     * @param estNoir vrai ssi il s'agit du joueur noir
     * @return vrai ssi la pose de deux pions sur cette case est autorisée dans ce
     * niveau
     */
    boolean poseDeDeuxPossible(Case[][] plateau, Coordonnees coord, boolean estNoir) {
        Case c =plateau[coord.ligne][coord.colonne];
        return((!c.tourPresente)&& (c.altitude<3)
                && (voisineEnemiePresente(plateau, coord, estNoir)));
       
    }
    /**
     * Indique s'il est possible d'activer une tour
     *
     * @param plateau le plateau
     * @param coord coordonnées de la case à considérer
     * @param estNoir vrai ssi il s'agit du joueur noir
     * @return vrai ssi l'activation d'une tour est possible
     */
    boolean activeTourPossible(Case[][] plateau, Coordonnees coord, boolean estNoir) {
        Case c =plateau[coord.ligne][coord.colonne];
        return ((c.tourPresente) && (c.estNoire == estNoir));        
    }
    /**
     * Indique s'il est possible de fusionner une tour
     *
     * @param plateau le plateau
     * @param coord coordonnées de la case à considérer
     * @param estNoir vrai ssi il s'agit du joueur noir
     * @return vrai ssi la fusion d'une tour est possible
     */
    boolean fusionTourPossible(Case[][] plateau, Coordonnees coord, boolean estNoir) {
        Case c =plateau[coord.ligne][coord.colonne];
        return ((c.tourPresente) && (c.estNoire == estNoir));        
    }
    /**
     * Indique s'il est possible d'actionner un chaton kamikaze
     * @param coord coordonnées de la case à considérer
     * @return vrai ssi l'activation d'un chat est possible
     */
    boolean chatPossible(Coordonnees coord) {
        return (Coordonnees.estBord(coord));        
    }
    /**
     * Indique s'il est possible de faire de la magie sur une case
     *
     * @param plateau le plateau
     * @param coord coordonnées de la case à considérer
     * @param estNoir vrai ssi il s'agit du joueur noir
     * @return vrai ssi la magie d'une tour est possible
     */
    boolean magiePossible(Case[][] plateau,Coordonnees coord, boolean estNoir) {
        Case c = plateau[coord.ligne][coord.colonne];
        Case sym = plateau[Coordonnees.coordSymetrique(coord).ligne][Coordonnees.coordSymetrique(coord).colonne];
        return (c.tourPresente && !(sym.tourPresente)
                && (sym.altitude +c.hauteur <= 4) && sym.nature=='T'
                && c.estNoire==estNoir);      
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
        return nombrePions; 
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
     * Chaîne de caractères correspondant à une action-mesure d'activation d'une tour.
     *
     * @param coord coordonnées de la case à activer
     * @param nbPionsNoirs nombre de pions noirs si cette action était jouée
     * @param nbPionsBlancs nombre de pions blancs si cette action était jouée
     * @return la chaîne codant cette action-mesure
     */
    static String chaineActionActive(Coordonnees coord,
            int nbPionsNoirs, int nbPionsBlancs) {
        return "A" + coord.carLigne() + coord.carColonne() + ","
                + nbPionsNoirs + "," + nbPionsBlancs;
    }
    /**
     * Chaîne de caractères correspondant à une action-mesure de fusion d'une tour.
     *
     * @param coord coordonnées de la case à fusioner
     * @param nbPionsNoirs nombre de pions noirs si cette action était jouée
     * @param nbPionsBlancs nombre de pions blancs si cette action était jouée
     * @return la chaîne codant cette action-mesure
     */
    static String chaineActionFusion(Coordonnees coord,
            int nbPionsNoirs, int nbPionsBlancs) {
        return "F" + coord.carLigne() + coord.carColonne() + ","
                + nbPionsNoirs + "," + nbPionsBlancs;
    }
    /**
     * Chaîne de caractères correspondant à une action-mesure d'activation d'un 
     * chaton kamikaze
     *
     * @param coord coordonnées de la case à activer
     * @param nbPionsNoirs nombre de pions noirs si cette action était jouée
     * @param nbPionsBlancs nombre de pions blancs si cette action était jouée
     * @param i l'indince de du bord
     * @return la chaîne codant cette action-mesure
     */
    static String chaineActionChat(Coordonnees coord,
            int nbPionsNoirs, int nbPionsBlancs, int i) {
        return "C" + coord.carBord(i)+ ","
                + nbPionsNoirs + "," + nbPionsBlancs;
    }
    /**
     * Chaîne de caractères correspondant à une action-mesure de Magie
     *
     * @param coord coordonnées de la case à teleporté
     * @return la chaîne codant cette action-mesure
     */
    static String chaineActionMagie(Coordonnees coord,int nbPionsNoirs, int nbPionsBlancs){
        return "M" + coord.carLigne() + coord.carColonne()+ ","
                + nbPionsNoirs + "," + nbPionsBlancs;
    }
    /**
     * Nombre de Pions a ajouté aprés la pose d'une tour "max"
     * @param plateau le plateau
     * @param coord les coordonnées de la case en question
     */
    int nbPionsApresMax(Case [][] plateau,Coordonnees coord){
        return(4-(plateau[coord.ligne][coord.colonne].altitude));
    }
    /**
     * Nombre de Pions de la couleur opposé détruits lors de l'activation d'une tour
     * @param plateau le plateau
     * @param coord les coordonnées de la tour activée
     * @param estNoir la couleur de la tour activée
     * @param i l'indince de du bord
     * @return le nombre de bion a enlevé
     */
    static int nbPionsDetruits(Case [][] plateau,Coordonnees coord, boolean estNoir){
        Coordonnees vois [];
        Case c =plateau[coord.ligne][coord.colonne];
        vois = coord.voisinesDirection(Direction.diagonales());
        int nbPionsVoisin=0;
        for (Coordonnees val : vois) {
                if (((val!=null && plateau[val.ligne][val.colonne].tourPresente 
                        && (c.hauteur+c.altitude) > 
                        (plateau[val.ligne][val.colonne].hauteur + 
                        plateau[val.ligne][val.colonne].altitude)
                        && estNoir!=plateau[val.ligne][val.colonne].estNoire))){

                    nbPionsVoisin+=plateau[val.ligne][val.colonne].hauteur;
                }
        }
        Coordonnees premiere[];
        premiere = coord.premiereSuivante(plateau);
        int nbPionsSuivant= 0;
        for (Coordonnees val : premiere) {
            if (val!= null  && (c.hauteur+c.altitude) > 
                        (plateau[val.ligne][val.colonne].hauteur + 
                        plateau[val.ligne][val.colonne].altitude)
                        && estNoir!=plateau[val.ligne][val.colonne].estNoire){
                    
                nbPionsSuivant+=plateau[val.ligne][val.colonne].hauteur;
                   
           }
            
        
        }
    return nbPionsVoisin+nbPionsSuivant;
    }
    /**
     * Nombre de Pions de la couleur détruits lors de la fusion
     * @param plateau le plateau
     * @param coord les coordonnées de la tour activée
     * @param estNoir la couleur de la tour activée
     * @return le nombre de bion a enlevé
     */
    static int nbPionsRecuperes(Case [][] plateau,Coordonnees coord, boolean estNoir){
        Coordonnees vois [];
        vois = coord.voisinesDirection(Direction.diagonales());
        int nbPionsVoisin=0;
        for (Coordonnees val : vois) {
                if (val!=null && estNoir==plateau[val.ligne][val.colonne].estNoire){

                    nbPionsVoisin+=plateau[val.ligne][val.colonne].hauteur;
                }
        }
        Coordonnees premiere[];
        premiere = coord.premiereSuivante(plateau);
        int nbPionsSuivant= 0;
        for (Coordonnees val : premiere) {
            if (val!= null && estNoir==plateau[val.ligne][val.colonne].estNoire){
                nbPionsSuivant+=plateau[val.ligne][val.colonne].hauteur;       
           }
        }
        
    return nbPionsVoisin+nbPionsSuivant;
    }
    /**
     * Nombre de Pions de la couleur résultant de la fusion à supprimer
     * @param plateau le plateau
     * @param coord les coordonnées de la tour activée
     * @param estNoir la couleur de la tour activée
     * @return le nombre de bion a enlevé
     */
    static int nbPionsSupApresFusion(Case [][] plateau, Coordonnees coord, boolean estNoir){
        int recup=nbPionsRecuperes(plateau, coord, estNoir);
        Case c =plateau[coord.ligne][coord.colonne];
        if ((c.hauteur+c.altitude)+recup>4){
            return (recup+c.hauteur+c.altitude)-4; // 4 étant la hauteur maxiamle d'une tour
        }
        return 0;
    }
    /**
     * Nombre de Pions à supprimer par le chaton kamikaze pour les noirs
     * @param plateau le plateau
     * @param coord les coordonnées du bord
     * @param estNoir la couleur des pions a detruire
     * @param i l'indince de du bord
     * @return le nombre de bion a enlevé
     */
    static int nbPionsSupNoirApresChat(Case [][] plateau, Coordonnees coord, boolean estNoir, int i){
        int detruitsNoirs=0;
        Coordonnees [] premiere;
        // rappel 2=Nord, 3=Sud, 4=Est, 1=Ouest

        switch(i){
            case 2:
                for (int j = 0; j < Coordonnees.NB_COLONNES; j++) {
                    coord.ligne=-1;
                    coord.colonne=j;
                    premiere = coord.premiereSuivanteDirection(plateau,Direction.SUD);
                    for (Coordonnees val : premiere) {
                        if (val!=null && plateau[val.ligne][val.colonne].estNoire==estNoir ){
                            detruitsNoirs+=plateau[val.ligne][val.colonne].hauteur;
                        }
                        
                    }
                    
                }   
                break;
            case 3:
                for (int j = 0; j < Coordonnees.NB_COLONNES; j++) {
                    coord.ligne=Coordonnees.NB_LIGNES;
                    coord.colonne=j;
                    premiere = coord.premiereSuivanteDirection(plateau,Direction.NORD);
                    for (Coordonnees val : premiere) {
                        if (val!=null && plateau[val.ligne][val.colonne].estNoire==estNoir ){
                            detruitsNoirs+=plateau[val.ligne][val.colonne].hauteur;
                        }
                        
                    }
                    
                }   
                break;
            case 4:
                for (int j = 0; j < Coordonnees.NB_LIGNES; j++) {
                    coord.ligne=j;
                    coord.colonne=Coordonnees.NB_COLONNES;
                    premiere = coord.premiereSuivanteDirection(plateau,Direction.OUEST);
                    for (Coordonnees val : premiere) {
                        if (val!=null && plateau[val.ligne][val.colonne].estNoire==estNoir ){
                            detruitsNoirs+=plateau[val.ligne][val.colonne].hauteur;
                        }

                    }

                }
                break;
            case 1:
                for (int j = 0; j < Coordonnees.NB_LIGNES; j++) {
                    coord.ligne=j;
                    coord.colonne=-1;
                    premiere = coord.premiereSuivanteDirection(plateau,Direction.EST);
                    for (Coordonnees val : premiere) {
                        if (val!=null && plateau[val.ligne][val.colonne].estNoire==estNoir ){
                            detruitsNoirs+=plateau[val.ligne][val.colonne].hauteur;
                        }

                    }

                }
                break;
            default:
                break;
        }
        return detruitsNoirs;
    }
        
     /**
     * Nombre de Pions à supprimer par le chaton kamikaze pour les noirs
     * @param plateau le plateau
     * @param coord les coordonnées du bord
     * @param estNoir la couleur la couleur des pions a detruire
     * @param i l'indice du bord
     * @return le nombre de bion a enlevé
     */
    static int nbPionsSupBlancApresChat(Case [][] plateau, Coordonnees coord, boolean estNoir, int i){
        int detruitsBlancs=0;
        Coordonnees [] premiere;
        // rappel 2=Nord, 3=Sud, 4=Est, 1=Ouest
        switch(i){
            case 2:
                for (int j = 0 ;  j< Coordonnees.NB_COLONNES; j++) {
                    coord.ligne=-1;
                    coord.colonne=j;
                    premiere = coord.premiereSuivanteDirection(plateau,Direction.SUD);
                    for (Coordonnees val : premiere) {
                        if (val!=null && plateau[val.ligne][val.colonne].estNoire==estNoir ){
                            detruitsBlancs+=plateau[val.ligne][val.colonne].hauteur;
                        }
                        
                    }
                    
                }   
                break;
            case 3:
                for (int j =0; j < Coordonnees.NB_COLONNES; j++) {
                    coord.ligne=Coordonnees.NB_LIGNES;
                    coord.colonne=j;
                    premiere = coord.premiereSuivanteDirection(plateau,Direction.NORD);
                    for (Coordonnees val : premiere) {
                        if (val!=null && plateau[val.ligne][val.colonne].estNoire==estNoir ){
                            detruitsBlancs+=plateau[val.ligne][val.colonne].hauteur;
                        }

                    }

                }
                break;
            case 4:
                for (int j = 0; j < Coordonnees.NB_LIGNES; j++) {
                    coord.ligne=j;
                    coord.colonne=Coordonnees.NB_COLONNES;
                    premiere = coord.premiereSuivanteDirection(plateau,Direction.OUEST);
                    for (Coordonnees val : premiere){
                        if (val!=null && plateau[val.ligne][val.colonne].estNoire==estNoir ){
                            detruitsBlancs+=plateau[val.ligne][val.colonne].hauteur;
                        }

                    }

                }
                break;
            case 1:
                for (int j = 0; j < Coordonnees.NB_LIGNES; j++) {
                    coord.ligne=j;
                    coord.colonne=-1;
                    premiere = coord.premiereSuivanteDirection(plateau,Direction.EST);
                    for (Coordonnees val : premiere) {
                        if (val!=null && plateau[val.ligne][val.colonne].estNoire==estNoir ){
                            detruitsBlancs+=plateau[val.ligne][val.colonne].hauteur;
                        } 
                    }

                }
                break;
            default:
                break;
        }
        
//        }
        return detruitsBlancs;
    }
    /**
     * Booléen qui renvoi vrai ssi il existe une tour voisine de la couleur opposée
     * @param plateau le plateau
     * @param coord les coordonnées de la case
     * @param estNoir la couleur du joueur
     * @return vrai ssi il existe une tour voisine de la couleur opposée
     */
    static boolean voisineEnemiePresente(Case[][] plateau, Coordonnees coord, 
            boolean estNoir ){
        Coordonnees vois [];
        vois = coord.voisinesDirection(Direction.toutes());
        boolean tourPresente=false;
        for (Coordonnees val : vois) {
            if( val!=null && plateau[val.ligne][val.colonne].tourPresente 
                    && (plateau[val.ligne][val.colonne].estNoire==!estNoir)){
                tourPresente=true;
            }
        }
    return tourPresente;    
    }
    /**
     * Booléen qui renvoi vrai si il y'a au moins une tour blanche et une tour noire
     * sur la colonne
     * @param plateau le plateau
     * @param coord les coordonnées de la case
     * @return vrai vrai si il y'a au moins une tour blanche et une tour 
     * noire sur la colonne
     */
    static boolean estCouvrant(Case[][] plateau, Coordonnees coord, boolean estNoir){
        boolean tourBlancheCol=false; 
        boolean tourNoireCol=false;
        boolean colonneCouvrante=true;
        boolean tourBlancheLig=false; 
        boolean tourNoireLig=false;
        boolean ligneCouvrante=true;
        for (int lig = 0; lig < Coordonnees.NB_LIGNES; lig++) {
            tourBlancheLig=false; 
            tourNoireLig=false;
            for (int col = 0; col < Coordonnees.NB_COLONNES; col++) {
                // avant la pose
                if(plateau[lig][col].tourPresente && !(plateau[lig][col].estNoire)){
                    tourBlancheLig=true;
                }
                if(plateau[lig][col].tourPresente && (plateau[lig][col].estNoire)){
                    tourNoireLig=true;
                }
                // après la pose sur coord
                if(!plateau[lig][col].tourPresente){
                    if(lig==coord.ligne && col==coord.colonne && estNoir){
                        tourNoireLig=true;
                    }
                    if(lig==coord.ligne && col==coord.colonne && !estNoir){
                        tourBlancheLig=true;
                    }
                }
            }
            // Présence tour Noire et tour Blanche sur la ligne 
            if(!(tourBlancheLig && tourNoireLig)){
                    colonneCouvrante=false;
                }
        }
        for (int col = 0; col < Coordonnees.NB_COLONNES; col++) {
            tourBlancheCol=false;
            tourNoireCol=false;
            for (int lig = 0; lig < Coordonnees.NB_LIGNES; lig++) {
                // avant la pose
                if(plateau[lig][col].tourPresente && !(plateau[lig][col].estNoire)){
                    tourBlancheCol=true;
                }
                if(plateau[lig][col].tourPresente && (plateau[lig][col].estNoire)){
                    tourNoireCol=true;
                }
                // après la pose sur coord
                if(!plateau[lig][col].tourPresente){
                    if(lig==coord.ligne && col==coord.colonne && estNoir){
                        tourNoireCol=true;
                    }
                    if(lig==coord.ligne && col==coord.colonne && !estNoir){
                        tourBlancheCol=true;
                    }
                }
            }
            // Présence tour Noire et tour Blanche sur la colonne 
            if(!(tourBlancheCol && tourNoireCol)){
                    ligneCouvrante=false;
            }
        }
        return (colonneCouvrante && ligneCouvrante);
    }
    /**
     * Booléen qui renvoi vrai si il y'a au moins une tour blanche et une tour noire
     * sur la ligne
     * @param plateau le plateau
     * @param coord les coordonnées de la case
     * @return vrai vrai  si il y'a au moins une tour blanche et une tour noire
     * sur la ligne
     */
    static boolean ligEstCouvrante(Case[][] plateau, Coordonnees coord, boolean estNoir){
        boolean tourBlancheLig=false; 
        boolean tourNoireLig=false;
        boolean ligneCouvrante=false;
        for (int col = 0; col < Coordonnees.NB_COLONNES; col++) {
            for (int lig = 0; lig < Coordonnees.NB_LIGNES; lig++) {
                if(plateau[lig][col].tourPresente && !(plateau[lig][col].estNoire)
                ||(!(plateau[coord.ligne][coord.colonne].tourPresente) && estNoir)){
                    System.out.println("tour blanche trouvé");
                    tourBlancheLig=true;
                }
                if(plateau[lig][col].tourPresente && (plateau[lig][col].estNoire)
                ||(!(plateau[coord.ligne][coord.colonne].tourPresente) && estNoir)){
                    System.out.println("tour noire trouvé");
                    tourNoireLig=true;
                }
                ligneCouvrante = tourBlancheLig && tourNoireLig;
                System.out.println(ligneCouvrante);
                
            }
        }
        
    
        return ligneCouvrante;    
    }
    
}