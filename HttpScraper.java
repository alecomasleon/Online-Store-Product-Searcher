package item_search;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public abstract class HttpScraper extends Scraper{
    protected String websiteLink;
    protected String classToExtract;
    protected String keyToExtract;

    protected HttpClient client;

    public HttpScraper(){
        client = HttpClient.newBuilder()
                .proxy(ProxySelector.getDefault())
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
    }
    public String[] scrape(String searchString) throws Exception{
        HttpRequest request = createRequest(searchString);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //System.out.println(response.uri());
        //System.out.println(response.body());
        Document doc = Jsoup.parse(response.body());
        //System.out.println(doc);

        Elements products = doc.getElementsByClass(classToExtract);
        //System.out.println(products.size());

        ArrayList<String> items = new ArrayList<>();
        for (Element e: products) {
            if(e.attributes().hasKey(keyToExtract)) {
                String attr = e.attr(keyToExtract);
                items.add(websiteLink + attr);
            }
        }

        return items.toArray(new String[0]);
    }
    public HttpRequest createRequest(String searchString) throws Exception{
        String url = makeURL(searchString);

        return HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0")
                //.header("Accept-Encoding", "gzip, deflate")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .header("DNT", "1")
                //.header("Connection", "close")
                .header("Upgrade-Insecure-Requests", "1")
                .version(HttpClient.Version.HTTP_2)
                .GET()
                .build();
    }
}
