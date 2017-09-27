import javax.xml.crypto.Data;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class CBroker extends Thread {
    private BlockingQueue msgQueue;
    private Socket socket;

    public CBroker(Socket socket, BlockingQueue msgQueue){
        this.socket = socket;
        this.msgQueue = msgQueue;
    }

    public void run(){
        try {
            String msg = msgQueue.take().toString();
            System.out.println("Sending <<" + msg + ">> to consumer!");
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(msg);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
