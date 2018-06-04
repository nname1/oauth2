package socket.TCP;

import socket.UDP.UDPServer;

import java.io.*;
import java.net.*;

public class TCPServer {


    //服务器IP
    public static final String SERVER_IP = "127.0.0.1";

    //服务器端口号
    public static final int SERVER_PORT = 10006;

    private ServerSocket serverSocket;

    private Socket socket;
    /***
     * 启动服务器
     * @param serverIp,serverPort
     */
    public void startServer(String serverIp, int serverPort) throws IOException {
        serverSocket = new ServerSocket(SERVER_PORT);
        int count =0;//记录客户端的数量
        while(true){
            socket = serverSocket.accept();
            ServerThread serverThread =new ServerThread(socket);
            serverThread.start();
            count++;
            System.out.println("count is ："+count);
        }
    }

    public static class ServerThread extends Thread{
        private Socket socket;
        public ServerThread(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            try{
                //获取输入流，并读取客户端信息
                InputStream is = socket.getInputStream();
                InputStreamReader isr =new InputStreamReader(is);
                BufferedReader br =new BufferedReader(isr);
                String info =null;
                while((info=br.readLine())!=null){
                    System.out.println("I am server，client says："+info);
                }
                socket.shutdownInput();

                //获取输出流，响应客户端的请求
                OutputStream os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os);
                pw.write("Welcome！");
                pw.flush();

                //关闭资源
                pw.close();
                os.close();
                br.close();
                isr.close();
                is.close();
                socket.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        TCPServer server = new TCPServer();
        server.startServer(SERVER_IP, SERVER_PORT);
    }
}
