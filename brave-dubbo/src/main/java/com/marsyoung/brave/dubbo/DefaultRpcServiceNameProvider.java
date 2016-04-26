package com.marsyoung.brave.dubbo;

import com.alibaba.dubbo.rpc.Invocation;

/**
 * Created by zhiyuma on 2016/4/26.
 */
public class DefaultRpcServiceNameProvider implements RpcServiceNameProvider{

    private final String serverName;

    public DefaultRpcServiceNameProvider(String serverName) {
        this.serverName=serverName;
    }

    @Override
    public String serviceName() {
        return serverName;
    }
}
