package com.marsyoung.brave.dubbo;

import com.github.kristofa.brave.ServerCreateSpanSwitch;

/**
 * 对于rest服务默认开启span生成
 * Created by zhiyuma on 2016/4/27.
 */
public class DefaultRpcServerCreateSpanSwitch implements ServerCreateSpanSwitch{

    @Override
    public Boolean ifTurnOn() {
        return true;
    }
}
