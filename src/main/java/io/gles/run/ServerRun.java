package io.gles.run;

import io.gles.socket.server.ServerException;
import io.gles.socket.server.SocketServer;

import static io.gles.run.SystemUtil.getProperty;

public class ServerRun {

    public static void main(String[] args) {

        try {

            Integer port = getProperty("port", 1024);
            new SocketServer(port)
                    .run();

        } catch (NumberFormatException | ServerException e) {
            System.err.println(e.getMessage());
        }

    }

}
