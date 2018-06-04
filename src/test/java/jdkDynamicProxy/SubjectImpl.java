package jdkDynamicProxy;

public class SubjectImpl implements Subject{

    @Override
    public void doSomething() {
        System.out.println("This is do something");
    }
}
