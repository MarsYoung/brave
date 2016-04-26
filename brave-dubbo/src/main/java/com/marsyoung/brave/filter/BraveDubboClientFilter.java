package com.marsyoung.brave.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.config.spring.ServiceBean;
import com.alibaba.dubbo.rpc.*;
import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.ClientRequestInterceptor;
import com.github.kristofa.brave.ClientResponseInterceptor;
import com.marsyoung.brave.dubbo.*;
import org.springframework.context.ApplicationContext;


/**
 * dubbo 调用 过滤器
 * Created by zhiyuma on 2016/4/22.
 */

@Activate(group = Constants.CONSUMER)
public class BraveDubboClientFilter implements Filter {

    private  RpcSpanNameProvider rpcSpanNameProvider;

    private  ClientRequestInterceptor requestInterceptor;
    private  ClientResponseInterceptor responseInterceptor;




    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        ApplicationContext context= ServiceBean.getSpringContext();
        rpcSpanNameProvider = context.getBean(RpcSpanNameProvider.class);

        requestInterceptor = context.getBean(Brave.class).clientRequestInterceptor();
        responseInterceptor = context.getBean(Brave.class).clientResponseInterceptor();

        //handle
        requestInterceptor.handle(new RpcClientRequestAdapter(invoker,(RpcInvocation) invocation,rpcSpanNameProvider));
        Result result= invoker.invoke(invocation);
        responseInterceptor.handle(new RpcClientResponseAdapter(invoker,(RpcInvocation)invocation,result));
        return result;
    }
}
