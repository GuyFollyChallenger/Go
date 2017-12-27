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
		for(int y = 0; y < taille ; y++){
			for(int x = 0; x < taille ; x++){
				inters.add(new Intersection(x, y));
			}
		}
	}
	
	public int ajoutePierre(int x, int y, Pierre p){
		int nb_prisonniers=0;
		for(Intersection inter : inters){
			if( inter.getX() == x && inter.getY() == y){
				if(inter.getPierre()==null){
					inter.setPierre(p);
					nb_prisonniers += actualiseGoban(x, y, p);
					
					if (nb_prisonniers == 0) {
						
						indexInterPrisonniers.clear();
						
						if (p.getCouleur()==CouleurPierre.Blanc) {
							prisonniersPotentiels(x, y, new Pierre(CouleurPierre.Noir));
						}
						
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
	
	
	public Pierre getPierre(int x, int y){
		
		for(Intersection inter : inters){
			if( inter.getX() == x && inter.getY() == y){
				return inter.getPierre();
			}
		}
		return null;
	}
	
	public int actualiseGoban(int x, int y, Pierre p) {
		
		int prisonnier = 0;
		
		for(Intersection inter : inters){
			
			//On pointe sur la pierre du bas. On la supprime si elle est prisonnière
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
		

	
	public void calculeScore(Joueur joueur1, Joueur joueur2) {
		
		
	}

	private boolean estPrisonniers() {
		
		
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
	
	private int determineIndex(int x, int y, int taille) {
		
		int ind = x + y * taille;
		return ind;
	}
	
	private int supprimePrisonniers() {
		
		int nbAsupprimer = indexInterPrisonniers.size();
		for (int i : indexInterPrisonniers) {
			inters.get(i).setPierre(null);
		}
		return nbAsupprimer;
	}
	
	
	private void prisonniersPotentiels(int x, int y, Pierre p) {
		
		for(int i=0; i<inters.size(); i++){
			
			//La pierre
			if(inters.get(i).getX() == x && inters.get(i).getY() == y){
				if(indexInterPrisonniers.indexOf(i)==-1){
					indexInterPrisonniers.add(i);
				}
			}
			
			//la pierre du haut
			if(inters.get(i).getX() == x && inters.get(i).getY() == y+1){
				if(inters.get(i).getPierre()!= null && inters.get(i).getPierre().getCouleur() != p.getCouleur()){
					if(indexInterPrisonniers.indexOf(i)==-1){
						indexInterPrisonniers.add(i);
						prisonniersPotentiels(x, y+1, p);
					}
				}
			}
			
			//la pierre de droite
			if(inters.get(i).getX() == x+1 && inters.get(i).getY() == y){
				if(inters.get(i).getPierre()!= null && inters.get(i).getPierre().getCouleur() != p.getCouleur()){
					if(indexInterPrisonniers.indexOf(i)==-1){
						indexInterPrisonniers.add(i);
						prisonniersPotentiels(x+1, y, p);
					}
				}
			}
			
			//la pierre du bas
			if(inters.get(i).getX() == x && inters.get(i).getY() == y-1){
				if(inters.get(i).getPierre()!= null && inters.get(i).getPierre().getCouleur() != p.getCouleur()){
					if(indexInterPrisonniers.indexOf(i)==-1){
						indexInterPrisonniers.add(i);
						prisonniersPotentiels(x, y-1, p);
					}
				}
			}
			
			//la pierre de gauche
			if(inters.get(i).getX() == x-1 && inters.get(i).getY() == y){
				if(inters.get(i).getPierre()!= null && inters.get(i).getPierre().getCouleur() != p.getCouleur()){
					if(indexInterPrisonniers.indexOf(i)==-1){
						indexInterPrisonniers.add(i);
						prisonniersPotentiels(x-1, y, p);
					}
				}
			}
			
		}
	}
		
	
}
