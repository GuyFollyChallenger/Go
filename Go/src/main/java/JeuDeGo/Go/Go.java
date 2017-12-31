package JeuDeGo.Go;


import java.util.ArrayList;
import java.util.Scanner;

//Classe qui permet de joueur le jeu de A à Z en mode console!
//Le manuel d'utilisation peut etre utile.
public class Go {


	private static Scanner scan_x;
	private static Scanner scan_y;
	private static Scanner scan_cont;


	public static void main( String[] args )
	{

		int x;
		int y;
		Joueur joueur1 = new Joueur("Roger", CouleurPierre.Noir);
		Joueur joueur2 = new Joueur("Irenne", CouleurPierre.Blanc);
		Joueur joueurActif;
		Joueur fantome = joueur1;
		Goban goban = new Goban(19);
		joueur1.setPasse(false);
		joueur2.setPasse(false);
		ArrayList<Intersection> listePierreCapture = new ArrayList<Intersection>();


		System.out.println("****** Debut partie jeu de Go *******\n");
		System.out.println(""+joueur1.getPseudo()+"(joueur "+joueur1.getCoul()+")"+" VS "+joueur2.getPseudo()+"(joueur "+joueur2.getCoul()+")"+"\n");

		//On vérifie les conditions de fin : Plus d'espace pour jouer ou les 2 joueurs passent leurs tours respectifs

		while (goban.gobanSature() == false && (joueur1.getPasse()== false || joueur2.getPasse() == false) ) {


			joueurActif = fantome;
			//On indique quel joueur doit jouer
			System.out.println("Tour de Joueur " + joueurActif.getCoul());

			scan_x = new Scanner(System.in);
			System.out.println("Entrer l'abscisse de l'intersection où vous voulez poser votre pierre : (Entre 0 et 18 ou -1 pour passer son tour) :");
			x = scan_x.nextInt();

			scan_y = new Scanner(System.in);
			System.out.println("Entrer l'ordonnée de l'intersection où vous voulez poser votre pierre : (Entre 0 et 18 ou -1 pour passer son tour) :");
			y = scan_y.nextInt();

			//Si les coordonnees sont en dehors des coordonnees du goban
			//Si le joueur entre -1, il souhaite passer son tour
			//Sinon c'est probablement une erreur de frappe
			//Il entre alors de nouvelles coordonnees

			if (x > 18 || x < 0 || y > 18 || y < 0)  
			{			
				if (x == -1 || y == -1)
				{

					//On change de joueur
					if (joueurActif == joueur1) {
						System.out.println("Le Joueur noir passe son tour \n");
						joueur1.setPasse(true);
						fantome = joueur2;

					} 
					if (joueurActif == joueur2) {
						System.out.println("Le Joueur blanc passe son tour \n");
						joueur2.setPasse(true);
						fantome = joueur1;

					}


				}
				else
				{
					//on conserve le joueur
					System.out.println("Coordonnees non valides. Entrez de nouvelles coordonées");
					fantome = joueurActif;
				}

			}

			//Si les coordonnees entrees correspondent à celles du goban
			//On ajoute la pierre
			//On actualise le goban qui permet de gerer la capture des pions et les coups suicidaires
			//Elle nous permettra egalement de calculer le score

			if (x>=0 && y>=0 && x<=18 && y<=18 )
			{

				Pierre p = new Pierre(joueurActif.getCoul());
				goban.ajoutePierre(x, y, p);
				int nb_pris = goban.actualiseGoban(x, y, p);
				if (nb_pris != -1)
				{
					joueurActif.ajoutePrisonniers(nb_pris);

				}
				//Affichage du goban
				System.out.println("Voici le goban: ");
				System.out.println(goban.afficheGoban(19));

				//On change de joueur
				if (joueurActif == joueur1)
				{
					fantome = joueur2;
				}
				else
				{
					fantome = joueur1;
				}


				//Si les coordonnees sont deja occupees, le joueur doit entrer de nouvelles coordonnees
				if (goban.isOccupe() == true)
				{
					//on consere donc le joueur
					System.out.println("Intersection occupee. Entrez de nouvelles coordonnees");
					fantome = joueurActif;
				}

			}

		}


		//A la fin de la partie
		//Les joueurs doivent enlever manuellement les pierres capturees du goban
		//C'est notre condition pour que le score soit correcte
		//C'est donc une etape essentielle. Tous les pions capturees doivent donc etre enlevees
		//Apres cela, les scores sont affichees

		System.out.println("*********** Fin partie *****************");
		System.out.println("Voici le goban: ");
		System.out.println(goban.afficheGoban(19));

		System.out.println("Maintenant enlevez toutes les pierres capturees sur goban. Entrez 1 pour enlever ou 0 Si vous avez fini!");		
		scan_cont = new Scanner(System.in);
		int continuer;
		System.out.println("Enlevez? Entrez 1 (oui) / 0 (non)");
		continuer = scan_cont.nextInt();


		while(continuer != 0)
		{
			scan_x = new Scanner(System.in);
			System.out.println("Entrez l'abscisse de l'intersection où vous voulez enlever la pierre : (Entre 0 et 18) :");
			x = scan_x.nextInt();

			scan_y = new Scanner(System.in);
			System.out.println("Entrez l'ordonnée de l'intersection où vous voulez enlever la pierre : (Entre 0 et 18) :");
			y = scan_y.nextInt();

			Pierre p = goban.supprimePierre(x,y);
			if( p == null)
			{
				System.out.println("La pierre n'existe pas sur le goban!\n");
			}

			else
			{
				listePierreCapture.add(new Intersection(x,y,p));
			}

			System.out.println("Continuer? Entrez 1 (oui) / 0 (non)");
			continuer = scan_cont.nextInt();
		}


		//Calcul et affichage du score		
		goban.calculeScore(joueur1, joueur2);
		System.out.println("Le score est: " + joueur1.getPseudo()+" ("+joueur1.getCoul()+")"+" "+joueur1.getScore()+" - "+joueur2.getScore()+" "+joueur2.getPseudo()+" ("+joueur2.getCoul()+")");

		if(joueur1.getScore() > joueur2.getScore()) {

			System.out.println("*** "+joueur1.getPseudo() + " "+"gagne ***");
		}

		if(joueur1.getScore() < joueur2.getScore()) {

			System.out.println("*** "+joueur2.getPseudo() + " "+"gagne ***");

		}
		else
		{
			System.out.println("*** Partie nulle ***");
		}

	}

}














