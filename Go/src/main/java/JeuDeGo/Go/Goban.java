package JeuDeGo.Go;

import java.util.ArrayList;
import java.util.List;

public class Goban {
	
	private int taille;
	private List<Intersection> inters = new ArrayList<Intersection>();
	private List<Integer> indexInterPrisonniers = new ArrayList<Integer>();
	
	//Getters et setters
	public List<Intersection> getIntersections(){
		return inters;
	}
	
	public int getTaille() {
		return taille;
	}

	public void setTaille(int taille) {
		this.taille = taille;
	}
	
	//Creation du goban
	public Goban(int taille){
		this.setTaille(taille);
		for(int x = 0; x < taille ; x++){
			for(int y = 0; y < taille ; y++){
				inters.add(new Intersection(x, y));
			}
		}
	}
	
	//ajouter une pierre sur une intersection du goban
	public int ajoutePierre(int x, int y, Pierre p){
		int nb_prisonniers=0;
		for(Intersection inter : inters){
			if( inter.getX() == x && inter.getY() == y){
				
				//s'il n'y a pas pierre qui existent deja sur l'intersection
				//on pose la pierre sur l'intersection
				if(inter.getPierre()==null){       
					inter.setPierre(p);
					nb_prisonniers += actualiseGoban(x, y, p); //on actualise le goban.On supprime en effet les pièrres que la pierre posee permet de capturer
					
					//si la pierre posee ne permet pas de capturer des prisonniers
					//on verifie si elle meme n'est pas prisonnière
					//si c'est le cas, on la supprime
					if (nb_prisonniers == 0) {
						
						indexInterPrisonniers.clear();
						
						//Si la pierre est de couleur blanche, elle est potentiellement prisonniere des pierres de couleur noire
						if (p.getCouleur()==CouleurPierre.Blanc) {
							prisonniersPotentiels(x, y, new Pierre(CouleurPierre.Noir));
						}
						
						//Si la pierre est de couleur noire, elle est potentiellement prisonniere des pierres de couleurs noirs
						if (p.getCouleur()==CouleurPierre.Noir) {
							prisonniersPotentiels(x, y, new Pierre(CouleurPierre.Blanc));
						}
						
						if(estPrisonniers()){
							
							supprimePierre(x, y);
							return -1;
						}
					}
				}
					
				return nb_prisonniers;
				}
			}
		
		return -1;
	}
	
	
	//Supprime la pierre sur une intersection donnee
	public Pierre supprimePierre(int x, int y){
		for(Intersection inter : inters){
			if( inter.getX() == x && inter.getY() == y && inter.getPierre()!= null){
				Pierre p = inter.getPierre();
				inter.setPierre(null);
				return p;
			}
		}
		return null;		
	}
	
	
	//obtient la pierre sur une intersection donnee
	public Pierre getPierre(int x, int y){
		
		for(Intersection inter : inters){
			if( inter.getX() == x && inter.getY() == y){
				return inter.getPierre();
			}
		}
		return null;
	}
	
	//actualise le goban apres qu'une pierre ait ete posee
	public int actualiseGoban(int x, int y, Pierre p) {
		
		int prisonnier = 0;
		
		//lorsqu'on est sur l'intersection de la pierre qui a ete posee
		for(Intersection inter : inters){
			
			//On pointe sur l'intersection situee en bas de celle ci s'il y en a
			//si elle contient une pierre
			//et si la couleur de cette derniere est differente de celle de la pierre posee, elle est potentiellement prisonniere
			//si au final elle l'est, on la supprime
			if(inter.getPierre() !=null && inter.getX() == x && inter.getY() == y-1){
				if(inter.getPierre().getCouleur()!= p.getCouleur()){
					indexInterPrisonniers.clear();
					prisonniersPotentiels(x, y-1, p);
					
					if(estPrisonniers()){
						prisonnier += supprimePrisonniers();
					}
				}
			}
			//On pointe sur la pierre de droite. On la supprime si elle est prisonnière
			if(inter.getPierre() !=null && inter.getX() == x+1 && inter.getY() == y){
				if(inter.getPierre().getCouleur()!=p.getCouleur()){
					indexInterPrisonniers.clear();
					prisonniersPotentiels(x+1, y, p);
					if(estPrisonniers()){
						prisonnier += supprimePrisonniers();
					}
					
				}				
			}
			//On pointe sur la pièrre du haut. On la supprime si elle est prisonnière
			if(inter.getPierre() !=null && inter.getX() == x && inter.getY() == y+1){
				if(inter.getPierre().getCouleur()!=p.getCouleur()){
					indexInterPrisonniers.clear();
					prisonniersPotentiels(x, y+1, p);
					if(estPrisonniers()){
						prisonnier += supprimePrisonniers();
					}
				}					
			}
			//On pointe sur la pièrre de gauche. On la supprime si elle est prisonnière
			if(inter.getPierre() !=null && inter.getX() == x-1 && inter.getY() == y){
				if(inter.getPierre().getCouleur()!=p.getCouleur()){
					indexInterPrisonniers.clear();
					prisonniersPotentiels(x-1, y, p);
					if(estPrisonniers()){
						prisonnier += supprimePrisonniers();
					}
				}					
			}
		}
		return prisonnier;
	}

	public boolean estPrisonniers() {
		
		
		for ( int i : indexInterPrisonniers) {
            
			//S'il y a une intersection située au dessus de la pierre et si elle n'est pas occupée
			//La pierre n'est pas prisonniere
            if (inters.get(i).getY() + 1 <= taille-1) {
                
            	int j = determineIndex(inters.get(i).getX(), inters.get(i).getY(), taille);
            	
            	if (inters.get(j).getPierre() == null) {
                    return false;
                }
            }
            
            //S'il y a une intersecton située en dessous de la pierre et si elle n'est pas occupée
            //La pierre n'est pas prisonniere
            if (inters.get(i).getY() - 1 >= 0) {
            	
            	int j = determineIndex(inters.get(i).getX(), inters.get(i).getY(), taille);
            	
            	if (inters.get(j).getPierre() == null) {
                    return false;
                }
            }
            
         
            //S'il y a une intersecton située a droite de la pierre et si elle n'est pas occupée
            //La pierre n'est pas prisonniere
            if (inters.get(i).getX() + 1 <= taille-1) {
            	
            	int j = determineIndex(inters.get(i).getX()+1, inters.get(i).getY(), taille);
            	
            	if (inters.get(j).getPierre() == null) {
                    return false;
                }
            }
            
            //S'il y a une intersecton située a droite de la pierre et si elle n'est pas occupée
            //La pierre n'est pas prisonniere
            if (inters.get(i).getX() - 1 >= 0) {
            	
            	int j = determineIndex(inters.get(i).getX()-1, inters.get(i).getY(), taille);
            	
            	if (inters.get(j).getPierre() == null) {
                    return false;
                }
            }
        }
        return true;
		
	}
	
	//determine l'index de l'intersection de coordonnees (x,y) dans la liste d'intersection
	public int determineIndex(int x, int y, int taille) {
		
		int ind = y + x * taille;
		return ind;
	}
	
	public int supprimePrisonniers() {
		
		int nbAsupprimer = indexInterPrisonniers.size();
		for (int i : indexInterPrisonniers) {
			inters.get(i).setPierre(null);
		}
		return nbAsupprimer;
	}
	
	
	public void prisonniersPotentiels(int x, int y, Pierre p) {
		
		for(int i=0; i<inters.size(); i++){
			
			//on ajoute la pierre
			if(inters.get(i).getX() == x && inters.get(i).getY() == y){
				if(indexInterPrisonniers.indexOf(i)==-1){
					indexInterPrisonniers.add(i);
				}
			}
			
			//on ajoute la pierre du haut si elle est de meme couleur que la pierre
			if(inters.get(i).getX() == x && inters.get(i).getY() == y+1){
				if(inters.get(i).getPierre()!= null) {
					if(inters.get(i).getPierre().getCouleur() == p.getCouleur()){
						if(indexInterPrisonniers.indexOf(i)==-1){
							indexInterPrisonniers.add(i);
							prisonniersPotentiels(x, y+1, p);
						}
					}
				} 
			}
			
			//on ajoute la pierre du bas si elle est de meme couleur que la pierre
			if(inters.get(i).getX() == x && inters.get(i).getY() == y-1){
				if(inters.get(i).getPierre()!= null) {
					if(inters.get(i).getPierre().getCouleur() != p.getCouleur()){
						if(indexInterPrisonniers.indexOf(i)==-1){
							indexInterPrisonniers.add(i);
							prisonniersPotentiels(x, y-1, p);
						}
					}
					
				}
			}
			
			//on ajoute la pierre de gauche si elle est de meme couleur que la pierre
			if(inters.get(i).getX() == x-1 && inters.get(i).getY() == y){
				if(inters.get(i).getPierre()!= null){
					if(inters.get(i).getPierre().getCouleur() == p.getCouleur()){
						if(indexInterPrisonniers.indexOf(i)==-1){
							indexInterPrisonniers.add(i);
							prisonniersPotentiels(x-1, y, p);
						}
					}
					
				}
			}
			
			//on ajoute la pierre de droite si elle est de meme couleurs que la pierre
			if(inters.get(i).getX() == x+1 && inters.get(i).getY() == y){
				if(inters.get(i).getPierre()!= null){
					if(inters.get(i).getPierre().getCouleur() == p.getCouleur()){
						if(indexInterPrisonniers.indexOf(i)==-1){
							indexInterPrisonniers.add(i);
							prisonniersPotentiels(x+1, y, p);
						}
					}
					
				}
			}
	
			
		}
	}
	
	
	public void creeGroupeTerritoire(int x, int y, List<Integer> indexIntersVide) {
		
		for(int i=0; i<inters.size(); i++){
			
			//l'intersection
			if(inters.get(i).getX() == x && inters.get(i).getY() == y){
				if(indexIntersVide.indexOf(i)==-1){
					indexIntersVide.add(i);
				}
			}
			
			//l'intersection située au dessus
			if(inters.get(i).getX() == x && inters.get(i).getY() == y+1){
				if(inters.get(i).getPierre()== null){
					if(indexIntersVide.indexOf(i)==-1){
						indexIntersVide.add(i);
						creeGroupeTerritoire(x, y+1, indexIntersVide);
					}
				}
			}
			
			//l'intersection située en dessous
			if(inters.get(i).getX() == x && inters.get(i).getY() == y-1){
				if(inters.get(i).getPierre()== null){
					if(indexIntersVide.indexOf(i)==-1){
						indexIntersVide.add(i);
						creeGroupeTerritoire(x, y-1, indexIntersVide);
					}
				}
			}
			

			//l'intersection située à gauche
			if(inters.get(i).getX() == x-1 && inters.get(i).getY() == y){
				if(inters.get(i).getPierre() == null){
					if(indexIntersVide.indexOf(i)==-1){
						indexIntersVide.add(i);
						creeGroupeTerritoire(x-1, y, indexIntersVide);
					}
				}
			}
			
			//l'intersection située à droite
			if(inters.get(i).getX() == x+1 && inters.get(i).getY() == y){
				if(inters.get(i).getPierre()== null){
					if(indexIntersVide.indexOf(i)==-1){
						indexIntersVide.add(i);
						creeGroupeTerritoire(x+1, y, indexIntersVide);
					}
				}
			}
			
			
		}
	}
	
	public Pierre proprietaireTerritoire(List<Integer> indexIntersVide)
	{
		Pierre proprietaire = null;
		int j = 0;
		
		for(int i : indexIntersVide){
			//l'intersection située en haut
			if(inters.get(i).getY()+1 <= taille-1){
				j = determineIndex( inters.get(i).getX() ,inters.get(i).getY()+1, taille);
				if(inters.get(j).getPierre()!=null ){
					if(proprietaire == null) {
						proprietaire = inters.get(j).getPierre();
					}
					else {
						if(inters.get(j).getPierre().getCouleur()!=proprietaire.getCouleur()){
							return null;
						}
					}
					
				}
			}
			
			//l'intersection située en bas
			if(inters.get(i).getY()-1 >= 0){
				j = determineIndex( inters.get(i).getX() ,inters.get(i).getY()+1, taille);
				if(inters.get(j).getPierre()!=null){
					if(proprietaire == null) {
						proprietaire = inters.get(j).getPierre();
					}
					else {
						if(inters.get(j).getPierre().getCouleur()!=proprietaire.getCouleur()){
							return null;
						}
					}
										
				}
			}
			
			
			//l'intersection située à gauche
			if(inters.get(i).getX()-1 >=0){
				j = determineIndex( inters.get(i).getX()-1 ,inters.get(i).getY(), taille);
				if(inters.get(j).getPierre()!=null){
					if(proprietaire == null) {
						proprietaire = inters.get(j).getPierre();
					}
					else {
						if(inters.get(j).getPierre().getCouleur()!=proprietaire.getCouleur()){
						return null;
						}
					}
				}
			}
			
			//l'intersection située à droite
			if(inters.get(i).getX()+1<=taille-1){
				j = determineIndex( inters.get(i).getX()+1 ,inters.get(i).getY(), taille);
				if(inters.get(j).getPierre()!=null){
					if(proprietaire == null) {
						proprietaire = inters.get(j).getPierre();
					}
					else {
						if(inters.get(j).getPierre().getCouleur()!=proprietaire.getCouleur()){
							return null;
						}
					}
				}
			}
		}	
		
		return proprietaire;
		
	}
	
	public void calculeScore(Joueur j1, Joueur j2) {
		
		List<List<Integer>> listeTerritoire = new ArrayList<List<Integer>>();
		
		for(int i=0; i<inters.size(); i++){
			
			
			if(inters.get(i).getPierre()!=null)
			{
				if(inters.get(i).getPierre().getCouleur()== j1.getCoul()) {
					j1.ajoutePoint(1);
				}
				if(inters.get(i).getPierre().getCouleur()== j2.getCoul()) {
					j2.ajoutePoint(1);
				}
			}
			
			//sinon si l'intersection est vide
			//si l'intersection ne fait pas partir d'un territoire
			//on l'ajoute à un territoire
			else{
				Boolean aTerritoire = false;
				
				for(List<Integer> liste : listeTerritoire){
	
					if(liste.indexOf(i)!=-1){
						
						aTerritoire = true;
						break;
					}
				}
				if(aTerritoire == false){
					List<Integer> newIndexIntersVide = new ArrayList<Integer>();
					creeGroupeTerritoire(inters.get(i).getX(), inters.get(i).getY(), newIndexIntersVide);
					listeTerritoire.add(newIndexIntersVide);
				}
			}
			
		}
		
		//Pour chaque territoire controler par un joueur, on ajoute le nombre d'intersection que le territoire contient à son score
		for(List<Integer> liste : listeTerritoire){
			
			Pierre prop = proprietaireTerritoire(liste);
	
			if(prop != null){
				
				//si c'est un territoire du joueur 1
				if(prop.getCouleur() == j1.getCoul()){
					
					j1.ajoutePoint(liste.size());	
				}
				
				//si c'est un territoire du joueur 2
				if(prop.getCouleur() == j2.getCoul()){
					
					j2.ajoutePoint(liste.size());
				}
			}
		}
		
	}

		
	
}
