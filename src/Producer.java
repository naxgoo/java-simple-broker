
import java.io.*;
import java.net.Socket;
import java.util.Random;

public class Producer {
    public static void main(String[] args) {
        try {
            Socket socket;
            socket = new Socket("127.0.0.1", 14141);
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            while (socket.isConnected()){
                int number = new Random().nextInt(100);
                out.println("1." + String.valueOf(number));
                System.out.println("Produced: " + number);
                Thread.sleep(1000);
            }
            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
