package com.marsyoung.brave.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.github.kristofa.brave.ServerRequestInterceptor;
import com.github.kristofa.brave.ServerResponseInterceptor;
import com.marsyoung.brave.dubbo.*;


/**
 * dubbo 调用 过滤器
 * Created by zhiyuma on 2016/4/22.
 */

@Activate(group = Constants.PROVIDER)
public class BraveDubboServerFilter implements Filter {

    private final RpcServiceNameProvider rpcServiceNameProvider;
    private final RpcSpanNameProvider rpcSpanNameProvider;

    private final ServerRequestInterceptor requestInterceptor;
    private final ServerResponseInterceptor responseInterceptor;

    public BraveDubboServerFilter(RpcServiceNameProvider rpcServiceNameProvider, RpcSpanNameProvider rpcSpanNameProvider, ServerRequestInterceptor requestInterceptor, ServerResponseInterceptor responseInterceptor) {
        this.rpcServiceNameProvider = rpcServiceNameProvider;
        this.rpcSpanNameProvider = rpcSpanNameProvider;
        this.requestInterceptor = requestInterceptor;
        this.responseInterceptor = responseInterceptor;
    }


    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        //handle
        requestInterceptor.handle(new RpcServerRequestAdapter(invoker,(RpcInvocation) invocation,rpcServiceNameProvider,rpcSpanNameProvider));
        Result result= invoker.invoke(invocation);
        responseInterceptor.handle(new RpcServerResponseAdapter(invoker,(RpcInvocation)invocation,result));
        return result;
    }
}
