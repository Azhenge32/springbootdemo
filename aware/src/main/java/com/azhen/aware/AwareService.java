package com.azhen.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

/**
 * @author Azhen
 * @date 2018/04/05
 */
@Service
public class AwareService implements BeanNameAware, ResourceLoaderAware,BeanFactoryAware {
    private String beanName;
    private ResourceLoader resourceLoader;
    private static BeanFactory beanFactory;
    @Override
    public void setBeanName(String s) {
        this.beanName = s;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void outputResult() {
        System.out.println("Bean的名称为：" + beanName);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        AwareService.beanFactory = beanFactory;
        System.out.println("设置了BeanFactory");
    }
}
