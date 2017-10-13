import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Broker {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket;
        BufferedReader in = null;
        BlockingQueue msgQueue = new LinkedBlockingDeque();
        String msg;
        JsonArray backupMsg = new JsonArray();
        JsonParser parser = new JsonParser();


        try {
            serverSocket = new ServerSocket(14141);
            System.out.println("Server socket created!");
            backupMsg = (JsonArray) parser.parse(new FileReader("queue.json"));
            for (int i = 0; i < backupMsg.size(); i++) {
                msgQueue.add(backupMsg.get(i).getAsString());
            }

            socket = serverSocket.accept();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        while ((msg = in.readLine()) != null){
            if (msg.startsWith("1")){
                new Thread(new PBroker(socket, msgQueue, backupMsg, msg.substring(2))).start();
            } else if (msg.startsWith("2")) {
                new Thread(new CBroker(socket, msgQueue, backupMsg)).start();
            } else {
                System.out.println("Idk what is that. Pls try again!");
            }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
