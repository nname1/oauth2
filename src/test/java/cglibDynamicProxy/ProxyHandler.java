package cglibDynamicProxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ProxyHandler implements MethodInterceptor {

    private static ProxyHandler handler =  new ProxyHandler();

    private ProxyHandler(){}

    public static ProxyHandler getInstance(){
        return handler;
    }
    public <T> T getProxyObject(Class<T> cls){
        return (T)Enhancer.create(cls,this);
    }
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("This is before");
        Object result = methodProxy.invokeSuper(o,objects);
        System.out.println("This is after");
        return result;
    }
}
