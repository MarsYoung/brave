package com.marsyoung.brave.dubbo;

import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.github.kristofa.brave.ClientResponseAdapter;
import com.github.kristofa.brave.KeyValueAnnotation;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by zhiyuma on 2016/4/25.
 */
public class RpcClientResponseAdapter implements ClientResponseAdapter{

    private final Invoker invoker;
    private final RpcInvocation invocation;
    private final Result result;

    public RpcClientResponseAdapter(Invoker invoker, RpcInvocation invocation, Result result) {
        this.invoker = invoker;
        this.invocation = invocation;
        this.result = result;
    }


    @Override
    public Collection<KeyValueAnnotation> responseAnnotations() {
        if(result!=null) {
            if (result.hasException()) {
                KeyValueAnnotation statusAnnotation = KeyValueAnnotation.create("rpc.consumer.response.status[cr]","error");
                return Arrays.asList(statusAnnotation);
            }
        }
        KeyValueAnnotation statusAnnotation = KeyValueAnnotation.create("rpc.consumer.response.status[cr]", "success");
        return Arrays.asList(statusAnnotation);

    }
}
