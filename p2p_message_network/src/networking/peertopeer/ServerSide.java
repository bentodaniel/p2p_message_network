package networking.peertopeer;

import networking.data.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

public class ServerSide extends Thread {

    private ServerSocket socket;
    private Peer peerSide;
    private Set<ServerSideThread> serverSideThreads = new HashSet<>();

    /**
     * Constructor
     * @param portNumber The port this server is connected at
     * @param peer The peerside of this server
     */
    public ServerSide(String portNumber, Peer peer) {
        try {
            this.socket = new ServerSocket(Integer.parseInt(portNumber));
            this.peerSide = peer;
        }
        catch (Exception e){
            System.err.println("Could not start server");
        }
    }

    public void run() {
        try {
            while (true) {
                ServerSideThread serverSideThread = new ServerSideThread(socket.accept(), this);
                serverSideThreads.add(serverSideThread);
                serverSideThread.start();
            }
        }
        catch (Exception e){

        }
    }

    public void sendMessage(Message message) {
        serverSideThreads.forEach(t -> {
            try {
                t.getOutStream().writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void disconnect() {
        try {
            socket.close();
        }
        catch (Exception e){
            System.err.println("Could not close the server correctly");
        }
    }

    public Set<ServerSideThread> getServerSideThreads() {
        return serverSideThreads;
    }

    public Peer getPeerSide() {
        return peerSide;
    }
}
