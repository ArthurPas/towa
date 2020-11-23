package towa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests sur la classe JoueurTowa.
 */
public class JoueurTowaTest {

    /**
     * Test de la fonction actionsPossibles. Commentez les appels aux tests des
     * niveaux inférieurs, n'activez que le test du niveau à valider.
     */
    @Test
    public void testActionsPossibles() {
//         testActionsPossibles_niveau1();
        testActionsPossibles_niveau2();
//     testActionsPossibles_niveau3();
 //        testActionsPossibles_niveau4();
    }

    /**
     * Test de la fonction actionsPossibles, au niveau 1.
     */
    public void testActionsPossibles_niveau1() {
        JoueurTowa joueur = new JoueurTowa();
        // un plateau sur lequel on veut tester actionsPossibles()
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU1);
        // on choisit la couleur du joueur
        boolean estNoir = true;
        // on choisit le niveau
        int niveau = 1;
        // on lance actionsPossibles
        String[] actionsPossibles = joueur.actionsPossibles(plateau, estNoir, niveau);
        // on peut afficher toutes les actions possibles calculées :
        Utils.afficherActionsPossibles(actionsPossibles);
        // on peut aussi tester si une action est dans les actions possibles :
        assertTrue(Utils.actionsPossiblesContient(actionsPossibles, "PaB,1,0"));
        // on peut aussi tester si une action n'est pas dans les actions 
        // possibles :
        assertFalse(Utils.actionsPossiblesContient(actionsPossibles, "PaQ,1,0"));
        assertFalse(Utils.actionsPossiblesContient(actionsPossibles, "PaA,0,0"));
        // testons les 4 coins :
        assertTrue(Utils.actionsPossiblesContient(actionsPossibles, "PaA,1,0"));
        assertTrue(Utils.actionsPossiblesContient(actionsPossibles, "PpA,1,0"));
        assertTrue(Utils.actionsPossiblesContient(actionsPossibles, "PaP,1,0"));
        assertTrue(Utils.actionsPossiblesContient(actionsPossibles, "PpP,1,0"));
        // vérifions s'il y a le bon nombre d'actions possibles :
        assertEquals(Coordonnees.NB_LIGNES * Coordonnees.NB_COLONNES,
                actionsPossibles.length);
    }

    /**
     * Test de la fonction actionsPossibles, au niveau 2.
     */
    @Test
    public void testActionsPossibles_niveau2() {
        JoueurTowa joueur = new JoueurTowa();
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU2);
        boolean estNoir = true;
        int niveau = 2;
        int nbPionsNoir = JoueurTowa.nbPions(plateau, true);
        int nbPionsBlancs = JoueurTowa.nbPions(plateau, false);
        // on lance actionsPossibles
        String[] actionsPossibles = joueur.actionsPossibles(plateau, estNoir, niveau);
        Coordonnees coord;
        // pose sur case vide : possible
        coord = Coordonnees.depuisCars('a', 'B');
        assertTrue(Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionPose(coord, nbPionsNoir + 1, nbPionsBlancs)));
        // pose sur case de même couleur : possible
        coord = Coordonnees.depuisCars('b', 'A');
        assertTrue(Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionPose(coord, nbPionsNoir + 1, nbPionsBlancs)));
        // pose sur case de couleur opposée : impossible
        coord = Coordonnees.depuisCars('a', 'G');
        assertFalse(Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionPose(coord, nbPionsNoir + 1, nbPionsBlancs)));
        // pose sur case de même couleur et hauteur > 1 : possible
        coord = Coordonnees.depuisCars('c', 'K');
        assertTrue(Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionPose(coord, nbPionsNoir + 1, nbPionsBlancs)));
    }
    @Test
    public void testActionsPossibles_niveau3() {
        JoueurTowa joueur = new JoueurTowa();
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU3);
        boolean estNoir = true;
        int niveau = 3;
        int nbPionsNoir = JoueurTowa.nbPions(plateau, true);
        int nbPionsBlancs = JoueurTowa.nbPions(plateau, false);
        String[] actionsPossibles = joueur.actionsPossibles(plateau, estNoir, niveau);
        Coordonnees coord;
        //pose sur une case avec un tour de même couleur de hauteur 1 : possible
        coord = Coordonnees.depuisCars('a', 'A');
        assertTrue(Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionPose(coord, nbPionsNoir + 1, nbPionsBlancs)));
        //pose sur une case avec un tour de même couleur de hauteur 3 : possible
        coord = Coordonnees.depuisCars('a', 'B');
        assertTrue(Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionPose(coord, nbPionsNoir + 1, nbPionsBlancs)));
        //pose sur une case avec une tour de la couleur opposée
        coord = Coordonnees.depuisCars('b', 'B');
        assertFalse(Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionPose(coord, nbPionsNoir +1, nbPionsBlancs)));
//        pose sur une case avec un tour de même couleur de hauteur 4 : impossible
        coord = Coordonnees.depuisCars('a', 'C');
        assertFalse(Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionPose(coord, nbPionsNoir + 1, nbPionsBlancs)));
    }
    @Test
    public void testActionsPossibles_niveau4() {
        JoueurTowa joueur = new JoueurTowa();
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU2);
        boolean estNoir = true;
        int niveau = 4;
        int nbPionsNoir = JoueurTowa.nbPions(plateau, true);
        int nbPionsBlancs = JoueurTowa.nbPions(plateau, false);
        
        String[] actionsPossibles = joueur.actionsPossibles(plateau, estNoir, niveau);
        Coordonnees coord;
        
        //pose sur une case avec un tour de même couleur de hauteur 1 : possible
        coord = Coordonnees.depuisCars('b', 'A');
        
        assertTrue(Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionPose(coord, nbPionsNoir + 1, nbPionsBlancs)));
        //pose sur une case avec un tour de même couleur de hauteur 3 : possible
        coord = Coordonnees.depuisCars('l', 'E');
        assertTrue(Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionPose(coord, nbPionsNoir + 1, nbPionsBlancs)));
        //pose sur une case avec un tour de même couleur de hauteur 4 : impossible
        coord = Coordonnees.depuisCars('c', 'K');
        assertFalse(Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionPose(coord, nbPionsNoir + 1, nbPionsBlancs)));
        //pose sur une case libre : possible
        coord = Coordonnees.depuisCars('a', 'A');
        assertTrue(Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionPose(coord, nbPionsNoir + 1, nbPionsBlancs)));
        //Activation d'une tour non présente
        coord = Coordonnees.depuisCars('a', 'A');
        assertFalse((Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionActive(coord, nbPionsNoir, nbPionsBlancs))));
        //activation d'une tour noire entourée 
        coord = Coordonnees.depuisCars('l', 'E');
        int nbPionsBlancsAEnlever = JoueurTowa.nbPionsDetruits(plateau, coord, true);
        assertTrue((Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionActive(coord, nbPionsNoir, nbPionsBlancs-nbPionsBlancsAEnlever))));
    }
    @Test
    public void testActionsPossibles_niveau5() {
        JoueurTowa joueur = new JoueurTowa();
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU2);
        boolean estNoir = true;
        int niveau = 5;
        int nbPionsNoir = JoueurTowa.nbPions(plateau, true);
        int nbPionsBlancs = JoueurTowa.nbPions(plateau, false);
        
        String[] actionsPossibles = joueur.actionsPossibles(plateau, estNoir, niveau);
        Coordonnees coord;
        
        //pose sur une case avec un tour de même couleur de hauteur 1 : possible
        coord = Coordonnees.depuisCars('b', 'A');
        
        assertTrue(Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionPose(coord, nbPionsNoir + 1, nbPionsBlancs)));
        //pose sur une case avec un tour de même couleur de hauteur 4 : impossible
        coord = Coordonnees.depuisCars('c', 'K');
        assertFalse(Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionPose(coord, nbPionsNoir + 1, nbPionsBlancs)));
        //pose sur une case libre non entourée : possible 
        coord = Coordonnees.depuisCars('a', 'A');
        assertTrue(Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionPose(coord, nbPionsNoir + 1, nbPionsBlancs)));
        //Activation d'une tour non présente
        coord = Coordonnees.depuisCars('a', 'A');
        assertFalse((Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionActive(coord, nbPionsNoir, nbPionsBlancs))));
        //activation d'une tour noire entourée 
        coord = Coordonnees.depuisCars('l', 'E');
        int nbPionsBlancsAEnlever = JoueurTowa.nbPionsDetruits(plateau, coord, true);
        assertTrue((Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionActive(coord, nbPionsNoir, nbPionsBlancs-nbPionsBlancsAEnlever))));
        //pose d'un pions a coté d'une tour ennemie 
        coord = Coordonnees.depuisCars('a', 'F');
        assertTrue(Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionPose(coord, nbPionsNoir + 2, nbPionsBlancs)));
        //pose d'un pions a coté d'une tour ennemie en diagonale
        coord = Coordonnees.depuisCars('d', 'D');
        assertTrue(Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionPose(coord, nbPionsNoir + 2, nbPionsBlancs)));
    }
    @Test
    public void testActionsPossibles_niveau6() {
        JoueurTowa joueur = new JoueurTowa();
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_NIVEAU2);
        boolean estNoir = true;
        int niveau = 6;
        int nbPionsNoir = JoueurTowa.nbPions(plateau, true);
        int nbPionsBlancs = JoueurTowa.nbPions(plateau, false);
        
        String[] actionsPossibles = joueur.actionsPossibles(plateau, estNoir, niveau);
        Coordonnees coord;
        
        //pose sur une case avec un tour de même couleur de hauteur 1 : possible
        coord = Coordonnees.depuisCars('b', 'A');
        
        assertTrue(Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionPose(coord, nbPionsNoir + 1, nbPionsBlancs)));
        //pose sur une case avec un tour de même couleur de hauteur 4 : impossible
        coord = Coordonnees.depuisCars('c', 'K');
        assertFalse(Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionPose(coord, nbPionsNoir + 1, nbPionsBlancs)));
        //pose sur une case libre non entourée : possible 
        coord = Coordonnees.depuisCars('a', 'A');
        assertTrue(Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionPose(coord, nbPionsNoir + 1, nbPionsBlancs)));
        //Activation d'une tour non présente
        coord = Coordonnees.depuisCars('a', 'A');
        assertFalse((Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionActive(coord, nbPionsNoir, nbPionsBlancs))));
        //activation d'une tour noire entourée 
        coord = Coordonnees.depuisCars('l', 'E');
        int nbPionsBlancsAEnlever = JoueurTowa.nbPionsDetruits(plateau, coord, true);
        assertTrue((Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionActive(coord, nbPionsNoir, nbPionsBlancs-nbPionsBlancsAEnlever))));
        //pose d'un pions a coté d'une tour ennemie 
        coord = Coordonnees.depuisCars('a', 'F');
        assertTrue(Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionPose(coord, nbPionsNoir + 2, nbPionsBlancs)));
        //pose d'un pions a coté d'une tour ennemie en diagonale
        coord = Coordonnees.depuisCars('d', 'D');
        assertTrue(Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionPose(coord, nbPionsNoir + 2, nbPionsBlancs)));
    }
    @Test 
    public void testActionsPossibles_niveau7(){
        JoueurTowa joueur = new JoueurTowa();
        Case[][] plateau = Utils.plateauDepuisTexte(PlATEAU_NIVEAU7);
        boolean estNoir = true;
        int niveau = 7;
        int nbPionsNoir = JoueurTowa.nbPions(plateau, true);
        int nbPionsBlancs = JoueurTowa.nbPions(plateau, false);
        
        String[] actionsPossibles = joueur.actionsPossibles(plateau, estNoir, niveau);
        Coordonnees coord;
        //pose sur une case avec un tour de même couleur de hauteur 1 : possible
        coord = Coordonnees.depuisCars('g', 'D');
                assertTrue(Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionPose(coord, nbPionsNoir + 1, nbPionsBlancs)));
        //Activation d'une tour non présente
        coord = Coordonnees.depuisCars('a', 'A');
        assertFalse((Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionActive(coord, nbPionsNoir, nbPionsBlancs))));
        //activation d'une tour noire entourée 
        coord = Coordonnees.depuisCars('g', 'F');
        int nbPionsBlancsAEnlever = JoueurTowa.nbPionsDetruits(plateau, coord, true);
        assertTrue((Utils.actionsPossiblesContient(actionsPossibles,
                JoueurTowa.chaineActionActive(coord, nbPionsNoir, nbPionsBlancs-nbPionsBlancsAEnlever))));
        
    }
    @Test
    public void testNbPions() {
        
        // plateau1 : 0 noir, 0 blanc
        Case[][] plateau1 = Utils.plateauDepuisTexte(PLATEAU_NIVEAU1);
        assertEquals(0, JoueurTowa.nbPions(plateau1, true));
        assertEquals(0, JoueurTowa.nbPions(plateau1, false));
        // plateau2 : 26 noirs, 20 blancs
        Case[][] plateau2 = Utils.plateauDepuisTexte(PLATEAU_NIVEAU2);
        assertEquals(26, JoueurTowa.nbPions(plateau2, true));
        assertEquals(20, JoueurTowa.nbPions(plateau2, false));        
        //plateau7 : 
        Case[][] plateau7 = Utils.plateauDepuisTexte(PlATEAU_NIVEAU7);
        assertEquals(7, JoueurTowa.nbPions(plateau7, true));
        assertEquals(15, JoueurTowa.nbPions(plateau7, false));
    }
    @Test
    public void testNbPionsDetruits(){
        Case[][] plateau3 = Utils.plateauDepuisTexte(PLATEAU_NIVEAU3);
        Coordonnees coord;
        //activation d'une tour noire entourée de tours plus hautes
        coord = Coordonnees.depuisCars('a', 'A');
        assertEquals(0, JoueurTowa.nbPionsDetruits(plateau3,coord, true));
        //activation d'une tour noire entourée d'une tour blanche plus basse : doit détruire 1
        coord = Coordonnees.depuisCars('a', 'B');
        assertEquals(3, JoueurTowa.nbPionsDetruits(plateau3,coord, true)); 
        //activation d'une tour noire entourée de tours plus hautes
        coord = Coordonnees.depuisCars('b', 'B');
        assertEquals(0, JoueurTowa.nbPionsDetruits(plateau3,coord, false));
        //Activation d'une tour blanche : 6 noir détruits
        coord = Coordonnees.depuisCars('b', 'C');
        assertEquals(6, JoueurTowa.nbPionsDetruits(plateau3,coord, false));
        //Activation tour blanche : 0 détruits
        coord = Coordonnees.depuisCars('p', 'F');
        assertEquals(0, JoueurTowa.nbPionsDetruits(plateau3,coord, false));
    }
    @Test
    public void testChaineActionPose() {
        assertEquals("PfK,3,8", 
                JoueurTowa.chaineActionPose(Coordonnees.depuisCars('f', 'K'), 3, 8));
        assertEquals("PaA,0,0", 
                JoueurTowa.chaineActionPose(Coordonnees.depuisCars('a', 'A'), 0, 0));
        assertEquals("PpP,10,10", 
                JoueurTowa.chaineActionPose(Coordonnees.depuisCars('p', 'P'), 10, 10));
    }
   @Test
    public void testChaineActionActive() {
        assertEquals("AfK,3,8", 
                JoueurTowa.chaineActionActive(Coordonnees.depuisCars('f', 'K'), 3, 8));
        assertEquals("AaA,0,0", 
                JoueurTowa.chaineActionActive(Coordonnees.depuisCars('a', 'A'), 0, 0));
        assertEquals("ApP,10,10", 
                JoueurTowa.chaineActionActive(Coordonnees.depuisCars('p', 'P'), 10, 10));
    }
   @Test
   public void testvoisineEnemieJoueurNoir(){
       Case[][] plateau2 = Utils.plateauDepuisTexte(PLATEAU_NIVEAU2);
       Coordonnees coord;
       //Tour blanche présente en jouant le noir
       coord = Coordonnees.depuisCars('a', 'F');
       assertEquals(true,JoueurTowa.voisineEnemiePresente(plateau2, coord, true) );
       coord = Coordonnees.depuisCars('k', 'N');
       assertEquals(true,JoueurTowa.voisineEnemiePresente(plateau2, coord, true) );
       //Pas de tour voisines 
       coord = Coordonnees.depuisCars('e', 'E');
       assertEquals(false,JoueurTowa.voisineEnemiePresente(plateau2, coord, true) );
   }
   @Test
      public void testvoisineEnemieJoueurBlanc(){
       Case[][] plateau2 = Utils.plateauDepuisTexte(PLATEAU_NIVEAU2);
       Coordonnees coord;
       //Tour blanche présente en jouant le blanc
       coord = Coordonnees.depuisCars('b', 'B');
       assertEquals(true,JoueurTowa.voisineEnemiePresente(plateau2, coord, false) );
       coord = Coordonnees.depuisCars('d', 'K');
       assertEquals(true,JoueurTowa.voisineEnemiePresente(plateau2, coord, false) );
       //pas de tour voisines
       coord = Coordonnees.depuisCars('e', 'E');
       assertEquals(false,JoueurTowa.voisineEnemiePresente(plateau2, coord, false) );
   }
    /**
     * Un plateau de base, sous forme de chaîne. Pour construire une telle
     * chaîne depuis votre sortie.log, déclarez simplement : final String
     * MON_PLATEAU = ""; puis copiez le plateau depuis votre sortie.log, et
     * collez-le entre les guillemets. Puis Alt+Shift+f pour mettre en forme.
     */
    final String PLATEAU_NIVEAU1
            = "   A   B   C   D   E   F   G   H   I   J   K   L   M   N   O   P \n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "a|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "b|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "c|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "d|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "e|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "f|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "g|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "h|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "i|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "j|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "k|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "l|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "m|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "n|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "o|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "p|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+";

    final String PLATEAU_NIVEAU2
            = "   A   B   C   D   E   F   G   H   I   J   K   L   M   N   O   P \n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "a|   |   |   |   |   |   |B1 |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "b|N1 |   |   |   |   |   |   |B1 |   |   |   |B1 |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "c|   |   |B1 |   |B1 |   |   |   |   |   |N4 |B1 |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "d|   |   |   |   |   |   |   |   |B1 |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "e|B1 |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "f|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "g|   |   |B1 |   |   |   |   |   |   |N1 |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "h|   |   |   |   |   |   |   |   |   |   |N1 |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "i|   |N1 |N1 |   |   |   |   |   |   |   |   |   |N1 |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "j|   |   |   |   |   |   |   |N1 |   |   |   |B1 |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "k|   |   |   |   |N1 |   |   |   |   |N2 |   |   |   |   |B1 |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "l|   |   |   |   |N3 |B4 |B1 |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "m|   |   |   |   |B1 |B2 |N1 |   |   |N1 |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "n|   |   |   |   |N1 |N1 |N2 |   |   |N1 |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "o|   |N1 |   |   |   |   |   |N1 |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "p|   |   |   |   |   |   |B1 |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n";
    final String PLATEAU_NIVEAU3
            = "   A   B   C   D   E   F   G   H   I   J   K   L   M   N   O   P \n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "a|N1 |N3 |N4 |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "b|   |B1 |B4 |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "c|   |B1 |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "d|   |B1 |N3 |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "e|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "f|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "g|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "h|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "i|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "j|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "k|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "l|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "m|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "n|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "o|   |   |   |   |   |B4 |B3 |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "p|   |   |   |   |   |B4 |B4 |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n";
    final String PlATEAU_NIVEAU7
            = " A   B   C   D   E   F   G   H   I   J   K   L   M   N   O   P \n" +
            " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n" +
            "a|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
            " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n" +
            "b|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
            " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n" +
            "c|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
            " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n" +
            "d|   |   |   |   |   |B1 |   |   |   |   |   |   |   |   |   |   |\n" +
            " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n" +
            "e|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
            " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n" +
            "f|   |   |   |   |   |N2 |B2 |   |   |   |   |   |   |   |   |   |\n" +
            " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n" +
            "g|   |   |B1 |N1 |   |N3 |   |   |B1 |B1 |   |N1 |   |   |   |   |\n" +
            " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n" +
            "h|   |   |   |   |B4 |   |   |   |   |   |   |   |   |   |   |   |\n" +
            " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n" +
            "i|   |   |   |   |   |B3 |   |B1 |   |   |   |   |   |   |   |   |\n" +
            " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n" +
            "j|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
            " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n" +
            "k|   |   |   |   |   |B1 |   |   |   |   |   |   |   |   |   |   |\n" +
            " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n" +
            "l|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
            " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n" +
            "m|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
            " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n" +
            "n|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
            " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n" +
            "o|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
            " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n" +
            "p|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
            " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n";
            
    
}

