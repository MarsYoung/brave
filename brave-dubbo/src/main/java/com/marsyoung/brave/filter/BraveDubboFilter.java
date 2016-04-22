package com.marsyoung.brave.filter;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.marsyoung.brave.dubbo.RpcClientRequestAdapter;
import com.marsyoung.brave.dubbo.RpcRequestInterceptor;
import com.marsyoung.brave.dubbo.RpcServiceNameProvider;
import com.marsyoung.brave.dubbo.RpcSpanNameProvider;


/**
 * dubbo 调用 过滤器
 * Created by zhiyuma on 2016/4/22.
 */

@Activate
public class BraveDubboFilter implements Filter {

    private final RpcServiceNameProvider rpcServiceNameProvider;
    private final RpcRequestInterceptor rpcRequestInterceptor;
    private final RpcSpanNameProvider rpcSpanNameProvider;

    public BraveDubboFilter(RpcServiceNameProvider rpcServiceNameProvider, RpcRequestInterceptor rpcRequestInterceptor, RpcSpanNameProvider rpcSpanNameProvider) {
        this.rpcServiceNameProvider = rpcServiceNameProvider;
        this.rpcRequestInterceptor = rpcRequestInterceptor;
        this.rpcSpanNameProvider = rpcSpanNameProvider;
    }


    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        //handle
        rpcRequestInterceptor.handle(new RpcClientRequestAdapter(invoker,(RpcInvocation) invocation,rpcServiceNameProvider,rpcSpanNameProvider));
        return invoker.invoke(invocation);
    }
}
