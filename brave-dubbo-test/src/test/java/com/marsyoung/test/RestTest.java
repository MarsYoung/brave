package com.marsyoung.test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.marsyoung.service.BraveTest;
import org.junit.Test;

import javax.ws.rs.GET;

public class RestTest extends BaseTest {

	@Test
	public void test(){
		com.alibaba.dubbo.container.Main.main(null);
	}
	

	
	
}
