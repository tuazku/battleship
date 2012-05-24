import java.util.Random;
import java.util.Scanner;

/**
 * @author Azamat Turgunbaev
 *
 */

public class Battleship {

	private Scanner in = new Scanner(System.in);
	private Random generator = new Random();
	
	private int mapAi[][] = new int[3][3];
	private int mapMy[][] = new int[3][3];
	private int minMaxMap[][] = new int[3][3];
	private int draftMap[][] = new int[3][3];
	
	private int countMyShips = 2;
	private int countAiShips = 2;
	
	private int x;
	private int y;
	private int horizontalOrVertical;
		
	public void fillMap() {
		
		for( int i = 0; i < 3; i++ ){
			
			for( int j = 0; j < 3; j++ ) {
				
				mapAi[i][j] = 0;
				mapMy[i][j] = 0;
			}
		}
		for( int i = 0; i < 3; i++ ){
			
			for( int j = 0; j < 3; j++ ) {
				
				minMaxMap[i][j] = 1;
				draftMap[i][j] = 1;
			}
		}
		
	}
	
	public void placeShips() {
		
		boolean valid = false;
		
		x = generator.nextInt( 3 );
		y = generator.nextInt( 3 );
		horizontalOrVertical = generator.nextInt(2);
		
		verifyPlacement(x, y, horizontalOrVertical, mapAi);
		
		while( !valid ) {
			System.out.println( "Please enter the start point of your ship and horizontal( 1 ) or vertical( 2 ) placement" );
			
			x = in.nextInt();
			y = in.nextInt();
			horizontalOrVertical = in.nextInt();
			
			--x;
			--y;
			--horizontalOrVertical;
			
			if( x < 3 && x >= 0 && y < 3 && y >= 0  )
			{
				valid = verifyPlacement(x, y, horizontalOrVertical, mapMy);
			}
			else {
				System.out.println( "Invalid location." );
			}
		}
	}
	
	public void printMaps() {
		
		System.out.println( "\n--- AI MAP ---" );
		System.out.println( "--------------" );
		System.out.println( "    1 2 3");
		for( int i = 0; i < 3; i++ ){
			System.out.print( "  " + ( i + 1 ) + "|");
			for( int j = 0; j < 3; j++ ) {
				hidedPrint( mapAi, i, j);
			}
			System.out.println();
		}
		
		
		System.out.println( "\n--- MY MAP ---" );
		System.out.println( "--------------" );
		
		System.out.println( "    1 2 3");
		for( int i = 0; i < 3; i++ ){
			System.out.print( "  " + ( i + 1 ) + "|" );
			for( int j = 0; j < 3; j++ ) {
				showedPrint( mapMy, i, j);
			}
			System.out.println();
		}
	}
	
	public void hidedPrint( int map[][], int i, int j ) {
	
		if( map[i][j] == -1 ) {
			System.out.print( "o|" );
		}
		else if( map[i][j] == 2 ) {
			System.out.print( "x|" );
		}
		else {
			System.out.print( "_|" );
		}
	}
	
	public void showedPrint( int map[][], int i, int j ) {
	
		if( map[i][j] == -1 ) {
			System.out.print( "o|" );
		}
		else if( map[i][j] == 2 ) {
			System.out.print( "x|" );
		}
		else if( map[i][j] == 1 ) {
			System.out.print( "<|" );
		}
		else {
			System.out.print( "_|" );
		}
	}
	
	public boolean verifyPlacement( int x, int y, int horizontalOrVertical, int map[][] ) {
		
		if( horizontalOrVertical == 0 && y < 2 ) {
			map[x][y] = 1;
			map[x][y + 1] = 1;
			return true;
		}
		else if( horizontalOrVertical == 0 && y == 2 ) {
			map[x][y] = 1;
			map[x][y - 1] = 1;
			return true;
		}
		else if( horizontalOrVertical == 1 && x < 2 ) {
			map[x][y] = 1;
			map[x + 1][y] = 1;
			return true;
		}
		else if( horizontalOrVertical == 1 && x == 2 ) {
			map[x][y] = 1;
			map[x - 1][y] = 1;
			return true;
		}
		else {
			System.out.println( "Invalid location." );
			return false;
		}
	}

/** AI SHOOT *********************************************************************/
//Shoots according to value received from minMax() method 
	
	public void shootAi() {
		
		boolean shootDone = false;
		
		while( shootDone == false ) {
					
			int max = 0;
			int x = 0;
			int y = 0;
			
			minMax( minMaxMap );
			
			for( int i = 0; i < 3; i++ ) {
				
				for( int j = 0; j < 3; j++ ) {
					
					if( max < minMaxMap[i][j] ) {
						max = minMaxMap[i][j];
						x = i;
						y = j;
					}
				}
			}
			
			if( mapMy[x][y] == 2 || mapMy[x][y] == -1 ) {
				continue;
			}
			else {
				if( mapMy[x][y] == 1) {
					mapMy[x][y] = 2;
					draftMap[x][y] = 3;
					countMyShips--;
					shootDone = true;
					
					System.out.println("--- AI HIT ---");
					System.out.println("--------------");
				}
				else {
					mapMy[x][y] = -1;
					draftMap[x][y] = 0;
					shootDone = true;
					System.out.println("- AI MISSED --");
					System.out.println("--------------");
				}
			}
			
			resetMinMaxMap();
		}
	}

/** PLAYERS SHOOT *******************************************************/
	
	public void shootMy(){
			
		boolean shootDone = false;
		
		System.out.println( "\nMake your shoot..." );
		
		while( !shootDone ) {
			
			x = in.nextInt();
			y = in.nextInt();
			
			--x;
			--y;
			
			if( x < 3 && x >= 0 && y < 3 && y >= 0 ) {
				if( mapAi[x][y] == 2 || mapAi[x][y] == -1 ) {
					System.out.println( "You have already shoot there\nShoot another coordinates...." );
				}
				else {
					if( mapAi[x][y] == 1) {
						mapAi[x][y] = 2;
						countAiShips--;
						shootDone = true;
						System.out.println("--------------");
						System.out.println("-- YOU HIT ---");
						System.out.println("--------------");
					}
					else {
						mapAi[x][y] = -1;
						System.out.println("--------------");
						System.out.println("- YOU MISSED -");
						System.out.println("--------------");
						shootDone = true;
					}
				}
			}
			else {
				System.out.println( "Wrong coordinates\nTry again...." );
			}
		}
	}
	
	public boolean gameOver() {
		if( countAiShips == 0 ){
			System.out.println("--------------");
			System.out.println("-- YOU WON ---");
			System.out.println("--------------");
			return true;
		}
		else if( countMyShips == 0 ) {
			System.out.println("--------------");
			System.out.println("- YOU LOOSED -");
			System.out.println("--------------");
			return true;
		}
		else{
			return false;
		}	
	}
	
	public void minMax( int map[][] ) {
				
		for( int i = 0; i < 3; i++ ) {
			
			for( int j = 0; j < 3; j++ ) {
				
				countMinMax( map, i, j );
				
			}
		}
	}
	
	public void countMinMax( int map[][], int x, int y ) {
		
		int sum = 0;
		
		if( ( x + 1 ) < 3 && map[x][y] == 1 && map[x + 1][y] != 0 ) {
			sum++;
		}	
		if( ( x - 1 ) >= 0 && map[x][y] == 1 && map[x - 1][y] != 0) {
			sum++;
		}
		if( ( y + 1 ) < 3 && map[x][y] == 1 && map[x][y + 1] != 0) {
			sum++;
		}
		if( ( y - 1 ) >= 0 && map[x][y] == 1 && map[x][y - 1] != 0) {
			sum++;
		}
		
		map[x][y] = sum;
	}
	
	public void resetMinMaxMap() {
		
		for( int i = 0; i < 3; i++ ) {
			
			for( int j = 0; j < 3; j++ ) {
				
				minMaxMap[i][j] = draftMap[i][j];
				System.out.print(minMaxMap[i][j]);
			}
			System.out.println();
		}
	}
}


