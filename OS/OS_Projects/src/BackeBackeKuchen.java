import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class BackeBackeKuchen {

	private static Queue <Kuchen> auslage = new LinkedList<Kuchen>();
	private static Lock lock = new ReentrantLock();;
	private static Condition notFull = lock.newCondition();
	private static Condition notEmpty = lock.newCondition();
	
	private static Thread Baker1;
	private static Thread Baker2;
	private static Thread Consumer;
	
	public static void main(String[] args) 
	{
		Baker1 = new Thread()
		{
			public void run() //backen()
			{
				while(true)
				{
					Kuchen kuchen = new Kuchen();
					
					lock.lock();
					try 
					{
						while(auslage.size()==5)
						{
							System.out.println("Die Auslage ist voll!");
							
							notFull.await();
						}
						
						auslage.add(kuchen);
						System.out.println("Kuchen von Baecker 1 gebacken und in Auslage :-)");
						
						notEmpty.signalAll();
						
					} 
					
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}			

					
					finally
					{
						lock.unlock();
					}
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
				}		
			};			
		
		Baker2 = new Thread()
		{
			@Override
			public void run() //backen()
			{
				while(true)
				{
				Kuchen kuchen = new Kuchen();
				
				lock.lock();
				try 
				{
					while(auslage.size()==5)
					{
						System.out.println("Die Auslage ist voll!");
						
						notFull.await();
					}
					
					auslage.add(kuchen);
					System.out.println("Kuchen von Baecker 2 gebacken und in Auslage :-)");
					
					notEmpty.signalAll();
					
					
				} 
				
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}			

				finally
				{
					lock.unlock();
				}
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}}
		};
		
		
		Consumer = new Thread()
		{
			public void run() //konsumieren()
			{
				while(true)
				{
				lock.lock();
				try 
				{
				
					while(auslage.size()==0)
					{
						System.out.println("Kein Kuchen da :-(");
						notEmpty.await();
					}
				
					
					Kuchen kuchen = auslage.peek();
					System.out.println("Hmm, lecker! Ich esse : " + kuchen);
					auslage.poll();
					
					notFull.signalAll();
					}
				
				catch (InterruptedException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				finally
				{
					lock.unlock();					
				}
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
					
			}
			
			
		};
		
		Baker1.start();
		Baker2.start();
		Consumer.start();
		
		while(true)
		{
			//System.out.println(Baker1.getState());
			
			
		}

	}
	
	static class Kuchen
	{
		
	}

}
