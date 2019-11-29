import java.util.Scanner;

/**
 * The main class that invokes all algorithms.
 * 
 * @author Brahma Dathan
 *
 */
public class Analyzer {
	public static final int DELAY_BETWEEN_REQUESTS = 1; // delay
	public static final int NUMBER_OF_CYCLINDERS = 1024;
	public static final int SWEEP_TIME = 30;
	private Requests requests = new Requests();

	/**
	 * Invokes each scheduling algorithm in turn. After invoking an algorithm, the
	 * process waits until both the request generator and the scheduler threads
	 * complete.
	 * 
	 * @param numberOfRequests number of requests to be processed.
	 */
	private void process(int numberOfRequests) {
		for (int index = 0; index < 4; index++) {
			RequestGenerator generator = new RequestGenerator(requests, numberOfRequests);
			Scheduler scheduler = AlgorithmFactory.instance().getScheduler(index, requests, numberOfRequests);
			generator.start();
			scheduler.start();
			try {
				generator.join();
				scheduler.join();
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}

	/**
	 * Starts up the Analyzer with the number of requests accepted from the user.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		int runs = 25; //Controls the Number of Runs
		System.out.print("Enter number of requests: ");
		Scanner scanner = new Scanner(System.in);
		int numberOfRequests = scanner.nextInt();
		scanner.close();
		for(int i = 0; i < runs; i++)	{
			new Analyzer().process(numberOfRequests);	
		}	
		System.out.println(); //prints blank line
		System.out.println("--------------------Average for " + runs + " runs----------------------"); //prints how many times it ran
		System.out.println("FCFS Average Tracks = " + Scheduler.FCFStotaltracks / runs + " FCFS Average Time Spent = " + Scheduler.FCFStotaltime / runs); // average of FCFS
		System.out.println("CLook Average Tracks = " + Scheduler.CLooktotaltracks / runs + " C-Look Average Time Spent = " + Scheduler.CLooktotaltime / runs); //average of C-Look
		System.out.println("Look Average Tracks =  " + Scheduler.Looktotaltracks / runs + " Look Average Time Spent = " + Scheduler.Looktotaltime / runs); //average of Look		
		System.out.println("SSTF Average Tracks =  " + Scheduler.SSTFtotaltracks / runs + " SSTF Average Time Spent = " + Scheduler.SSTFtotaltime / runs); //average of SSTF
	}
}
