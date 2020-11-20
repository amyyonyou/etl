package com.mw.plugins.shiro;

import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;

public class ShiroUtils {
	public String getCurrentUserName() {
		final String currentUserName = (String) SecurityUtils.getSubject().getPrincipal();
		return currentUserName;
	}

	public boolean isSignedIn() {
		return SecurityUtils.getSubject().isAuthenticated();
	}

	public static I findUser() {
		final String currentUserName = (String) SecurityUtils.getSubject().getPrincipal();
		if (StringUtils.isNotEmpty(currentUserName)) {
			return new I(currentUserName);
		} else {
			return null;
		}
	}

	public static String findUserName() {
		return findUser().getName();
	}

}
