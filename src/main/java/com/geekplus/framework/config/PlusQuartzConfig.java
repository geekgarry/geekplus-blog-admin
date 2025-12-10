//package com.geekplus.framework.config;
//
//import org.quartz.Scheduler;
//import org.quartz.SchedulerException;
//import org.quartz.impl.StdSchedulerFactory;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * author     : geekplus
// * email      :
// * date       : 9/12/25 3:07 PM
// * description: //TODO
// */
//@Configuration
//@ConditionalOnProperty(name = "spring.quartz.auto-startup", havingValue = "true", matchIfMissing = true)
//public class PlusQuartzConfig {
//
//    @Bean
//    public Scheduler scheduler() throws SchedulerException {
//        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
//        scheduler.start();
//        return scheduler;
//    }
//
////    @Bean
////    public JobFactory jobFactory(AutowireCapableBeanFactory beanFactory) {
////        SpringBeanJobFactory factory = new SpringBeanJobFactory();
////        factory.setBeanFactory(beanFactory);
////        return factory;
////    }
//}
