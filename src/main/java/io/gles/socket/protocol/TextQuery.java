package io.gles.socket.protocol;

import static io.gles.socket.protocol.TextProtocol.isQuery;

public class TextQuery {

    private transient int length;
    private transient String query;

    public TextQuery(final String data) throws TextException {

        if (!isQuery(data)) {
            throw new TextException("Query invalid.");
        }

        String[] datas = data.split(TextConstants.QUERY_SEPARATOR);
        this.length = Integer.parseInt(datas[0]);
        this.query = datas[1];

        if (this.length != this.query.length()) {
            throw new TextException("Query length is different of query received.");
        }
    }

    public int getLength() {
        return length;
    }

    public String getQuery() {
        return query;
    }
}
