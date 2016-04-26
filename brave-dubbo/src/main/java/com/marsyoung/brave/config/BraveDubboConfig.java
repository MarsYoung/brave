package com.marsyoung.brave.config;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.scribe.ScribeSpanCollector;
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
    @Value("10.16.12.137")//zipkin.scribecollector.ip
    String scribecollectorIp;//10.16.12.137
    @Value("9410")//"zipkin.scribecollector.port"
    Integer scribecollectorPort;//9410

    @Bean
    @Scope(value = "singleton")
    public Brave brave() {
        Brave.Builder builder = new Brave.Builder(serverName);
        return builder.spanCollector(new ScribeSpanCollector(scribecollectorIp, scribecollectorPort)).build();
    }
}
