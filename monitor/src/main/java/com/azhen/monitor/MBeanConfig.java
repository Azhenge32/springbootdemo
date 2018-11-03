package com.azhen.monitor;

import com.azhen.mbean.Hello;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.export.MBeanExporter;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableMBeanExport
public class MBeanConfig {
    @Bean Hello hello() {
        return new Hello();
    }
    @Bean
    public MBeanExporter mbeanExporter(Hello hello) {
        MBeanExporter exporter = new MBeanExporter();
        Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("spitter:name=SpittleController", hello);
        exporter.setBeans(beans);
        return exporter;
    }
}
