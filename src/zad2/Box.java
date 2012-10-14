package zad2;

import java.util.concurrent.Semaphore;

public class Box {
	
	private static final int MAX_THREADS = 2;
	private static Semaphore accessControl = new Semaphore(MAX_THREADS);
	private static int current_in = 0;
	
	public void put_in(int value)
	{
		this.waitSec(2);
		

		if(this.acquireAcces())
		{
			System.out.println("\t\t\t\t+Rozpoczeto funkcje PUT_IN");
			while( this.current_in != 0)
			{
				System.out.println("\t\t\t\t*Czekam az bedzie puste");
				
			}
			current_in = value;
			System.out.println("\t\t\t\t*Wlozono "+Integer.toString(value));
			accessControl.release();

			System.out.println("\t\t\t\t-Zakonczono funkcje PUT_IN");
		}
	}
	
	public void get_from()
	{

		this.waitSec(2);

		


		if(this.acquireAcces())
		{
			System.out.println("+Rozpoczeto funkcje GET_FROM");
			while( this.current_in == 0)
			{
				System.out.println("*Czekam aby moc cos zabrac");

			}

			System.out.println("*Zabrano "+Integer.toString(current_in));
			current_in = 0;
			accessControl.release();
			System.out.println("-Zakonczono funkcje GET_FROM");
		}
	}
	
	private boolean acquireAcces()
	{
		try {
			this.accessControl.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	private void waitSec(int value)
	{
		try {
			Thread.sleep(value*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
