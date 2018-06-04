package jmx;

public class Test {
    public static void main(String[] args){
        HelloAgent helloAgent = new HelloAgent();
        helloAgent.startHtmlAdaptorServer();
    }
}
