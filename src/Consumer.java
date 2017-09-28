import java.io.*;
import java.net.Socket;

public class Consumer {
    public static void main(String[] args) {
        try {
            Socket socket;

            while (true){

                Thread.sleep(1000);

                socket = new Socket("127.0.0.1", 14141);

                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF("take");

                InputStream inSocket = socket.getInputStream();
                DataInputStream in = new DataInputStream(inSocket);

                System.out.println("Consumed: <<" + in.readUTF() + ">>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
