import java.io.*;
import java.net.Socket;
import java.util.Random;

public class Producer {
    public static void main(String[] args) {
        try {
            Socket socket;
            while (true){
                socket = new Socket("127.0.0.1", 14141);

                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF("put");

                int number = new Random().nextInt(100);
                out.writeUTF(String.valueOf(number));
                System.out.println("Produced: " + number);

                Thread.sleep(1000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
