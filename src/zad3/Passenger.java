package zad3;

public class Passenger extends Thread {
	private Elevator elevator;
	private boolean waiting = true;
	private int id = 0;
	private int on_floor = 0;
	private int goal_floor = 0;

	
	/**
	 * Konstruktor pasazera
	 * @param elevator instacja windy ktorej pasazer chce uzyc
	 * @param id jego numer id (zawsze unikalny
	 * @param on_floor pietro na ktorym sie znajduje
	 * @param goal_floor pietro na ktore chce sie dostac
	 */
	Passenger(Elevator elevator,int id, int on_floor, int goal_floor)
	{
		this.elevator = elevator;
		this.id = id;
		this.on_floor = on_floor;
		this.goal_floor = goal_floor;
	}
	
	public void run() 
	{
		System.out.println("Pasazer "+Integer.toString(id)+" czeka na winde na " +
				Integer.toString(on_floor)+" pietrze i chce jechac na "+
				Integer.toString(goal_floor)+" pietro");
		
		elevator.try_enter(this,on_floor,goal_floor); // sprawdza czy pasazer nadal ptorzeubje wejsc do windy
		if(waiting)
		{
			System.out.println( this.get_enter_info() );
			this.waiting = false;
			elevator.go_on(goal_floor);
			elevator.remove_passenger(this);
			System.out.println( this.get_leave_info() );
			elevator.leave();
		}
	}
	
	public String get_enter_info()
	{
		return "+Pasazer "+Integer.toString(id)+" wszedl do windy na " +
				Integer.toString(on_floor)+" pietrze i jedzie na "+
				Integer.toString(goal_floor)+" pietro";
	}
	
	public String get_leave_info()
	{
		return "-Pasazer "+Integer.toString(id)+" wszedl do windy na " +
				Integer.toString(on_floor)+" pietrze i wyszedl na "+
				Integer.toString(goal_floor)+" pietrze";
	}
	
	/**
	 * Zwraca true jesli podane argumenty sa takie same dla danego pasazera
	 * i pasazer nadal czeka na winde
	 * @param floor pietro na ktormy sie znajduje winda
	 * @param goal cel windy
	 * @return
	 */
	public boolean check_floors(int floor,int goal)
	{
		if(this.waiting == true && this.on_floor == floor && this.goal_floor == goal)
			return true;
		else
			return false;
	}
	
	public int get_id()
	{
		return this.id;
	}
	
	public void set_waiting(boolean value)
	{
		this.waiting = value;
	}
	
	public boolean get_waiting()
	{
		return this.waiting;
	}

	@Override
	public String toString() {
		return "Passenger" + id + " [na pietrze ="+ on_floor + 
				", jade na=" + goal_floor + 
				" czekam =" + waiting + " ]";
	}
	
}
