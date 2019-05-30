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
	 * 1. Timer와 UpdateTask를 이용한 Thread 실행
     * 2. Report URI를 통해 CoAPServer ReportResource에 접속
	 */
	
	
	
	//  1. Timer와 UpdateTask를 이용한 Thread 실행 
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		Timer timer = new Timer();
		timer.schedule(new UpdateTask(), 0, Global.pollinginterval);
	}
	
	
	
	// 2-1. Califorinum Lib에서 제공하는 CoapClient 객체 생성 및 실행
	CoapClient client=new CoapClient();
    
	
	private class UpdateTask extends TimerTask {
		@Override
		public void run() {
			// 2-2. CoAP Server Report URI Setting
			// 2-2. CoAP Server Report URI Setting
			
			// 실습하였던 CoAP Server의 Control URI 완성하기 coap://ServerIP:5683/report/DeviceID
			// Fill () in  here
			String uri = "coap://" + Global.serverIP + ":" + Global.coapServerPort + "/" + "report"+"/"+Global.SYSTEMID;
			client.setURI(uri);
			
			//2-3. CoAP Server에게 보낼 JSONOBject 메시지 생성
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
			//2-4. Client에게 String으로 만들어서 Put 전송	및 Response 로그 값 출력
			String payload = json.toString();
			System.out.println("Report Request:"+payload);
			
			//CoapResponse를 사용해서 Server로 put 메소드를 사용해서 보내고 Response 값 받기 (client 객체 사용)
			// Fill () in  here
			CoapResponse resp = client.put(payload, MediaTypeRegistry.APPLICATION_JSON);
			System.out.println("Report Response:"+resp.getResponseText());

		}

	}
}
