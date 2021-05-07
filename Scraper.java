package item_search;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public abstract class Scraper {
    protected String name;
    protected String websiteLinkToSearch;
    protected String websiteLink;

    protected int maxItemsPerWebsite;

    public Scraper(){
        this.maxItemsPerWebsite = 10;
    }
    public Scraper(int maxItemsPerWebsite){
        this.maxItemsPerWebsite = maxItemsPerWebsite;
    }

    public String getName(){return name;}

    public int getMaxItemsPerWebsite(){return maxItemsPerWebsite;}
    public void setMaxItemsPerWebsite(int maxItemsPerWebsite){this.maxItemsPerWebsite = maxItemsPerWebsite;}

    public abstract Product[] scrape(String searchString) throws Exception;

    public String makeURL(String searchString){
        String newString = searchString.toLowerCase(Locale.ROOT).replace(' ', '+');
        String query = URLEncoder.encode(newString, StandardCharsets.UTF_8);
        return this.websiteLinkToSearch + query;
    }
    public Product[] extractElements(String html){
        Document doc = Jsoup.parse(html);
        //System.out.println(doc);

        Product[] products = this.getProductsFromDoc(doc);
        /*for (Product i: products) {
            System.out.println(i.toString());
        }*/

        return products;
    }
    public static float toFloat(String price){
        StringBuilder newPrice = new StringBuilder(price);
        deleteChar(newPrice, "$");
        deleteChar(newPrice, ",");
        return Float.parseFloat(newPrice.toString());
    }
    private static void deleteChar(StringBuilder builder, String character){
        int index = builder.indexOf(character);
        if(index != -1) {
            builder.deleteCharAt(index);
        }
    }

    public abstract Product[] getProductsFromDoc(Document doc);
}