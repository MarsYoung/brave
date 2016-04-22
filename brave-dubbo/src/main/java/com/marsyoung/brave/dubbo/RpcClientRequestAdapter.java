package com.marsyoung.brave.dubbo;

import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.github.kristofa.brave.ClientRequestAdapter;
import com.github.kristofa.brave.IdConversion;
import com.github.kristofa.brave.KeyValueAnnotation;
import com.github.kristofa.brave.SpanId;
import com.github.kristofa.brave.http.BraveHttpHeaders;
import com.github.kristofa.brave.internal.Nullable;
import com.marsyoung.brave.constants.DubboAttachments;

import java.net.URI;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by zhiyuma on 2016/4/22.
 */
public class RpcClientRequestAdapter implements ClientRequestAdapter {

    private final Invoker invoker;
    private final RpcInvocation invocation;
    private final RpcSpanNameProvider rpcSpanNameProvider;
    private final RpcServiceNameProvider rpcServiceNameProvider;


    public RpcClientRequestAdapter(Invoker invoker, RpcInvocation invocation,  RpcServiceNameProvider rpcServiceNameProvider,RpcSpanNameProvider rpcSpanNameProvider) {
        this.invoker = invoker;
        this.invocation = invocation;
        this.rpcServiceNameProvider = rpcServiceNameProvider;
        this.rpcSpanNameProvider = rpcSpanNameProvider;
    }

    @Override
    public String getSpanName() {
        return rpcSpanNameProvider.spanName(invocation);
    }

    @Override
    public void addSpanIdToRequest(@Nullable SpanId spanId) {
        if (spanId == null) {
            invocation.setAttachment(DubboAttachments.Sampled.getName(),"0");
        } else {
            invocation.setAttachment(DubboAttachments.Sampled.getName(), "1");
            invocation.setAttachment(DubboAttachments.TraceId.getName(), IdConversion.convertToString(spanId.getTraceId()));
            invocation.setAttachment(DubboAttachments.SpanId.getName(), IdConversion.convertToString(spanId.getSpanId()));
            if (spanId.getParentSpanId() != null) {
                invocation.setAttachment(DubboAttachments.ParentSpanId.getName(), IdConversion.convertToString(spanId.getParentSpanId()));
            }
        }
    }

    @Override
    public String getClientServiceName() {
        return rpcServiceNameProvider.serviceName(invocation);
    }

    @Override
    public Collection<KeyValueAnnotation> requestAnnotations() {
        //TODO 优化显示信息
        String serviceName=invoker.getInterface().getName();
        String methodName=invocation.getMethodName();
        KeyValueAnnotation serviceNameAnnotation = KeyValueAnnotation.create("rpc.serviceName", serviceName);
        KeyValueAnnotation methodNameAnnotation = KeyValueAnnotation.create("rpc.methodName", methodName);
        return Arrays.asList(serviceNameAnnotation,methodNameAnnotation);
    }
}
