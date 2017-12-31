package JeuDeGo.Go;

import java.util.ArrayList;

public class Joueur {

	private String pseudo;
	private int score;
	ArrayList<Pierre> ListePierre = new ArrayList<Pierre>();
	private CouleurPierre coul;
	private boolean passe;
	private int nbPrisonniers;
	
	public Joueur(String pseudo, CouleurPierre coul){

		this.passe = false;
		this.score = 0;
		this.nbPrisonniers = 0;
		this.pseudo = pseudo;
		this.coul = coul;

	}
	
	//Getters et setters		
	public boolean getPasse(){
		return passe;
	}
	
	public void setPasse(boolean b){
		passe = b;
	}


	public CouleurPierre getCoul() {
		return coul;
	}

	public void setCoul(CouleurPierre coul) {
		this.coul = coul;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getNbPrisonniers() {
		return nbPrisonniers;
	}

	public void setNbPrisonniers(int nbPrisonniers) {
		this.nbPrisonniers = nbPrisonniers;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public void ajoutePoint(int point) {
		 score += point;
		
	}
	public void ajoutePrisonniers(int nb) {
		nbPrisonniers+=nb;
	}
	
		
}
