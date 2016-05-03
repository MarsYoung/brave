package com.marsyoung.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.marsyoung.service.BraveTest;
import com.marsyoung.service.BraveTest2;

import javax.ws.rs.Encoded;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
/**
 * Created by zhiyuma on 2016/4/26.
 */
@Service(protocol = "rest")
@Produces(ContentType.APPLICATION_JSON_UTF_8)
@Path("braveTest")
public class BraveTestImpl implements BraveTest{

    @Reference
    BraveTest2 braveTest2;

    @GET
    @Path("/testBrave1")
    @Override
    public String testBrave1(String name) {
        System.out.println("testBrave1 : " +name);
        //return "test";
        return braveTest2.test2Brave2();
    }


}
