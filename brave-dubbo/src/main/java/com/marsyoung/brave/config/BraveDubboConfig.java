package com.marsyoung.brave.config;

import com.github.kristofa.brave.*;
import com.github.kristofa.brave.http.DefaultSpanNameProvider;
import com.github.kristofa.brave.http.SpanNameProvider;
import com.github.kristofa.brave.scribe.ScribeSpanCollector;
import com.marsyoung.brave.dubbo.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;

/**
 * Created by zhiyuma on 2016/4/25.
 */
@Configuration
//@ImportResource({"classpath:META-INF/spring/brave-dubbo.xml"})
public class BraveDubboConfig {

    @Value("${dubbo.application.name}")
    String serverName;
    @Value("${zipkin.scribecollector.ip}")//zipkin.scribecollector.ip
    String scribecollectorIp;//10.16.12.137
    @Value("${zipkin.scribecollector.port}")//"zipkin.scribecollector.port"
    Integer scribecollectorPort;//9410

    @Bean
    @Scope(value = "singleton")
    public Brave brave() {
        Brave.Builder builder = new Brave.Builder(serverName,new DefaultRpcServerCreateSpanSwitch(),0);
        return builder.spanCollector(new ScribeSpanCollector(scribecollectorIp, scribecollectorPort)).traceSampler(Sampler.create(1.0f))
                .build();
    }
    @Bean
    @Scope(value = "singleton")
    public RpcSpanNameProvider rpcSpanNameProvider(){
        return new DefaultRpcSpanNameProvider();
    }


    /*
    * for jersey filter
    * */
    @Bean
    @Scope(value = "singleton")
    public SpanNameProvider spanNameProvider() {//for jersey span
        return new DefaultSpanNameProvider();
    }

    @Bean
    @Scope(value = "singleton")
    public ServerRequestInterceptor serverRequestInterceptor(){
        return brave().serverRequestInterceptor();
    }

    @Bean
    @Scope(value = "singleton")
    public ServerResponseInterceptor serverResponseInterceptor(){
        return brave().serverResponseInterceptor();
    }

    @Bean
    @Scope(value = "singleton")
    public ClientRequestInterceptor clientRequestInterceptor(){
        return brave().clientRequestInterceptor();
    }

    @Bean
    @Scope(value = "singleton")
    public ClientResponseInterceptor clientResponseInterceptor(){
        return brave().clientResponseInterceptor();
    }

}
