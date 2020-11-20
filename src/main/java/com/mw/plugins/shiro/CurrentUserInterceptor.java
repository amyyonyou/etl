package com.mw.plugins.shiro;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CurrentUserInterceptor extends HandlerInterceptorAdapter {

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		String uri = request.getRequestURI();
		logger.info("postHandle-> uri={}", uri);

		if (SecurityUtils.getSubject().isAuthenticated()) {
			I i = ShiroUtils.findUser();
			request.setAttribute("i", i);

			logger.info("postHandle-> i={}", i);
			// List<Map<String, Object>> pemisiList = roleMenuDao.findListForPemisi(i.getNb(), i.getPlatformNb());
			// if(!pemisiList.contains(uri)){
			// 没有权限
			// }
		}
	}

	private final static Logger logger = LoggerFactory.getLogger(CurrentUserInterceptor.class);
}
