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
	 * 1. Observe URI를 통해서 CoAPServer에 ControltResource에 접속 
	 * 1-1. Califorinum Lib에서 제공하는 CoapClient 객체 생성 및 실행 
	 * 1-2. CoAP Server Observe URI Setting 
	 * 1-3. CoAP Observe Handler 
	 * 1-4. onLoad 메소드 및 Coap Observe Get으로 요청 확인 Response Message Parsing 
	 * 1-5. Response값을 토대로 RaspberryPi LED 제어하는 클래스 실행
	 */

	// 1-1. Califorinum Lib에서 제공하는 CoapClient 객체 생성 및 실행
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

			// 1-4. onLoad 메소드 및 Coap Observe Get으로 요청 확인
			@Override
			public void onLoad(CoapResponse response) {
				try {

					String content = response.getResponseText();
					System.out.println("NOTIFICATION: " + content);

					System.out.println("Observe Response" + response);

					JSONObject js = new JSONObject(content);
					String control = js.get("Control").toString();

					Global.setState(control);

					// 1-5. Response값을 토대로 RaspberryPi LED 제어하는 클래스 실행
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