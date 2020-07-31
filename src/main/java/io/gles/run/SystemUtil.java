package io.gles.run;

import io.gles.socket.client.ClientException;
import io.gles.socket.client.SocketClient;
import io.gles.socket.server.ServerException;
import io.gles.socket.server.SocketServer;

import java.util.Objects;

public class SystemUtil {

    public static String getProperty(String property, String valueDefault) {
        return Objects.nonNull(System.getProperty(property)) ? System.getProperty(property) : valueDefault;
    }

    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public static Integer getProperty(String property, Integer valueDefault) throws NumberFormatException {

        Integer value = valueDefault;
        if (Objects.nonNull(System.getProperty(property))) {
            try {
                value = Integer.valueOf(System.getProperty(property));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("The property 'port' is not a number");
            }
        }

        return value;
    }

}
