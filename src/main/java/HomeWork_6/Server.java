package HomeWork_6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 3333;
    private List<Handler> handlers;

    public Server() {
        this.handlers = new ArrayList<Handler>();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server start");
            while (true) {
                System.out.println("Waiting for connection ");
                Socket socket = serverSocket.accept();
                System.out.println("Client connection");
                Handler handler = new Handler(socket,this);
                handlers.add(handler);
                handler.handle();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcast(String message) {
        for (Handler handler : handlers) {
            handler.send(message);
        }
    }
}
