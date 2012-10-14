package main;

import zad2.Getter;
import zad2.Putter;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Getter getter = new Getter();
		Putter putter = new Putter();
		
		new Thread(putter).start();
		new Thread(getter).start();

	}

}
