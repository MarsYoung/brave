package com.github.kristofa.brave;

/**
 * Created by zhiyuma on 2016/4/27.
 */
public class DefaultServerCreateSpanSwitch implements ServerCreateSpanSwitch {
    @Override
    public Boolean ifTurnOn() {
        return false;
    }
}
