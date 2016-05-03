package com.marsyoung.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.marsyoung.service.BraveTest;
import com.marsyoung.service.BraveTest2;

/**
 * Created by zhiyuma on 2016/4/26.
 */
@Service(protocol = "dubbo")
public class BraveTest2Impl implements BraveTest2 {


    @Override
    public String test2Brave2() {
        return "test2Brave2";
    }
}
