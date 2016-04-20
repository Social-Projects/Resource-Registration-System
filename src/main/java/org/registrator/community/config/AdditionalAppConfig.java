package org.registrator.community.config;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executor;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.registrator.community.mailer.ReloadableMailSender;
import org.registrator.community.mailer.ReloadableMailSenderImpl;
import org.registrator.community.service.SettingsService;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

@Configuration
@EnableAsync
@ComponentScan({"org.registrator.community.components", "org.registrator.community.dao"})
@PropertySource(value = "classpath:mail.properties")
public class AdditionalAppConfig implements AsyncConfigurer {

    @Autowired
    private Environment env;

    @Autowired
    private SettingsService settingsService;

    @Bean(name = "mailSender")
    public ReloadableMailSender mailSender(){
        return new ReloadableMailSenderImpl(settingsService.getSmtpParameters());
    }
    
    @Bean
    public VelocityEngine velocityEngine() throws VelocityException, IOException{
        VelocityEngineFactoryBean factory = new VelocityEngineFactoryBean();
        factory.setResourceLoaderPath(env.getProperty("velocity.resource.loader.path"));
        factory.setPreferFileSystemAccess(env.getProperty("velocity.prefer.file.systema.access", Boolean.class, false));
        Properties props = new Properties();
        props.put("resource.loader", "class");
        props.put("class.resource.loader.class", env.getProperty("velocity.resource.loader.class"));
        factory.setVelocityProperties(props);
        return factory.createVelocityEngine();
    }
    

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(5);
        threadPool.setMaxPoolSize(5);
        threadPool.setAwaitTerminationSeconds(300); // 300s = 5 minutes
        threadPool.setThreadNamePrefix("async_task-");
        threadPool.initialize();
        return threadPool;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}

