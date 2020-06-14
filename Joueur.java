public class Joueur {
    private static String nom;
    
    public Joueur(){
        //Par défaut, le nom du joueur est "Anonyme".
        nom = "Anonyme";
    }
    
    public static void setNom(String newNom){
      nom = newNom;
    }
    
    public static String getNom(){
      return nom;
    }
}
