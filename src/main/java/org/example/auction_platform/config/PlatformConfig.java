package org.example.auction_platform.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "org.example.auction_platform")
@EnableAspectJAutoProxy
public class PlatformConfig {

}
