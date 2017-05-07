import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.leapmotion.leap.*;

import java.util.Scanner; 

/**
 * 
 */

/**
 * @author joepl
 * @extends Listener
 */
public class LeapListener extends Listener {
		
	
	/**
	 * @param controller
	 */
	public void onInIt(Controller controller) 
	{
			
		System.out.println("Initialized");
		
	}
		
	
	
	/**
	 * @param controller
	 */
	public void onConnect(Controller controller) 
	{
			
		System.out.println("Connected to Motion Sensor");
			
		//add gestures
		controller.enableGesture(Gesture.Type.TYPE_SWIPE);
			
		controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
			
		controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
			
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
	
	}
		
	
	/**
	 * @param controller
	 */
	public void onDisconnect(Controller controller) {
				
		System.out.println("Motion Sensor Disconnected");
		
	}
		
		
	/**
	 * @param controller
	 */
	public void onExit(Controller controller) {
		
		System.out.println("Exited");
		
	}
		
		
	/**
	 * @param controller
	 */
	public void onFrame(Controller controller) {
		
		//create Frame object for the controller to read from
		Frame frame = controller.frame();
		
		//Gesture data
		GestureList gestures = frame.gestures();
		
		//Iterate through loop until count is equal to the amount of gestures
		for(int i=0;i<gestures.count();i++)
		{
			
			//Gesture object that is equal to the gesture at the i frame
			Gesture gesture = gestures.get(i);
			
			//String that will be translated
			String q = "";
			
			//types of gestures
			switch(gesture.type()) 
			{
					
				case TYPE_KEY_TAP:
					
					//set String q to "You" since a Screen Tap gesture was read
					q = "This%20is%20a%20key%20tap%20gesture";
					
					try {
						//translate the String q by running translateText() method with a parameter of q
						translateText(q);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				break;
				
				case TYPE_SWIPE:
					
					//set String q to "You" since a Screen Tap gesture was read
					q = "This%20is%20a%20swipe";
					
					try {
						//translate the String q by running translateText() method with a parameter of q
						translateText(q);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				break;
					
				case TYPE_INVALID:
				break;
					
				case TYPE_CIRCLE:
					
					//Create a Circle Gesture Object
					CircleGesture circle = new CircleGesture(gesture);
					
					//If the angle between the normal and the pointable object drawing the circle is less than 90 degrees, then the circle is clockwise.
					if(circle.pointable().direction().angleTo(circle.normal()) <= Math.PI/4)
					{
					
						q = "This%20circle%20is%20rotating%20clockwise";
						
						try {
							//translate q
							translateText(q);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					} else {
					
						q = "This%20circle%20is%20rotating%20counterclockwise";
						
						try {
							//translate q
							translateText(q);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}//end else
					
				break;
						
						
				case TYPE_SCREEN_TAP:
					
					//set String q to "You" since a Screen Tap gesture was read
					q = "You";
					
					//System.out.println("YOU");
					
										
				try {
					//translate the String q by running translateText() method with a paramter of q
					translateText(q);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				break;
					
			}// end Switch statement
					
		}//end for loop

	}//end onFrame
	
	//method that actually translate the text
	/**
	 * @param q
	 * @throws Exception
	 */
	public static void translateText(String q) throws Exception 
	{
		
		String sourceLang = " ";
		
		//find the source language, or the language you want to translate to
		Scanner scan = new Scanner(System.in);
		System.out.println("What language would you like to translate to: ");
		sourceLang = scan.next();
		scan.close();
		
		//create URL to request translation using the REST method
		URL googleTranslate = new URL("https://translation.googleapis.com/language/translate/v2?key=AIzaSyB3fYnEkoanbhGwmo9Suti1hrLmdf0Kz6o&source=en&target=" + sourceLang + "&q=" + q);
		
		//open the URL connection
		URLConnection yc = googleTranslate.openConnection();
		
		//create a Buffered Reader Object to read the input stream
		BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

		//Google Gson parser to read only 'translations' Json array
		//parse Buffered Reader named in
		JsonElement jelement = new JsonParser().parse(in);
		//create Json Object and retrieve as Json object
	    JsonObject  jobject = jelement.getAsJsonObject();
	    //retrieve Json 'data' array
	    jobject = jobject.getAsJsonObject("data");
	    //retrieve Json 'translations' array
	    JsonArray jarray = jobject.getAsJsonArray("translations");
	    //retrieve instance at 0 as a Json Object
	    jobject = jarray.get(0).getAsJsonObject();
	    //set result equal to the translated text and run toString() on Object
	    String result = jobject.get("translatedText").toString();
	    
	    //output result
	    System.out.println("The translated result: " + result);
	    
	    //close buffered reader
	    in.close();
	    
		
	}//end translateText
	
}//end class
