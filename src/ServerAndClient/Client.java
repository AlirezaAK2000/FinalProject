package ServerAndClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private String InetAdress;
    private Socket socket;
    private int port;
    private ObjectInputStream objectInputStream;
    private InputStream inputStream;
    private PrintWriter printWriter;

    public Client(String inetAdress, int port) throws IOException {
        InetAdress = inetAdress;
        this.port = port;
        socket=new Socket(inetAdress,port);
        objectInputStream=new ObjectInputStream(socket.getInputStream());
        inputStream=socket.getInputStream();

        printWriter=new PrintWriter(socket.getOutputStream());
    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
