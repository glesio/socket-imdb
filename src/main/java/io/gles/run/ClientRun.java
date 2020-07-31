package io.gles.run;

import io.gles.socket.client.ClientException;
import io.gles.socket.client.SocketClient;

import java.util.Scanner;

import static io.gles.run.SystemUtil.getProperty;
import static io.gles.socket.protocol.TextConstants.CLOSE_COMMAND;

public class ClientRun {

    public static void main(String[] args) {

        try (Scanner keyboard = new Scanner(System.in);) {

            String host = getProperty("host", "localhost");
            Integer port = getProperty("port", 1024);

            @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
            SocketClient client = new SocketClient(host, port);

            String TYPE = "Type a movie title and press <enter> to search: ";
            System.out.print(TYPE);

            while (keyboard.hasNextLine()) {
                String input = keyboard.nextLine();
                if (input.isEmpty()) {
                    System.out.print(TYPE);
                } else {
                    if (CLOSE_COMMAND.equals(input)) {
                        client.close();
                        return;
                    } else {
                        client.send(input)
                                .receive()
                                .ifPresent(payload -> {
                                    System.out.print(payload);
                                    System.out.println("---");
                                    System.out.print(TYPE);
                                });
                    }
                }
            }

        } catch (NumberFormatException | ClientException e) {
            System.err.println(e.getMessage());
        }

    }

}
