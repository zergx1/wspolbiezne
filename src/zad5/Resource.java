package zad5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Vector;

import zad3.Passenger;

public class Resource {
	/**
	 * 0 - Free
	 * 1 - Taken by readers
	 * 2 - Taken by writer
	 */
	private int status = 0;
	private int current_id = 0;
	// LISTA OSOB KTORE CHCA DOSAC DOSTEP - KOLEJKA FIFO
	private Vector<Person> persons_list = new Vector<Person>();
	// LISTA OSOB OBECNIE OBSLUGUJACYCH ZASOB
	private Vector<Person> active_list = new Vector<Person>();

	

	public synchronized boolean get_access(Person p)
	{
		
		//wait_sec(1);
		boolean can_use = true;
		PersonType person_type = p.getPt(); // Get Person type

		//Dlugi warunek, nie do wytlumaczenia
		while(status == 2 || ((this.current_id != p.get_id() && this.current_id != 0 && status != 1 ) ||  (status == 1 && !this.can_get_access(p)) ) )
		{

				try {
					wait();
				} catch (InterruptedException e) {
					return false;
				}
				
//			}
		}
		can_use = can_use && add_to_active(p);
		if(can_use)
		{

			return true;
		}
	
		return false;
	}
	
	public synchronized void leave_access(Person p)
	{
		remove_from_active(p);
		if( p.get_actions_left() > 0)
			add_to_queue(p);
		else
			p.deleted_info();
		if( active_list.isEmpty() )
		{
			status = 0;
			//System.out.println("Wywoluje notify");
			//this.get_vectors_info();
			notifyAll();
			Person pn = this.getNextPerson();
			if(pn != null)
			{
				this.current_id = pn.get_id();
//				System.out.println("Nastepny"+String.valueOf(this.current_id));
//				if( current_id == 2)
//				{
//				System.out.println("Status: "+String.valueOf(status)+" current_id: "+ String.valueOf(current_id)+" can_get_access: "+String.valueOf(this.can_get_access(pn)));
//				System.out.println(pn.toString());
//				}
			}
		}
		
	}
	
	public boolean add_to_active(Person p)
	{
		PersonType person_type = p.getPt(); // Get Person type
		if(status == 0)
		{
			active_list.add(p);
			if(person_type == PersonType.READER)
				status = 1;
			else if(person_type == PersonType.WRITER)
				status = 2;
			this.remove_from_queue(p);
			return true;
		}
		else if(status == 1 && person_type == PersonType.READER)
		{
			active_list.add(p);
			this.remove_from_queue(p);
			return true;

		}
		// inaczej pisarz chce dostep lub pisarz ma dostep, wiec nie mozna dodac do aktywnej listy
		return false;
	}
	
	public void remove_from_active(Person p)
	{
		active_list.remove(p);
	}
	

	public void add_to_queue(Person p)
	{
		persons_list.add(p);
	}
	

	public void remove_from_queue(Person p)
	{
		persons_list.remove(p);
	}
	
	public void write(Person p,String text)
	{
		try{
			// Create file 
			FileWriter fstream = new FileWriter("out.txt",true);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(p.get_info()+text+"\n");
			//Close the output stream
			out.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}


	public String read()
	{
		String read = "";
		//wait_sec(3);
		try{
			// Open the file that is the first 
			// command line parameter
			FileInputStream fstream = new FileInputStream("out.txt");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
				// Print the content on the console
				//System.out.println (strLine);
				read += strLine;
			}
			//Close the input stream
			in.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		return read;
	}
	
	private Person getNextPerson()
	{
		if(this.persons_list.size() > 0)
			return this.persons_list.firstElement();
		else
			return null;
	}
	
	
	public void vector_info(Vector<Person> o)
	{
		for(int i=0;i<o.size();i++)
		{
			System.out.println( o.elementAt(i).toString() );
		}
	}
	
	public void get_vectors_info()
	{
		System.out.println("ACTIVE");
		this.vector_info(this.active_list);
		System.out.println("PERSON");
		this.vector_info(this.persons_list);
	}

	public void wait_sec(int sec)
	{
		try {
			Thread.sleep(sec*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	/**
	 * Sprawdza czy osoba moze dolaczyc do uzywania zasobu
	 * Czyli tylko jesli zasob jest posiadany przez czytelnika
	 * i zadna poprzedzajaca osoba w kolejce nie jest pisarzem
	 * 
	 * @param p
	 * @return
	 */
	private boolean can_get_access(Person p)
	{
		PersonType type = p.getPt();
		if( type == PersonType.READER)
		{
			//System.out.println("SPRAWDZAM "+p.toString());
			//this.get_vectors_info();
			for(int i=0;i<=this.persons_list.size();i++)
			{
				Person current = persons_list.elementAt(i);
				if(current.getPt() == PersonType.WRITER)
				{
					return false;
				}
				else if(current == p)
				{
					return true;
				}
			}
		}
		return false;
		
	}

}
