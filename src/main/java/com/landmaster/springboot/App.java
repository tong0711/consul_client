package com.landmaster.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
//@Controller
@EnableAutoConfiguration
@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class App {
    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    private DiscoveryClient discoveryClient;/**
     * 从所有服务中选择一个服务（轮询）
     */
    @RequestMapping("/discover")
    public Object discover() {
        return loadBalancer.choose("tomcat").getUri().toString();
    }

    /**
     * 获取所有服务
     */
    @RequestMapping("/services")
    public Object services() {
        return discoveryClient.getInstances("tomcat");
    }

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World my frist for consul client!";
    }

    public static void main(String[] args) throws Exception {
    	
        SpringApplication.run(App.class, args);
    }


}