package com.mw.ctdb.rtdb;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mw.config.ComponentConfig;
import com.mw.config.DataSourceConfig;
import com.mw.config.DataSourceSCADAConfig;
import com.mw.config.FreeMarkerConfig;
import com.mw.config.WebConfig;
import com.mw.ctdb.rtdb.inputdata.InputDataService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { ComponentConfig.class, DataSourceConfig.class, DataSourceSCADAConfig.class, WebConfig.class, FreeMarkerConfig.class })
public class InputDataTest {

	@Resource
	private InputDataService inputDataService;

	@Test
	public void test() throws Exception {
		inputDataService.saveData();
	}

	private static final Logger logger = LoggerFactory.getLogger(InputDataTest.class);
}
