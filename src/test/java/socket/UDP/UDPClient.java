package socket.UDP;

import java.io.IOException;
import java.net.*;

public class UDPClient {

    private DatagramSocket clientSocket;

    public String sendUDPRequest(String serverIp, int serverPort, String str) {
        String recvStr = "";

        try {
            //创建UDP Socket
            clientSocket = new DatagramSocket();

            //向服务器发送数据
            byte[] sendBuf;
            sendBuf = str.getBytes();
            InetAddress serverAddr = InetAddress.getByName(serverIp);
            DatagramPacket sendPacket
                    = new DatagramPacket(sendBuf ,sendBuf.length , serverAddr , serverPort);
            try {
                clientSocket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //接收服务器的响应
            byte[] recvBuf = new byte[UDPServer.MAX_BYTES];
            DatagramPacket recvPacket
                    = new DatagramPacket(recvBuf , recvBuf.length);
            try {
                clientSocket.receive(recvPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //显示响应
            recvStr = new String(recvPacket.getData() , 0 ,recvPacket.getLength());
        } catch (SocketException e1) {
            e1.printStackTrace();
        } catch (UnknownHostException e2) {
            e2.printStackTrace();
        } finally {
            if (null != clientSocket) {
                clientSocket.close();
                clientSocket = null;
            }
        }

        return recvStr;
    }

    public static void main(String[] args) {
        UDPClient client = new UDPClient();
        String recvStr = client.sendUDPRequest(UDPServer.SERVER_IP, UDPServer.SERVER_PORT, "aaaAAAbbbBBBcccCCC");
        System.out.println("收到:" + recvStr);
    }
}
