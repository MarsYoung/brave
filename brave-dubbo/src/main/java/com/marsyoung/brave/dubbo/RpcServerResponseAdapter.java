package com.marsyoung.brave.dubbo;

import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.github.kristofa.brave.KeyValueAnnotation;
import com.github.kristofa.brave.ServerResponseAdapter;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by zhiyuma on 2016/4/25.
 */
public class RpcServerResponseAdapter implements ServerResponseAdapter{
    private final Invoker invoker;
    private final RpcInvocation invocation;
    private final Result result;

    public RpcServerResponseAdapter(Invoker invoker, RpcInvocation invocation, Result result) {
        this.invoker = invoker;
        this.invocation = invocation;
        this.result = result;
    }

    @Override
    public Collection<KeyValueAnnotation> responseAnnotations() {
        if(result!=null&&result.hasException()){
            KeyValueAnnotation statusAnnotation = KeyValueAnnotation.create("rpc.provider.response.exception[ss]",result.getException().getMessage()==null? "":result.getException().getMessage());
            return Arrays.asList(statusAnnotation);
        }else{
            KeyValueAnnotation statusAnnotation = KeyValueAnnotation.create("rpc.provider.response.result[ss]", result.getValue()==null?"":result.getValue().toString());
            return Arrays.asList(statusAnnotation);
        }
    }
}
