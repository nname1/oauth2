package jmx;

public class HelloWorld implements HelloWorldMBean {

    private String greeting;

    public HelloWorld(String greeting) {
        this.greeting = greeting;
    }
    public HelloWorld() {
        this.greeting = "hello world!";
    }
    public String getGreeting() {
        return greeting;
    }
    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
    public void printGreeting() {
        System.out.println(greeting);
    }
}
