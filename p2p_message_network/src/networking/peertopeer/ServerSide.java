package networking.peertopeer;

import networking.data.Message;
import networking.exception.PeerException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
    public ServerSide(String portNumber, Peer peer) throws PeerException {
        try {
            this.socket = new ServerSocket(Integer.parseInt(portNumber));
            this.peerSide = peer;
        }
        catch (Exception e){
            throw new PeerException("Could not start server");
        }
    }

    public void run() {
        try {
            while (true) {
                ServerSideThread serverSideThread = new ServerSideThread(socket.accept(), this);
                serverSideThreads.add(serverSideThread);
                serverSideThread.start();
            }
        } catch (IOException | PeerException e) {
            interrupt();
        }
    }

    /**
     * Sends a message to all connected peers
     * @param message The message to send
     */
    public void sendMessage(Message message) {
        serverSideThreads.forEach(t -> {
            try {
                t.getOutStream().writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public Set<ServerSideThread> getServerSideThreads() {
        return serverSideThreads;
    }

    public Peer getPeerSide() {
        return peerSide;
    }

    public ServerSocket getSocket() {
        return socket;
    }
}
