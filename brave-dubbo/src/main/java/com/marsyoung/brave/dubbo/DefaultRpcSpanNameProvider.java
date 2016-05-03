package com.marsyoung.brave.dubbo;

import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;

/**
 * Created by zhiyuma on 2016/4/26.
 */
public class DefaultRpcSpanNameProvider implements  RpcSpanNameProvider {


    @Override
    public String spanName(Invoker invoker, Invocation invocation, int whereCreate) {
       return  invoker.getInterface().getName()+":"+invocation.getMethodName()+"["+(whereCreate==1?"cg":"sg")+"]";
    }
}
