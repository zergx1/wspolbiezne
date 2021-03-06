package zad4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import zad2.Utils;

public class Manager4 {
	  public static void main(String[] args) {

		  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		  Elevator w = new Elevator();
		  w.start();	// uruchomienie windy
		  
		  while(true)
		  {
			  try {
					br.readLine();
			  } catch (IOException e) {
					System.out.println("Problem z wczytywaniem z konsoli ");
			  }
			  int startFloor = Utils.randomIntFromRange(1, Elevator.MAX_FLOOR);
			  int finishFloor = 0;
			  do
			  {
				  finishFloor = Utils.randomIntFromRange(1, Elevator.MAX_FLOOR);
			  }
			  while( startFloor == finishFloor );
				  
//			  Passenger p = new Passenger(w, startFloor, finishFloor);
//			  p.start();
			  Passenger p = new Passenger(w, 10, 8);
			  p.start();
			  Passenger p1 = new Passenger(w, 8, 10);
			  p1.start();
			  Passenger p2 = new Passenger(w, 8, 9);
			  p2.start();
			  Passenger p3 = new Passenger(w, 8, 6);
			  p3.start();
			  Passenger p4 = new Passenger(w, 8, 7);
			  p4.start();
			  
			  try {
				Thread.sleep(Utils.randomMillisecond(5));
			} catch (InterruptedException e) {}
		  }

	  }
	}