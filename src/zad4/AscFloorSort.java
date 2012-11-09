package zad4;

import java.util.Comparator;

/**
 * Comarator sortuje pasazerow zaczynajac od osoby ktora jedzie na najnizsze pietro do osoby jadacej na najwyzsze
 * Wykorzystywany jest gdy winda jedzie w gore, wtedy pierwszy wychodzi ten co na najnizsze pietro jedzie
 * @author Adam
 *
 */
public class AscFloorSort implements Comparator<Passenger>{

	@Override
	public int compare(Passenger arg0, Passenger arg1) {
		return arg0.getFinishFloor() -arg1.getFinishFloor();
	}

}
