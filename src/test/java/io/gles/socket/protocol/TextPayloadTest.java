package io.gles.socket.protocol;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TextPayloadTest {

    private final transient String SOME_PAYLOAD = "1000:Some Payload";

    @Test
    public void shouldCreateTextPayload() throws TextException {

        assertDoesNotThrow(() ->
            new TextPayload(SOME_PAYLOAD)
        );

        TextPayload textPayload = new TextPayload(SOME_PAYLOAD);
        assertEquals(1000, textPayload.getLength());
        assertEquals("Some Payload\n", textPayload.getPayload());
    }

    @Test
    public void shouldNotCreateTextPayload() {

        assertThrows(TextException.class, () -> {
            new TextPayload("Some Payload");
        });

    }

    @Test
    public void shouldAddValueToTextPayload() throws TextException {

        TextPayload textPayload = new TextPayload(SOME_PAYLOAD);
        textPayload.add("Another value");
        assertEquals("Some Payload\nAnother value\n", textPayload.getPayload());
    }

    @Test
    public void shouldFillTextPayload() throws TextException {
        TextPayload textPayload = new TextPayload("22:Payload");
        textPayload.add("Another value");

        assertEquals("Payload\nAnother value\n", textPayload.getPayload());
        assertTrue(textPayload.isFilled());
    }

}
