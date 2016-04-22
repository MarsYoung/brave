# brave-dubbo #

content
1.设计
  通过Dubbo的Filter extentions进行Dubbo Rpc调用的捕获，
  通过Adapter处理捕获的请求数据，并通过Tracer将其发送到Collector.
  
  不同Tracer之间的数据传递，通过Brave默认的ThreadBinder进行，
  具体的信息通过State对象进行封装和处理。
  
  如何让Dubbo项目实现0配置监听，一个是通过Extension，一个是dubbo默认会加载
  META-INFO的spring下的xml，可以通过打包的方式，把dubbo相关Brave配置打到
  dubbo-brave中。
  
  其余信息均模仿brave其它框架的实现。
  
2.使用
    
    2.1 download source
    2.2 修改brave-dubbo中的 配置文件，配置好brave对象，其中会注入
        spanCollector，tracer,binder,interceptor等多个对象。参考
        brave-core中的com.github.kristofa.brave.BraveApiConfig。
    2.3 打包
    2.4 引入到对应的dubbo项目。
    

3.备注
    dubbo版本2.8.5-SNAPSHOT
    