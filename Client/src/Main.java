import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONException;

import Global.*;
import com.pi4j.wiringpi.Gpio;
import com.mir.Service.*;
public class Main {

	public static void main(String[] args) throws IOException, JSONException {
		
		/*
		 * 1. Get CoAP Information
		 * 2. Run CoAP Client Connect URI
		 * 3. Setup GPIO
		 */
		
		parseCFG();
		
		//2. Run CoAP Client Connect URI에서 (Connect Class에서 session()실행) 
		//fill () in here
		new Connect().session();
		
		//3. Setup GPIO
		Gpio.wiringPiSetup();
		
	}	
	
	/*
	 * 1. Get CoAP Information through File
	 * 	1-1. File Read
	 *  1-2. Get My DeviceID, CoAPServerIP, COAPServerPort, Mode("pull" or "push") Period and Save Global Class
	 */
	static void parseCFG() {

		try {
			//1-1. File Read
			FileReader fileReader = new FileReader("sim.cfg");
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			//1-2. Get My DeviceID, CoAPServerIP, COAPServerPort, Mode("pull" or "push") Period and Save Global Class
			Global.SYSTEMID = bufferedReader.readLine();
			Global.serverIP = bufferedReader.readLine();
			Global.coapServerPort = bufferedReader.readLine();
			Global.Mode = bufferedReader.readLine();
			Global.pollinginterval = Integer.parseInt(bufferedReader.readLine());
			
			
			System.out.println("ID\t\t\t\t" + Global.SYSTEMID);
			System.out.println("ServerIP\t\t\t" + Global.serverIP);
			System.out.println("COAP SERVER PORT\t\t" + Global.coapServerPort);
			System.out.println("Mode\t\t\t\t"+Global.Mode);
			System.out.println("Interval\t\t\t" + Global.pollinginterval);
		
			bufferedReader.close();

		} catch (Exception e) {
			System.out.println("CAN NOT FIND CONFIG FILE(sim.cfg), Check and Restart");
			System.exit(-1);
		}
	}
}
