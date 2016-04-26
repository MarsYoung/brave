package com.marsyoung.brave.dubbo;

import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.github.kristofa.brave.*;
import com.marsyoung.brave.constants.DubboAttachments;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by zhiyuma on 2016/4/25.
 */
public class RpcServerRequestAdapter implements ServerRequestAdapter {

    private final Invoker invoker;
    private final RpcInvocation invocation;
    private final RpcServiceNameProvider rpcServiceNameProvider;
    private final RpcSpanNameProvider rpcSpanNameProvider;

    public RpcServerRequestAdapter(Invoker invoker, RpcInvocation invocation, RpcServiceNameProvider rpcServiceNameProvider, RpcSpanNameProvider rpcSpanNameProvider) {
        this.invoker = invoker;
        this.invocation = invocation;
        this.rpcServiceNameProvider = rpcServiceNameProvider;
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
        return rpcSpanNameProvider.spanName(invoker,invocation);
    }

    @Override
    public Collection<KeyValueAnnotation> requestAnnotations() {
        return Collections.emptyList();
    }
    private SpanId getSpanId(String traceId, String spanId, String parentSpanId) {
        if (parentSpanId != null)
        {
            return SpanId.create(IdConversion.convertToLong(traceId), IdConversion.convertToLong(spanId), IdConversion.convertToLong(parentSpanId));
        }
        return SpanId.create(IdConversion.convertToLong(traceId), IdConversion.convertToLong(spanId), null);
    }
}
