package jdkDynamicProxy;

public class Test {

    public static void main(String[] args){
        Subject subject= (Subject) new ProxyHandler().getProxyObject(new SubjectImpl());
        subject.doSomething();
    }
}
