package item_search;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.logging.Handler;

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

        Scraper google = new GoogleScraper(maxItemsPerWebsite);
        websites.put(google.getName(), google);

        Scraper bestBuy = new BestBuyScraper(maxItemsPerWebsite);
        websites.put(bestBuy.getName(), bestBuy);

        Scraper ebay = new EBayScraper(maxItemsPerWebsite);
        websites.put(ebay.getName(), ebay);

        Scraper homedepot = new HomeDepotScraper(maxItemsPerWebsite);
        websites.put(homedepot.getName(), homedepot);
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
        long start = System.currentTimeMillis();

        ArrayList<Product> items = new ArrayList<>();
        ArrayList<Thread> threads = new ArrayList<>();
        ArrayList<SearchRunnable> runnables = new ArrayList<>();
        Thread thread;

        for (String websiteName: websitesToSearch) {
            SearchRunnable run = new SearchRunnable(searchString, websiteName);
            thread = new Thread(run);
            runnables.add(run);
            threads.add(thread);
            thread.start();
        }

        while(threads.size() > 0) {
            for (int i = 0; i < threads.size(); i++) {
                if (!threads.get(i).isAlive()) {
                    items.addAll(Arrays.asList(runnables.get(i).getProducts()));
                    threads.remove(i);
                    runnables.remove(i);
                    break;
                }
            }
        }
        Product[] array = items.toArray(new Product[0]);
        Arrays.sort(array, new SortProducts());

        long end = System.currentTimeMillis();
        System.out.println(end-start);
        return array;
    }
    public void setMaxItemsPerWebsite(int maxItems){
        for (Scraper s: websites.values()) {
            s.setMaxItemsPerWebsite(maxItems);
        }
    }
    public void setMaxItemsToDefault(){
        setMaxItemsPerWebsite(defaultMaxItems);
    }

    private class SearchRunnable implements Runnable{
        private Product[] products;
        private String searchString;
        private String websiteName;

        public SearchRunnable(){}
        public SearchRunnable(String searchString, String websiteName){
            this.searchString = searchString;
            this.websiteName = websiteName;
        }

        @Override
        public void run() {
            products = search(searchString, websiteName);
        }

        public void setSearchString(String searchString){ this.searchString = searchString; }
        public String getSearchString(){ return searchString; }

        public void setWebsiteName(String websiteName){ this.websiteName = websiteName; }
        public String getWebsiteName(){ return websiteName; }

        public Product[] getProducts(){return products;}
    }
}
