package networking.data;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum Operation {
        OP_REQUEST, OP_RESPONSE, OP_NEW_CONNECTION, OP_MESSAGE
    };

    private Operation op;
    private String contents;

    public Message(){
        this.op = null;
        this.contents = null;
    }

    /* GETTERS */

    public Operation getOp() {
        return op;
    }

    public String getContents() {
        return contents;
    }

    /* SETTERS */

    public void setOp(Operation op) {
        this.op = op;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

}
