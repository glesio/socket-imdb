package io.gles.socket.protocol;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TextQueryTest {

    @Test
    public void shouldCreateTextQuery() throws TextException {

        assertDoesNotThrow(() ->
            new TextPayload("6:Matrix")
        );

        TextQuery textQuery = new TextQuery("6:Matrix");
        assertEquals(6, textQuery.getLength());
        assertEquals("Matrix", textQuery.getQuery());
    }

    @Test
    public void shouldNotCreateTextPayload() {

        assertThrows(TextException.class, () -> {
            new TextQuery("Some Query");
        });

        assertThrows(TextException.class, () -> {
            new TextQuery("100:Some Query");
        });

    }

    @Test
    public void shouldReturnLengthAndQuery() throws TextException {
        TextQuery textQuery = new TextQuery("6:Matrix");
        assertEquals(6, textQuery.getLength());
        assertEquals("Matrix", textQuery.getQuery());
    }


}
