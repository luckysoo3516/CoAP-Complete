package com.mir.Service;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.json.JSONException;
import org.json.JSONObject;

import Global.Global;

public class Report extends Thread {

	/*
	 * 1. Timer UpdateTask Thread
     * 2. Report URI CoAPServer ReportResource
	 */



	//  1. Timer UpdateTask Thread
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		Timer timer = new Timer();
		timer.schedule(new UpdateTask(), 0, Global.pollinginterval);
	}



	// 2-1. Califorinum Lib CoapClient
	CoapClient client=new CoapClient();


	private class UpdateTask extends TimerTask {
		@Override
		public void run() {
			// 2-2. CoAP Server Report URI Setting
			// 2-2. CoAP Server Report URI Setting

			// CoAP Server Control URI coap://ServerIP:5683/report/DeviceID
			// Fill () in  here
			String uri = "coap://" + Global.serverIP + ":" + Global.coapServerPort + "/" + "report"+"/"+Global.SYSTEMID;
			client.setURI(uri);

			//2-3. CoAP Server JSONOBject
			JSONObject json = new JSONObject();
			try {
				String state=Global.getState();

				if(state.equals("")) {
					state="OFF";
				}

				json.put("State", state);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//2-4. Client String Put Response
			String payload = json.toString();
			System.out.println("Report Request:"+payload);

			//CoapResponse Server put Response (client)
			// Fill () in  here
			CoapResponse resp = client.put(payload, MediaTypeRegistry.APPLICATION_JSON);
			System.out.println("Report Response:"+resp.getResponseText());

		}

	}
}
