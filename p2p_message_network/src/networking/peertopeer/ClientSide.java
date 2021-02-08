package networking.peertopeer;

import networking.data.Message;
import networking.utils.Utils;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.*;
import java.net.Socket;

public class ClientSide extends Thread {

    private Socket socket;
    private Peer peerSide;

    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;

    /**
     * Constructor
     * @param socket The socket this client is connected to
     * @param peer The peerside of this client
     */
    public ClientSide(Socket socket, Peer peer) {
        try {
            this.socket = socket;
            this.peerSide = peer;

            this.outStream = new ObjectOutputStream(socket.getOutputStream());
            this.inStream = new ObjectInputStream(socket.getInputStream());
        }
        catch (Exception e){
            System.err.println("Could not start client side");
        }
    }

    public void sayHello(String address) {
        try {
            Message msg_hello = new Message();
            msg_hello.setOp(Message.Operation.OP_NEW_CONNECTION);
            msg_hello.setContents(Utils.convertStringToValueJsonString(address));

            // send hello
            outStream.writeObject(msg_hello);
        }
        catch (Exception e){
            System.err.println("Could not establish connection with another peer");
        }
    }

    public void run() {
        try {
            while (true) {
                try {
                    Message msg_received = (Message) inStream.readObject();

                    if (msg_received != null && msg_received.getOp() == Message.Operation.OP_MESSAGE){
                        JsonObject jsonObject = Json.createReader(new StringReader(msg_received.getContents())).readObject();

                        if (jsonObject.containsKey("username")){
                            System.out.println("[" + jsonObject.getString("username") + "]: " +
                                    jsonObject.getString("message"));
                        }
                    }

                } catch (Exception e) {
                    interrupt();
                }
            }
        }
        catch (Exception e) {

        }
    }

}
