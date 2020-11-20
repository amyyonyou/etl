package com.mw.plugins.shiro;

import java.io.Serializable;

public class I implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;

	public I(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("I [name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

}
