package zad2;

import java.util.Random;

public class Utils {

	/**
	 * Funkcja sprawdza czy podany ciag znakow jest liczba calkowita 
	 * 
	 * @param input 
	 * @return true/false
	 */
	static public boolean isInteger( String input ) 
	{ 
		try  
		{  
			Integer.parseInt( input );  
			return true;  
		}catch( Exception e){return false;}
	} 
	
	/**
	 * Funkcja zwraca losowa ilosc milisekund
	 * @param to do ilu sekund
	 * @return czas w milisekundach
	 */
	static public int randomMillisecond(int to) {
		int time = 0;

		Random generator = new Random();
		time = generator.nextInt( to * 1000);
		
		return time;
	}
	
	/**
	 * Losuje liczby z zakresu od a do b
	 * @param a od
	 * @param b	do
	 * @return	wylosowana liczba
	 */
	static public int randomIntFromRange(int a, int b)
	{
		int number = 0;
		number = a + (int)(Math.random() * ((b - a) + 1));
		
		return number;
	}
}
