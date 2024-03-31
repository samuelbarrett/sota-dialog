package datatypes;

import java.io.Serializable;

public class UDPMessage implements Serializable {
    public final int sequenceNumber;
    public final Data data;

    public UDPMessage(int sequenceNumber, Data data) {
        this.sequenceNumber = sequenceNumber;
        this.data = data;
    }
}