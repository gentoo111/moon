package com.moon.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by szz on 2018/4/4 20:58.
 * Email szhz186@gmail.com
 * <p>
 * hystrix进行资源隔离，其实是提供了一个抽象，叫做command，就是说，如果要把对某一个依赖服务的所有调用请求，全部隔离在同一份资源池内
 * 对这个依赖服务的所有调用请求，全部走这个资源池内的资源，不会去用其他的资源了，这个就叫做资源隔离
 * hystrix最最基本的资源隔离的技术，线程池隔离技术
 * 对某一个依赖服务，商品服务，所有的调用请求，全部隔离到一个线程池内，对商品服务的每次调用请求都封装在一个command里面
 * 每个command（每次服务调用请求）都是使用线程池内的一个线程去执行的
 * 所以哪怕是对这个依赖服务，商品服务，现在同时发起的调用量已经到了1000了，但是线程池内就10个线程，最多就只会用这10个线程去执行
 * 不会说，对商品服务的请求，因为接口调用延迟，将tomcat内部所有的线程资源全部耗尽，不会出现了
 * 不让超出这个量的请求去执行，不要因为某一个依赖服务的故障，导致耗尽了缓存服务中的所有的线程资源去执行
 */
public class CommandHelloWorld extends HystrixCommand<String> {

    private final String name;

    public CommandHelloWorld(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected String run(){
        return "Hello"+name+"!";
    }
}
