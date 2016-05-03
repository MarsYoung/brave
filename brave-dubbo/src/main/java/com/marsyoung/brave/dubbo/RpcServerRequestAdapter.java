package com.marsyoung.brave.dubbo;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.github.kristofa.brave.*;
import com.marsyoung.brave.constants.DubboAttachments;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by zhiyuma on 2016/4/25.
 */
public class RpcServerRequestAdapter implements ServerRequestAdapter {

    private final Invoker invoker;
    private final RpcInvocation invocation;
    private final RpcSpanNameProvider rpcSpanNameProvider;

    public RpcServerRequestAdapter(Invoker invoker, RpcInvocation invocation, RpcSpanNameProvider rpcSpanNameProvider) {
        this.invoker = invoker;
        this.invocation = invocation;
        this.rpcSpanNameProvider = rpcSpanNameProvider;
    }

    @Override
    public TraceData getTraceData() {
        final String sampled = invocation.getAttachment(DubboAttachments.Sampled.getName());
        if (sampled != null) {
            if (sampled.equals("0") || sampled.toLowerCase().equals("false")) {
                return TraceData.builder().sample(false).build();
            } else {
                final String parentSpanId = invocation.getAttachment(DubboAttachments.ParentSpanId.getName());
                final String traceId = invocation.getAttachment(DubboAttachments.TraceId.getName());
                final String spanId = invocation.getAttachment(DubboAttachments.SpanId.getName());

                if (traceId != null && spanId != null) {
                    SpanId span = getSpanId(traceId, spanId, parentSpanId);
                    return TraceData.builder().sample(true).spanId(span).build();
                }
            }
        }
        return TraceData.builder().build();
    }

    @Override
    public String getSpanName() {
        return rpcSpanNameProvider.spanName(invoker,invocation,2);
    }

    @Override
    public Collection<KeyValueAnnotation> requestAnnotations() {
        //TODO 如果client没有监控，那么这个地方也是需要生成这些东西的（源代码这里是个empty）
        String serviceName=invoker.getInterface().getName();
        String methodName=invocation.getMethodName();
        KeyValueAnnotation serviceNameAnnotation = KeyValueAnnotation.create("rpc.provider.serviceName[sr]", serviceName);
        KeyValueAnnotation methodNameAnnotation = KeyValueAnnotation.create("rpc.provider.methodName[sr]", methodName);
        KeyValueAnnotation paramTypes = KeyValueAnnotation.create("rpc.provider.paramTypes[sr]", getParamTypeString());
        KeyValueAnnotation params = KeyValueAnnotation.create("rpc.provider.params[sr]", getParamString());
        return Arrays.asList(serviceNameAnnotation,methodNameAnnotation,paramTypes,params);
    }

    private String getParamTypeString(){
        StringBuilder sn = new StringBuilder();
        Class<?>[] types = invocation.getParameterTypes();
        if (types != null && types.length > 0) {
            boolean first = true;
            for (Class<?> type : types) {
                if (first) {
                    first = false;
                } else {
                    sn.append(",");
                }
                sn.append(type.getName());
            }
        }
        return sn.toString();
    }

    private String getParamString(){
        StringBuilder sn = new StringBuilder();
        Object[] args = invocation.getArguments();
        if (args != null && args.length > 0) {
            try {
                sn.append(JSON.json(args));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sn.toString();
    }

    private SpanId getSpanId(String traceId, String spanId, String parentSpanId) {
        if (parentSpanId != null)
        {
            return SpanId.create(IdConversion.convertToLong(traceId), IdConversion.convertToLong(spanId), IdConversion.convertToLong(parentSpanId));
        }
        return SpanId.create(IdConversion.convertToLong(traceId), IdConversion.convertToLong(spanId), null);
    }
}
