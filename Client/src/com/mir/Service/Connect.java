package com.mir.Service;


import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.json.JSONException;
import org.json.JSONObject;

import Global.Global;

public class Connect {
	/*
	 * Connect 
	 * 
	 */
	
	//1-1. Californium에서 제공하는 CoapClient 객체 생성 및 실행
	//fill () in here
	CoapClient client=new CoapClient();
	
	

	public void session() throws JSONException {
		
		//1-2. 접속한 CoapServer URI 주소 세팅
		// 실습하였던 CoAP Server의 connect URI 완성하기 
		// Fill () in  here
		String uri =  "coap://" + Global.serverIP + ":" + Global.coapServerPort + "/" + "connect";		
		System.out.println("ConnectURI "+uri);
		client.setURI(uri);
		
		//1-3. CoapServer에게 보낼 JSONObject 메시지 생성
		JSONObject json = new JSONObject();
		json.put("DeviceID", Global.SYSTEMID);
		json.put("State", "off");
		json.put("Mode", Global.Mode);
		
		
		//1-4. JSON값 String으로 변환하여 Server에게 PUT으로 전송 및 Response값 로그 출력
		String payload = json.toString();
		System.out.println("Connect Request:"+payload);
		
		//CoapResponse를 사용해서 Server로 post 메소드를 사용해서 보내고 Response 값 받기 (client 객체 사용)
		// Fill () in  here
		CoapResponse resp1 = client.post(payload, MediaTypeRegistry.APPLICATION_JSON);
		System.out.println("Connect Response:"+resp1.getResponseText());
        
		
		//2. Run CoAP Client Report URI 
		com.mir.Service.Report report = new com.mir.Service.Report();
		report.start();
		
		//1. Mode에 따른 실행 
		if (Global.Mode.equals("pull")) {
			com.mir.Service.Control control = new com.mir.Service.Control();
			control.start();
		}
		
		else {
			com.mir.Service.Observe observe=new com.mir.Service.Observe();
			observe.start();
		}


	}
	

}
