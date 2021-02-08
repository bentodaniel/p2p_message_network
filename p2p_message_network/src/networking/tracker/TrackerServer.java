package networking.tracker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TrackerServer {

    private final List<String> listOfPeers = new ArrayList<>();

    public static void main(String[] args) {
        if (args.length == 1) { //received the port as argument

            TrackerServer server = new TrackerServer();

            server.startServer(Integer.parseInt(args[0]));

        }else {
            System.err.println("Wrong number of arguments, execute as: TrackerServer <port>");
            System.exit(-1);
        }
    }

    public List<String> getListOfPeers() {
        return listOfPeers;
    }

    public void addPeerToListOfPeers(String newPeer) {
        listOfPeers.add(newPeer);
    }

    public void startServer(int port){
        ServerSocket sSoc = null;

        try {
            sSoc = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

        System.out.println("Tracker server started...");

        while(true) {
            try {
                Socket inSoc = sSoc.accept();
                TrackerServerThread newTrackerServerThread = new TrackerServerThread(inSoc, this);
                newTrackerServerThread.start();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        //sSoc.close();
    }
}
