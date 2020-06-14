import java.util.Scanner;

public class Main {
    public static void main (String[] args){
      
        boolean nouvellePartie;
        nouvellePartie = Jeu.demanderPartie();
        Jeu.demanderNom();
        
        while (nouvellePartie == true){
          Plateau p = Plateau.creerPlateau();
          Jeu.demanderCoup(p, Plateau.hauteur, Plateau.largeur);
          Plateau.afficherPlateau(p);
          Plateau.afficherPerdu(p);
          nouvellePartie = Jeu.demanderPartie();
        }
    }
}
