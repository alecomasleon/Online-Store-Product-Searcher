package item_search;

import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class HttpScraper extends Scraper{
    protected HttpClient client;

    public HttpScraper(int maxItemsPerWebsite){
        super(maxItemsPerWebsite);
        createClient();
    }
    public HttpScraper(){
        super();
        createClient();
    }
    public Product[] scrape(String searchString) throws Exception{
        HttpRequest request = createRequest(searchString);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //System.out.println(response.uri());
        //System.out.println(response.body());
        return extractElements(response.body());
    }
    public HttpRequest createRequest(String searchString) throws Exception{
        String url = makeURL(searchString);

        return HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .header("DNT", "1")
                .header("Upgrade-Insecure-Requests", "1")
                .version(HttpClient.Version.HTTP_2)
                .GET()
                .build();
    }
    public void createClient(){
        client = HttpClient.newBuilder()
                .proxy(ProxySelector.getDefault())
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
    }
}
