import java.util.Scanner;
import java.util.Random;

public class Plateau {
    static int hauteur, largeur, nbMines;
    static public String[][] plateau;
    private static boolean[][] mines;
        /*indique où sont les mines sur le plateau*/
    private int[][] etats;
        /*indique dans quel état est chaque case
          (cachée, révélée, avec/sans drapeau)*/
    private int[][] adja;
        /*indique le nombre de mines adjacentes 
          à chaque case*/

    //CONSTRUCTEUR
    public Plateau(int hauteur, int largeur, int nbMines){
      this.hauteur = hauteur;
      this.largeur = largeur;
      this.nbMines = nbMines;
      this.plateau = new String [hauteur][largeur];
      this.mines = new boolean [hauteur][largeur];
      this.adja = new int [hauteur][largeur];
      this.etats = new int [hauteur][largeur];
    }
    
    
    //GETTERS 
    
    
    public static boolean getMines(Plateau p, int hauteur, int largeur){
      return p.mines[hauteur][largeur];
    }
    
    
    public static int getEtats(Plateau p, int hauteur, int largeur){
      return p.etats[hauteur][largeur];
    }
    
    
    //methode qui verifie si les caracteres d'un string sont alphabetiques (pour detecter erreurs de frappe durant creerPlateau())
    public static boolean isAlpha(String string){
      char[] chars = string.toCharArray();
      for (char c : chars) {
          if(!Character.isLetter(c)) {
              return false;
          }
      }
      System.out.print("Vous avez saisi des caracteres alphabetiques ou une valeur nulle. ");
      return true;
    }
    
    
    
    //AUTRES METHODES DE LA CLASSE PLATEAU
    
    
    
    //methode qui initialise un Plateau p selon les choix de l'utilisateur
    public static Plateau creerPlateau(){
      Scanner in = new Scanner(System.in);
      int hauteur = 0; int largeur = 0; int nbMines = 0;
      
      System.out.println("Veuillez indiquer en chiffres la hauteur souhaitee pour le plateau : ");
      String shauteur = in.nextLine();
      while (isAlpha(shauteur) || shauteur.equals("0")){
        System.out.println("Veuillez indiquer le nombre de lignes souhaitees pour le plateau : ");
        shauteur = in.nextLine();
      }
      if (isAlpha(shauteur) == false){
        hauteur = Integer.parseInt(shauteur);
      }
      System.out.println("Veuillez maintenant indiquer en chiffres le nombre de colonnes souhaitee pour le plateau : ");
      String slargeur = in.nextLine();
      while (isAlpha(slargeur)){
        System.out.println("Veuillez indiquer en nombre de cases la largeur souhaitée pour le plateau : ");
        slargeur = in.nextLine();
      }
      if (isAlpha(slargeur) == false){
        largeur = Integer.parseInt(slargeur);
      }

      System.out.println("Veuillez enfin indiquer en chiffres le nombre de mines souhaitées pour cette partie : ");
      String sNbMines = in.nextLine();
      while (isAlpha(sNbMines)){
        System.out.println("Veuillez indiquer en chiffres le nombre de mines souhaitées pour le plateau : ");
        sNbMines = in.nextLine();
      }
      if (isAlpha(sNbMines) == false){
        nbMines = Integer.parseInt(sNbMines);
        while (nbMines >= hauteur * largeur){
          System.out.println("Le nombre de mines est egal ou superieur au nombre de cases du plateau.");
          System.out.println("Veuillez indiquer en chiffres le nombre de mines souhaitees pour cette partie : ");
          sNbMines = in.nextLine();
          if (isAlpha(sNbMines) == false){
            nbMines = Integer.parseInt(sNbMines);
          }
        }
      }
      Plateau p = new Plateau(hauteur, largeur, nbMines);
      ajouteMinesAlea(p, hauteur, largeur, nbMines);
      initialiseEtats(p, hauteur, largeur);
      System.out.println();
      System.out.println("* LE DEMINEUR *");
      afficherPlateau(p);
      return p;
    }
    
    
    //toutes les cases sont a l'etat caché (= 0). 
    //p.etats = 0 --> caché ; =1 --> revelé
    public static void initialiseEtats(Plateau p, int hauteur, int largeur){
      for(int h=0 ; h <= hauteur-1 ; h++){
        for(int l=0 ; l <= largeur-1; l++){
          p.etats[h][l] = 0;
        }
      }
    }
      
      
      
      public static void afficherPlateau(Plateau p){
        System.out.println(); //creer un espace en haut du plateau

        //affichage du numero des cases en largeur:
        //quand la largeur est > 10, il se forme un decalage entre les numeros et le contenu du plateau 
        //il faut y remedier par des ajouts d'espaces 
        for (int i = 0; i<= largeur ; i++){
          if (i == 0 | i >= 10) {
            System.out.print(" "+i+" ");
          } else if (i > 0 | i < 10) {
            System.out.print("  "+i+" ");//il suffit de rajouter 2 espaces au lieu d'un quand il n'y a qu'un seul chiffre a afficher 
          }
        }
        System.out.println();//retour a la ligne a la fin de l'iteration
        
        //affichage des numeros de case en hauteur:
        for(int h = 0; h<hauteur; h++){
          if ((h+1) <10){ //meme probleme avec les espaces pour la hauteur
          System.out.print(" "+(h+1)+"  ");//on rajoute 2 espaces au lieu d'un apres le chiffre a afficher
          } else if ((h+1) >= 10) {
            System.out.print(" "+(h+1)+" ");
            
          }//affichage du contenu du tableau
          for(int l = 0; l<largeur; l++){
              if(p.etats[h][l] == 0){
                p.plateau[h][l] = " .  ";
              } else if (p.etats[h][l] == 1 && p.mines[h][l] == false){
                p.plateau[h][l] = " "+Integer.toString(p.adja[h][l])+"  ";
              } else if (p.etats[h][l] == 2){
                p.plateau[h][l] = " ?  ";
              } else if (p.etats[h][l] == 1 && p.mines[h][l] == true ) {
              p.plateau[h][l] = " *  "; //affiche * si mine.
            }
            
            System.out.print(p.plateau[h][l]);// permet d'afficher le contenu du tableau sur une meme ligne
          }//finFor
        System.out.println();//retour a la ligne au moment de h+1
        }
        System.out.println();//creer espace en bas du plateau
      }

    
    private static void ajouteMinesAlea(Plateau p, int hauteur, int largeur, int nbMines){
      for(int m=1; m <= nbMines ; m++){
        Random random = new Random();
        int position_h = random.nextInt(hauteur-1);
        int position_l = random.nextInt(largeur-1);
        p.mines[position_h][position_l] = true;
        calculAdjacence(p, position_h, position_l, hauteur, largeur);
      }
    }
    
    
    
    private static void calculAdjacence(Plateau p, int position_h, int position_l, int hauteur, int largeur){
      //si rien ne sort des limites
      if (position_h-1 > -1 & position_h+1 <= hauteur-1 & position_l-1 > -1 & position_l+1 <= largeur-1 ){
        p.adja[position_h-1][position_l-1] += 1;
        p.adja[position_h-1][position_l] += 1;
        p.adja[position_h-1][position_l+1] += 1;
        
        p.adja[position_h][position_l-1] += 1;
        p.adja[position_h][position_l+1] += 1;
        
        p.adja[position_h+1][position_l-1] += 1;
        p.adja[position_h+1][position_l] += 1;
        p.adja[position_h+1][position_l+1] += 1;
        
        //si la position_h-1 n'existe pas sur le plateau
      } else if (position_h-1 < 0){
          p.adja[position_h+1][position_l] += 1;
          //si la position_l-1 n'existe pas sur le plateau
          if (position_l-1 < 0 ){
            p.adja[position_h][position_l+1] += 1;
            p.adja[position_h+1][position_l+1] += 1;
          }//ou alors si la position_l+1 sort des limites 
          else if (position_l+1 > largeur-1){ 
            p.adja[position_h+1][position_l-1] += 1;
            p.adja[position_h][position_l-1] += 1;
          } else {
            p.adja[position_h][position_l+1] += 1;
            p.adja[position_h+1][position_l+1] += 1;
            p.adja[position_h+1][position_l-1] += 1;
            p.adja[position_h][position_l-1] += 1;
          }
        
        //si h+1 n'existe pas sur le plateau  
      } else if (position_h+1 > hauteur-1){
          p.adja[position_h-1][position_l] += 1;
          //si la position_l-1 n'existe pas sur le plateau
          if  (position_l-1 < 0 ){
            p.adja[position_h-1][position_l+1] += 1;
            p.adja[position_h][position_l+1] += 1;
          }//ou alors si la position_l+1 sort des limites 
          else if (position_l+1 > largeur-1){
            p.adja[position_h-1][position_l-1] += 1;
            p.adja[position_h][position_l-1] += 1;
          } else {
            p.adja[position_h-1][position_l+1] += 1;
            p.adja[position_h][position_l+1] += 1;
            p.adja[position_h-1][position_l-1] += 1;
            p.adja[position_h][position_l-1] += 1;
          }
        }
    }
   
   
   
   public static void revelerCase(Plateau p, int position_h, int position_l){
     //p.etats = 0 --> caché ; =1 --> revelé ; (=2 --> drapeau '?')
     if (p.mines[position_h][position_l] == false){
       p.etats[position_h][position_l] = 1;
       System.out.println(p.etats[position_h][position_l]);
     }
   }
   
   
   
   public static void afficherPerdu(Plateau p){
    System.out.println(); 
    System.out.println();
    for (int i = 0; i<= largeur ; i++){
      if (i == 0 | i >= 10) {
        System.out.print(" "+i+" ");
        } else if (i > 0 | i < 10) {
        System.out.print("  "+i+" ");
        }
      }
      System.out.println();
      for(int h = 0; h<hauteur; h++){
        if ((h+1) <10){
          System.out.print(" "+(h+1)+"  "); 
        } else if ((h+1) >= 10) {
          System.out.print(" "+(h+1)+" ");
        }
        for(int l = 0; l<largeur; l++){
          if (p.mines[h][l] == true) {
              p.plateau[h][l] = " *  "; //affiche * si mine. 
          } else if (p.adja[h][l] > 0 & p.mines[h][l] == false){
            p.plateau[h][l] = " "+Integer.toString(p.adja[h][l])+"  ";
          } else {
            p.plateau[h][l] = " .  "; 
              }
          System.out.print(p.plateau[h][l]);// permet d'afficher le contenu du tableau sur une meme ligne
          }//finFor
          System.out.println();//retour a la ligne au moment de h+1
        }
        System.out.println();//creer espace en bas du plateau
        System.out.println("**** PERDU ****");
        System.out.println();//creer espace en bas du plateau
      }
}
