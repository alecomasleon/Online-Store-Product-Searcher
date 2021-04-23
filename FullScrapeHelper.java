package item_search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class FullScrapeHelper {
    private final Hashtable<String, Scraper> websites;

    public FullScrapeHelper(){
        websites = new Hashtable<>();

        Scraper amazon = new AmazonScraper();
        websites.put(amazon.getName(), amazon);

        Scraper walmart = new WalmartScraper();
        websites.put(walmart.getName(), walmart);

        Scraper costco = new CostcoScraper();
        websites.put(costco.getName(), costco);

        Scraper indigo = new IndigoScraper();
        websites.put(indigo.getName(), indigo);
    }
    public String[] searchAll(String searchString){
        ArrayList<String> items = new ArrayList<>();

        for (String websiteName: websites.keySet()) {
            items.addAll(Arrays.asList(search(searchString, websiteName)));
        }
        return items.toArray(new String[0]);
    }

    public String[] search(String searchString, String websiteName){
        if(websites.containsKey(websiteName)){
            try {
                return websites.get(websiteName).scrape(searchString);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return new String[0];
    }
}
