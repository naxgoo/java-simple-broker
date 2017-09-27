import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class PBroker extends Thread{
    private BlockingQueue msgQueue;
    private Socket socket;

    public PBroker(Socket socket, BlockingQueue msgQueue){
        this.msgQueue = msgQueue;
        this.socket = socket;
    }

    public void run(){
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            String msg = in.readUTF();
            System.out.println("Got message <<" + msg + ">>. Sending it to queue.");
            msgQueue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
