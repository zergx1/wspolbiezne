package main;

import java.awt.Checkbox;

import zad2.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		Getter getter = new Getter();
//		Putter putter = new Putter();
//		
//		new Thread(putter).start();
//		new Thread(getter).start();
		String a = "1,2,3,4,5";
		String b = "1,4,5,6";
//		Collections collections = new Collections();
		Collections collections = new Collections(a,b);
		try {
			SymmetricDiffrence checkA = new SymmetricDiffrence(collections, "A");
			SymmetricDiffrence checkB = new SymmetricDiffrence(collections, "B");
			checkA.start();
			checkB.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
