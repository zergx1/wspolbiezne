package zad2;

import java.util.Vector;

public class SymmetricDiffrence extends Thread{
	
	private Collections collections;
	private String howToCheck;
	
	/**
	 * Konstruktor watku sprawdzajacego roznice symetryczna. Parametr howToCheck okresla w ktorym zbiorze sprawdzac
	 * Przyklad 
	 * howToCheck == A
	 * wtedy sprawdza ktore elementy ze zbioru A sa niepowtarzalne
	 * howToCheck == B
	 * sprawdza ktore elementy w zbiorze B sa niepowtarzalne
	 * @param collections
	 * @param howToCheck przyjmuje A lub B
	 * @throws Exception wyjatek gdy podalismy drugi argument zly
	 */
	public SymmetricDiffrence(Collections collections, String howToCheck) throws Exception
	{
		this.collections = collections;
		if( ("A").equalsIgnoreCase(howToCheck) || ("B").equalsIgnoreCase(howToCheck) )
			this.howToCheck = howToCheck;
		else
			throw new Exception("Brak opcji " + howToCheck);
	}

	@Override
	public void run() {
		try {
			if( ("A").equalsIgnoreCase(howToCheck) )
				checkFromA();
			else
				checkFromB();
			
			System.out.println("Watek " + howToCheck + " zakonczyl dzialanie");
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Wystapil wyjatek w");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Funkcja sprawdza czy element zbioru A jest w zbiorze B
	 * @throws InterruptedException 
	 */
	public void checkFromA() throws InterruptedException
	{
		String who = "Watek A-B";
		int sizeA = collections.getSize(who, "A");
		int sizeB = collections.getSize(who, "B");
		
		for( int i = 0 ; i < sizeA ; i++)
		{
			int a = collections.getFrom(who, "A", i);	// pobranie elementu ze zbioru A
			for(int j = 0 ; j < sizeB ; j++)
			{
				int b = collections.getFrom(who, "B", j);	// pobranie elementu ze zbioru B
				if( a == b )	// jezeli element ze zbioru A wystepuje w zbiorze B to bierzemy kolejny z A
					break;
				else
					if( j+1 == sizeB)	// jezeli to jest juz ostatni element
						collections.putResult(who, a);
			}
		}
	}
	
	/**
	 * Funkcja sprawdza czy element zbioru B jest w zbiorze A
	 * @throws InterruptedException 
	 */
	public void checkFromB() throws InterruptedException
	{
		String who = "\tWatek B-A";
		int sizeA = collections.getSize(who, "A");
		int sizeB = collections.getSize(who, "B");
		
		for( int i = 0 ; i < sizeB ; i++)
		{
			int b = collections.getFrom(who, "B", i);	// pobranie elementu ze zbioru B
			for(int j = 0 ; j < sizeA ; j++)
			{
				int a = collections.getFrom(who, "A", j);	// pobranie elementu ze zbioru B
				if( a == b )	// jezeli element ze zbioru A wystepuje w zbiorze B to bierzemy kolejny z A
					break;
				else
					if( j+1 == sizeA)	// jezeli to jest juz ostatni element
						collections.putResult(who, b);
			}
		}
	}
	
	

}
