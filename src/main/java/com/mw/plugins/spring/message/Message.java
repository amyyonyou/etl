package com.mw.plugins.spring.message;

public final class Message {
	
	private final MessageType type;
	
	private final String cssClass;
	
	private final String text;
	
	private final String json;

	public Message(MessageType type, String text) {
		this.type = type;
		this.cssClass = type.getCssClass();
		this.text = text;
		this.json = null;
	}
	
	public Message(MessageType type, String text, String json) {
		this.type = type;
		this.cssClass = type.getCssClass();
		this.text = text;
		this.json = json;
	}

	public static Message success(String text) {
		return new Message(MessageType.SUCCESS, text);
	}
	public static Message success(String text, String json) {
		return new Message(MessageType.SUCCESS, text, json);
	}

	public static Message info(String text) {
		return new Message(MessageType.INFO, text);
	}

	public static Message warning(String text) {
		return new Message(MessageType.WARNING, text);
	}

	public static Message error(String text) {
		return new Message(MessageType.ERROR, text);
	}

	public MessageType getType() {
		return type;
	}

	public String getText() {
		return text;
	}
	
	public String getJson() {
		return json;
	}
	
	public String getCssClass() {
		return cssClass;
	}
	
	public String toString() {
		return type + ": " + text + " : " + json;
	}

	
}