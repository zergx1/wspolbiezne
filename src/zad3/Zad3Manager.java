package zad3;

import java.util.Scanner;

public class Zad3Manager {
	
	/**
	 *  Menadzer zadania 3
	 *  Pobiera dwie liczby oddzielone spacja, 
	 *  1 ozancza pietro na ktorym znajduje sie pasazer
	 *  2 liczba oznacza pietro na ktore chce sie dostac pasazer
	 *  string e konczy zadanie
	 *  
	 *  po podaniu stringu a X gdzie x jest liczba zostanie automatycznie wygenerowanych
	 *  X pasazerow o roznych celach i pietrach
	 */
	public Zad3Manager()
	{
		Elevator e = new Elevator();
		String text = "";
		Scanner input = new Scanner(System.in);
		int id = 0;
		Passenger p;
		while(text != "e")
		{
			text = input.nextLine();
			if(text.equalsIgnoreCase("e"))
				break;
			String[] choices;
			choices = text.split(" ");
			if(choices[0].equalsIgnoreCase("a"))
			{
				int maximum = 10;
				int minimum = 0;
				for(int i=0;i< Integer.parseInt(choices[1]);i++)
				{
					int floor = minimum + (int)(Math.random()*maximum); 
					int goal = minimum + (int)(Math.random()*maximum); 
					p = new Passenger(e,i+id+1, floor, goal);
					p.start();
					e.add_passenger(p);
				}
			}
			else
			{
				p = new Passenger(e,id+1, Integer.parseInt(choices[0]), Integer.parseInt(choices[1]));
				id = id+1;
				p.start();
				e.add_passenger(p);
			}
		}
//		Passenger p = new Passenger(e, 1, 0, 5);
//		p.start();
//		p = new Passenger(e, 2, 3, 5);
//		p.start();

	}

}
