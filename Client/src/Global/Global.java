package Global;


public class Global {
	public static int pollinginterval = 2000;
	public static String SYSTEMID = "";
	public static String coapServerPort = "5683";
	public static String Mode = "pull";
	public static String serverIP = "192.168.1.220";

	public static String state = "";
	public static String control = "";
	
	
	
	public static String getState() {
		return state;
	}

	public static void setState(String state) {
		Global.state = state;
	}


	public static void setControl(String control) {
		Global.control = control;
	}

	public static String getControl() {
		return control;
	}
}
