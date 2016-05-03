package com.marsyoung.test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.marsyoung.service.BraveTest;
import org.junit.Test;

public class Test1 extends BaseTest {

	@Reference
	BraveTest braveTest;
	
	@Test
	public void test(){
		int i=0;
//		while(i++<100) {
			braveTest.testBrave1("test");
//		}

		try {
			Thread.sleep(20000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	

	
	
}
