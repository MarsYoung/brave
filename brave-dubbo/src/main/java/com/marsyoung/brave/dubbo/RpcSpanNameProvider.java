package com.marsyoung.brave.dubbo;

import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;

/**
 * Created by zhiyuma on 2016/4/22.
 */
public interface RpcSpanNameProvider {


    /*
    *  whereCreate 1: client generate 2: server generate
    * */
    String spanName(Invoker invoker, Invocation invocation,int whereCreate);


}
