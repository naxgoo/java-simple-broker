import com.google.gson.JsonArray;
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
    private String msg;


    public PBroker(Socket socket, BlockingQueue msgQueue, JsonArray backupMsg, String msg){
        this.msgQueue = msgQueue;
        this.socket = socket;
        this.backupMsg = backupMsg;
        this.msg = msg;
    }

    public void run(){
        try {
            System.out.println("Got message <<" + msg + ">>. Sending it to queue.");
            msgQueue.put(msg);
            backupMsg.add(new JsonPrimitive(msg));
            FileWriter file = new FileWriter("queue.json");
            file.write(backupMsg.toString());
            file.flush();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }
}
