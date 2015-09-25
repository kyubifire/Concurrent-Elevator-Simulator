package lift;

import java.util.ArrayList;

/**
 * This default lift controller doesn't work very well!
 * You need to implement this lift controller as specified.
 * @author K. Bryson
 */

/*
 * Edited by: Nilo Lisboa
 * SID#: 14097296
 */
public class MyLiftController implements LiftController {
	//passes all tests
	//Variables to keep track of floor calling elevator and direction required
	int[] up = new int[9];
	int[] down = new int[9];  //arrays to keep track of up buttons and down buttons across all floors
	boolean doorsOpen = false; //open the doors!
	boolean close = false;     //close the doors!

	
	int liftfloor;
	Direction liftdirection;
	
    /* Interface for People */
	
	//for the sake of simplicity, if the floor >= 4 then execute first half, else execute the other half.
	//It would suck to possibly check 8 if statements.
    synchronized public void callLift(int floor, Direction direction) throws InterruptedException {
    	if (direction == Direction.UP) {
    		up[floor] = 1;
    	} else {
    		down[floor] =  1;
    	}
    	while(!(liftfloor == floor && liftdirection == direction)){ //while lift isn't on the correct floor going the same way, wait for it!
    		wait();
    	}
    	doorsOpen = false;
    	close = true;
    	notifyAll();
    }

    synchronized public void selectFloor(int floor) throws InterruptedException{
    	if (liftdirection == Direction.UP && liftfloor <= floor){ 
    		if (floor == 8){
    			down[floor] = 1;     //exception for floor 8 that only goes down
    		}else{
    			up[floor] = 1;
    		}
    	} else if (liftdirection == Direction.UP && liftfloor > floor) {
    		down[floor] = 1;
    	} else if (liftdirection == Direction.DOWN && liftfloor >= floor) {
    		if (floor == 0) {
    			up[floor] = 1;      //exception for floor 0 that only goes up
    		} else {
    			down[floor] = 1;
    		}
    	} else if (liftdirection == Direction.DOWN && liftfloor < floor) {
    		up[floor] = 1;
    	}
    	doorsOpen = false;
    	close = true;
    	notifyAll();
    	while(!(liftfloor == floor)){ //independent of direction, don't return if lift isn't on the floor
    		wait();
    	}
    	doorsOpen = false;
    	close = true;       
    	notifyAll();
    	
    }

    
    /* Interface for Lifts */
    synchronized public boolean liftAtFloor(int floor, Direction direction) {	
    	liftfloor = floor;
    	liftdirection = direction;
    	
    	if (direction == Direction.UP) {
    		if (up[floor] == 1) {   //is there a button pressed up?
    			up[floor] = 0;
    			return true;
    		} else {
    			return false;
    		}
    	} else if (direction == Direction.DOWN) {
    		if (down[floor] == 1) { //is there a button pressed down?
    			down[floor] = 0;
    			return true;
    		} else {
    			return false;
    		}
    	}
    	return false;
    }

    synchronized public void doorsOpen(int floor) throws InterruptedException {
    	close = false;
    	doorsOpen = true;
    	notifyAll();
    	while(!close){     //while people arent inside the elevator, dont close
    		wait();
    	}	
    }

    synchronized public void doorsClosed(int floor) { //reset variables
    	if (liftdirection == Direction.UP) {
    		up[floor] = 0;
    	} else {                      //Erase the button press from the system
    		down[floor] = 0;
    	}
    	doorsOpen = false;
    	close = true;
    }

}
