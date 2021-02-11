package gui.java.client;

import networking.peertopeer.Peer;
import networking.peertopeer.ServerSide;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Inet4Address;

public class TempClient {
    public static void main(String[] args) {
        String trackerAddress = "localhost:55555";

        Peer peer1 = null;
        Peer peer2 = null;
        Peer peer3 = null;

        /** PPER 1 */
        try {
            peer1 = new Peer("A", "1111");
            String[] adds = peer1.connectToTracker(trackerAddress);
            peer1.connectToPeers(adds);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        /** PEER 2 */
        try {
            peer2 = new Peer("B", "2222");
            String[] adds = peer2.connectToTracker(trackerAddress);
            peer2.connectToPeers(adds);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        /** PEER 3 */
        try {
            peer3 = new Peer("C", "3333");
            String[] adds = peer3.connectToTracker(trackerAddress);
            peer3.connectToPeers(adds);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }



        try {
            peer1.communicate("OLA FROM A");
        }
        catch (Exception e) {
            System.out.println("cant communicate a");
        }

        try {
            peer2.communicate("OLA FROM B");
        }
        catch (Exception e) {
            System.out.println("cant communicate a");
        }

        try {
            peer3.communicate("OLA FROM C");
        }
        catch (Exception e) {
            System.out.println("cant communicate a");
        }

        try {
            peer2.communicate("OLA 2 FROM B");
        }
        catch (Exception e) {
            System.out.println("cant communicate a");
        }


        /*
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter the username and the port number for this peer: (separate with ' ')");
            String[] inputValues = bufferedReader.readLine().split(" ");


            peer.username = inputValues[0];
            peer.port = inputValues[1];
            peer.address = Inet4Address.getLocalHost().getHostAddress() + ":" + inputValues[1];



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

         */
    }
}
