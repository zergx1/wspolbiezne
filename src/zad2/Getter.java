package zad2;

public class Getter implements Runnable {


	public void run() {
		Box box = new Box();
		for(int i=1;i<10;i++)
		{

			box.get_from();
		}
	}
}
