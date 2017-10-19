import java.io.*;
import java.net.Socket;
import java.util.Random;

public class Producer {
    public static void main(String[] args) {
        try {
            Socket socket;
            DataOutputStream out;
            int number;

            while (true){

                socket = new Socket("127.0.0.1", 14141);
                out = new DataOutputStream(socket.getOutputStream());
                number = new Random().nextInt(100);
                out.writeUTF("1." + String.valueOf(number));
                System.out.println("Produced: " + number);

                Thread.sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
