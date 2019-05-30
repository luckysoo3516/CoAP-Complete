package com.tipw.global;

import com.tipw.coap.resource.ObserveResource;

public class DeviceInfo {

	/*
	 * Data Structure
	 * 
	 * 1. Device Information 2. Event Control
	 * 
	 * 
	 */

	// 1. Device Information
	String deviceID;
	String mode; // pull, push
	String state; // Current Device State on, off
	String event; // When an event is requested
	boolean isEvent;

	public DeviceInfo(String deviceID, String state, String mode) {
		this.deviceID = deviceID;
		this.state = state;
		this.mode = mode;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean getIsEvent() {
		return isEvent;
	}

	public void setIsEvent(boolean isEvent) {
		this.isEvent = isEvent;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	// observe
	/*
	 * 2. Event Control
	 */
	ObserveResource resource;
	
	
	
	public ObserveResource getResource() {
		return resource;
	}

	public void setResource(ObserveResource resource) {
		this.resource = resource;
	}

	public void setEvent(boolean isEvent) {
		this.isEvent = isEvent;
	}

	public boolean ControlEvent(String newState) {
		this.event = newState;
		if (this.mode.equals("pull")) {
			this.isEvent = true;
		}
		
		else  {
			if(this.resource==null) {
				return false;
			} else {
				this.resource.changed();	
			}			
		}
		
		return true;
	}

}
