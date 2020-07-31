package io.gles.socket.protocol;

import static io.gles.socket.protocol.TextConstants.LINE_SEPARATOR;
import static io.gles.socket.protocol.TextConstants.QUERY_SEPARATOR;
import static io.gles.socket.protocol.TextProtocol.isQuery;

public class TextPayload {

    private transient int length;

    private transient StringBuilder payload;

    public TextPayload(final String data) throws TextException {

        if (!isQuery(data)) {
            throw new TextException("Invalid header Payload.");
        }

        String[] datas = data.split(QUERY_SEPARATOR);
        this.length = Integer.parseInt(datas[0]);
        this.payload = new StringBuilder(datas[1])
                .append(LINE_SEPARATOR);

    }

    public int getLength() {
        return length;
    }

    public boolean add(String data) {
        boolean payloadLength = (this.length != this.payload.length());

        if (payloadLength) {
            this.payload.append(data)
                    .append(LINE_SEPARATOR);
        }
        return payloadLength;
    }

    public String getPayload() {
        return payload.toString();
    }

    public boolean isFilled() {
        return this.length == payload.length();
    }
}
