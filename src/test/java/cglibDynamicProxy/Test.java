package cglibDynamicProxy;

public class Test {

    public static void main(String[] args){
        Subject subject= ProxyHandler.getInstance().getProxyObject(Subject.class);
        subject.doSomething();
    }
}
