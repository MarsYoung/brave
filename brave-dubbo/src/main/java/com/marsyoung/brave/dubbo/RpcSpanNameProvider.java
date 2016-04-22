package com.marsyoung.brave.dubbo;

import com.alibaba.dubbo.rpc.Invocation;

/**
 * Created by zhiyuma on 2016/4/22.
 */
public interface RpcSpanNameProvider {

    String spanName(Invocation invocation);
}
