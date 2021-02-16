package gui.java.presentation.fx.model;

import javafx.beans.property.*;
import javafx.scene.control.SingleSelectionModel;

/**
 * Executes a gui model to start a peer
 */
public class StartPeerModel {

    private final StringProperty username;
    private final StringProperty ip;
    private final StringProperty port;
    private final StringProperty trackerIp;
    private final StringProperty trackerPort;
    private final StringProperty peersAddresses;
    private final BooleanProperty dontConnectWithAnyone;

    /**
     * Executes the constructor for the model
     *  activateLessonService The service to be set in the model
     */
    public StartPeerModel(){
        this.username = new SimpleStringProperty();
        this.ip = new SimpleStringProperty();
        this.port = new SimpleStringProperty();
        this.trackerIp = new SimpleStringProperty();
        this.trackerPort = new SimpleStringProperty();
        this.peersAddresses = new SimpleStringProperty();
        this.dontConnectWithAnyone = new SimpleBooleanProperty();
    }

    /** GETTERS */

    public String getUsername() {
        return username.get();
    }

    public String getIp() {
        return ip.get();
    }

    public String getPort() {
        return port.get();
    }

    public String getTrackerIp() {
        return trackerIp.get();
    }

    public String getTrackerPort() {
        return trackerPort.get();
    }

    public String getPeersAddresses() {
        return peersAddresses.get();
    }

    public boolean getDontConnectWithAnyone() {
        return dontConnectWithAnyone.get();
    }


    /** SETTERS */

    public void setUsername(String username) {
        this.username.set(username);
    }

    public void setIp(String ip) {
        this.ip.set(ip);
    }

    public void setPort(String port) {
        this.port.set(port);
    }

    public void setTrackerIp(String trackerIp) {
        this.trackerIp.set(trackerIp);
    }

    public void setTrackerPort(String trackerPort) {
        this.trackerPort.set(trackerPort);
    }

    public void setPeersAddresses(String peersAddresses) {
        this.peersAddresses.set(peersAddresses);
    }

    public void setDontConnectWithAnyone(boolean value) {
        this.dontConnectWithAnyone.set(value);
    }


    /** PROPERTIES */

    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty ipProperty() {
        return ip;
    }

    public StringProperty portProperty() {
        return port;
    }

    public StringProperty trackerIpProperty() {
        return trackerIp;
    }

    public StringProperty trackerPortProperty() {
        return trackerPort;
    }

    public StringProperty peersAddressesProperty() {
        return peersAddresses;
    }

    public BooleanProperty dontConnectWithAnyoneProperty() {
        return dontConnectWithAnyone;
    }


    /**
     * Clears the model's properties
     */
    public void clearProperties() {
        username.set("");
        //should clear the ip field?
        port.set("");
        trackerIp.set("");
        trackerPort.set("");
        peersAddresses.set("");
        dontConnectWithAnyone.set(false);
    }
}
