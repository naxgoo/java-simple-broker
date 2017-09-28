import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jdk.nashorn.internal.parser.JSONParser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Broker {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket;
        BlockingQueue msgQueue = new LinkedBlockingDeque();
        String msg;
        JsonArray backupMsg = new JsonArray();
        File file = new File("queue.json");
        JsonParser parser = new JsonParser();


        try {
            serverSocket = new ServerSocket(14141);
            System.out.println("Server socket created!");
            if(file.exists()){
                backupMsg = (JsonArray) parser.parse(new FileReader("queue.json"));
                for (int i = 0; i < backupMsg.size(); i++) {
                    msgQueue.add(backupMsg.get(i));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true){
            try{
                if (serverSocket != null) {
                    socket = serverSocket.accept();
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    msg = in.readUTF();
                    if (msg.equals("put")){
                        new Thread(new PBroker(socket, msgQueue, backupMsg)).start();
                    } else if (msg.equals("take")) {
                        new Thread(new CBroker(socket, msgQueue, backupMsg)).start();
                    } else {
                        System.out.println("Idk what is that. Pls try again!");
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
