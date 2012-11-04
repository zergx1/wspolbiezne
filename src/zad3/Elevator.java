package zad3;

import java.util.Vector;

public class Elevator {
	/**
	 * @uml.property  name="busy"
	 */
	private boolean busy = false;
	/**
	 * @uml.property  name="goes_on"
	 */
	private int goes_on = 0;
	/**
	 * @uml.property  name="current_floor"
	 */
	private int current_floor = 0;
	/**
	 * @uml.property  name="passengers"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="elevator:zad3.Passenger"
	 */
	private Vector<Passenger> passengers = new Vector<Passenger>();
	

	/**
	 *  Proba wejscia do windy, wywolywana za kazdym razem jak
	 *  pasazer chce wejsc do windy
	 */
	public synchronized boolean try_enter(Passenger p,int floor, int goal_floor)
	{
		if(busy == true)
		{
			try {
				wait();
			} catch (InterruptedException e) {
				return true;
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		
		else if(p.get_waiting())
		{
			if(current_floor != floor)
				go_on(floor);
			//Winda zostala zwolniona
			busy = true;
			return true;
		}
		return false;
		
	}
	
	/**
	 * Symulacja jazdy na podane pietro 
	 * @param floor pietro na ktore chce sie pasazer dostac
	 */
	public void go_on(int floor)
	{
		wait_sec(1);
		// SPRAWDZA CZY NA DANYM PIETRZE SA PASAZEROWIE O TYM SAMYM CELU
		// JESLI TAK ZABIERA ICH
		Vector<Passenger> additional_passengers = this.check_same_passangers(current_floor, floor);
		if(additional_passengers.size() > 0)
		{
			for(int i = 0 ;i<additional_passengers.size();i++)
			{
				Passenger p = additional_passengers.elementAt(i);
				System.out.println( p.get_enter_info() );
			}
		}
		System.out.println("Cel:" + Integer.toString(floor) + " pietro");

		while(current_floor != floor)
		{
			//wait_sec(2);
			if(floor > current_floor)
				set_current_floor(current_floor + 1);
			else if( floor < current_floor)
				set_current_floor(current_floor - 1);
			System.out.println("Obecne pietro:" + Integer.toString(current_floor));
		}
		
		// WYSIADKA DODATKOWYCH PASAZEROW
		if(additional_passengers.size() > 0)
		{
			for(int i = 0 ;i<additional_passengers.size();i++)
			{
				Passenger p = additional_passengers.elementAt(i);
				System.out.println( p.get_leave_info() );
				p.set_waiting(false);
				this.remove_passenger(p);
				//p.interrupt(); 
			}
		}
		
	}
	
	public synchronized void leave()
	{
		busy = false;
		notifyAll();
        Passenger p = this.getNextPassanger();
        if( p != null)
        	p.run();
	}
	
	private void set_current_floor(int value)
	{
		//System.out.println(this.PassangersLeft());

		current_floor = value;
	}
	
	private void wait_sec(int sec)
	{
		try {
			Thread.sleep(sec*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	public void add_passenger(Passenger p)
	{
		this.passengers.add(p);
	}
	
	/**
	 * Sprawdza czy na danym pietrze sa pasazerowie ktorzy chca jechac na to samo pietro
	 * @param floor obecne pietro na ktorym jest zatrzymana winda
	 * @param goal pietro na ktore chce sie dostac pasazer
	 */
	private Vector<Passenger> check_same_passangers(int floor,int goal)
	{
		Vector<Passenger> result = new Vector<Passenger>();
		for(int i=0;i<this.passengers.size();i++)
		{
			Passenger p = this.passengers.elementAt(i);
			if(p.check_floors(floor, goal))
				result.add(p);
		}
		
		return result;
		
	}
	
	public void remove_passenger(Passenger p)
	{
		this.passengers.remove(p);
	}
	
	@Override
	public String toString() {
		return "Elevator [zajet =" + busy + ", jade na=" + goes_on
				+ ", obecne pietro=" + current_floor + "]";
	}
	
	public String PassangersLeft()
	{
		String result = "";
		for(int i=0;i<this.passengers.size();i++)
		{
			result += this.passengers.elementAt(i).get_id()+",";
		}
		return result;
	}
	
	private Passenger getNextPassanger()
	{
		if(this.passengers.size() > 0)
			return this.passengers.firstElement();
		else
			return null;
	}
	
	

}
