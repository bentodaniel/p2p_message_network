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
import java.util.Scanner;

public class Peer {

    private String username;
    private String port;
    private String address;
    private ServerSide serverSide;

    public static void main(String[] args) {
        if (args.length != 1) { //received the port as argument
            System.err.println("Wrong number of arguments, execute as: Peer <TrackerServerIP>:<TrackerServerPORT>");
            System.exit(-1);
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter the username and the port number for this peer: (separate with ' ')");
            String[] inputValues = bufferedReader.readLine().split(" ");

            Peer peer = new Peer();
            peer.username = inputValues[0];
            peer.port = inputValues[1];
            peer.address = Inet4Address.getLocalHost().getHostAddress() + ":" + inputValues[1];

            peer.serverSide = new ServerSide(peer.port, peer);
            peer.serverSide.start();

            System.out.println("Choose the operation mode:\n1 -> Manual\n2 -> Automatic");
            String op_mode = bufferedReader.readLine();
            while (!op_mode.equals("1") && !op_mode.equals("2")){
                System.out.println("Invalid mode. Please type the chosen mode again.");
                op_mode = bufferedReader.readLine();
            }

            if (op_mode.equals("1")){
                System.out.println("Insert ip:port, space separated of the peers: ");
                String[] peersAddress = bufferedReader.readLine().split(" ");

                peer.connectToPeers(peersAddress);
            }
            else {
                peer.connectToTracker(args[0]);
            }

        }
        catch (Exception e){
            System.err.println("Something went wrong with this peer.");
        }
    }

    /**
     * Connects the peer to the tracker server and gets all necessary info
     * @param trackerServerAddress Address of the tracker server in the form of ip:port
     */
    private void connectToTracker(String trackerServerAddress){
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
            connectToPeers(Utils.convertListToArray(adresses));

        }catch (PeerException e){
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Could not connect to tracker server");
        }
    }

    /**
     * Connects to all other peers and sends them a hello message
     * @param peersAddress The list of addresses of the other peers
     */
    private void connectToPeers(String[] peersAddress) {
        if (peersAddress == null){
            return;
        }

        for(String peerAddress : peersAddress){
            String[] addressValues = peerAddress.split(":");

            Socket socket;
            try {
                socket = new Socket(addressValues[0], Integer.parseInt(addressValues[1]));
                ClientSide client = new ClientSide(socket, this);
                client.sayHello(address);
                client.start();

            } catch (Exception e) {
                System.err.println(peerAddress + " is an invalid address or something went wrong.");
            }
        }
        communicate();
    }

    /**
     * Connect to a new peer.
     * Used after refeiving a hello from another peer
     * @param newPeerAddress The other peer's address
     */
    public void connectNewPeer(String newPeerAddress) {
        String[] addressValues = newPeerAddress.split(":");

        Socket socket;
        try {
            socket = new Socket(addressValues[0], Integer.parseInt(addressValues[1]));
            ClientSide client = new ClientSide(socket, this);
            client.start();

        } catch (Exception e) {
            System.err.println(newPeerAddress + " is an invalid address or something went wrong.");
        }
    }

    /**
     * Communicate with the other peers
     */
    public void communicate() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("You can communicate (e to exit):");
            String message;

            while (true) {
                message = sc.nextLine();

                if (message.equals("e")){
                    serverSide.disconnect();
                    break;
                }
                else {
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
            System.exit(0);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Something went wrong during communication");
        }
    }
}
