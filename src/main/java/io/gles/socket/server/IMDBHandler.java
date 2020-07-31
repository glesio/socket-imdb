package io.gles.socket.server;

import io.gles.imdb.IMDBException;
import io.gles.imdb.IMDBSearch;
import io.gles.socket.protocol.TextProtocol;
import io.gles.socket.protocol.TextQuery;
import io.gles.socket.protocol.TextException;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import static io.gles.socket.protocol.TextConstants.CLOSE_COMMAND;
import static io.gles.socket.server.ActionEnum.*;
import static io.gles.socket.protocol.TextProtocol.isQuery;

public class IMDBHandler implements Runnable {

    private final transient Socket socket;
    private final transient TextProtocol textProtocol;

    public IMDBHandler(Socket socket, TextProtocol textProtocol) {
        this.socket = socket;
        this.textProtocol = textProtocol;
    }

    @Override public void run() {

        try (Scanner scanner = new Scanner(socket.getInputStream());
                PrintStream printStream = new PrintStream(socket.getOutputStream())) {

            @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
            IMDBSearch imdbSearch = new IMDBSearch();

            while (scanner.hasNextLine()) {

                String input = scanner.nextLine();
                switch (action(input)) {

                    case CLOSE:
                        closeAction();
                        break;

                    case SEARCH:
                        printStream.println(searchAction(imdbSearch, input));
                        break;

                    default:
                    case UNKNOWN:
                        printStream.println(textProtocol.info("The protocol is text:\n* To search send to: '<query length>:<query>'\n* To close connection send to ':close'"));
                        break;
                }

            }

        } catch (IOException e) {
            Logger.getLogger(e.getMessage());
        }

    }

    protected void closeAction() throws IOException {
        this.socket.close();
    }

    protected String searchAction(IMDBSearch imdbSearch, String input) {
        try {
            TextQuery query = new TextQuery(input);
            List<String> titles = imdbSearch.byTitle(query.getQuery());
            if (titles.isEmpty()) {
                return textProtocol.info("Nothing found.");
            } else {
                return textProtocol.payload(titles);
            }

        } catch (TextException | IMDBException e) {
            return textProtocol.error(e.getMessage());
        }
    }

    protected ActionEnum action(String input) {
        if (CLOSE_COMMAND.equals(input)) {
            return ActionEnum.CLOSE;
        } else if (isQuery(input)) {
            return SEARCH;
        }

        return UNKNOWN;
    }

}
