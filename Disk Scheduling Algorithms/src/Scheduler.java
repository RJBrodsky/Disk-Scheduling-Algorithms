import java.util.Vector;

/**
 * Implements or specifies the common aspects of all disk scheuling algorithms.
 * 
 * @author Brahma Dathan
 * @author Ryan Brodsky
 *
 */
public abstract class Scheduler extends Thread {
	protected Vector<Integer> tempRequests = new Vector<Integer>();
	protected Requests requests;
	protected int numberOfRequests;
	protected int processed = 0;
	protected Vector<Integer> newRequests;
	protected int currentPosition = 0;

	/**
	 * These fields are for the statistics
	 */
	protected int numberofTracksMoved;//Number of tracks moved?
	protected int timeSpent;//Time Spent?
	
	/**
	 * Integers to keep track of the totals for each run.
	 * These variables are used later to find the average.
	 * These were added to help me write the report
	 */
	static int FCFStotaltracks; 
	static int CLooktotaltracks;
	static int SSTFtotaltracks;
	static int Looktotaltracks;
	static int FCFStotaltime;
	static int CLooktotaltime;
	static int SSTFtotaltime;
	static int Looktotaltime;

	/**
	 * Stores the Requests object and number of requests.
	 * 
	 * @param requests
	 * @param numberOfRequests
	 */
	public Scheduler(Requests requests, int numberOfRequests) {
		this.requests = requests;
		this.numberOfRequests = numberOfRequests;
	}

	/**
	 * Initialize the relevant variables for the specific scheduling algorithm.
	 */
	public abstract void initialize();

	/**
	 * Each algorithm has a specific way of choosing the next request. After that
	 * some preparations have to be done before accepting the next request. This
	 * method does all that.
	 */
	public abstract void processNextRequest();

	/**
	 * Returns the name of the scheduling algorithm.
	 * 
	 * @return the name of the algorithm
	 */
	public abstract String getAlgorithmName();
	
	/**
	 * The common parts of the algorithm for all disk schedulers.
	 */
	public void run() {
		initialize();
		while (processed < numberOfRequests) {
			if (tempRequests.size() == 0) {
				newRequests = requests.get(true);
			} else {
				newRequests = requests.get(false);
			}
			tempRequests.addAll(newRequests);
			processNextRequest();
		}
		printStatistics(getAlgorithmName());
	}

	/**
	 * Simulates the processing of seeking to a track by sleeping for a specific
	 * period.The sleep time is quite approximate. A more sophisticated approach
	 * would make it proportional to the square root of the distance to be moved.
	 * 
	 * @param tracks number of tracks to be moved
	 * @return time slept
	 */
	public int sleep(int tracks) {
		if (tracks == 0) {
			return 0;
		}
		try {
			int time = Math.max(1, tracks * Analyzer.SWEEP_TIME / Analyzer.NUMBER_OF_CYCLINDERS);
			Thread.sleep(time);
			return time;
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		return 0;
	}

	/**
	 * Prints the statistics for a scheduling algorithm.
	 * @param algorithm the name of the algorithm.
	 */
	public void printStatistics(String algorithm) {
		
		/**
		 * These if statements keep track of each runs number of tracks moved and time spent
		 * In my analyzer class i loop the main method 25 times and then averaged these values
		 * Without my analyzer class this code doesn't serve a purpose
		 */
		if(getAlgorithmName().equalsIgnoreCase("FCFS"))	{ // keeps track of tracks and time spent for average
			FCFStotaltracks += numberofTracksMoved;
			FCFStotaltime += timeSpent;
		}
		if(getAlgorithmName().equalsIgnoreCase("Look"))	{ // keeps track of tracks and time spent for average
			Looktotaltracks += numberofTracksMoved;
			Looktotaltime += timeSpent;
		}
		if(getAlgorithmName().equalsIgnoreCase("C-Look"))	{ // keeps track of tracks and time spent for average
			CLooktotaltracks += numberofTracksMoved;
			CLooktotaltime += timeSpent;
		}
		if(getAlgorithmName().equalsIgnoreCase("SSTF"))	{ // keeps track of tracks and time spent for average
			SSTFtotaltracks += numberofTracksMoved;
			SSTFtotaltime += timeSpent;
		}
		
		/**
		 * This is the output for each run.
		 */
		System.out.println(getAlgorithmName() + ":"); //Print algorithm name
		System.out.println("Number of Tracks Moved: " + numberofTracksMoved);// print numTracksMoved
		System.out.println("Requests Processed " + numberOfRequests + " Time Spent " + timeSpent); //print numRequests and timeSpent
	}
}