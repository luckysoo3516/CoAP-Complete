
import java.io.IOException;
import java.util.Scanner;

import com.tipw.global.*;

public class Main {
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		
		
		/*
		 * 1. Run CoAP Server
		 * 2. Process user Event
		 * 
		 */
		
		
		// 2. Run CoAP Server
		new CoAPServerBase();
		


		// 2. Process user Event
		while (true) {
			/*
			 * 3. Process User Event
			 * 
			 * 3-1. Get Device ID
			 * 3-2. Get Event State
			 * 3-3. Set Device Event
			 */
			
			// 2-1. Get Device ID
			System.out.println("DeviceID Input:");
			String id = scan.next();
			DeviceInfo device = Global.device_list.get(id);
					
			if (device == null) {
				System.out.println("[" + id + "] can not find");
				continue;
			}
			
			
			
			// 2-2. Get Event State
			String state = null;
			while (true) {
				System.out.println("User Input OFF:0 ON:1 :");
				int input = scan.nextInt();
				
				if (input == 0) {
					state = "OFF";
				} else if (input == 1) {
					state = "ON";
				} else {
					System.out.println("Wrong");
					continue;
				}
				break;
			}
			
			// 2-3. Set Device Event
			device.ControlEvent(state);
		}
		
	}

}
