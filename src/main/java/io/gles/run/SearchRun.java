package io.gles.run;

import io.gles.socket.client.ClientException;
import io.gles.socket.client.SocketClient;

import static io.gles.run.SystemUtil.getProperty;

public class SearchRun {

    public static void main(String[] args) {

        try {

            String host = getProperty("host", "localhost");
            Integer port = getProperty("port", 1024);

            SocketClient client = new SocketClient(host, port);

            String title = String.join(" ", args);

            client.send(title)
                    .receive()
                    .ifPresent(System.out::print);

            client.close();
        } catch (NumberFormatException | ClientException e) {
            System.err.println(e.getMessage());
        }

    }

}
