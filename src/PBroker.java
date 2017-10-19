import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class PBroker extends Thread{
    private BlockingQueue msgQueue;
    private Socket socket;
    private String msg;
    private JsonArray backupMsg;

    PBroker(Socket socket, String msg, BlockingQueue msgQueue, JsonArray backupMsg){
        this.msgQueue = msgQueue;
        this.socket = socket;
        this.msg = msg;
        this.backupMsg = backupMsg;
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
