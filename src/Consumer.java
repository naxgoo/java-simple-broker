import java.io.*;
import java.net.Socket;

public class Consumer {
    public static void main(String[] args) {

        try {
            Socket socket;
            socket = new Socket("127.0.0.1", 14141);
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            while (socket.isConnected()){
                out.println("2.");
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("Consumed: <<" + in.readLine() + ">>");

                Thread.sleep(1000);
            }
            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
