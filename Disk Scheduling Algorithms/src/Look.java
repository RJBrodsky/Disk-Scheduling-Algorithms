import java.util.Collections;
/**
 * Implements the Elevator algorithm.
 * 
 * @author Brahma Dathan
 *
 */
public class Look extends Scheduler {
	private Direction direction; //keeps track of the direction
	private int head = 0; //keeps track of the current Head position

	private enum Direction {
		UP, DOWN
	};

	/**
	 * Stores the Requests object and number of requests to be processed using the
	 * superclass's constructor.
	 * 
	 * @param requests
	 * @param numberOfRequests
	 */
	public Look(Requests requests, int numberOfRequests) {
		super(requests, numberOfRequests);
	}

	/**
	 * Initializes the direction, numberOfTracks, and timeSpent. 
	 */
	public void initialize() {
		this.direction = Direction.UP; //Initializes the direction to UP, We start at 0
		this.numberofTracksMoved = 0; //Initialize to Zero
		this.timeSpent = 0; //Initialize to Zero

	}

	/**
	 * This method process the next Request, It starts by sorting the list
	 * then based on the direction finds the next request.
	 * it updates the statistics, then removes that request
	 * It then repeats everything until all request have been processed
	 */
	@Override
	public void processNextRequest() {
		
		Collections.sort(tempRequests); //Sorts the Current Request List

		if(direction == Direction.UP)	{	//checks if direction is Up
			for (int i = 0; i < tempRequests.size(); i++)	{ //loops through sorted list starting at smallest value
				
				if(processed == numberOfRequests) {//Checks to see if the Number Processed equals total requests
					break;
				}

				if(tempRequests.get(i) >= head )	{ //Checks if requests is greater or equal to head
					//System.out.println("Request from:  " + tempRequests.get(i) + " Direction UP"); //Print message for testing
					numberofTracksMoved = numberofTracksMoved + (tempRequests.get(i) - head); //updates number of tracks moved
					timeSpent = timeSpent + sleep(tempRequests.get(i) - head); //updates time spent
					head = tempRequests.get(i); //sets requests value as new head
					tempRequests.remove(i); //removes requests from list
					processed++; //increase number processed
					break; //breaks out of loop to restart
				}
				
				if(head >= tempRequests.lastElement())	{ //Checks if the head is greater or equal to last element
					direction = Direction.DOWN; //direction Change
				}
			}		
		}
		
		if(direction == Direction.DOWN)	{	//checks if direction is down		
			for (int i = tempRequests.size() - 1; i >= 0; i--)	{ //loops through sorted list starting at greatest value
				
				if(processed == numberOfRequests) {//Checks to see if the Number Processed equals total requests
					break;
				}

				if(tempRequests.get(i) <= head )	{ //checks if the request is less than or equal to head
					//System.out.println(numberofTracksMoved);
					numberofTracksMoved = numberofTracksMoved + (head - tempRequests.get(i)); //updates number of tracks moved
					timeSpent = timeSpent + sleep(head - tempRequests.get(i)); //updates time spent
					head = tempRequests.get(i); //sets request value as new head
					tempRequests.remove(i); //removes request from list
					processed++; //increase number processed
					break; //break out of loop to restart
				}
				
				if(head <= tempRequests.firstElement())	{//Checks if the head is less than or equal to first element
					direction = Direction.UP; //direction Change
				}
			}			
		}
	}

	@Override
	public String getAlgorithmName() {
		return "Look";
	}
}
