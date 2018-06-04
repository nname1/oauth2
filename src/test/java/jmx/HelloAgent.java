package jmx;

import com.sun.jdmk.comm.HtmlAdaptorServer;

import javax.management.*;

public class HelloAgent{

    private MBeanServer mbs;
    private HtmlAdaptorServer htmlAdaptorServer = new HtmlAdaptorServer();

    public HelloAgent(){
        this.mbs = MBeanServerFactory.createMBeanServer("HelloAgent");
        registerMBeans();
    }

    private void registerMBeans(){
        try{
            HelloWorld hw = new HelloWorld();
            ObjectName helloWorldName = new ObjectName("HelloAgent:name=helloWorld");
            ObjectName adapterName = new ObjectName("HelloAgent:name=HtmlAdaptorServer");
            mbs.registerMBean(htmlAdaptorServer, adapterName);
            mbs.registerMBean(hw,helloWorldName);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void startHtmlAdaptorServer(){
        try {
            htmlAdaptorServer.setPort(9092);
            htmlAdaptorServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
