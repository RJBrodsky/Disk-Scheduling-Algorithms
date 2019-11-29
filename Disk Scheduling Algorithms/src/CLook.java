/**
 * @author Ryan Brodsky
 * ICS 462 Operating Systems 
 * Assignment 6 Part 4 
 * 8/6/2019
 */

import java.util.Collections;

/**
 * Implements the C Look Algorithm
 */
public class CLook extends Scheduler {

	private int head = 0; //keeps track of the current Head position

	/**
	 * Stores the Requests object and number of requests to be processed using the
	 * superclass's constructor.
	 * 
	 * @param requests
	 * @param numberOfRequests
	 */
	public CLook(Requests requests, int numberOfRequests) {
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
	 * if the num processed == num request it breaks out
	 * it then loops through each request, starting at the lowest number.
	 * if the request is greater too or equal too the head in records the request and moves on
	 * it resets the head on last request
	 */
	@Override
	public void processNextRequest() {
		
		Collections.sort(tempRequests); //Sorts the Current Request List

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
			
			if(head >= tempRequests.lastElement())	{ //Checks if the head is greater or equal to last element
				int temp = head;
				head = tempRequests.firstElement();
				numberofTracksMoved = numberofTracksMoved + (temp - head); //updates number of tracks moved
				timeSpent = timeSpent + sleep(temp - head); //updates time spent
				break;
			}
		}		
	}

	@Override
	public String getAlgorithmName() {
		return "C-Look";
	}
}