package io.gles.imdb;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public class IMDBSearch {

//    private static final String URL = "https://www.imdb.com/find";
    private static final String URL = "https://www.imdb.com/search/title/";
    public List<String> byTitle(String title) throws IMDBException {
        try {
            Document html = Jsoup.connect(String.format("%s?view=simple&count=50&title=%s", URL, title)).get();

            return html.select(".col-title .lister-item-header")
                    .eachText();

        } catch (IOException e) {
            throw new IMDBException("Fail to search titles", e.getCause());
        }
    }

}
