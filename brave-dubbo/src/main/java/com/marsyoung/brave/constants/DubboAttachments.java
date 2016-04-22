package com.marsyoung.brave.constants;

/**
 * Created by zhiyuma on 2016/4/22.
 */
public enum DubboAttachments {

    TraceId("Dubbo-TraceId"),
    /**
     * Span id http header field name.
     */
    SpanId("Dubbo-SpanId"),
    /**
     * Parent span id http header field name.
     */
    ParentSpanId("Dubbo-ParentSpanId"),
    /**
     * Sampled http header field name. Indicates if this trace should be sampled or not.
     */
    Sampled("Dubbo-Sampled");

    private final String name;
    DubboAttachments(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
