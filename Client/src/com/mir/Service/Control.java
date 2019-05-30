package com.mir.Service;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.californium.core.CoapClient;
import org.json.JSONObject;


import Global.Global;

public class Control extends Thread {


	/*
	 1. Timer UpdateTask Thread
     2. Report URI CoAPServer ReportResource
     3. Response LED
	*/


	// 1. Timer UpdateTask Thread
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		Timer timer = new Timer();
		timer.schedule(new UpdateTask(), 0, Global.pollinginterval);
	}


	//2-1. Califorinum Lib CoapClient
	CoapClient client=new CoapClient();



	private class UpdateTask extends TimerTask {
		@Override
		public void run() {
			// 2-2. CoAP Server Control URI Setting
			// �ǽ��Ͽ��� CoAP Server�� Control URI
			// Fill () in  here
			String uri =  "coap://" + Global.serverIP + ":" + Global.coapServerPort + "/" + "control" + "/" + Global.SYSTEMID;
			System.out.println("ControlURI"+uri);
			client.setURI(uri);


			//2-3.CoAP Server Get Response JSON Parsing
			try {

				//Server get Response
				// Fill () in  here
				String response = client.get().getResponseText();
				System.out.println("Control Response:" + response);
				JSONObject js = new JSONObject(response);
				//JSONObject "Control"
				// Fill () in  here
				String control = js.getString("Control");
				Global.setState(control);

				//3. Response LED
				if (control.matches("ON")) {
					new com.mir.GPIO.StateMachine("ON").start();

				} else if (control.matches("OFF")) {
					new com.mir.GPIO.StateMachine("OFF").start();
				}

			} catch (Exception e) {
				e.getStackTrace();
			}

		}
	}
}
