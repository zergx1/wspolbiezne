package zad4;

import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;

public class Elevator extends Thread {

	public static final int MAX_FLOOR = 10;
	public static final int GOING_UP = 1;
	public static final int GOING_DOWN = -1;
	public static final int STOP = 0;
	
	private List passengersWaiting = new Vector<Passenger>();
	private List passengersIn = new Vector<Passenger>();
	private int currentFloor = 0;
	private int finishFloor;
	private int direction;
	private Semaphore semaphore = new Semaphore(1,true);
	private boolean called = false;	// winda nie moze zatrzymac sie gdy zostala wezwana, jedzie bezposrednio po pasazera
	private Object monitor = new Object();
	public void run()
	{
		boolean temp = false;
		
		while(true)
		{
			try {
				temp = false;
				sleep(1000);
				
				semaphore.acquire();
				
				if( passengersIn.isEmpty() && passengersWaiting.isEmpty() && !called)	// nic sie nie dzieje
				{
					synchronized (monitor) {
						monitor.wait();
					}
				}
				
				// true gdy ktos jest w windzie
				// false gdy nikogo nie ma w windzie
				if( !passengersIn.isEmpty() )	// wysiadki
					temp = checkPassengerExit();

				// skomplikowany warunek
				// true gdy nikogo nie ma w windzie 
				// true gdy ktos jest jeszcze w windzie ale ktos wysiadal na tym pietrze
				// false gdy ktos jest ale nikt nie wysiadl czyli winda jedzie dalej
				temp = passengersIn.isEmpty() || (!passengersIn.isEmpty() && temp);
				
				// true gdy ktos czeka, winda nie jest wezwana, i poprzedni warunek
				if( !passengersWaiting.isEmpty() && !called && temp)	// wsiadki
				{
					checkPassengerEnter();
				}
				
				// true gdy ktos czeka, w windzie nikt nie jedzie i winda nie ma jeszcze wezwania
				if( !passengersWaiting.isEmpty() && passengersIn.isEmpty() && !called) // wezwanie do pierwszego pasazera
				{
					checkFirstPassenger();
				}
				
				goOn();	// jedz
				
				
				semaphore.release();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 *  Ruch windy
	 */
	public void goOn() {
		if ( direction == GOING_UP) // jazda w gore
		{
			if (currentFloor < finishFloor)
				currentFloor++;
		} else // jazda w dol
		{
			if (currentFloor > finishFloor)
				currentFloor--;
		}
		
		if( currentFloor == finishFloor)	// dojechal, wiec jezeli to bylo wezwanie juz nie jest
			called = false;
		
		System.out.println("Pietro " + currentFloor);
	}

	
	public int call(Passenger p) throws InterruptedException
	{
		System.out.println(p.toString() + "czeka na winde na pietrze " + p.getStartFloor());
		
		semaphore.acquire();
		if( passengersIn.isEmpty() && passengersWaiting.isEmpty() && !called)	// nic sie nie dzieje
		{
			synchronized (monitor) {
				monitor.notifyAll();
			}
		}
		passengersWaiting.add(p);
		semaphore.release();
		
		synchronized(p)
		{
			p.wait();
			return currentFloor;
		}
	}
	
	/**
	 * Inni pasazerowie wchodza ktorzy jada w tym samym kierunku
	 * @throws InterruptedException 
	 */
	public void checkPassengerEnter() throws InterruptedException
	 {
		
		boolean flag = false;	// okresla czy ktos juz wsiadl
		boolean flag2 = false;	// czy wazny jest kierunek
		if( passengersIn.isEmpty() )
			flag2 = true;
		
		 for( int i = 0 ; i < passengersWaiting.size() ; i++)
		 {
			 Passenger p = (Passenger) passengersWaiting.get(i);
			 if( p.getStartFloor() == currentFloor ) // jezeli jada w tym samym kierunku i sa na tym pietrze co winda
			 {
				 if( !flag2 && p.getDirection() != direction)	// jezeli nie wazny jest kierunek to napewno nie wejdzie
					 continue;	// sprawdza kolejnego pasazera gdy kierunek jest istotny i nie zgadza sie
				 
				 p.enter();
				 passengersIn.add(p);
				 passengersWaiting.remove(p);
				 
				 if( !flag && flag2)	// jezeli to byl pierwszy pasazer i wczesniej nie wazny byl kierunek
				 {
					 finishFloor = p.getFinishFloor();	// okresla gdzie chcemy jechac
					 checkDirection();
					 flag = true;	// i mozemy sprawdzac dalej majac juz kierunek okreslony
				 }
			 }
		 
	 	}
		 if( passengersIn.size() > 1 )
		 {
			 if( direction == GOING_UP )	// ustawia najdalsze pietro w zaleznosci od kierunku
			 {
				 finishFloor = Collections.max(passengersIn,new AscFloorSort()).getFinishFloor();	// najwyzsze pietro
			 }
			 else
			 {
				 finishFloor = Collections.min(passengersIn,new AscFloorSort()).getFinishFloor() ;	// najnizsze pietro
			 }
		 }
		 
			
		
	 }
	
	/**
	 * Pasazerowie ktorzy wychodza
	 * 
	 * @throws InterruptedException 
	 */
	public boolean checkPassengerExit() throws InterruptedException
	 {
		boolean flag = false;	// ktos wysiadl	pomocne zeby sprawdzac czy ktos wsiada
		
		 for( int i = 0 ; i < passengersIn.size() ; i++)
		 {
			 Passenger p = (Passenger) passengersIn.get(i);
			 if( p.getFinishFloor() == currentFloor ) // jezeli tu wysiadaja
			 {
				 p.exit();
				 passengersIn.remove(p);
				 exit(p);
				 flag = true;
			 }
		 
	 	}
		 
		 return flag;
		
	 }
	
	/**
	 * Wzywa winde do pierwszego pasazera
	 */
	public void checkFirstPassenger()
	{
		 Passenger p = (Passenger) passengersWaiting.get(0);	// pierwszy z kolejki
		 System.out.println(p.toString() + "wezwal winde na pietro " + p.getStartFloor());
		 finishFloor = p.getStartFloor();
		 checkDirection();
		 called = true;
	}
	
		/**
		 *  Ustala kierunek jazdy, wolana tylko po ustaleniu pietra koncowego
		 */
		private void checkDirection() {
			if (finishFloor > currentFloor)
				direction = GOING_UP;
			else
				direction = GOING_DOWN;
		}
		
		/**
		 * Budzenie pasazera
		 * @param p pasazer
		 */
		private void exit(Passenger p)
		{
			synchronized (p) {
			p.notifyAll();	
			}
		}
}
