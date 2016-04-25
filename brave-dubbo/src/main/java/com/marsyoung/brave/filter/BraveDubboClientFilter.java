package com.marsyoung.brave.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.github.kristofa.brave.ClientRequestInterceptor;
import com.github.kristofa.brave.ClientResponseInterceptor;
import com.marsyoung.brave.dubbo.*;


/**
 * dubbo 调用 过滤器
 * Created by zhiyuma on 2016/4/22.
 */

@Activate(group = Constants.CONSUMER)
public class BraveDubboClientFilter implements Filter {

    //private final RpcServiceNameProvider rpcServiceNameProvider;
    private final RpcSpanNameProvider rpcSpanNameProvider;

    private final ClientRequestInterceptor requestInterceptor;
    private final ClientResponseInterceptor responseInterceptor;

    public BraveDubboClientFilter( RpcSpanNameProvider rpcSpanNameProvider, ClientRequestInterceptor requestInterceptor, ClientResponseInterceptor responseInterceptor) {
        this.rpcSpanNameProvider = rpcSpanNameProvider;
        this.requestInterceptor = requestInterceptor;
        this.responseInterceptor = responseInterceptor;
    }


    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        //handle
        requestInterceptor.handle(new RpcClientRequestAdapter(invoker,(RpcInvocation) invocation,rpcSpanNameProvider));
        Result result= invoker.invoke(invocation);
        responseInterceptor.handle(new RpcClientResponseAdapter(invoker,(RpcInvocation)invocation,result));
        return result;
    }
}
