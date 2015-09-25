
package lift;

/**
 * This default lift controller doesn't work very well!
 * You need to implement this lift controller as specified.
 * @author K. Bryson
 */
public class MyLiftController implements LiftController {

    /* Interface for People */
    public void callLift(int floor, Direction direction) throws InterruptedException {}

    public void selectFloor(int floor) throws InterruptedException{}

    
    /* Interface for Lifts */
    public boolean liftAtFloor(int floor, Direction direction) {return true;}

    public void doorsOpen(int floor) throws InterruptedException {}

    public void doorsClosed(int floor) {}

}
