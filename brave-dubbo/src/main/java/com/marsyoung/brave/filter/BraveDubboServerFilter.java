package com.marsyoung.brave.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.config.spring.ServiceBean;
import com.alibaba.dubbo.rpc.*;
import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.ServerRequestInterceptor;
import com.github.kristofa.brave.ServerResponseInterceptor;
import com.marsyoung.brave.dubbo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;


/**
 * dubbo 调用 过滤器
 * Created by zhiyuma on 2016/4/22.
 */

@Activate(group = Constants.PROVIDER)
public class BraveDubboServerFilter implements Filter {

    private RpcSpanNameProvider rpcSpanNameProvider;

    private ServerRequestInterceptor requestInterceptor;
    private ServerResponseInterceptor responseInterceptor;



    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        ApplicationContext context=ServiceBean.getSpringContext();
        rpcSpanNameProvider = context.getBean(RpcSpanNameProvider.class);

        requestInterceptor = context.getBean(Brave.class).serverRequestInterceptor();
        responseInterceptor = context.getBean(Brave.class).serverResponseInterceptor();
        //handle
        requestInterceptor.handle(new RpcServerRequestAdapter(invoker,(RpcInvocation) invocation,rpcSpanNameProvider));
        Result result= invoker.invoke(invocation);
        responseInterceptor.handle(new RpcServerResponseAdapter(invoker,(RpcInvocation)invocation,result));
        return result;
    }
}
