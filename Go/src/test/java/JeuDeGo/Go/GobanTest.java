package JeuDeGo.Go;

import static org.junit.Assert.*;

import org.junit.Test;

public class GobanTest {

	@Test
	public void test() {
		
		Goban g = new Goban(19);
		
		//assertEquals("intersection", null , g.getPierre(1,1));

		
		
		Pierre b = new Pierre(CouleurPierre.Blanc);
		Pierre n = new Pierre(CouleurPierre.Noir);
				
		g.ajoutePierre(11, 12, b);
		g.ajoutePierre(11, 13, n);
		
		assertEquals("Obtient la pierre située aux coordonnées (11 ,12) ", b, g.getPierre(11, 12));
		assertEquals("Obtient la pierre située aux coordonnées (11 ,13) ", n, g.getPierre(11, 13));	
		
			
	}

}
