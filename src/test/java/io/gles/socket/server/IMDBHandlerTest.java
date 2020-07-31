package io.gles.socket.server;

import io.gles.imdb.IMDBException;
import io.gles.imdb.IMDBSearch;
import io.gles.socket.protocol.TextProtocol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import static io.gles.socket.protocol.TextConstants.CLOSE_COMMAND;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IMDBHandlerTest {

    private transient Socket socket;

    private transient IMDBHandler imdbHandler;

    @BeforeEach
    public void setUpMockito() throws IOException {
        this.socket = mock(Socket.class);
        when(socket.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));
        when(socket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        imdbHandler = new IMDBHandler(this.socket, new TextProtocol());
    }

    @Test
    public void shouldReturnAction() {
        assertEquals(ActionEnum.CLOSE, imdbHandler.action(CLOSE_COMMAND));
        assertEquals(ActionEnum.UNKNOWN, imdbHandler.action("Title"));
        assertEquals(ActionEnum.SEARCH, imdbHandler.action("5:Title"));
    }

    @Test
    public void shouldReturnSearchActionWithTitles() throws IMDBException {

        TextProtocol textProtocol = new TextProtocol();
        List<String> titles = List.of("Title", "Title2");

        IMDBSearch imdbSearch = mock(IMDBSearch.class);
        when(imdbSearch.byTitle(anyString())).thenReturn(titles);

        assertEquals(textProtocol.payload(titles), imdbHandler.searchAction(imdbSearch, textProtocol.query("Title")));

    }

    @Test
    public void shouldReturnSearchActionWithNotFound() throws IMDBException {

        TextProtocol textProtocol = new TextProtocol();
        List<String> titles = List.of();

        IMDBSearch imdbSearch = mock(IMDBSearch.class);
        when(imdbSearch.byTitle(anyString())).thenReturn(titles);

        assertEquals(textProtocol.info("Nothing found."), imdbHandler.searchAction(imdbSearch, textProtocol.query("Other title")));

    }

    @Test
    public void shouldReturnSearchActionWithError() throws IMDBException {

        TextProtocol textProtocol = new TextProtocol();

        IMDBSearch imdbSearch = mock(IMDBSearch.class);
        when(imdbSearch.byTitle(anyString())).thenThrow(new IMDBException("IMDB Exception", null));

        assertEquals(textProtocol.error("IMDB Exception"), imdbHandler.searchAction(imdbSearch, textProtocol.query("Other title")));

    }

    @Test
    public void shouldActionClose() throws IMDBException {

        assertDoesNotThrow(() -> {
            imdbHandler.closeAction();
        });

    }

}
