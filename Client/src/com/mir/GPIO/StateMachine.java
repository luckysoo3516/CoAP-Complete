package com.mir.GPIO;
import com.pi4j.wiringpi.SoftPwm;
import Global.Global;

public class StateMachine extends Thread {
	/*��������� GPIO ����
	 * 1. ��������� Pin_NUMBER ����
	 * 2. softPwmCreate ��������� Dimming�� ���� 0 �ּ� 100 �ִ�
	 * 3. Server�κ��� ���� ���� ���� 
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
		
		//dimming �� ���� ���ϱ� 0~ 100����
		//Fill in here()
		SoftPwm.softPwmCreate(PIN_NUMBER,0,100);

		System.out.println("=========");
		System.out.println("LED CONTROL");
		System.out.println("DIMMING =" + control);
		System.out.println("=========");

		

		if (control.equals("ON")) {
			//dimming �� �ִ� �� �Է�
			//Fill in here()
			SoftPwm.softPwmWrite(PIN_NUMBER, 100);
			System.out.println("PIN_NUMBER" + PIN_NUMBER);
			System.out.println("ON!");
		}

		else {
			//dimming �� �ּ� �� �Է�
			SoftPwm.softPwmWrite(PIN_NUMBER, 0);
			System.out.println("PIN_NUMBER" + PIN_NUMBER);
			System.out.println("OFF!");
		}
		Global.setState(control);

	}

}
