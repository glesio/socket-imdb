package io.gles.socket.client;

import io.gles.socket.protocol.TextException;
import io.gles.socket.protocol.TextPayload;
import io.gles.socket.protocol.TextProtocol;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class SocketClient {

    private transient Socket socket;

    private transient TextProtocol textProtocol = new TextProtocol();

    private transient boolean sendQuery = false;

    public SocketClient(String host, int port) throws ClientException {
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            throw new ClientException("Fail on open connection", e.getCause());
        }
    }

    // Resources closing with socket
    @SuppressWarnings("PMD.CloseResource")
    public SocketClient send(String message) throws ClientException {
        try {
            PrintStream printStream = new PrintStream(socket.getOutputStream());
            printStream.println(textProtocol.query(message));
            this.sendQuery = true;
        } catch (IOException e) {
            throw new ClientException("Error to send message", e.getCause());
        }
        return this;
    }

    // Resources closing with socket and dataflow is expected
    @SuppressWarnings({"PMD.CloseResource", "PMD.DataflowAnomalyAnalysis"})
    public Optional<String> receive() throws ClientException {
        try {

            if (!this.sendQuery)
                throw new ClientException("Error: To receive some data is need to send a query");

            Scanner reader = new Scanner(socket.getInputStream());
            TextPayload textPayload = null;
            while (reader.hasNextLine()) {
                String line = reader.nextLine();

                if (textProtocol.isQuery(line)) {
                    textPayload = new TextPayload(line);
                } else if (Objects.nonNull(textPayload)) {
                    textPayload.add(line);

                    if (textPayload.isFilled()) {
                        this.sendQuery = false;
                        return Optional.of(textPayload.getPayload());
                    }
                }
            }

        } catch (IOException | TextException e) {
            throw new ClientException("Error to receive payload", e.getCause());
        }

        return Optional.empty();
    }

    public void close() throws ClientException {
        try {
            if (socket != null && !socket.isClosed())
                socket.close();
        } catch (IOException e) {
            throw new ClientException("Fail on close connection", e.getCause());
        }
    }
}
