package networking.peertopeer;

import networking.data.Message;
import networking.exception.PeerException;
import networking.utils.Utils;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.*;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.List;

public class Peer {

    private String username;
    private String port;
    private String address;
    private boolean needToGreetPeer;
    private ServerSide serverSide;

    /**
     * Constructor
     * @param username This peer's username
     * @param port This peer's port
     * @throws PeerException If an error occurs
     */
    public Peer(String username, String port) throws PeerException {
        try {
            this.username = username;
            this.port = port;
            this.address = Inet4Address.getLocalHost().getHostAddress() + ":" + port;
            this.needToGreetPeer = false;

            this.serverSide = new ServerSide(port, this);
            this.serverSide.start();
        }
        catch (Exception e) {
            throw new PeerException("Couldn't start this peer");
        }
    }

    /**
     * Connects the peer to the tracker server and gets all necessary info
     * @param trackerServerAddress Address of the tracker server in the form of ip:port
     * @return String[] containing the addresses of the other peers
     */
    public String[] connectToTracker(String trackerServerAddress) throws PeerException {
        this.needToGreetPeer = true;

        String[] addressValues = trackerServerAddress.split(":");
        Socket socket;
        ObjectOutputStream outStream;
        ObjectInputStream inStream;
        try {
            socket = new Socket(addressValues[0], Integer.parseInt(addressValues[1]));

            outStream = new ObjectOutputStream(socket.getOutputStream());
            inStream = new ObjectInputStream(socket.getInputStream());

            Message msg_request = new Message();
            msg_request.setOp(Message.Operation.OP_REQUEST);
            msg_request.setContents(Utils.convertStringToValueJsonString(address));

            // send request
            outStream.writeObject(msg_request);

            // receive response
            Message msg_response = (Message) inStream.readObject();

            if (msg_response == null || msg_response.getOp() != Message.Operation.OP_RESPONSE) {
                throw new PeerException("There was an error receiving the response message. Please try again.");
            }

            inStream.close();
            outStream.close();
            socket.close();

            List<String> adresses = Utils.convertJsonStringToStringList(msg_response.getContents());
            return Utils.convertListToArray(adresses);

        } catch (PeerException | ClassNotFoundException | IOException e) {
            throw new PeerException("Something went wrong trying to connect to tracker server");
        }
    }

    /**
     * Connects to all other peers and sends them a hello message
     * @param peersAddress The list of addresses of the other peers
     * @param greetPeer A boolean that defines if the peers to who this peer connects should receive a hello message
     * @return True if it connects to all peers correctly.
     * False if no addresses were given or if an error occurs
     */
    public boolean connectToPeers(String[] peersAddress) throws PeerException {
        if (peersAddress == null){
            return false;
        }

        for(String peerAddress : peersAddress){
            ClientSide client = connectNewPeer(peerAddress);

            if (needToGreetPeer) {
                client.sayHello(address);
            }
        }
        return true;
    }

    /**
     * Connect to a new peer without saying hello
     * @param newPeerAddress The other peer's address
     * @return ClientSide created for this new connection if it connects to the peer correctly.
     * Null if an error occurs
     */
    public ClientSide connectNewPeer(String newPeerAddress) {
        ClientSide client;
        String[] addressValues = newPeerAddress.split(":");

        Socket socket;
        try {
            socket = new Socket(addressValues[0], Integer.parseInt(addressValues[1]));
            client = new ClientSide(socket, this);
            client.start();

        } catch (Exception e) {
            return null;
        }
        return client;
    }

    /**
     * Communicate with the other peers
     */
    public void communicate(String message) {
        JsonObject json = Json.createObjectBuilder()
                .add("username", username)
                .add("message", message)
                .build();

        Message msg = new Message();
        msg.setOp(Message.Operation.OP_MESSAGE);
        msg.setContents(json.toString());

        serverSide.sendMessage(msg);
    }
}
