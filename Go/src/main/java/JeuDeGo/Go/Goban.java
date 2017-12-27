package JeuDeGo.Go;

import java.util.ArrayList;
import java.util.List;

public class Goban {
	
	private int taille;
	private List<Intersection> inters = new ArrayList<Intersection>();
	
	//Getters et setters
	public List<Intersection> getIntersections(){
		return inters;
	}
	
	public int getTaille(){
		return taille;
	}
	
	//Creation du goban
	public Goban(int taille){
		this.taille = taille;
		for(int y = 0; y < taille ; y++){
			for(int x = 0; x < taille ; x++){
				inters.add(new Intersection(x, y));
			}
		}
	}
	
	public int addPierre(int x, int y, Pierre p){
		int nb_prisonniers=0;
		for(Intersection inter : inters){
			if( inter.getX() == x && inter.getY() == y){
				if(inter.getPierre()==null){
					inter.setPierre(p);
					nb_prisonniers += actualiseGoban(x, y, p);
					
				return nb_prisonniers;
				}
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
		
		
	}
	
	public void calculeScore(Joueur joueur1, Joueur joueur2) {
		
	}
	
	
	
}
