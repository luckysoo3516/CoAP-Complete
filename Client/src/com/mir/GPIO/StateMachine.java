package com.mir.GPIO;
import com.pi4j.wiringpi.SoftPwm;
import Global.Global;

public class StateMachine extends Thread {
	/*라즈베리파이 GPIO 제어
	 * 1. 라즈베리파이 Pin_NUMBER 설정
	 * 2. softPwmCreate 라즈베리파이 Dimming값 지정 0 최소 100 최대
	 * 3. Server로부터 받은 값을 토대로 
	 */
	private String control;
	final int PIN_NUMBER = 1;
	public StateMachine(String control) {
		this.control = control;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		//dimming 값 범위 정하기 0~ 100까지
		//Fill in here()
		SoftPwm.softPwmCreate(PIN_NUMBER,0,100);

		System.out.println("=========");
		System.out.println("LED CONTROL");
		System.out.println("DIMMING =" + control);
		System.out.println("=========");

		

		if (control.equals("ON")) {
			//dimming 값 최대 값 입력
			//Fill in here()
			SoftPwm.softPwmWrite(PIN_NUMBER, 100);
			System.out.println("PIN_NUMBER" + PIN_NUMBER);
			System.out.println("ON!");
		}

		else {
			//dimming 값 최소 값 입력
			SoftPwm.softPwmWrite(PIN_NUMBER, 0);
			System.out.println("PIN_NUMBER" + PIN_NUMBER);
			System.out.println("OFF!");
		}
		Global.setState(control);

	}

}
