package dataprocessors;

import java.io.ByteArrayOutputStream;

import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import datatypes.Data;
import datatypes.Message;
import eventsystem.EventGenerator;

public class UDPSender extends DataProcessor {

    private String ip;
    private int port;

    private InetAddress address;
    private DatagramSocket socket;

    private int seq = 0;
    private boolean initialized = false;

    public UDPSender(String ip, int port) {
        this.port = port;
        this.ip = ip;
    }

    private void init() throws SocketException, UnknownHostException {
        if(initialized == false) { 
            socket = new DatagramSocket();
            address = InetAddress.getByName(ip);
        }
    }

    @Override
    protected Data process(Data input, EventGenerator sender) {
        try {
            this.init();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
    
            oos.writeObject(new Message(seq, input));
            oos.flush();

            byte[] serialized = baos.toByteArray();

            DatagramPacket packet = new DatagramPacket(serialized, serialized.length, this.address, this.port);
            socket.send(packet);
            seq++;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
