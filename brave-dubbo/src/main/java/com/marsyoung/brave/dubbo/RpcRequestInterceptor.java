package com.marsyoung.brave.dubbo;

import com.github.kristofa.brave.ClientRequestAdapter;
import com.github.kristofa.brave.ClientTracer;
import com.github.kristofa.brave.KeyValueAnnotation;
import com.github.kristofa.brave.SpanId;

import static com.github.kristofa.brave.internal.Util.checkNotNull;

/**
 * rpc请求处理器，拦截器
 * Created by zhiyuma on 2016/4/22.
 */
public class RpcRequestInterceptor {

    private final ClientTracer clientTracer;

    public RpcRequestInterceptor(ClientTracer clientTracer) {
        this.clientTracer = checkNotNull(clientTracer, "Null clientTracer");
    }

    public void handle(ClientRequestAdapter adapter) {

        SpanId spanId = clientTracer.startNewSpan(adapter.getSpanName());
        if (spanId == null) {
            // We will not trace this request.
            adapter.addSpanIdToRequest(null);
        } else {
            adapter.addSpanIdToRequest(spanId);
            clientTracer.setCurrentClientServiceName(adapter.getClientServiceName());
            for(KeyValueAnnotation annotation : adapter.requestAnnotations()) {
                clientTracer.submitBinaryAnnotation(annotation.getKey(), annotation.getValue());
            }
            clientTracer.setClientSent();
        }

    }
}
