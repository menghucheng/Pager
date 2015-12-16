package com.imooc.test;

import org.apache.log4j.Logger;
import org.junit.Test;

public class testLOG {
	
	private static final Logger LOG = Logger.getLogger(testLOG.class);
	
	@Test
	public void test(){
		LOG.debug("Hello World!");
		LOG.info("INfo");
	}
}
