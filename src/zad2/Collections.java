package zad2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.concurrent.Semaphore;

public class Collections {

	private Semaphore semaphoreA, semaphoreB, semaphoreResult;	// semafory pilnujace kolejno zbioru A,B i wyniku
	private final Vector<Integer> collectionA = new Vector<Integer>();	// wektor zbioru A
	private final Vector<Integer> collectionB = new Vector<Integer>();	// wektor zbioru B
	private final Vector<Integer> result = new Vector<Integer>();		// rozwiazanie
	
	/**
	 *  Konstruktor przyjmuje z konsoli dwa zbiory liczbowe 
	 */
	public Collections()
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String tempLine = "";
		
		System.out.println("Prosze podac pierwszy zbior liczbowy skladajacy sie z liczb calkowitych. Klawisz q konczy podawanie zbioru");
		
		while(true)
		{
            try {
				tempLine = br.readLine();
			} catch (IOException e) {
				System.out.println("Problem z wczytywaniem z konsoli ");
				e.printStackTrace();
			}
			if(tempLine.contains("q") || tempLine.contains("Q"))	// zakonczenie wczytywania jezeli bylo q
				break;
			if(!Utils.isInteger(tempLine))
				System.out.println("Prosze podac liczbe calkowita");
			else
				this.collectionA.add(Integer.valueOf(tempLine));

		}
		
		System.out.println("Prosze podac drugi zbior liczbowy skladajacy sie z liczb calkowitych. Klawisz q konczy podawanie zbioru");
		
		while(true)
		{
            try {
				tempLine = br.readLine();
			} catch (IOException e) {
				System.out.println("Problem z wczytywaniem z konsoli ");
				e.printStackTrace();
			}
			if(tempLine.contains("q") || tempLine.contains("Q"))	// zakonczenie wczytywania jezeli bylo q
				break;
			if(!Utils.isInteger(tempLine))
				System.out.println("Prosze podac liczbe calkowita");
			else
				this.collectionB.add(Integer.valueOf(tempLine));

		}
		
		initSemaphore();

		
	}
	
	/**
	 * Konstruktor w argumentach przyjmuje dwa zbiory, odrzuca wartosci nie bedace liczbami calkowitymi
	 * @param a ciag znakow bedacy pierwszym zbiorem
	 * @param b ciag znakow bedacy drugim zbiorem
	 */
	public Collections(String a, String b)
	{
		String[] arrayA = a.split(",");
		String[] arrayB = b.split(",");
		
		for(int i = 0 ; i < arrayA.length ; i++)
		{
			String temp = arrayA[i]; 
			if(Utils.isInteger(temp))
				this.collectionA.add(Integer.valueOf(temp));
		}
		
		for(int i = 0 ; i < arrayB.length ; i++)
		{
			String temp = arrayB[i]; 
			if(Utils.isInteger(temp))
				this.collectionB.add(Integer.valueOf(temp));
		}
		
		initSemaphore();
	}
	
	/**
	 *  Funkcja incjalizuje semafory jako semafory binarne
	 */
	private void initSemaphore()
	{
		semaphoreA = new Semaphore(1,true);
		semaphoreB = new Semaphore(1,true);
		semaphoreResult = new Semaphore(1,true);
	}
	
	/**
	 * Funkcja pobiera z jednego ze zbiorow element
	 * @param who kto pobiera
	 * @param from skad
	 * @param at ktory element
	 * @return
	 * @throws InterruptedException
	 */
	public int getFrom(String who, String from, int at) throws InterruptedException
	{
		int element;
		System.out.println( who + " chce pobrac " + at + " element ze zbioru " + from);
		
		if( ("A").equalsIgnoreCase(from) )
		{
			if( semaphoreA.availablePermits() == 0)	// sprawdzenie czy semafor A jest opuszczony
				System.out.println( who + " musi poczekac - semafor na zbiorze A jest opuszczony");
			
			semaphoreA.acquire();
			System.out.println( who + " zajol zasob i opuscil semafor na zbior A");
			
			// ************************************
			Thread.sleep((long) (Math.random() * 1000));				// imitacja zajetosci procesu
			// ************************************
			
			element = (int) getElementFromA(at);
			System.out.println( who + " zwolnil zasob i podniosl semafor ze zbioru A\n");
			semaphoreA.release();
			return element;
		}
		else
		{
			if( semaphoreB.availablePermits() == 0 ) // sprawdzenie czy semafor A jest opuszczony
				System.out.println( who + " musi poczekac - semafor na zbiorze B jest opuszczony");
			
			semaphoreB.acquire();
			System.out.println( who + " zajol zasob i opuscil semafor na zbior B");
			// ************************************
			Thread.sleep((long) (Math.random() * 1000));				// imitacja zajetosci procesu
			// ***********************************
			element = (int) getElementFromB(at);
			System.out.println( who + " zwolnil zasob i podniosl semafor ze zbioru B\n");
			semaphoreB.release();
			return element;
		}
			

	}
	
	/**
	 * Funkcja odklada do wektora rezultatu wynik
	 * @param who kto odklada
	 * @param result wynik
	 * @throws InterruptedException
	 */
	public void putResult(String who, int result) throws InterruptedException
	{
		System.out.println( who + " chce dolozyc do wyniku " + result );
		
	
		if( semaphoreResult.availablePermits() == 0)	// sprawdzenie czy semafor A jest opuszczony
			System.out.println( who + " musi poczekac - semafor na zbiorze wynikow jest opuszczony");
			
		semaphoreResult.acquire();
		System.out.println( who + " zajol zasob i opuscil semafor na zbior wynikow");
		
		// ************************************
		Thread.sleep((long) (Math.random() * 1000));				// imitacja zajetosci procesu
		// ************************************
		putResultElement(result);
		System.out.println( who + " zwolnil zasob i podniosl semafor ze zbioru wynikow\n");
		semaphoreResult.release();
		
	}
	
	public int getSize(String who, String from) throws InterruptedException
	{
		int size;
		System.out.println( who + " chce pobrac dlugosc zbioru " + from);
		
		if( ("A").equalsIgnoreCase(from) )
		{
			if( semaphoreA.availablePermits() == 0)	// sprawdzenie czy semafor A jest opuszczony
				System.out.println( who + " musi poczekac - semafor na zbiorze A jest opuszczony");
			
			semaphoreA.acquire();
			System.out.println( who + " zajol zasob i opuscil semafor na zbior A");
			size = getSize(from);
			System.out.println( who + " zwolnil zasob i podniosl semafor ze zbioru A\n");
			semaphoreA.release();
			return size;
		}
		else
		{
			if( semaphoreB.availablePermits() == 0 ) // sprawdzenie czy semafor A jest opuszczony
				System.out.println( who + " musi poczekac - semafor na zbiorze B jest opuszczony");
			
			semaphoreB.acquire();
			System.out.println( who + " zajol zasob i opuscil semafor na zbior B");
			size = (int) getSize(from);
			System.out.println( who + " zwolnil zasob i podniosl semafor ze zbioru B\n");
			semaphoreB.release();
			return size;
		}
			

	}
	
	private synchronized Object getElementFromA(int i)
	{
		if(i < collectionA.size() && i >= 0)
			return collectionA.get(i);
		else
			return null;
	}
	
	private synchronized Object getElementFromB(int i)
	{
		if(i < collectionB.size() && i >= 0)
			return collectionB.get(i);
		else
			return null;
	}
	
	private synchronized void putResultElement(int i)
	{
		if( !result.contains(i) )
			result.add(i);
	}
	
	/**
	 * Funkcja zwraca dlugosc wektora 
	 * @param collection A lub B
	 * @return
	 */
	private synchronized int getSize(String collection)
	{
		if( ("A").equalsIgnoreCase(collection) )
			return collectionA.size();
		else
			return collectionB.size();
	}
	
	public void printCollections()
	{
		System.out.println("Zbior A" + collectionA);
		System.out.println("Zbior B" + collectionB);
		System.out.println("Roznica symetryczna " + result);
	}
	
}
