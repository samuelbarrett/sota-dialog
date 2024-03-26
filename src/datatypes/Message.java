package datatypes;

import java.io.Serializable;

public class Message implements Serializable {
    public final int sequenceNumber;
    public final Data data;

    public Message(int sequenceNumber, Data data) {
        this.sequenceNumber = sequenceNumber;
        this.data = data;
    }
}