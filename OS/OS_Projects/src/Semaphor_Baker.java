import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Semaphor_Baker
{
	private static Semaphore semaphor = new Semaphore(1, true);

	private static Thread Baker1;
	private static Thread Baker2;
	private static Thread Consumer;

	private static Queue<Kuchen> auslage = new LinkedList<Kuchen>();

	public static void main(String[] args)
	{
		Baker1 = new Thread()
		{
			@Override
			public void run() // bake()
			{
				while (true)
				{
					try
					{
						while (auslage.size() >= 5)
						{
							System.out.println("Die Auslage ist voll!");
							semaphor.release();
						}

						Kuchen kuchen = new Kuchen();
						semaphor.acquire();

						auslage.add(kuchen);
						System.out
								.println("Kuchen von Baecker 1 gebacken und in Auslage :-)");

						

					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally
					{
						semaphor.release();
					}
					try
					{
						Thread.sleep(2000);
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};

		Baker2 = new Thread()
		{
			@Override
			public void run()
			{
				while (true)
				{
					try
					{
						while (auslage.size() >= 5)
						{
							System.out.println("Die Auslage ist voll!");
							semaphor.release();
						}

						Kuchen kuchen = new Kuchen();
						semaphor.acquire();

						auslage.add(kuchen);
						System.out
								.println("Kuchen von Baecker 2 gebacken und in Auslage :-)");

					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally
					{
						semaphor.release();
					}
					try
					{
						Thread.sleep(3000);
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		};

		Consumer = new Thread()
		{
			@Override
			public void run()
			{
				while (true)
				{
					while (auslage.size() == 0)
					{
						System.out.println("Kein Kuchen da :-(");
						semaphor.release();
					}

					try
					{
						Kuchen kuchen = auslage.peek();
						semaphor.acquire();

						System.out.println("Hmm, lecker! Ich esse : " + kuchen);

						auslage.poll();
						
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally
					{
						semaphor.release();
					}
					try
					{
						Thread.sleep(1000);
					} catch (InterruptedException e)
					{
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
			
		}

	}

	static class Kuchen
	{

	}

}
