package socket.TCP;

import java.io.*;
import java.net.Socket;

public class TCPClient {

    public String sendTCPRequest(String serverIp, int serverPort, String str) {

        try {
            //1、创建客户端Socket，指定服务器地址和端口
            Socket socket = new Socket(serverIp, serverPort);
            //2、获取输出流，向服务器端发送信息
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.write(str);
            pw.flush();
            socket.shutdownOutput();

            //3、获取输入流，并读取服务器端的响应信息
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String info = null;
            StringBuilder sb=new StringBuilder();
            while ((info = br.readLine()) != null) {
                sb.append(info);
                System.out.println("I am client，Server says：" + info);
            }
            br.close();
            is.close();
            pw.close();
            os.close();
            socket.close();
            return sb.toString();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        TCPClient client = new TCPClient();
        String recvStr = client.sendTCPRequest(TCPServer.SERVER_IP, TCPServer.SERVER_PORT, "aaaAAAbbbBBBcccCCC");
        System.out.println("收到:" + recvStr);
    }

}
