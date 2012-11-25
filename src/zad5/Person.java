package zad5;


public class Person extends Thread {
	
	private PersonType pt;
	private Resource res;
	private int actions_left = 2;
	private int id;
	
	
	Person(Resource res,int id, PersonType pt, int actions_left)
	{
		this.res = res;
		this.id = id;
		this.pt = pt;
		this.actions_left = actions_left;
		this.created_info();
		res.add_to_queue(this);
		//this.start();
		//res.get_vectors_info();
	}
	
	public void run() 
	{

		//this.wait_for_acces();
		
		for(int i=0;i<=this.actions_left;i++)
		{
			//System.out.println("ITERACJA! "+String.valueOf(i)+this.get_info());
		boolean access_granted = res.get_access(this);
		//res.get_vectors_info();
		if(access_granted && actions_left > 0)
		{
			this.use_action();
			this.get_acces_info();
			//res.get_vectors_info();
			res.wait_sec(2);
						
			if(pt == PersonType.WRITER)
			{
				res.write(this,"test");
				this.write_info("test");
			}
			else if(pt == PersonType.READER)
			{
				String msg = res.read();
				this.read_info(msg);
			}
			this.leave_acces_info();
			res.leave_access(this);
		}
		}
		//System.out.println("KONIEC!"+this.get_info());
	}
	
	
	public synchronized void wait_for_acces()
	{
		try {
			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized void wake_up()
	{
		this.notify();
	}
	
	public PersonType getPt() {
		return pt;
	}
	public void setPt(PersonType pt) {
		this.pt = pt;
	}
	
	private void use_action()
	{
		actions_left -= 1;
	}
	
	public int get_actions_left() {
		return actions_left;
	}
	
	public String get_info()
	{
		String type,id;
		if(pt == PersonType.READER)
			type = "R";
		else
			type = "W";
		
		id = String.valueOf(this.id);
		return type+id;
	}
	public void created_info()
	{

		String msg = "+"+this.get_info()+" Stworzono i czeka na dostep";
		System.out.println(msg);
	}
	
	public void get_acces_info()
	{
		String msg = "O-"+this.get_info()+" Otrzymal dostep";
		System.out.println(msg);
	}
	
	public void leave_acces_info()
	{
		String msg = "Z-"+this.get_info()+" zwolnil dostep";
		System.out.println(msg);
	}
	
	public void write_info(String text)
	{
		String msg = "A-"+this.get_info()+" dopisal "+text;
		System.out.println(msg);
	}
	
	public void read_info(String text)
	{
		String msg = "A-"+this.get_info()+" przeczytal znakow: "+text.length();
		System.out.println(msg);
	}
	
	public void deleted_info()
	{
//		String msg = "-"+this.get_info()+" wykorzystal wszystkie swoje akcje";
//		System.out.println(msg);
	}

	@Override
	public String toString() {
		return "Person [ pt=" + this.pt + ", actions_left="
				+ actions_left + ", id=" + id + "]";
	}
	
	public int get_id()
	{
		return this.id;
	}
	
	
	

}
