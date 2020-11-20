package com.mw.plugins.spring.message;

/**
 * Used to transport messages back to the client.
 */
public class ResponseMessage {
    public enum Type {
        success, warn, error, info;
    }

    private final Type type;
    private final String text;
    private final String json;
    
    public ResponseMessage(Type type, String text, String json) {
        this.type = type;
        this.text = text;
        this.json = json;
    }

    public String getText() {
        return text;
    }

    public Type getType() {
        return type;
    }

	public String getJson() {
		return json;
	}
    
}
