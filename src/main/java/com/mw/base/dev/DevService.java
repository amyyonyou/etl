package com.mw.base.dev;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DevService {
	@Value("${env.server.ip}")
	private String serverIp;
	@Value("${env.server.ip.production}")
	private String productionServerIp;

	public String getHostName() throws UnknownHostException {
		String hostName = InetAddress.getLocalHost().getHostName();
		return hostName;
	}

	public String getHostAddress() throws UnknownHostException {
		String hostAddress = InetAddress.getLocalHost().getHostAddress();
		return hostAddress;
	}

	public String getServerIp() {
		return serverIp;
	}

	public String getProductionServerIp() {
		return productionServerIp;
	}

	public boolean isLegalServer() {
		try {
			boolean rst = StringUtils.equals(getHostAddress(), serverIp);
			if (!rst) {
				logger.warn("Invalid server!-> hostName={}, hostAddress={}, serverIp={}", getHostName(), getHostAddress(), getServerIp());
			}

			return rst;
		} catch (Exception e) {
			logger.warn("isLegalServer", e);
			return false;
		}
	}

	public boolean isProduction() {
		try {
			boolean rst = StringUtils.equals(getHostAddress(), productionServerIp);
			if (!rst) {
				logger.warn("Not production server-> hostName={}, hostAddress={}, productionServerIp={}", getHostName(), getHostAddress(), getProductionServerIp());
			}

			return rst;
		} catch (Exception e) {
			logger.warn("isProduction", e);
			return false;
		}
	}

	private final static Logger logger = LoggerFactory.getLogger(DevService.class);
}
