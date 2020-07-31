package io.gles.imdb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class IMDBSearchTest {

    private transient IMDBSearch imdbSearchMock;

    @BeforeEach
    public void setUpMockito() throws IMDBException {
        this.imdbSearchMock = mock(IMDBSearch.class);
        when(imdbSearchMock.byTitle(anyString())).thenReturn(List.of("Matrix", "Matrix Revolution"));
    }

    @Test
    public void shouldGetSomeTitles() throws IMDBException {

        when(imdbSearchMock.byTitle(anyString())).thenReturn(List.of(anyString()));
        List<String> titles = this.imdbSearchMock.byTitle("flash");
        assertNotNull(titles);
        assertTrue(titles.size() > 0);

    }

    @Test
    public void shouldDoesNotExecption() {
        assertDoesNotThrow(() -> {
            this.imdbSearchMock.byTitle("Start Wars");
        });
    }

    @Test
    public void shouldThrowExecption() {
        assertThrows(IMDBException.class, () -> {
            when(this.imdbSearchMock.byTitle(anyString())).thenThrow(IMDBException.class);
            this.imdbSearchMock.byTitle("Start Wars");
        });
    }

}
