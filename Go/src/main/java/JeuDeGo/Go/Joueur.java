package JeuDeGo.Go;

public class Joueur {

	private String pseudo;
	private int score;
	private Pierre pierre;
	private boolean fin;
	private int nbPrisonniers;
	
	public Joueur(String pseudo, Pierre pierre, int score){
		this.pseudo = pseudo;
		this.pierre = pierre;
		fin = false;
		nbPrisonniers = 0;
		this.score = score;
	}
	
	//Getters et setters
	public String getPseudo(){
		return pseudo;
	}
	
	public int getNbPrisonniers() {
		return nbPrisonniers;
	}
	
	public Pierre getPierre(){
		return pierre;
	}
	
	public int getScore(){
		return score;
	}
	
	public boolean getFin(){
		return fin;
	}


	//Augmente le nombre de prisonniers du joueur 
	public void addPrisonniers(int nb) {
		nbPrisonniers+=nb;
	}

	
	//Augmente le score du joueur
	public void addPoint(int p){
		score+=p;
	}
	
	
	//si le joueur souhaite terminer la partie ou pas
	public void setFin(boolean b){
		fin = b;
	}
	
	
}
