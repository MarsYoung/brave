package com.marsyoung.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.marsyoung.service.BraveTest;

/**
 * Created by zhiyuma on 2016/4/26.
 */
@Service(protocol = "dubbo")
public class BraveTestImpl implements BraveTest{


    @Override
    public String testBrave(String name) {
        System.out.println("testBrave");
        return "testBrave";
    }
}
