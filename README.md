# p2p_message_network
A simple p2p messaging system

## How it works
Each peer has a ClientSide and a ServerSide for each peer it connects to, it creates a new ClientSide that listens to messages sent by the other peer's server. Whenever the peer wants to send a message, passes it to the ServerSide, which passes it to all of its threads and sends the message to each of the clients (other peers listening to messages from this peer).


The peer can connect automatically, using a TrackerServer (if it is on), which stores a list with every connected peer's address. The peer requests the list of addresses from the TrackerServer and then connects to each of the peers invididually. In this case, after a new peer connects to another peer, he sends an "hello" message with its own address so that the other peer can connect back.


The peer can also connect manually, by setting the address of the other peer. In this case, the peer will listen to messages sent from the other, but the other peer will not listen to messages sent by this peer unless he manually connects to this peer.
