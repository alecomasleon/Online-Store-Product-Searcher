package item_search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class FullScrapeHelper {
    private final Hashtable<String, Scraper> websites;
    static final int defaultMaxItems = 10;

    public FullScrapeHelper(int maxItemsPerWebsite){
        websites = new Hashtable<>();

        Scraper amazon = new AmazonScraper(maxItemsPerWebsite);
        websites.put(amazon.getName(), amazon);

        Scraper walmart = new WalmartScraper(maxItemsPerWebsite);
        websites.put(walmart.getName(), walmart);

        Scraper costco = new CostcoScraper(maxItemsPerWebsite);
        websites.put(costco.getName(), costco);

        Scraper indigo = new IndigoScraper(maxItemsPerWebsite);
        websites.put(indigo.getName(), indigo);
    }
    public FullScrapeHelper(){
        this(defaultMaxItems);
    }
    public Product[] searchAll(String searchString){
        return searchCertain(searchString, websites.keySet().toArray(new String[0]));
    }

    public Product[] search(String searchString, String websiteName){
        if(websites.containsKey(websiteName)){
            try {
                return websites.get(websiteName).scrape(searchString);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return new Product[0];
    }
    public Product[] searchCertain(String searchString, String[] websitesToSearch){
        ArrayList<Product> items = new ArrayList<>();

        for (String websiteName: websitesToSearch) {
            items.addAll(Arrays.asList(search(searchString, websiteName)));
        }
        return items.toArray(new Product[0]);
    }
    public void setMaxItemsPerWebsite(int maxItems){
        for (Scraper s: websites.values()) {
            s.setMaxItemsPerWebsite(maxItems);
        }
    }
    public void setMaxItemsToDefault(){
        setMaxItemsPerWebsite(defaultMaxItems);
    }
}
