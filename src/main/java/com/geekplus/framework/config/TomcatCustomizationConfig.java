//package com.geekplus.framework.config;
//
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//import org.springframework.boot.web.server.WebServerFactoryCustomizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * author     : geekplus
// * email      :
// * date       : 11/19/25 10:20 AM
// * description: //TODO
// */
//@Configuration
//public class TomcatCustomizationConfig {
//    @Bean
//    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> customizer() {
//        return (factory) -> {
//            factory.setPort(8443); // 设置端口号
//            factory.setContextPath("/"); // 设置上下文路径
//            // 可以在这里添加更多的自定义配置
//        };
//    }
//}
