package io.gles.socket.protocol;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TextProtocolTest {

    private final transient TextProtocol textProtocol = new TextProtocol();

    @Test
    public void shouldReturnQuery() {
        assertEquals("6:Matrix", textProtocol.query("Matrix"));
        assertEquals("9:Star Wars", textProtocol.query("Star Wars"));
    }

    @Test
    public void shouldReturnPayload() {
        assertEquals("13:Transformer\n", textProtocol.payload(List.of("Transformers")));
        assertEquals("25:Matrix\nMatrix Revolution\n", textProtocol.payload(List.of("Matrix", "Matrix Revolution")));
    }

    @Test
    public void shouldReturnInfo() {
        assertEquals("15:INFO=Not found.", textProtocol.info("Not found."));
        assertEquals("41:INFO=Invalid command\nSend another command", textProtocol.info("Invalid command\nSend another command"));
    }

    @Test
    public void shouldReturnError() {
        assertEquals("20:ERROR=Unknown error.", textProtocol.error("Unknown error."));
        assertEquals("32:ERROR=Some error\nCause of error.", textProtocol.error("Some error\nCause of error."));
    }

    @Test
    public void shouldValidQuery() {

        assertTrue(TextProtocol.isQuery("5:Matrix"));
        assertFalse(TextProtocol.isQuery("Transformers"));

        assertTrue(TextProtocol.isQuery("17:Matrix Revolution"));
        assertFalse(TextProtocol.isQuery("Matrix Revolution"));
    }

}
