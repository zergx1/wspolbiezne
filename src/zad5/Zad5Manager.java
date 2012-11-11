package zad5;

public class Zad5Manager {
	
	public Zad5Manager(){
		Resource r = new Resource();
//		for(int i=0;i<4;i++)
//		{
			Person p = new Person(r,1,PersonType.READER,2);
			p.start();

			p = new Person(r,2,PersonType.READER,2);
			p.start();
			
			p = new Person(r,3,PersonType.WRITER,2);
			p.start();
			
			p = new Person(r,4,PersonType.READER,2);
			p.start();

			p = new Person(r,5,PersonType.READER,2);
			p.start();

//			p = new Person(r,6,PersonType.WRITER,2);
//			p.start();
//			p = new Person(r,7,PersonType.READER,2);
//			p.start();


		//}
//		p.run();
//		Person c = new Person(r,2,PersonType.WRITER,2);
//		c.run();
	}

}
