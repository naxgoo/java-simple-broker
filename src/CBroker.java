import com.google.gson.JsonArray;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class CBroker extends Thread {
    private BlockingQueue msgQueue;
    private Socket socket;
    private JsonArray backupMsg;

    public CBroker(Socket socket, BlockingQueue msgQueue, JsonArray backupMsg){
        this.socket = socket;
        this.msgQueue = msgQueue;
        this.backupMsg = backupMsg;
    }

    public void run(){
        try {
            String msg = msgQueue.take().toString();
            System.out.println("Sending <<" + msg + ">> to consumer!");
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(msg);
            backupMsg.remove(0);
            //System.out.println("This is consumer's thread: " + Thread.currentThread().getId());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
