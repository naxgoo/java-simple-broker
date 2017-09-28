import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class PBroker extends Thread{
    private BlockingQueue msgQueue;
    private Socket socket;
    private JsonArray backupMsg;


    public PBroker(Socket socket, BlockingQueue msgQueue, JsonArray backupMsg){
        this.msgQueue = msgQueue;
        this.socket = socket;
        this.backupMsg = backupMsg;
    }

    public void run(){
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            String msg = in.readUTF();
            System.out.println("Got message <<" + msg + ">>. Sending it to queue.");
            msgQueue.put(msg);
            backupMsg.add(new JsonPrimitive(msg));
            FileWriter file = new FileWriter("queue.json");
            file.write(backupMsg.toString());
            file.flush();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
