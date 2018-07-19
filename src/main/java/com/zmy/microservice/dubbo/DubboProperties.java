package com.zmy.microservice.dubbo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;


@ConfigurationProperties(prefix = "gingkoo.dubbo")
public class DubboProperties {


    private Application application;

    private Registry registry;

    private Provider provider;

    private Consumer consumer;

    private Protocol primaryProtocol;

    private Protocol secondaryProtocol;


    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Registry getRegistry() {
        return registry;
    }

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public Protocol getPrimaryProtocol() {
        return primaryProtocol;
    }

    public void setPrimaryProtocol(Protocol primaryProtocol) {
        this.primaryProtocol = primaryProtocol;
    }

    public Protocol getSecondaryProtocol() {
        return secondaryProtocol;
    }

    public void setSecondaryProtocol(Protocol secondaryProtocol) {
        this.secondaryProtocol = secondaryProtocol;
    }


    @Data
    /**
     * Dubbo application配置
     */
    public static class Application {

        private String id;

        /**
         * 应用名称
         */
        @NotNull
        private String name;

        /**
         * 应用版本
         */
        private String version;

        /**
         * 应用负责人，请采用“邮箱前缀-手机号码”的格式填写，方便后期的服务治理；例如：zhangsan-13012345678
         */
        private String owner;

        /**
         * 组织名(BU或部门)
         */
        private String organization;

        /**
         * 用于服务分层对应的架构
         */
        private String architecture;

        /**
         * 环境，如：develop/test/product
         */
        private String environment;

        /**
         * Java代码编译器，可选：javassist、jdk
         */
        private String compiler;

        /**
         * 日志输出方式，如：slf4j/jcl/log4j/jdk
         */
        private String logger;
    }


    /**
     * Dubbo registry配置
     */
    @Data
    public static class Registry {

        /**
         * 注册中心引用BeanId，可以在<dubbo:service registry="">或<dubbo:reference registry="">中引用此ID
         */
        private String id;

        /**
         * 注册中心服务器地址，如果地址没有端口缺省为9090，同一集群内的多个地址用逗号分隔，如：ip:port,ip:port
         */
        @NotNull
        private String address;

        /**
         * 注册中心登录用户名，如果注册中心不需要验证可不填
         */
        private String username;

        /**
         * 注册中心登录密码，如果注册中心不需要验证可不填
         */
        private String password;

        /**
         * 注册中心缺省端口，当address没有带端口时使用此端口做为缺省值
         */
        private Integer port;

        /**
         * 注同中心地址协议，支持zookeeper/redis/multicase三种协议
         */
        private String protocol;

        /**
         * 网络传输方式，可选mina,netty
         */
        private String transporter;

        /**
         * 注册中心请求超时时间(毫秒)
         */
        private Integer timeout;

        /**
         * 注册中心会话超时时间(毫秒)，用于检测提供者非正常断线后的脏数据，比如用心跳检测的实现，此时间就是心跳间隔，不同注册中心实现不一样。
         */
        private Integer session;

        /**
         * 使用文件缓存注册中心地址列表及服务提供者列表，应用重启时将基于此文件恢复，注意：两个注册中心不能使用同一文件存储
         */
        private String file;

        /**
         * 停止时等待通知完成时间(毫秒)
         */
        private Integer wait;

        /**
         * 注册中心不存在时，是否报错
         */
        private Boolean check;

        /**
         * 是否向此注册中心注册服务，如果设为false，将只订阅，不注册
         */
        private Boolean register;

        /**
         * 服务是否动态注册，如果设为false，注册后将显示后disable状态，需人工启用，并且服务提供者停止时，也不会自动取消册，需人工禁用
         */
        private Boolean dynamic;

        /**
         * 是否向此注册中心订阅服务，如果设为false，将只注册，不订阅
         */
        private Boolean subscribe;

    }

    /**
     * Dubbo provider配置
     */
    @Data
    public static class Provider {

        private String id;

        /**
         * 是否启用Provider
         */
        private Boolean enable = false;

        /**
         * 服务主机名，多网卡选择或指定VIP及域名时使用，为空则自动查找本机IP，建议不要配置，让Dubbo自动获取本机IP
         */
        private String host;

        /**
         * 服务线程池大小(固定大小)
         */
        private Integer threads;

        /**
         * 请求及响应数据包大小限制，单位：字节; 默认8M
         */
        private Integer payload;

        /**
         * 提供者上下文路径，为服务path的前缀
         */
        private String path;

        /**
         * 协议的服务器端实现类型，比如：dubbo协议的mina,netty等，http协议的jetty,servlet等;
         * dubbo协议缺省为netty，http协议缺省为servlet
         */
        private String server;

        /**
         * 协议的客户端实现类型，比如：dubbo协议的mina,netty等
         */
        private String client;

        /**
         * 协议编码方式
         */
        private String codec;

        /**
         * 服务提供方远程调用过程拦截器名称，多个名称用逗号分隔
         */
        private String filter;

        /**
         * 线程池类型，可选：fixed/cached
         */
        private String threadpool;

        /**
         * 服务提供者最大可接受连接数
         */
        private Integer accepts = 0;

        /**
         * 远程服务调用超时时间(毫秒)
         */
        private Integer timeout;

        /**
         * 远程服务调用重试次数，不包括第一次调用，不需要重试请设为0
         */
        private Integer retries;

        /**
         * 是否缺省异步执行，不可靠异步，只是忽略返回值，不阻塞执行线程
         */
        private Boolean async;

        /**
         * 设为true，表示使用缺省代理类名，即：接口名 + Local后缀。
         */
        private Boolean stub;

        /**
         * 设为true，表示使用缺省Mock类名，即：接口名 + Mock后缀。
         */
        private Boolean mock;

        /**
         * 服务是否动态注册，如果设为false，注册后将显示后disable状态，需人工启用，并且服务提供者停止时，也不会自动取消册，需人工禁用。
         */
        private Boolean dynamic;

        /**
         * 服务负责人，请采用“邮箱前缀-手机号码”的格式填写，方便后期的服务治理；例如：zhangsan-13012345678
         */
        private String owner;

        /**
         * 服务权重
         */
        private Integer weight;

        /**
         * 服务提供者每服务每方法最大可并行执行请求数
         */
        private Integer executes;

        /**
         * 每服务消费者每服务每方法最大并发调用数
         */
        private Integer actives;

        /**
         * 生成动态代理方式，可选：jdk/javassist
         */
        private String proxy;

        /**
         * 集群方式，可选：failover/failfast/failsafe/failback/forking
         */
        private String cluster;

        /**
         * 令牌验证，为空表示不开启，如果为true，表示随机生成动态令牌
         */
        private Boolean token;

        /**
         * 延迟注册服务时间(毫秒)- ，设为-1时，表示延迟到Spring容器初始化完成时暴露服务
         */
        private Integer delay;
    }

    /**
     * Dubbo consumer配置
     */
    @Data
    public static class Consumer {

        private String id;

        /**
         * 是否启用Consumer
         */
        private Boolean enable = false;

        /**
         * 远程服务调用超时时间(毫秒)
         */
        private Integer timeout;

        /**
         * 远程服务调用重试次数，不包括第一次调用，不需要重试请设为0
         */
        private Integer retries;

        /**
         * 负载均衡策略，可选值：random,roundrobin,leastactive，分别表示：随机，轮循，最少活跃调用
         */
        private String loadbalance;

        /**
         * 是否缺省异步执行，不可靠异步，只是忽略返回值，不阻塞执行线程
         */
        private Boolean async;

        /**
         * 每个服务对每个提供者的最大连接数，rmi、http、hessian等短连接协议支持此配置，dubbo协议长连接不支持此配置
         */
        private Integer connections;

        /**
         * 启动时检查提供者是否存在，true报错，false忽略
         */
        private Boolean check;

        /**
         * 生成动态代理方式，可选：jdk/javassist
         */
        private String proxy;

        /**
         * 调用服务负责人，请采用“邮箱前缀-手机号码”的格式填写，方便后期的服务治理；例如：zhangsan-13012345678
         */
        private String owner;

        /**
         * 每服务消费者每服务每方法最大并发调用数
         */
        private Integer actives;

        /**
         * 集群方式，可选：failover/failfast/failsafe/failback/forking
         */
        private String cluster;

        /**
         * 服务消费方远程调用过程拦截器名称，多个名称用逗号分隔
         */
        private String filter;

        /**
         * 服务消费方引用服务监听器名称，多个名称用逗号分隔
         */
        private String listener;

        /**
         * 以调用参数为key，缓存返回结果，可选：lru, threadlocal, jcache等
         */
        private String cache;

        /**
         * 是否启用JSR303标准注解验证，如果启用，将对方法参数上的注解进行校验
         */
        private Boolean validation;

        /**
         * lazy create connection
         */
        private Boolean lazy;
    }

    /**
     * Dubbo protocol配置
     */
    @Data
    public static class Protocol {

        /**
         * 协议BeanId，可以在<dubbo:service protocol="">中引用此ID，如果ID不填，缺省和name属性值一样，重复则在name后加序号
         */
        private String id;

        /**
         * 协议名称
         */
        @NotNull
        private String name = "dubbo";

        /**
         * 服务端口;
         * dubbo协议缺省端口为20880，rmi协议缺省端口为1099，http和hessian协议缺省端口为80。
         * 如果配置为-1 或者 没有配置port，则会分配一个没有被占用的端口，分配
         * 的端口在协议缺省端口的基础上增长，确保端口段可控。
         */
        private Integer port = 20880;

        /**
         * -服务主机名，多网卡选择或指定VIP及域名时使用，为空则自动查找本机IP，-建议不要配置，让Dubbo自动获取本机IP
         */
        private String host;

        /**
         * 线程池类型，可选：fixed/cached
         */
        private String threadpool;

        /**
         * 服务线程池大小(固定大小)
         */
        private Integer threads;

        /**
         * io线程池大小(固定大小), 默认：cpu个数+1
         */
        private Integer iothreads;

        /**
         * 服务提供方最大可接受连接数
         */
        private Integer accepts;

        /**
         * 请求及响应数据包大小限制，单位：字节
         */
        private Integer payload;

        /**
         * 协议编码方式
         */
        private String codec;

        /**
         * 协议序列化方式，当协议支持多种序列化方式时使用，比如：dubbo协议的dubbo,hessian2,java,compactedjava，以及http协议的json等
         */
        private String serialization;

        /**
         * 协议的服务器端实现类型，比如：dubbo协议的mina,netty等，http协议的jetty,servlet等
         */
        private String server;

        /**
         * 协议的客户端实现类型，比如：dubbo协议的mina,netty等
         */
        private String client;

        /**
         * 该协议的服务是否注册到注册中心
         */
        private Boolean register;

        /**
         * 心跳间隔，对于长连接，当物理层断开时，比如拔网线，TCP的FIN消息来不及发送，对方收不到断开事件，此时需要心跳来帮助检查连接是否已断开
         */
        private Integer heartbeat;

        /**
         * 网络读写缓冲区大小
         */
        private Integer buffer;

        /**
         * 序列化编码
         */
        private String charset = "UTF-8";

        /**
         * 设为true，将向logger中输出访问日志，也可填写访问日志文件路径，直接把访问日志输出到指定文件
         */
        private String accessLog;

        /**
         * 提供者上下文路径，为服务path的前缀
         */
        private String path;

        private String contextpath;

        /**
         * 协议的消息派发方式，用于指定线程模型，比如：dubbo协议的all, direct, message, execution, connection等
         */
        private String dispatcher;

        /**
         * 协议的服务端和客户端实现类型，比如：dubbo协议的mina,netty等，可以分拆为server和client配置
         */
        private String transporter;

        /**
         * 线程池队列大小，当线程池满时，排队等待执行的队列大小，建议不要设置，当线程程池时应立即失败，重试其它服务提供机器，而不是排队，除非有特殊需求。
         */
        private Integer queues;

        /**
         * 是否默认协议, primary-protocol默认为true, secondary-protocol默认为false
         */
        private Boolean isDefault;
    }

}
