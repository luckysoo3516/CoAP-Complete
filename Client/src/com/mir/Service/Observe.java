package com.mir.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;

import org.json.JSONObject;

import Global.Global;

public class Observe extends Thread {

	/*
	 * 1. Observe URI CoAPServer ControltResource
	 * 1-1. Califorinum Lib CoapClient
	 * 1-2. CoAP Server Observe URI Setting
	 * 1-3. CoAP Observe Handler
	 * 1-4. onLoad Coap Observe Get Response Message Parsing
	 * 1-5. Response RaspberryPi LED
	 */

	// 1-1. Califorinum Lib CoapClient
	CoapClient client = new CoapClient();

	// 1-2. CoAP Server Observe URI Setting
	@Override
	public void run() {
		super.run();
		String uri = "coap://" + Global.serverIP + ":" + Global.coapServerPort + "/" + "obs" + "/" + Global.SYSTEMID;
		System.out.println(uri);
		client.setURI(uri);

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("ObserveURI" + uri);

		// 1-3. CoAP Observe Handler
		CoapObserveRelation relation = client.observe(new CoapHandler() {

			// 1-4. onLoad Coap Observe Get
			@Override
			public void onLoad(CoapResponse response) {
				try {

					String content = response.getResponseText();
					System.out.println("NOTIFICATION: " + content);

					System.out.println("Observe Response" + response);

					JSONObject js = new JSONObject(content);
					String control = js.get("Control").toString();

					Global.setState(control);

					// 1-5. Response RaspberryPi LED
					if (control.matches("ON")) {
						new com.mir.GPIO.StateMachine("ON").start();

					} else if (control.matches("OFF")) {
						new com.mir.GPIO.StateMachine("OFF").start();
					}

				} catch (Exception e) {
					// TODO: handle exception
				}

			}

			@Override
			public void onError() {
				// TODO Auto-generated method stub

			}
		});

		// Wait for Event
		try {
			br.readLine();
		} catch (IOException e) {
		}
		relation.proactiveCancel();

	}

}
