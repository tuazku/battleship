
/**
 * @author Azamat Turgunbaev
 *
 */
public class BattleshipTest {

	public static void main( String [] args ) {
		
		Battleship battleship = new Battleship();
		battleship.fillMap();
		battleship.placeShips();
		battleship.printMaps();
		
		while( !battleship.gameOver() ) {
			battleship.shootMy();
			battleship.shootAi();
			battleship.printMaps();
		}
	}
}
