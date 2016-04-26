package com.marsyoung.brave.dubbo;

import com.alibaba.dubbo.rpc.Invocation;

/**
 * dubbo rpc的调用的name生成接口
 * Created by zhiyuma on 2016/4/22.
 */
public interface RpcServiceNameProvider {

    String serviceName();

}
