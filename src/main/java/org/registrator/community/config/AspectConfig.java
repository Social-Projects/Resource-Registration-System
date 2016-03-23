package org.registrator.community.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan({ "org.registrator.community.controller",  "org.registrator.community.profiler"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AspectConfig {

}