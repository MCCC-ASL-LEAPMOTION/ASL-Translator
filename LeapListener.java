import com.leapmotion.leap.Gesture.State;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.leapmotion.leap.*;

import java.util.Scanner; 

/**
 * 
 */

/**
 * @author joepl
 *
 */


class LeapListener extends Listener {
		
	
	public void onInIt(Controller controller) 
	{
			
		System.out.println("Initialized");
		
	}
		
	
	public void onConnect(Controller controller) 
	{
			
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
		
		//Gesture data
		GestureList gestures = frame.gestures();
				
		for(int i=0;i<gestures.count();i++)
		{
				
			Gesture gesture = gestures.get(i);
					
			switch(gesture.type()) 
			{
					
				case TYPE_KEY_TAP:
				break;
				
				case TYPE_SWIPE:
				break;
					
				case TYPE_INVALID:
				break;
					
				case TYPE_CIRCLE:
					
					CircleGesture circle = new CircleGesture(gesture);
						
					//clockwise or counter clockwise
					String clockwiseness;
				
					if(circle.pointable().direction().angleTo(circle.normal()) <= Math.PI/4)
					{
					
						String q = "clockwise";
						
						try {
							System.out.println(q);
							translateText(q);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					} else {
					
						String q = "counterclockwise";
						
						try {
							translateText(q);
							System.out.println(q);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					

				break;
						
						
				case TYPE_SCREEN_TAP:
						
					ScreenTapGesture screenTap = new ScreenTapGesture(gesture);
					
					String q = "You";
					
					//System.out.println("YOU");
					
										
				try {
					translateText(q);
					System.out.println(q);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				break;
					
			}// end Switch statement
					
		}//end for loop

	}//end onFrame
	
	
	public static void translateText(String q) throws Exception 
	{
		
		String sourceLang = " ";
		
		//find the source language, or the language you want to translate to
		Scanner scan = new Scanner(System.in);
		System.out.println("What language would you like to translate to: ");
		sourceLang = scan.next();
		//System.out.println(sourceLang);
		scan.close();
		
		//URL googleTranslate = new URL("https://translation.googleapis.com/language/translate/v2?key=AIzaSyB3fYnEkoanbhGwmo9Suti1hrLmdf0Kz6o&source=" +  + "&target=" + "&q=" + q);
		
		URL googleTranslate = new URL("https://translation.googleapis.com/language/translate/v2?key=AIzaSyB3fYnEkoanbhGwmo9Suti1hrLmdf0Kz6o&source=en&target=" + sourceLang + "&q=" + q);
		
		URLConnection yc = googleTranslate.openConnection();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
		
		String inputLine = null;
		while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);
		}
	    
	    in.close();
	    
		
	}//end translateText
	
}//end class
