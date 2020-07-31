package io.gles.socket.server;

import io.gles.socket.protocol.TextProtocol;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketServer {

    private final transient Integer port;

    public SocketServer(Integer port) {
        this.port = port;
    }

    public void run() throws ServerException {
        try (ServerSocket server = new ServerSocket(this.port)) {

            System.out.println(String.format("Socket Server running on port: %d", port));

            while (!server.isClosed()) {
                new Thread(new IMDBHandler(server.accept(), new TextProtocol()))
                        .start();
            }
        } catch (IOException e) {
           throw new ServerException("Fail on create server", e.getCause());
        }
    }

}
