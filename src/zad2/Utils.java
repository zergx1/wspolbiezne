package zad2;

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
}
