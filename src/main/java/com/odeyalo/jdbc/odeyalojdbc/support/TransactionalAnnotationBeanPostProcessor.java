package com.odeyalo.jdbc.odeyalojdbc.support;

import com.odeyalo.jdbc.odeyalojdbc.exceptions.ProxyException;
import com.odeyalo.jdbc.odeyalojdbc.stereotype.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
public class TransactionalAnnotationBeanPostProcessor implements BeanPostProcessor {
    private final Map<String, Method> methods = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(TransactionalAnnotationBeanPostProcessor.class);
    private final TransactionInvocationHandlerDelegate transactionInvocationHandlerDelegate;

    public TransactionalAnnotationBeanPostProcessor(@Qualifier("transactionInvocationHandlerDelegateImpl") TransactionInvocationHandlerDelegate transactionInvocationHandlerDelegate) {
        this.transactionInvocationHandlerDelegate = transactionInvocationHandlerDelegate;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Transactional.class)) {
                this.logger.info("Found method with transaction annotation: {}", method.getName());
                this.methods.put(beanName, method);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method method = methods.get(beanName);
        if(method != null) {
            try {
                Transactional transactional = method.getAnnotation(Transactional.class);
                return Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                    private final Logger logger = LoggerFactory.getLogger(getClass());
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        this.logger.info("======= START TRANSACTION FOR THE METHOD: {}, ISOLATION LEVEL: {} =======", method.getName(), transactional.isolationLevel().name());
                        Object invoke = transactionInvocationHandlerDelegate.invokeTransaction(method, bean, transactional.isolationLevel(), args);
                        this.logger.info("======= TRANSACTION FINISHED SUCCESSFUL =======");
                        return invoke;
                    }
                });
            } catch (Throwable throwable) {
                this.logger.info("Creation proxy failed");
                throw new ProxyException(throwable.getMessage());
            }
        }
        return bean;
    }
}
