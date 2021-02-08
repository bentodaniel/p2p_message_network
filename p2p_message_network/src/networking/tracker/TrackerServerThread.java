package networking.tracker;

import networking.data.Message;
import networking.exception.TrackerServerException;
import networking.utils.Utils;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TrackerServerThread extends Thread {
    private final Socket socket;
    private final TrackerServer trackerServer;

    TrackerServerThread(Socket inSoc, TrackerServer trackerServer) {
        this.socket = inSoc;
        this.trackerServer = trackerServer;
    }

    public void run() {
        try {
            System.out.println("Tracker thread from server to client");

            ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());

            Message msg_request = (Message) inStream.readObject();

            if (msg_request == null || msg_request.getOp() != Message.Operation.OP_REQUEST) {
                throw new TrackerServerException("There was an error receiving the request message. Please try again.");
            }

            Message msg_tracker_response = new Message();
            msg_tracker_response.setOp(Message.Operation.OP_RESPONSE);
            msg_tracker_response.setContents(Utils.convertStringListToJsonString(trackerServer.getListOfPeers()));

            //critical area? many threads accessing listOfPeers at the same time
            trackerServer.addPeerToListOfPeers(Utils.getValueFromValueJsonString(msg_request.getContents()));

            outStream.writeObject(msg_tracker_response);

            outStream.close();
            inStream.close();
            socket.close();
        }
        catch (TrackerServerException e){
            System.err.println(e.getMessage());
        }
        catch (Exception e) {
            System.err.println("Something went wrong with a tracker server thread");
        }
    }
}
