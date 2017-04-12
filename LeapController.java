/**
 * 
 */
import java.io.IOException;

import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;

class LeapListener extends Listener {
	
	public void onInIt(Controller controller) {
		System.out.println("Initialized");
	}
	
	public void onConnect(Controller controller) {
		
		System.out.println("Connected to Motion Sensor");
		
		//add gestures
		controller.enableGesture(Gesture.Type.TYPE_SWIPE);
		
		controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
		
		controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
		
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
	}
	
	public void onDisconnect(Controller controller) {
			System.out.println("Motion Sensor Disconnected");
	}
	
	public void onExit(Controller controller) {
		System.out.println("Exited");
	}
	
	public void onFrame(Controller controller) {
		Frame frame = controller.frame();
		/**System.out.println("frame id: " + frame.id()
							+ " timestamp: " + frame.timestamp()
							+ " number of hands: " + frame.hands().count()
							+ " number of fingers: " + frame.fingers().count()
							+ " tools: " + frame.tools().count()
							+ " number of gestures: " + frame.gestures().count());**/
		
		/**for(Hand hand: frame.hands()) {
			
			//find hand data
			String handType = hand.isLeft() ? "Left Hand" : "Right Hand";
			System.out.println(handType + " " + ", id: " + hand.id() 
								+ ", Palm Position: " + hand.palmPosition());
			
			//pitch direction and yaw data
			Vector normal = hand.palmNormal();
			Vector direction = hand.direction();
			
			System.out.println("Pitch: " + Math.toDegrees(direction.pitch())
								+ " Roll: " + Math.toDegrees(normal.roll())
								+ " Yaw: " + Math.toDegrees(direction.yaw()));
		
		}**/
		
		//finger data
		for(Finger finger: frame.fingers()) {
			System.out.println("Finger Type: " + finger.type()
								+ " id: " + finger.id()
								+ " Finger Length: " + finger.length()
								+ " Finger Width: " + finger.width());
			
			//bone data
			for(Bone.Type boneType : Bone.Type.values()) {
				Bone bone = finger.bone(boneType);
				System.out.println("Bone Type: " + bone.type()
									+ " Start: " + bone.prevJoint()
									+ " End: " + bone.nextJoint()
									+ " Direction: " + bone.direction());
			}
			
		}
		
		
	}
	
}

/**
 * @author joepl
 *
 */
public class LeapController {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LeapListener listener = new LeapListener();
		Controller controller = new Controller();
		
		controller.addListener(listener);
		
		System.out.println("Press Enter to quit");
		
		try {
			System.in.read();
		} catch (IOException e){
			e.printStackTrace();
		}
		
		
		controller.removeListener(listener);

	}
	
}
