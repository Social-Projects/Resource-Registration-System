package org.registrator.community.config;

import java.io.IOException;
import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

@Configuration
@ComponentScan({"org.registrator.community.components", "org.registrator.community.dao"})
@PropertySource(value = "classpath:mail.properties")
public class AdditionalAppConfig {

    @Autowired
    private Environment env;

    @Bean(name = "mailSender")
    public JavaMailSender mailSender(){
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setDefaultEncoding("UTF-8");
        sender.setHost("smtp.gmail.com");
        sender.setProtocol("smtps");
//        sender.setPort(587);
        sender.setPort(465);
        sender.setUsername("resources.registrator@gmail.com");
        sender.setPassword("m@!RljNg");
       
        Properties javaMailProperties = new Properties();
        javaMailProperties.setProperty("mail.smtp.auth", "true");
        javaMailProperties.setProperty("mail.smtp.starttls.enable", "true");
        javaMailProperties.setProperty("mail.smtp.socketFactory.fallback", "true");
        sender.setJavaMailProperties(javaMailProperties);

        return sender;
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
}

