package com.mw.plugins.spring.message;

import org.apache.commons.lang3.StringUtils;

public enum MessageType {
	
	INFO, 

	SUCCESS, 
	
	WARNING, 
	
	ERROR;
	
	private final String cssClass;
	
	private MessageType() {
		if(StringUtils.equalsIgnoreCase("INFO", name())){
			cssClass = "alert alert-info"; 
		}else if(StringUtils.equalsIgnoreCase("SUCCESS", name())){
			cssClass = "alert alert-success";  
		}else if(StringUtils.equalsIgnoreCase("WARNING", name())){
			cssClass = "alert"; 
		}else if(StringUtils.equalsIgnoreCase("ERROR", name())){
			cssClass = "alert alert-danger";
		}else{
			cssClass = name().toLowerCase(); 
		}
	}
	
	public String getCssClass() {
		return cssClass;
	}
}