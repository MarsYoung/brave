# brave-dubbo #

content
1.设计
  通过Dubbo的Filter extentions进行Dubbo Rpc调用的捕获，
  通过Adapter处理捕获的请求数据，并通过Tracer将其发送到Collector.
  
  不同Tracer之间的数据传递，通过Brave默认的ThreadBinder进行，
  具体的信息通过State对象进行封装和处理。（Brave已经实现）
  
  如何让Dubbo项目实现0配置监听，一个是通过Extension，一个是dubbo默认会加载
  META-INFO的spring下的xml，可以通过打包的方式，把dubbo相关Brave配置打到
  dubbo-brave中。
  
  略微的修改了brave的server receive的handler，加入了一个开关，来控制是否在server端产生span.
  默认的是关闭的，开启可以通过Brave builder配置为开启，可以参考该项目中的BraveDubboConfig类。
  
  
2.使用
    
    2.1 download source
    
    2.2 修改brave-dubbo中的 配置文件 brave-dubbo.properties,配置好scribe,eg:
        
        #zipkin.scribecollector
        zipkin.scribecollector.ip=10.16.12.137
        zipkin.scribecollector.port=1463
        
    2.3 打包
    
    2.4 引入到对应jar包到需要监控的dubbo项目，并且在对应的项目中加入properties配置：
       classpath*:brave-dubbo.properties
    
    

3.备注
    dubbo版本2.8.5-SNAPSHOT
    当filter获取不到对应的项目的springContext时，可以通过加入Service来解决。