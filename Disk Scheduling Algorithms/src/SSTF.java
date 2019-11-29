/**
 * @author Ryan Brodsky
 * ICS 462 Operating Systems 
 * Assignment 6 Part 4 
 * 8/6/2019
 */

import java.util.Collections;
import java.util.Vector;
import java.lang.Math; 

/**
 * Implements the shortest seek time first algorithm
 */
public class SSTF extends Scheduler {

	private int head = 0; //keeps track of the current Head position
	private int next = 0; //keeps track of next request 

	/**
	 * Stores the Requests object and number of requests to be processed using the
	 * superclass's constructor.
	 * 
	 * @param requests
	 * @param numberOfRequests
	 */
	public SSTF(Requests requests, int numberOfRequests) {
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
	 * This method process the next Request, It starts by sorting the list
	 * then calls the find next method to find the closest request
	 * it then records the request and moves on
	 */
	@Override
	public void processNextRequest() {

		Collections.sort(tempRequests); //Sorts the Current Request List

		if(processed == numberOfRequests) {//Checks to see if the Number Processed equals total requests
			return;
		}

		findNext(head, tempRequests); //finds closest request
		//System.out.println("Request from:  " + tempRequests.get(next) + " New Distance: " + Math.abs((tempRequests.get(next) - head)));
		numberofTracksMoved = numberofTracksMoved + (Math.abs(tempRequests.get(next) - head)); //updates number of tracks moved
		timeSpent = timeSpent + sleep(Math.abs(tempRequests.get(next) - head)); //updates time spent
		head = tempRequests.get(next); //sets requests value as new head
		tempRequests.remove(next); //removes requests from list
		processed++; //increase number processed
		return;

	}
	
	/**
	 * This method takes the head and tempRequest as parameters,
	 * and finds the closest request to the head.
	 * @param head
	 * @param tempRequests
	 */
	public void findNext(int head, Vector<Integer> tempRequests) {
		int temp = Integer.MAX_VALUE; 
		
		for(int i = 0; i < tempRequests.size(); i++)	{
			if (Math.abs(head - tempRequests.get(i)) < temp)	{ //if the absolute value of head minus current request is less than temp
				temp = Math.abs(head - tempRequests.get(i)); //updates temp value
				next = i; //marks next request
			}
		}
	}

	@Override
	public String getAlgorithmName() {
		return "SSTF";
	}
}