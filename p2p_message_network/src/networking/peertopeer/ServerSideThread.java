package networking.peertopeer;

import networking.data.Message;
import networking.exception.PeerException;
import networking.utils.Utils;

import java.io.*;
import java.net.Socket;

public class ServerSideThread extends Thread {

    private ServerSide serverSide;
    private Socket socket;

    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;

    public ServerSideThread(Socket socket, ServerSide serverSide) throws PeerException {
        try {
            this.serverSide = serverSide;
            this.socket = socket;

            this.outStream = new ObjectOutputStream(socket.getOutputStream());
            this.inStream = new ObjectInputStream(socket.getInputStream());
        }
        catch (Exception e){
            throw new PeerException("Could not start server side thread");
        }
    }

    public ObjectOutputStream getOutStream() {
        return outStream;
    }

    public void run() {
        try {
            while (true) {
                Message msg = (Message) inStream.readObject();

                if (msg != null && msg.getOp() == Message.Operation.OP_NEW_CONNECTION){
                    //get content from msg -> parse from json to string
                    String newPeerAddress = Utils.getValueFromValueJsonString(msg.getContents());

                    //call on serverside, to call on peer, to add a new peer connection
                    serverSide.getPeerSide().connectNewPeer(newPeerAddress);
                }
            }
        }
        catch (Exception e) {
            serverSide.getServerSideThreads().remove(this);
        }
    }

}
