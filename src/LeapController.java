import java.io.IOException;
import com.leapmotion.leap.*;

/**
 * @author joepl
 *
 */
//Creates Leap Controller to manage the listener and controller.
//Class also adds the listener to the Controller object named controller
public class LeapController {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Creates the LeapListener Object names listener to read input
		LeapListener listener = new LeapListener();
		
		//Creates the Controller Object named controller that controls the flow of the program
		Controller controller = new Controller();
		
		//adds the listener to the Leap Motion controller
		controller.addListener(listener);
		
		System.out.println("Press Enter to quit");
		
		//reads input from the listener, throws exception if errors
		try {
			System.in.read();
		} catch (IOException e){
			e.printStackTrace();
		}
		
		//removes the listener from the controller, and exits
		controller.removeListener(listener);

	}
	
}
