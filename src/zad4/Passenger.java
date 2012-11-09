package zad4;

public class Passenger extends Thread {

	private String name;
	private Elevator elevator;
	private int startFloor;
	private int finishFloor;
	private int direction;
	
	public Passenger(Elevator elevator, int startFloor, int finishFloor) {
		this.elevator = elevator;
		this.startFloor = startFloor;
		this.finishFloor = finishFloor;
		
		if( finishFloor > startFloor)
			this.direction = Elevator.GOING_UP;
		else
			this.direction = Elevator.GOING_DOWN;
	}
	
	public void run()
	{
		name = Thread.currentThread().getName().substring(7);	// pobranie numeru watku
		try {
			startFloor = elevator.call(this);
			exit();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void exit()
	{
		System.out.println(this + "wysiadlem z windy na pietrze " + startFloor);
	}
	public int getStartFloor() {
		return startFloor;
	}
	public int getFinishFloor() {
		return finishFloor;
	}
	public int getDirection() {
		return direction;
	}

	public void enter() {
		System.out.println(this + "wsiadlem do windy na pietrze " + startFloor + " i chce jechac na pietro " + finishFloor);
	}

	public String toString() {
		return "Pasazer " + name + " ";
	}

}
