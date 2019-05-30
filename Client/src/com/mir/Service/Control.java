package com.mir.Service;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.californium.core.CoapClient;
import org.json.JSONObject;


import Global.Global;

public class Control extends Thread {
	
	
	/*
	 1. Timer와 UpdateTask를 이용한 Thread 실행
     2. Report URI를 통해 CoAPServer ReportResource에 접속
     3. Response값을 토대로 라즈베리파이에 LED를 제어 
	*/

	
	// 1. Timer와 UpdateTask를 이용한 Thread 실행 
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		Timer timer = new Timer();
		timer.schedule(new UpdateTask(), 0, Global.pollinginterval);
	}
	
	
	//2-1. Califorinum Lib에서 제공하는 CoapClient 객체 생성 및 실행
	CoapClient client=new CoapClient();
	


	private class UpdateTask extends TimerTask {
		@Override
		public void run() {
			// 2-2. CoAP Server Control URI Setting
			// 실습하였던 CoAP Server의 Control URI 완성하기 
			// Fill () in  here
			String uri =  "coap://" + Global.serverIP + ":" + Global.coapServerPort + "/" + "control" + "/" + Global.SYSTEMID;
			System.out.println("ControlURI"+uri);
			client.setURI(uri);
			
			
			//2-3.CoAP Server에게 Get으로 요청 및 Response 값에 대한 JSON Parsing
			try {
				
				//Server에게 get 메소드를 사용해서 보내고 Response 값 받기
				// Fill () in  here
				String response = client.get().getResponseText();
				System.out.println("Control Response:" + response);
				JSONObject js = new JSONObject(response);
				//JSONObject를 이용해서 "Control"값 파싱하기
				// Fill () in  here
				String control = js.getString("Control");
				Global.setState(control);
				
				//3. Response 값을 토대로 라즈베리파이에 LED를 제어하는 클래스 실행
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
