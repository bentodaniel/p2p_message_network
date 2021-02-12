package networking.peertopeer;

import gui.java.presentation.fx.inputcontroller.ChatWindowController;
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
    private ServerSide serverSide;

    private ChatWindowController chatWindowController;

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

            this.serverSide = new ServerSide(port, this);
            this.serverSide.start();
        }
        catch (Exception e) {
            throw new PeerException("Couldn't start this peer");
        }
    }

    public void setChatWindowController(ChatWindowController chatWindowController) {
        this.chatWindowController = chatWindowController;
    }

    /**
     * Connects the peer to the tracker server and gets all necessary info
     * @param trackerIp Ip of the tracker server
     * @param trackerPort Port of the tracker server
     * @return String[] containing the addresses of the other peers
     */
    public String[] connectToTracker(String trackerIp, String trackerPort) throws PeerException {
        Socket socket;
        ObjectOutputStream outStream;
        ObjectInputStream inStream;
        try {
            socket = new Socket(trackerIp, Integer.parseInt(trackerPort));

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
     * @param needToGreetPeer A boolean that defines if, after connecting to a peer, the peer should connect back.
     *                        Generally the use of true is advised
     * @return True if it connects to all peers correctly.
     * False if no addresses were given or if an error occurs
     */
    public boolean connectToPeers(String[] peersAddress, boolean needToGreetPeer) throws PeerException {
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

    public void receiveMessage(String user, String msg){
        if (chatWindowController == null) {
            System.out.println("[" + user + "]: " + msg);
        }
        else {
            chatWindowController.receiveMessage(user, msg);
        }
    }

    public String getUsername() {
        return username;
    }
}
