package zad4;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Join {
	  public static void main(String[] args) {
//	    Thread t = new Thread() {
//	      public void run() {
//	        System.out.println("Reading");
//	        try {
//	          System.in.read();
//	        } catch (java.io.IOException ex) {
//	          System.err.println(ex);
//	        }
//	        System.out.println("Thread Finished.");
//	      }
//	    };
//	    System.out.println("Starting");
//	    t.start();
//	    System.out.println("Joining");
//	    try {
//	      t.join();
//	    } catch (InterruptedException ex) {
//	      // should not happen:
//	      System.out.println("Who dares interrupt my sleep?");
//	    }
//	    System.out.println("Main Finished.");
		
//		  Random r = new Random();
//		  Elevator e = new Elevator();
//		  e.start();
//		  Passenger p = new Passenger(10, 5, e);
//		  p.start();
//		  Passenger p2 = new Passenger(1, 15, e);
//		  p2.start();
//		  Passenger p3 = new Passenger(1, 8, e);
		  Elevator w = new Elevator();
		  w.start();
		  Passenger p = new Passenger(w, 2, 10);
		  Passenger p2 = new Passenger(w, 6, 1);
		  p.start();
		  p2.start();

	  }
	}