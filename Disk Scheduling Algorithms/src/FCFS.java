/**
 * @author Ryan Brodsky
 * ICS 462 Operating Systems 
 * Assignment 6 Part 4 
 * 8/6/2019
 */

/**
 * Implements the First Come First Serve Algorithm
 */
public class FCFS extends Scheduler {
	private int head = 0; //keeps track of the current Head position

	/**
	 * Stores the Requests object and number of requests to be processed using the
	 * superclass's constructor.
	 * 
	 * @param requests
	 * @param numberOfRequests
	 */
	public FCFS(Requests requests, int numberOfRequests) {
		super(requests, numberOfRequests);
	}

	/**
	 * Initializes the numberOfTracks, and timeSpent. 
	 */
	public void initialize() {
		this.numberofTracksMoved = 0; //Initialize to Zero
		this.timeSpent = 0; //Initialize to Zero
	}

	/**
	 *Loops through request and grabs the whatever request is next.
	 *No sorting, takes request in order received
	 */
	@Override
	public void processNextRequest() {

		for (int i = 0; i < tempRequests.size(); i++)	{ 

			if(processed == numberOfRequests) {//Checks to see if the Number Processed equals total requests
				break;
			}

			if(tempRequests.get(i) >= head )	{ //Checks if requests is greater or equal to head
				//System.out.println("Request from:  " + tempRequests.get(i) + " New Distance: " + (tempRequests.get(i) - head));
				numberofTracksMoved = numberofTracksMoved + (tempRequests.get(i) - head); //updates number of tracks moved
				timeSpent = timeSpent + sleep(tempRequests.get(i) - head); //updates time spent
				head = tempRequests.get(i); //sets requests value as new head
				tempRequests.remove(i); //removes requests from list
				processed++; //increase number processed
				break; //breaks out of loop to restart
			}
			
			if(tempRequests.get(i) < head)	{
				//System.out.println("Request from:  " + tempRequests.get(i) + " New Distance: " + (head - tempRequests.get(i))); //Print message for testing
				numberofTracksMoved = numberofTracksMoved + (head - tempRequests.get(i)); //updates number of tracks moved
				timeSpent = timeSpent + sleep(head - tempRequests.get(i)); //updates time spent
				head = tempRequests.get(i); //sets request value as new head
				tempRequests.remove(i); //removes request from list
				processed++; //increase number processed
				break;
			}
		}		
	}

	@Override
	public String getAlgorithmName() {
		return "FCFS";
	}
}