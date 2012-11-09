package zad4;

import java.util.Comparator;

/**
 * Comarator sortuje pasazerow zaczynajac od osoby ktora jedzie na najwyzsze pietro do osoby jadacej na najnizsze
 * Wykorzystywany jest gdy winda jedzie w dol, wtedy pierwszy wychodzi ten co na najwyzsze pietro jedzie
 * @author Adam
 *
 */
public class DescFloorSort implements Comparator<Passenger>{

	@Override
	public int compare(Passenger arg0, Passenger arg1) {
		return arg1.getFinishFloor() -arg0.getFinishFloor();
	}

}
