import java.util.Scanner;

public class Jeu {
    private Joueur leJoueur;
    private Plateau lePlateau;
    
    
    //methode qui demande a l'utilisateur s'il souhaite jouer une nouvelle partie
    public static boolean demanderPartie(){
      System.out.println("Souhaitez vous creer une nouvelle partie ? O / N");
          boolean continuer = true;
          Scanner in = new Scanner(System.in);
          String reponse = in.nextLine().toUpperCase();
          while(!reponse.equals("O") && !reponse.equals("N")) {
            System.out.println("Le caractere saisi n'etait ni 'O' ni 'N'. Souhaitez vous creer une nouvelle partie ? O / N");
            reponse = in.nextLine().toUpperCase();
            } 
            if (reponse.equals("N")){
              System.out.println("La partie est terminee.");
              continuer = false;
            } else if (reponse.equals("O")){
              continuer = true;
            }
          return continuer;
    }
    
    
    
    public static void demanderNom(){
      System.out.println("Quel est votre nom ?");
      Scanner in = new Scanner(System.in);
      String reponse = in.nextLine();
      Joueur j = new Joueur();
      j.setNom(reponse);
      System.out.print("Bonjour "+ reponse + ". ");
    }
    
    
    
    public static void demanderCoup(Plateau p, int hauteurplateau, int largeurplateau){
      System.out.println("Quel coup désirez-vous jouer ?");
      int colonne = validiteCoup("Nº de la colonne : ", largeurplateau)-1;
      int ligne = validiteCoup("Nº de la ligne : ", hauteurplateau)-1 ;
      boolean presenceMine = Plateau.getMines(p, colonne, ligne);
      while(presenceMine == false){
        Plateau.revelerCase(p, ligne, colonne);
        Plateau.afficherPlateau(p);
        System.out.println("Mine evitée.");
        demanderCoup(p, hauteurplateau, largeurplateau);
        presenceMine = true;
      }
    }
    
    
  
    //methode qui pose la question passee en argument en affichant un message d'erreur chaque fois que la reponse saisie n'est pas un caractere numerique
    
    public static int validiteCoup(String question, int tailleplateau){
      System.out.print(question);
      Scanner p = new Scanner(System.in);
      String position = p.nextLine();
      int positionJouee = 1;
      while(Plateau.isAlpha(position) || position == "0" || Integer.parseInt(position) > tailleplateau ){
        System.out.print("Veuillez saisir uniquement des caractères numériques et qui ne depassent pas les dimensions du plateau de jeu. ");
        System.out.print(question);
        position = p.nextLine();
      }
      if (Plateau.isAlpha(position) == false){
        positionJouee = Integer.parseInt(position);
      }
      return positionJouee;
    }
}
